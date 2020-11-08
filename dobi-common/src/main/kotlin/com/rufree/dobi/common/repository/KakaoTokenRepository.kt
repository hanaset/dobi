package com.rufree.dobi.common.repository

import com.rufree.dobi.common.entity.KakaoToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface KakaoTokenRepository: JpaRepository<KakaoToken, Long> {

    fun findByUid(uid: Long): KakaoToken?

    fun findByTokenExpireDateBefore(localDateTime: LocalDateTime): List<KakaoToken>
}