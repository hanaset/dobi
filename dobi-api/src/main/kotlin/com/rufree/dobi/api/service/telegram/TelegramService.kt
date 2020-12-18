package com.rufree.dobi.api.service.telegram

import com.rufree.dobi.common.entity.TelegramMessageEntity
import com.rufree.dobi.common.entity.enums.MsgStatus
import com.rufree.dobi.common.repository.TelegramMessageRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.telegram.telegrambots.meta.exceptions.TelegramApiException


@Service
@ConditionalOnProperty(prefix = "dobi.alarm.scheduler", name = ["enabled"], havingValue = "true")
class TelegramService(
    private val telegramMessageRepository: TelegramMessageRepository,
    private val telegramBotService: TelegramBotService
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun sendMessage() {
        val messages = telegramMessageRepository.findByStatus(MsgStatus.READY)

        messages.forEach {
            // 먼저 전송 완료로 변경
            it.status = MsgStatus.SENT
            try {
                telegramBotService.send(it.message, it.channelId)
            } catch (ex: TelegramApiException) {
                it.status = MsgStatus.FAILED
            }
            telegramMessageRepository.save(it)
        }

        if (messages.isNotEmpty()) {
            logger.info("Telegram Message : ${messages.size}건 전송하였습니다.")
        }
    }

    fun saveMessage(channelId: String, msg: String) {
        telegramMessageRepository.save(
            TelegramMessageEntity(
                message = msg,
                channelId = channelId.toString()
            )
        )
    }
}