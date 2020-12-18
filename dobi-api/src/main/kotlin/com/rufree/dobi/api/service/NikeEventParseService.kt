package com.rufree.dobi.api.service

import com.rufree.dobi.api.client.NikeJsoupClient
import com.rufree.dobi.common.entity.EventItemEntity
import com.rufree.dobi.common.entity.enums.EventItemStatus
import com.rufree.dobi.common.repository.EventItemRepository
import org.jsoup.nodes.Element
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Service
class NikeEventParseService(
    private val nikeJsoupClient: NikeJsoupClient,
    private val eventItemRepository: EventItemRepository
) {

    private val logger = LoggerFactory.getLogger(NikeEventParseService::class.java)

    fun parseEventItem() {

        val elements = nikeJsoupClient.itemParse()
        val eventItems = elements.mapNotNull { makeEventItem(it) }.filter { validEventItem(it) }

        eventItemRepository.saveAll(eventItems)
        logger.info("${eventItems.size}개를 파싱하였습니다.")
    }

    private fun validEventItem(eventItemEntity: EventItemEntity): Boolean {
        return !eventItemRepository.existsByUrlAndStatus(eventItemEntity.url, EventItemStatus.COMING_SOON)
    }


    private fun makeEventItem(element: Element): EventItemEntity? {
        val date = LocalDateTime.parse(element.attr("data-active-date"), DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"))
        val subElement = element.selectFirst("[class=card-link d-sm-b comingsoon]")
        val name = subElement.attr("title")
        val url = "https://www.nike.com${subElement.attr("href")}"
        val image = element.selectFirst("img").attr("data-src")
        val txtSubject = try {
            element.selectFirst("[class=figcaption-content]").text()
        } catch (e: NullPointerException) {
            return null
        }

        val time = when {
            txtSubject.contains("오전") -> {
                LocalTime.parse(txtSubject.substring(3, 8))
            }
            txtSubject.contains("오후") -> {
                LocalTime.parse(txtSubject.substring(3, 8)).plusHours(12)
            }
            else -> {
                logger.warn("NikeClient::makeEventItem ==> txtSubject Warning $txtSubject")
                return null
            }
        }

        val applyDate = LocalDateTime.of(date.toLocalDate(), time)

        return EventItemEntity(
            applyDate = applyDate,
            winnerDate = date,
            url = url,
            image = image,
            name = name
        )
    }
}