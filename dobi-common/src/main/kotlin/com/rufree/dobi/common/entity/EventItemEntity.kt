package com.rufree.dobi.common.entity

import com.rufree.dobi.common.entity.enums.AlarmStatus
import com.rufree.dobi.common.entity.enums.EventItemStatus
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "event_item")
class EventItemEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = -1,

        val name: String,

        val url: String,

        val image: String,

        @Enumerated(value = EnumType.STRING)
        var status: EventItemStatus = EventItemStatus.COMING_SOON,

        @Column(name = "apply_date")
        val applyDate: LocalDateTime,

        @Column(name = "winner_date")
        val winnerDate: LocalDateTime

): AbstractBaseAuditEntity()