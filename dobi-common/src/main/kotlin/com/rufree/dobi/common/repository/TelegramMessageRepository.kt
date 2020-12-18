package com.rufree.dobi.common.repository

import com.rufree.dobi.common.entity.TelegramMessageEntity
import com.rufree.dobi.common.entity.enums.MsgStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TelegramMessageRepository : JpaRepository<TelegramMessageEntity, Long> {

    fun findByStatus(status: MsgStatus = MsgStatus.READY): List<TelegramMessageEntity>
}