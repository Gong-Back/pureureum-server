package gongback.pureureumserver.scheduler.culturalevent

import gongback.pureureumserver.domain.culturalevent.CulturalEvent
import gongback.pureureumserver.domain.culturalevent.CulturalEventRepository
import gongback.pureureumserver.domain.culturalevent.existsByCulturalEventId
import gongback.pureureumserver.service.CulturalEventClient
import gongback.pureureumserver.service.LockTemplate
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
    @Transactional
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
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

                culturalEvents.culturalEventDtos
                    .filter { culturalEventRepository.existsByCulturalEventId(it.culturalEventId).not() }
                    .forEach {
                        log.info("[syncCulturalEventToDatabase] save cultural event to database: ${it.culturalEventId}")
                        culturalEventRepository.save(CulturalEvent(it.toCulturalEventInformation()))
                    }

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
}
