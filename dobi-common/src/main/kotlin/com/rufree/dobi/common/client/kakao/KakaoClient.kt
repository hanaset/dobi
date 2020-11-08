package com.rufree.dobi.common.client.kakao

import com.google.gson.Gson
import com.rufree.dobi.common.client.AbstractClient
import com.rufree.dobi.common.client.kakao.dto.request.KakaoMsgRequest
import com.rufree.dobi.common.client.kakao.dto.response.KakaoUserResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Component
class KakaoClient(
    @Value("\${kakao.url.api}") private val kakaoUrl: String
): AbstractClient() {
    private val logger = LoggerFactory.getLogger(this::class.java)

    private lateinit var kakaoClientService: KakaoClientService

    init {
        val retrofit = Retrofit.Builder()
                .baseUrl(kakaoUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        this.kakaoClientService = retrofit.create(KakaoClientService::class.java)

        logger.info("KakaoClient Init::url=${kakaoUrl}")
    }

    fun getUserInfo(token: String): Response<KakaoUserResponse> {
        val accessToken = "Bearer $token"
        return isSuccessful(kakaoClientService.getUserInfo(accessToken), "KakaoClient::getUserInfo", logger)
    }

    fun sendMessage(token: String, kakaoMsgRequest: KakaoMsgRequest): Response<Any> {
        val accessToken = "Bearer $token"
        val request = Gson().toJson(kakaoMsgRequest)
        return isSuccessful(kakaoClientService.sendMessage(accessToken, request), "KakaoClient::sendMessage", logger)
    }

    fun sendMessage2(token: String, kakaoMsgRequest: KakaoMsgRequest): Response<Any> {
        val accessToken = "Bearer $token"
        return isSuccessful(kakaoClientService.sendMessage2(accessToken, kakaoMsgRequest), "KakaoClient::sendMessage", logger)
    }
}