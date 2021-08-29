package com.rufree.dobi.api.service

import com.gargoylesoftware.htmlunit.html.HtmlAnchor
import com.gargoylesoftware.htmlunit.html.HtmlButton
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput
import com.gargoylesoftware.htmlunit.html.HtmlInput
import com.gargoylesoftware.htmlunit.html.HtmlItalic
import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput
import com.gargoylesoftware.htmlunit.html.HtmlSelect
import com.gargoylesoftware.htmlunit.html.HtmlSpan
import com.gargoylesoftware.htmlunit.html.HtmlTextInput
import com.gargoylesoftware.htmlunit.javascript.host.event.Event
import com.rufree.dobi.api.client.NikeWebClientFactory
import com.rufree.dobi.common.entity.EventHistoryEntity
import com.rufree.dobi.common.entity.enums.EventHistoryStatus
import com.rufree.dobi.common.entity.enums.EventItemStatus
import com.rufree.dobi.common.repository.EventHistoryRepository
import com.rufree.dobi.common.repository.EventItemRepository
import com.rufree.dobi.common.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class NikeApplyService(
    private val eventItemRepository: EventItemRepository,
    private val userRepository: UserRepository,
    private val eventHistoryRepository: EventHistoryRepository
) {

    private val baseUrl = "https://www.nike.com/kr"

    fun apply() {
        val eventItemList = eventItemRepository.findByStatus(EventItemStatus.APPLYING)
        val userList = userRepository.findByEnable()
        eventItemList.forEach { item ->
            val url = item.url.replace(baseUrl, "")
            userList.filter { user ->
                eventHistoryRepository.findByUserIdAndEventIdAndStatus(userId = user.id, eventId = item.id, status = EventHistoryStatus.APPLIED) == null
            }.
            forEach { user ->
                applyPersonal(user.email, user.password, url)
                eventHistoryRepository.save(EventHistoryEntity(userId = user.id, eventId = item.id, status = EventHistoryStatus.APPLIED))
            }
        }
    }

    fun applyPersonal(id: String, password: String, successUrl: String) {
        val webClient = NikeWebClientFactory.getWebClient()
        val page: HtmlPage = webClient.getPage("${baseUrl}/ko_kr/login?successUrl=${successUrl}")

        val idInput: HtmlTextInput = page.getFirstByXPath("//*[@id=\"j_username\"]")
        val pwdInput: HtmlPasswordInput = page.getFirstByXPath("//*[@id=\"j_password\"]")
        val loginButton: HtmlButton =
            page.getFirstByXPath("/html/body/section/section/div[2]/div/div[2]/div/div[2]/div/button")

        idInput.text = id
        pwdInput.text = password

        val productPage: HtmlPage = loginButton.click()

        // TODO 테스트 필요
        val checkBox: HtmlInput = productPage.getFirstByXPath("//*[@id=\"infoAgree\"]")
        checkBox.isChecked = true

        val sizeInput: HtmlInput =
            productPage.getFirstByXPath("/html/body/div[1]/div/div[1]/div[2]/div[1]/section/div[2]/aside/div[2]/div/div/div/div/form/div/input")
        val sizeList: HtmlSelect = productPage.getFirstByXPath("//*[@id=\"selectSize\"]")

        // Option 선택
        val option = sizeList.options.find { it.getAttribute("data-value") == "265" || it.getAttribute("data-value") == "240" } ?: sizeList.getOption(3)

        // 선택한 옵션으로 Form 데이터 채움
        sizeInput.setAttribute("value", option.getAttribute("data-skuid"))

        val selectedPage: HtmlPage = sizeList.setSelectedAttribute(option, true)

        val button: HtmlAnchor = selectedPage.getFirstByXPath("//*[@id=\"btn-buy\"]")
        val appliedPage: HtmlPage = button.click()

        webClient.close()
    }

    fun test2(id: String, password: String, successUrl: String) {

        val webClient = NikeWebClientFactory.getWebClient()
        val page: HtmlPage = webClient.getPage("${baseUrl}/ko_kr/login?successUrl=${successUrl}")

        val idInput: HtmlTextInput = page.getFirstByXPath("//*[@id=\"j_username\"]")
        val pwdInput: HtmlPasswordInput = page.getFirstByXPath("//*[@id=\"j_password\"]")
        val loginButton: HtmlButton =
            page.getFirstByXPath("/html/body/section/section/div[2]/div/div[2]/div/div[2]/div/button")

        idInput.text = id
        pwdInput.text = password

        val myPage: HtmlPage = loginButton.click()

//        val agreeCheckBox: HtmlSpan = myPage.getFirstByXPath("/html/body/section/section/article[2]/div/div[2]/div/div/div[1]/div/div/form/div[5]/div/span")
//        val myPage2: HtmlPage = agreeCheckBox.click()
        val checkBox: HtmlInput = myPage.getFirstByXPath("//*[@id=\"receiveEmail\"]")
        checkBox.isChecked = false
        val button: HtmlButton = myPage.getFirstByXPath("/html/body/section/section/article[2]/div/div[2]/div/div/div[3]/div/button")
        val appliedPage: HtmlPage = button.click()

        println(appliedPage.asXml())
        // 응모 버튼 클릭 추가
        webClient.close()

    }

    fun test(id: String, password: String, successUrl: String) {

        val webClient = NikeWebClientFactory.getWebClient()
        val page: HtmlPage = webClient.getPage("${baseUrl}/ko_kr/login?successUrl=${successUrl}")

        val idInput: HtmlTextInput = page.getFirstByXPath("//*[@id=\"j_username\"]")
        val pwdInput: HtmlPasswordInput = page.getFirstByXPath("//*[@id=\"j_password\"]")
        val loginButton: HtmlButton =
            page.getFirstByXPath("/html/body/section/section/div[2]/div/div[2]/div/div[2]/div/button")

        idInput.text = id
        pwdInput.text = password

        val productPage: HtmlPage = loginButton.click()

        val sizeInput: HtmlInput =
            productPage.getFirstByXPath("/html/body/div[1]/div/div[1]/div[2]/div[1]/section/div[2]/aside/div[2]/div/div/div/div/form/div/input")
        sizeInput.setAttribute("value", "235")
        val sizeList: HtmlSelect = productPage.getFirstByXPath("//*[@id=\"selectSize\"]")
        val options = sizeList.options
        val selectedPage: HtmlPage = sizeList.setSelectedAttribute(options[2], true)

        val button: HtmlAnchor = selectedPage.getFirstByXPath("//*[@id=\"btn-buy\"]")
        val appliedPage: HtmlPage = button.click()

        println(appliedPage.asXml())
        // 응모 버튼 클릭 추가
        webClient.close()
    }
}