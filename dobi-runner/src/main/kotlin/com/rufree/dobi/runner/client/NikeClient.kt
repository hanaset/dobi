package com.rufree.dobi.runner.client

import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class NikeClient {

    private val logger = LoggerFactory.getLogger(NikeClient::class.java)

    fun itemParse(): List<Element> {

        val document = Jsoup.connect("https://www.nike.com/kr/launch/?type=upcoming&activeDate=date-filter:AFTER").get()
        val elements = document.select("div[data-active-date]")

        val eventItem = mutableListOf<Element>()
        // 응모 가능한 요소들만 거르는 작업
        for (element in elements) {
            val text = element.selectFirst("[class=txt-subject]").text()

            if (text.contains("응모 시작")) {
                eventItem.add(element)
            }
        }

        return eventItem
    }
}