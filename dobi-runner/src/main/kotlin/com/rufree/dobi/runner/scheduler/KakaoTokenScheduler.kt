package com.rufree.dobi.runner.scheduler

import com.rufree.dobi.runner.service.KakaoTokenRefreshService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class KakaoTokenScheduler(
    private val kakaoTokenRefreshService: KakaoTokenRefreshService
) {

    @Scheduled(cron = "1 0 */2 * * *", zone = "Asia/Seoul")
    fun refreshToken() {
        kakaoTokenRefreshService.refreshToken()
    }
}