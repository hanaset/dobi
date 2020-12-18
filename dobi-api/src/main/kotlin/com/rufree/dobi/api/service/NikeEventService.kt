package com.rufree.dobi.api.service

import com.rufree.dobi.api.constants.TelegramChat
import com.rufree.dobi.api.service.telegram.TelegramService
import com.rufree.dobi.common.entity.EventItemEntity
import com.rufree.dobi.common.entity.enums.EventItemStatus
import com.rufree.dobi.common.repository.EventItemRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
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