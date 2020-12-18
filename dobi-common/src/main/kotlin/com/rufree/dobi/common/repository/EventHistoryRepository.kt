package com.rufree.dobi.common.repository

import com.rufree.dobi.common.entity.EventHistoryEntity
import com.rufree.dobi.common.entity.enums.EventHistoryStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventHistoryRepository: JpaRepository<EventHistoryEntity, Long> {

    fun findByUserIdAndEventIdAndStatus(userId: Long, eventId: Long, status: EventHistoryStatus): EventHistoryEntity?
}