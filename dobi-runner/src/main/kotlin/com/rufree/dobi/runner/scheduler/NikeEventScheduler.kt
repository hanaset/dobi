package com.rufree.dobi.runner.scheduler

import com.rufree.dobi.runner.service.NikeEventService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class NikeEventScheduler(
        private val nikeEventService: NikeEventService
) {

    @Scheduled(cron = "1 * * * * *", zone = "Asia/Seoul")
    fun checkEvent() {
        nikeEventService.checkingEventItem()
    }

    @Scheduled(cron = "1 * * * * *", zone = "Asia/Seoul")
    fun sendEvent() {
        nikeEventService.sendEventItem()
    }
}