package com.rufree.dobi.api.service.discord

import discord4j.core.event.domain.message.MessageCreateEvent

interface Command {
    fun execute(event: MessageCreateEvent)
    fun getResponse(contents: Map<String, String>?): String
    fun getWords(event: MessageCreateEvent): Map<String, String>?
}