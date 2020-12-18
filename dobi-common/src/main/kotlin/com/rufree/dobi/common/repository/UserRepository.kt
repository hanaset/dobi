package com.rufree.dobi.common.repository

import com.rufree.dobi.common.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<UserEntity, Long> {

    fun findByEnable(enable: Boolean = true): List<UserEntity>
}