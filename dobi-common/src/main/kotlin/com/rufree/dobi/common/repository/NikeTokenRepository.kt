package com.rufree.dobi.common.repository

import com.rufree.dobi.common.entity.NikeToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NikeTokenRepository: JpaRepository<NikeToken, Long> {

    fun findByUid(uid: Long): List<NikeToken>
}