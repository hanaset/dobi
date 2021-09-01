package com.rufree.dobi.api.scheduler

import com.rufree.dobi.api.service.NikeApplyService
import com.rufree.dobi.api.service.NikeEventParseService
import com.rufree.dobi.api.service.NikeEventService
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(prefix = "dobi.nike.scheduler", name = ["enabled"], havingValue = "true")
class NikeScheduler(
    private val nikeEventParseService: NikeEventParseService,
    private val nikeEventService: NikeEventService,
    private val nikeApplyService: NikeApplyService
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    // 1시간에 한번씩 상품 파싱
    @Scheduled(initialDelay = 1000 * 3, fixedDelay = 1000 * 60 * 60)
    fun parsing() {
        logger.info("========== Nike parsing() Start ====================")
        nikeEventParseService.parseEventItem()
        logger.info("========== Nike parsing() End ====================")
    }

    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Seoul")
    fun dailyMorningAlarm() {
        logger.info("========== Nike dailyMorningAlarm() Start ====================")
        nikeEventService.dailyMorningAlarm()
        logger.info("========== Nike dailyMorningAlarm() End ====================")
    }

    @Scheduled(cron = "0 0 19 * * *", zone = "Asia/Seoul")
    fun dailyEveningAlarm() {
        logger.info("========== Nike dailyEveningAlarm() Start ====================")
        nikeEventService.dailyEveningAlarm()
        logger.info("========== Nike dailyEveningAlarm() End ====================")
    }

    // 1분에 한번씩 이벤트 시작에 대해 확인
    @Scheduled(cron = "1 * * * * *", zone = "Asia/Seoul")
    fun checkApplying() {
        logger.info("========== Nike checkApplying() Start ====================")
        nikeEventService.checkApplying()
        logger.info("========== Nike checkApplying() End ====================")
    }

    // 1분에 한번씩 이벤트 시작에 대해 확인
    @Scheduled(cron = "1 * * * * *", zone = "Asia/Seoul")
    fun checkComplete() {
        logger.info("========== Nike checkComplete() Start ====================")
        nikeEventService.checkComplete()
        logger.info("========== Nike checkComplete() End ====================")
    }

    // 5분에 한번씩 접수 스케줄러 활성화
    @Scheduled(initialDelay = 1000 * 3, fixedDelay = 1000 * 60 * 5)
    fun apply() {
        logger.debug("========== Nike apply() Start ====================")
        nikeApplyService.apply()
        logger.debug("========== Nike apply() End ====================")
    }
}