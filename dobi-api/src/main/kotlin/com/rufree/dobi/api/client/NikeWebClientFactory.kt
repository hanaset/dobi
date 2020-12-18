package com.rufree.dobi.api.client

import com.gargoylesoftware.htmlunit.BrowserVersion
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController
import com.gargoylesoftware.htmlunit.WebClient

object NikeWebClientFactory {

    fun getWebClient(): WebClient {

        val webClient = WebClient(BrowserVersion.INTERNET_EXPLORER)
        webClient.options.isCssEnabled = false
        webClient.options.isJavaScriptEnabled = true
        webClient.options.isRedirectEnabled = true
        // 조금이라도 더 로딩을 빠르게 하기 위해 이미지 다운로드 false
        webClient.options.isDownloadImages = false
        webClient.options.isThrowExceptionOnFailingStatusCode = false
        webClient.options.isThrowExceptionOnScriptError = false
        webClient.ajaxController = NicelyResynchronizingAjaxController()

        return webClient
    }
}