package com.rufree.dobi.runner.scheduler

import com.rufree.dobi.runner.service.NikeEventService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class NikeParseScheduler(
        private val nikeEventService: NikeEventService
) {

    @Scheduled(cron = "1 0 0 * * *", zone = "Asia/Seoul")
    fun eventParsing() {
        nikeEventService.parseEventItem()
    }
}