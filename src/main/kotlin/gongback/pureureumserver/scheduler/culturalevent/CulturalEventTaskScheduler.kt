package gongback.pureureumserver.scheduler.culturalevent

import gongback.pureureumserver.domain.culturalevent.CulturalEvent
import gongback.pureureumserver.domain.culturalevent.CulturalEventRepository
import gongback.pureureumserver.domain.culturalevent.existsByCulturalEventId
import gongback.pureureumserver.service.CulturalEventClient
import gongback.pureureumserver.service.LockService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private const val DEFAULT_START_INDEX = 1L
private const val DEFAULT_INCREASE_VALUE = 10L
private val log = LoggerFactory.getLogger(CulturalEventTaskScheduler::class.java)

private const val DEFAULT_CULTURAL_EVENT_LOCK_KEY = "syncCulturalEventToDatabase"

@Service
class CulturalEventTaskScheduler(
    private val culturalEventClient: CulturalEventClient,
    private val culturalEventRepository: CulturalEventRepository,
    private val lockService: LockService,
) {
    @Transactional
    @Scheduled(cron = "0 59 19 * * *", zone = "Asia/Seoul")
    fun syncCulturalEventToDatabase() {
        log.info("[syncCulturalEventToDatabase] start sync cultural event to database")
        if (!lockService.lock(DEFAULT_CULTURAL_EVENT_LOCK_KEY)) {
            log.info("[syncCulturalEventToDatabase] already running")
            return
        }

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
        } finally {
            log.info("[syncCulturalEventToDatabase] unlock")
            lockService.unlock(DEFAULT_CULTURAL_EVENT_LOCK_KEY)
        }
    }
}
