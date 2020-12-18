package com.rufree.dobi.common.entity

import com.rufree.dobi.common.entity.enums.MsgStatus
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "telegram_message")
class TelegramMessageEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = -1,

    @Column(name = "channel_id")
    val channelId: String? = null,

    val message: String,

    @Enumerated(value = EnumType.STRING)
    var status: MsgStatus = MsgStatus.READY

): AbstractBaseAuditEntity()