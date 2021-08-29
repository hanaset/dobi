package com.rufree.dobi.api.service

import com.rufree.dobi.api.constants.TelegramChat
import com.rufree.dobi.api.service.telegram.TelegramService
import com.rufree.dobi.common.entity.EventItemEntity
import com.rufree.dobi.common.entity.enums.EventItemStatus
import com.rufree.dobi.common.repository.EventItemRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class NikeEventService(
    private val eventItemRepository: EventItemRepository,
    private val telegramService: TelegramService
) {

    private val logger = LoggerFactory.getLogger(NikeEventService::class.java)

    fun checkApplying() {
        val now = LocalDateTime.now()
        val items = eventItemRepository.findByStatus(EventItemStatus.COMING_SOON).filter { it.applyDate.isBefore(now) }
        items.forEach {
            it.status = EventItemStatus.APPLYING

            telegramService.saveMessage(TelegramChat.ALARM_GROUP, makeApplyText(it))
            logger.info("${it.name} 의 상태를 변경합니다.")
        }
        eventItemRepository.saveAll(items)
    }

    fun checkComplete() {
        val now = LocalDateTime.now()
        val items = eventItemRepository.findByStatus(EventItemStatus.APPLYING).filter { it.winnerDate.isBefore(now) }
        items.forEach {
            it.status = EventItemStatus.COMPLETE

            telegramService.saveMessage(TelegramChat.ALARM_GROUP, makeCompleteText(it))
            logger.info("${it.name} 의 상태를 변경합니다.")
        }
        eventItemRepository.saveAll(items)

    }

    fun dailyMorningAlarm() {
        val items = eventItemRepository.findByStatus(EventItemStatus.COMING_SOON)
            .filter { it.applyDate.toLocalDate() == LocalDate.now() }
        val messageList = items.mapIndexed { index, eventItemEntity ->
            "${index + 1}. ${eventItemEntity.name} : ${eventItemEntity.applyDate.format(DateTimeFormatter.ofPattern("HH:mm"))}"
        }
        val message =
            "<b>오늘은 ${items.size}개의 드로우 이벤트가 있습니다.</b>\n" +
            "${messageList.joinToString("\n")}"

        telegramService.saveMessage(TelegramChat.ALARM_GROUP, message)
    }

    fun dailyEveningAlarm() {
        val items = eventItemRepository.findByStatus(EventItemStatus.COMING_SOON)
            .filter { it.applyDate.toLocalDate() == LocalDate.now().plusDays(1) }
        val messageList = items.mapIndexed { index, eventItemEntity ->
            "${index + 1}. ${eventItemEntity.name} : ${eventItemEntity.applyDate.format(DateTimeFormatter.ofPattern("HH:mm"))}"
        }
        val message =
            "<b>내일은 ${items.size}개의 드로우 이벤트가 있습니다.</b>\n" +
            "${messageList.joinToString("\n")}"

        telegramService.saveMessage(TelegramChat.ALARM_GROUP, message)
    }

    private fun makeApplyText(entity: EventItemEntity): String {
        return """
            <b>[${entity.name}]</b>이 현재 응모 진행 중입니다.
            
            * 응모 일시 : ${entity.applyDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))}
            * url : ${entity.url}
        """.trimIndent()
    }

    private fun makeCompleteText(entity: EventItemEntity): String {
        return """
            <b>[${entity.name}]</b> 당첨자 발표가 되었습니다.!!
            어서 빨리 확인해보세요.

            * url : ${entity.url}
        """.trimIndent()
    }
}