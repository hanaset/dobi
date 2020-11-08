package com.rufree.dobi.api.service

import com.rufree.dobi.common.entity.KakaoToken
import com.rufree.dobi.common.repository.KakaoTokenRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class KakaoTokenService (
        private val kakaoTokenRepository: KakaoTokenRepository
) {

    fun tokenSaveOrUpdate(uid: Long, token: String, refreshToken: String) {
        val kakaoToken = kakaoTokenRepository.findByUid(uid) ?: KakaoToken(uid = uid, token = "", refreshToken = "")
        kakaoToken.apply {
            this.token = token
            this.refreshToken = refreshToken
            this.tokenExpireDate = LocalDateTime.now().plusHours(6)
            this.refreshExpireDate = LocalDateTime.now().plusMonths(1)
        }

        kakaoTokenRepository.save(kakaoToken)
    }
}