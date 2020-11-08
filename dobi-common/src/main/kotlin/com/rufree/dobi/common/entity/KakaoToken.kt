package com.rufree.dobi.common.entity

import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "kakao_token")
class KakaoToken(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = -1,

        val uid: Long,

        var token: String,

        @Column(name = "refresh_token")
        var refreshToken: String,

        @Column(name = "token_expire_date")
        var tokenExpireDate: LocalDateTime = LocalDateTime.now().plusHours(6),

        @Column(name = "refresh_token_expire_date")
        var refreshExpireDate: LocalDateTime = LocalDateTime.now().plusMonths(1)
): AbstractBaseAuditEntity()