package com.rufree.dobi.common.entity

import com.rufree.dobi.common.entity.enums.EventHistoryStatus
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "event_history")
class EventHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1,

    @Column(name = "event_id")
    val eventId: Long,

    @Column(name = "user_id")
    val userId: Long,

    @Enumerated(value = EnumType.STRING)
    val status: EventHistoryStatus
): AbstractBaseAuditEntity()