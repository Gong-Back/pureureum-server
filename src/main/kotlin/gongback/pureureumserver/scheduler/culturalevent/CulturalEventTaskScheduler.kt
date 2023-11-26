package gongback.pureureumserver.scheduler.culturalevent

import gongback.pureureumserver.domain.culturalevent.CulturalEvent
import gongback.pureureumserver.domain.culturalevent.CulturalEventRepository
import gongback.pureureumserver.domain.culturalevent.existsByCulturalEventId
import gongback.pureureumserver.service.CulturalEventClient
import gongback.pureureumserver.service.LockTemplate
import gongback.pureureumserver.service.dto.CulturalEventResponse
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private const val DEFAULT_START_INDEX = 1L
private const val DEFAULT_INCREASE_VALUE = 10L
private const val DEFAULT_CULTURAL_EVENT_LOCK_KEY = "syncCulturalEventToDatabase"
private val log = LoggerFactory.getLogger(CulturalEventTaskScheduler::class.java)

@Service
class CulturalEventTaskScheduler(
    private val culturalEventClient: CulturalEventClient,
    private val culturalEventRepository: CulturalEventRepository,
    private val lockTemplate: LockTemplate,
) {
    /**
     * 매일 00:30:00에 실행
     */
    @Transactional
    @Scheduled(cron = "0 30 0 * * *", zone = "Asia/Seoul")
    fun syncCulturalEventToDatabase() {
        log.info("[syncCulturalEventToDatabase] start sync cultural event to database")
        try {
            lockTemplate.executeWithNoRetry(DEFAULT_CULTURAL_EVENT_LOCK_KEY) {
                culturalEventSync()
            }
        } catch (e: RuntimeException) {
            log.error("[syncCulturalEventToDatabase] already running")
        }
    }

    private fun culturalEventSync() {
        try {
            log.info("[syncCulturalEventToDatabase] lock")

            var startIndex = DEFAULT_START_INDEX
            var endIndex = DEFAULT_INCREASE_VALUE
            while (true) {
                val culturalEvents = culturalEventClient.getCulturalEvents(startIndex, endIndex)

                // 존재하지 않는 항목 저장
                saveCulturalEvents(culturalEvents)

                // 존재하는 항목 업데이트
                updateCulturalEvents(culturalEvents)

                if (culturalEvents.listTotalCount <= endIndex) {
                    break
                }

                startIndex += DEFAULT_INCREASE_VALUE
                endIndex += DEFAULT_INCREASE_VALUE
            }
            log.info("[syncCulturalEventToDatabase] end sync cultural event to database")
        } catch (e: Exception) {
            log.error("[syncCulturalEventToDatabase] error occurred while sync cultural event to database", e)
        }
    }

    private fun saveCulturalEvents(culturalEvents: CulturalEventResponse) {
        culturalEvents.culturalEventDtos
            .filter { culturalEventRepository.existsByCulturalEventId(it.culturalEventId).not() }
            .forEach {
                log.info("[syncCulturalEventToDatabase] save cultural event to database: ${it.culturalEventId}")
                culturalEventRepository.save(CulturalEvent(it.toCulturalEventInformation()))
            }
    }

    private fun updateCulturalEvents(culturalEvents: CulturalEventResponse) {
        val existCulturalEventIds = getExistCulturalEventIds(culturalEvents)
        culturalEventRepository.findByInformationCulturalEventIdIn(existCulturalEventIds)
            .forEach {
                log.info("[syncCulturalEventToDatabase] update cultural event to database: ${it.information.culturalEventId}")
                it.updateInformation(
                    culturalEvents.culturalEventDtos.first { dto -> dto.culturalEventId == it.information.culturalEventId }
                        .toCulturalEventInformation(),
                )
            }
    }

    private fun getExistCulturalEventIds(culturalEvents: CulturalEventResponse) = culturalEvents.culturalEventDtos
        .filter { culturalEventRepository.existsByCulturalEventId(it.culturalEventId) }
        .map { it.culturalEventId }
}
