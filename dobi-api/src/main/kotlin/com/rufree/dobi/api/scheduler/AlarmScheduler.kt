package com.rufree.dobi.api.scheduler

import com.rufree.dobi.api.service.telegram.TelegramService
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(prefix = "dobi.alarm.scheduler", name = ["enabled"], havingValue = "true")
class AlarmScheduler(
    private val telegramService: TelegramService
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Scheduled(initialDelay = 1000 * 3, fixedDelay = 1000 * 3)
    fun sendMessage() {
        logger.debug("========== sendMessage() Start ====================")
        telegramService.sendMessage()
        logger.debug("========== sendMessage() End ====================")
    }

}