package com.rufree.dobi.runner.service

import com.rufree.dobi.common.client.kakao.KakaoClient
import com.rufree.dobi.common.client.kakao.dto.request.*
import com.rufree.dobi.common.entity.EventItem
import com.rufree.dobi.common.entity.enums.AlarmStatus
import com.rufree.dobi.common.entity.enums.EventItemStatus
import com.rufree.dobi.common.repository.EventItemRepository
import com.rufree.dobi.common.repository.KakaoTokenRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NikeEventService(
        private val eventItemRepository: EventItemRepository,
        private val kakaoTokenRepository: KakaoTokenRepository,
        private val kakaoClient: KakaoClient
) {

    private val logger = LoggerFactory.getLogger(NikeEventService::class.java)

    fun checkingEventItem() {
        val now = LocalDateTime.now()
        val items = eventItemRepository.findByStatus(EventItemStatus.COMING_SOON).filter { it.applyDate.isBefore(now) }
        items.forEach {
            it.status = EventItemStatus.APPLYING
            logger.info("${it.name} 의 상태를 변경합니다.")
        }
        eventItemRepository.saveAll(items)
    }


    fun sendEventItem() {
        val items = eventItemRepository.findByStatusAndAlarmStatus(EventItemStatus.APPLYING, AlarmStatus.BEFORE)
        items.forEach {
            it.alarmStatus = AlarmStatus.SEND_START
            eventItemRepository.save(it)

            sendMessage(it)

            it.alarmStatus = AlarmStatus.SEND_COMPLETE
            eventItemRepository.save(it)
        }
    }

    fun sendMessage(item: EventItem) {

        val userTokens = kakaoTokenRepository.findAll()

        val request = KakaoMsgRequest(
                content = KakaoContent(
                        title = "${item.name} 응모 시작",
                        description = "${item.name} 이/가 응모를 시작하엿습니다.\n${item.url}",
                        imageUrl = item.image,
                        imageWidth = 400,
                        imageHeight = 400,
                        link = KakaoMsgLink(webUrl = item.url, mobileWebUrl = item.url)
                ),
                social = KakaoSocial(),
                buttons = listOf(KakaoButton(title = "웹으로 이동", link = KakaoMsgLink(webUrl = item.url, mobileWebUrl = item.url)), KakaoButton(title = "앱으로 이동", link = KakaoMsgLink(webUrl = item.url, mobileWebUrl = item.url)))
        )

        userTokens.forEach {
            kakaoClient.sendMessage(it.token, request)
        }

        logger.info("${item.name} 상품을 총 ${userTokens.size}명에게 메세지를 발송하였습니다.")
    }
}