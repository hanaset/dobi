package com.rufree.dobi.runner.service

import com.rufree.dobi.common.client.kakao.KakaoAuthClient
import com.rufree.dobi.common.repository.KakaoTokenRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class KakaoTokenRefreshService(
        private val kakaoAuthClient: KakaoAuthClient,
        private val kakaoTokenRepository: KakaoTokenRepository
) {

    fun refreshToken() {
        val tokens = kakaoTokenRepository.findByTokenExpireDateBefore(LocalDateTime.now())

        tokens.forEach {
            val res = kakaoAuthClient.refreshToken(it.refreshToken).body()

            if(res != null) {
                it.token = res.accessToken
                it.tokenExpireDate = LocalDateTime.now().plusHours(6)

                kakaoTokenRepository.save(it)
            }
        }
    }
}