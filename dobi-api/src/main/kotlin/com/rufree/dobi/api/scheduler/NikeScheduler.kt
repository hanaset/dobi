package com.rufree.dobi.api.scheduler

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
    private val nikeEventService: NikeEventService
) {

    private val logger = LoggerFactory.getLogger(this::class.java)

    // 1시간에 한번씩 상품 파싱
    @Scheduled(initialDelay = 1000 * 3, fixedDelay = 1000 * 60 * 60)
    fun parsing() {
        logger.debug("========== Nike parsing() Start ====================")
        nikeEventParseService.parseEventItem()
        logger.debug("========== Nike parsing() End ====================")
    }

    // 1분에 한번씩 이벤트 시작에 대해 확인
    @Scheduled(initialDelay = 1000 * 5, fixedDelay = 1000 * 60)
    fun checkApplying() {
        logger.debug("========== Nike checkApplying() Start ====================")
        nikeEventService.checkApplying()
        logger.debug("========== Nike checkApplying() End ====================")
    }

    // 1분에 한번씩 이벤트 시작에 대해 확인
    @Scheduled(initialDelay = 1000 * 7, fixedDelay = 1000 * 60)
    fun checkComplete() {
        logger.debug("========== Nike checkComplete() Start ====================")
        nikeEventService.checkComplete()
        logger.debug("========== Nike checkComplete() End ====================")
    }
}