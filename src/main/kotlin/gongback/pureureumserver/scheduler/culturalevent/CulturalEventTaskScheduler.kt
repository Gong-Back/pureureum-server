package gongback.pureureumserver.scheduler.culturalevent

import gongback.pureureumserver.domain.culturalevent.CulturalEvent
import gongback.pureureumserver.domain.culturalevent.CulturalEventRepository
import gongback.pureureumserver.domain.culturalevent.existsByCulturalEventId
import gongback.pureureumserver.domain.file.File
import gongback.pureureumserver.service.CulturalEventClient
import gongback.pureureumserver.service.FileClient
import gongback.pureureumserver.service.LockTemplate
import gongback.pureureumserver.service.dto.CulturalEventDto
import gongback.pureureumserver.support.constant.FilePackage
import gongback.pureureumserver.support.domain.FileContentType
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private const val DEFAULT_START_INDEX = 1L
private const val DEFAULT_INCREASE_VALUE = 10L
private const val DEFAULT_CULTURAL_EVENT_LOCK_KEY = "syncCulturalEventToDatabase"
private val log = LoggerFactory.getLogger(CulturalEventTaskScheduler::class.java)
private const val LOG_PREFIX = "[syncCulturalEventToDatabase]"

@Service
class CulturalEventTaskScheduler(
    private val fileClient: FileClient,
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
        log.info("$LOG_PREFIX start sync cultural event to database")
        try {
            lockTemplate.executeWithNoRetry(DEFAULT_CULTURAL_EVENT_LOCK_KEY) {
                culturalEventSync()
            }
        } catch (e: RuntimeException) {
            log.error("$LOG_PREFIX already running")
        }
    }

    private fun culturalEventSync() {
        try {
            log.info("$LOG_PREFIX lock")

            var startIndex = DEFAULT_START_INDEX
            var endIndex = DEFAULT_INCREASE_VALUE
            while (true) {
                val culturalEvents = culturalEventClient.getCulturalEvents(startIndex, endIndex)
                val (existEvents, notExistEvents) = culturalEvents.culturalEventDtos.partition {
                    culturalEventRepository.existsByCulturalEventId(it.culturalEventId)
                }

                // 존재하지 않는 항목 저장
                saveCulturalEvents(notExistEvents)

                // 존재하는 항목 업데이트
                updateCulturalEvents(existEvents)

                if (culturalEvents.listTotalCount <= endIndex) {
                    break
                }

                startIndex += DEFAULT_INCREASE_VALUE
                endIndex += DEFAULT_INCREASE_VALUE
            }
            log.info("$LOG_PREFIX end sync cultural event to database")
        } catch (e: Exception) {
            log.error("$LOG_PREFIX error occurred while sync cultural event to database", e)
        }
    }

    private fun saveCulturalEvents(culturalEvents: List<CulturalEventDto>) {
        culturalEvents.forEach {
            val thumbnail = uploadCulturalEventThumbnail(it)
            saveCulturalEvent(it, thumbnail)
        }
    }

    private fun uploadCulturalEventThumbnail(it: CulturalEventDto): File? {
        log.info("$LOG_PREFIX upload thumbnail image: ${it.culturalEventId}")

        val culturalEvent = culturalEventClient.getCulturalEvent(it.culturalEventId) ?: return null
        val thumbnail = culturalEventClient.getCulturalEventThumbnail(culturalEvent)
        val fileExtension = culturalEvent.fileExtsnNm
        val filePackage = "${FilePackage.COMMON.toLowercase()}/${FilePackage.CULTURAL_EVENT.toLowercase()}"
        val fileKey = "$filePackage/${culturalEvent.fileId}.$fileExtension"

        fileClient.uploadFile(
            fileKey = fileKey,
            fileStream = thumbnail.inputStream(),
            fileSize = thumbnail.size.toLong(),
            fileExtension = culturalEvent.fileExtsnNm,
        )

        return File(
            fileKey = fileKey,
            contentType = FileContentType.fromExtension(fileExtension).contentType,
            originalFileName = culturalEvent.fileId,
        )
    }

    private fun saveCulturalEvent(culturalEventDto: CulturalEventDto, thumbnail: File?) {
        log.info("$LOG_PREFIX save cultural event to database: ${culturalEventDto.culturalEventId}")
        val culturalEvent = CulturalEvent(
            information = culturalEventDto.toCulturalEventInformation(),
        )
        culturalEvent.updateThumbnail(thumbnail)
        culturalEventRepository.save(culturalEvent)
    }

    private fun updateCulturalEvents(culturalEvents: List<CulturalEventDto>) {
        val existCulturalEventIds = culturalEvents.map { it.culturalEventId }
        culturalEventRepository.findByInformationCulturalEventIdIn(existCulturalEventIds)
            .forEach {
                val targetCulturalEvent = culturalEvents.first { dto -> dto.culturalEventId == it.information.culturalEventId }

                log.info("$LOG_PREFIX update thumbnail image: ${it.culturalEventId}")
                fileClient.deleteFile(it.thumbnail.fileKey)
                val thumbnail = uploadCulturalEventThumbnail(targetCulturalEvent)

                log.info("$LOG_PREFIX update cultural event to database: ${it.information.culturalEventId}")
                it.updateInformation(targetCulturalEvent.toCulturalEventInformation())
                it.updateThumbnail(thumbnail)
            }
    }
}
