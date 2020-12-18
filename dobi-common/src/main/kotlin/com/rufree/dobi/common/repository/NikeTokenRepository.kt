package com.rufree.dobi.common.repository

import com.rufree.dobi.common.entity.NikeTokenEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NikeTokenRepository: JpaRepository<NikeTokenEntity, Long> {

    fun findByUid(uid: Long): List<NikeTokenEntity>
}