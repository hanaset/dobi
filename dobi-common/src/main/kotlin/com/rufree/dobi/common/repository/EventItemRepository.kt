package com.rufree.dobi.common.repository

import com.rufree.dobi.common.entity.EventItemEntity
import com.rufree.dobi.common.entity.enums.AlarmStatus
import com.rufree.dobi.common.entity.enums.EventItemStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventItemRepository: JpaRepository<EventItemEntity, Long> {

    fun existsByUrlAndStatus(url: String, status: EventItemStatus): Boolean

    fun findByStatus(status: EventItemStatus): List<EventItemEntity>
}