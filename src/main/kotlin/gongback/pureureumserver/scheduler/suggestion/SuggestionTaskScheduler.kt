package gongback.pureureumserver.scheduler.suggestion

import gongback.pureureumserver.domain.suggestion.SuggestionRepository
import gongback.pureureumserver.domain.suggestion.SuggestionStatus
import gongback.pureureumserver.service.LockTemplate
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.ZoneId

private const val DEFAULT_TIME_ZONE = "Asia/Seoul"
private const val DEFAULT_SUGGESTION_TASK_LOCK_KEY = "updateSuggestionStatus"
private val log = LoggerFactory.getLogger(SuggestionTaskScheduler::class.java)


@Service
class SuggestionTaskScheduler(
    private val suggestionRepository: SuggestionRepository,
    private val lockTemplate: LockTemplate,
) {
    @Transactional
    @Scheduled(cron = "0 0 0 * * *", zone = DEFAULT_TIME_ZONE)
    fun updateSuggestionStatus() {
        log.info("[updateSuggestionStatus] start update suggestion status")
        try {
            lockTemplate.executeWithNoRetry(DEFAULT_SUGGESTION_TASK_LOCK_KEY) {
                updateSuggestionStatusExecute()
            }
        } catch (e: RuntimeException) {
            log.error("[updateSuggestionStatus] already running")
        }
    }

    private fun updateSuggestionStatusExecute() {
        try {
            log.info("[updateSuggestionStatus] lock")

            val suggestions = suggestionRepository.findByEndDateBeforeThanEqual(LocalDate.now(ZoneId.of(DEFAULT_TIME_ZONE)))
            suggestions.map { it.changeStatus(SuggestionStatus.FINISHED) }
            suggestionRepository.saveAll(suggestions)

            log.info("[updateSuggestionStatus] end update suggestion status")
        } catch (e: Exception) {
            log.error("[updateSuggestionStatus] update suggestion status error", e)
        }
    }
}
