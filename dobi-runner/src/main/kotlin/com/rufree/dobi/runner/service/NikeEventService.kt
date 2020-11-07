package com.rufree.dobi.runner.service

import com.rufree.dobi.common.entity.EventItem
import com.rufree.dobi.common.entity.enums.EventItemStatus
import com.rufree.dobi.common.repository.EventItemRepository
import com.rufree.dobi.runner.client.NikeClient
import org.jsoup.nodes.Element
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Service
class NikeEventService(
        private val nikeClient: NikeClient,
        private val eventItemRepository: EventItemRepository
) {

    private val logger = LoggerFactory.getLogger(NikeEventService::class.java)

    fun parseEventItem() {

        val elements = nikeClient.itemParse()
        val eventItems = elements.mapNotNull { makeEventItem(it) }.filter { validEventItem(it) }

        eventItemRepository.saveAll(eventItems)
    }

    private fun validEventItem(eventItem: EventItem): Boolean {
        return !eventItemRepository.existsByUrlAndStatus(eventItem.url, EventItemStatus.COMING_SOON)
    }


    private fun makeEventItem(element: Element): EventItem? {

        val date = LocalDate.parse(element.attr("data-active-date"), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.0"))
        val subElement = element.selectFirst("[class=comingsoon]")
        val name = subElement.attr("title")
        val url = "https://www.nike.com${subElement.attr("href")}"
        val image = element.selectFirst("img").attr("data-src")
        val txtSubject = element.selectFirst("[class=txt-subject]").text()

        val time = when{
            txtSubject.contains("오전") -> {
                LocalTime.parse(txtSubject.substring(3,8))
            }
            txtSubject.contains("오후") -> {
                LocalTime.parse(txtSubject.substring(3,8)).plusHours(12)
            }
            else -> {
                logger.warn("NikeClient::makeEventItem ==> txtSubject Warning $txtSubject")
                return null
            }
        }

        val applyDate = LocalDateTime.of(date, time)

        return EventItem(
                applyDate = applyDate,
                url = url,
                image = image,
                name = name
        )
    }
}