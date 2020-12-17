package com.rufree.dobi.api.service.discord

import com.rufree.dobi.api.constants.Commands
import discord4j.core.event.domain.message.MessageCreateEvent
import org.springframework.stereotype.Service
import java.lang.Exception
import javax.annotation.PostConstruct

val helpBasicResponse = """
안녕하세요 Hana Bot입니다.

현재 Hana Bot에서 제공하고 있는 기능은 다음과 같습니다.
```
1. 일정 및 참석 여부 관리 => [사용법] !명령어 일정
```

""".trimIndent()

val helpResponse = """
안녕하세요 Hana Bot입니다.
검색하신 명령어는 다음과 같습니다.

{help}
""".trimIndent()

val helpNotFoundResponse = """
안녕하세요 Hana Bot입니다.
입력하신 명령어는 확인하실수 없습니다.

** 명령어 목록 **
```
1. 일정 및 참석 여부 관리 => [사용법] !명령어 일정
2. 유튜브 음악 재생 => [사용법] !명령어 음악
```
""".trimIndent()

@Service
class HelpCommandService(
        private val helpService: HelpService
) : Command {

    @PostConstruct
    fun init() {
        val command = "!명령어"
        Commands.commands[command] = this
    }

    override fun execute(event: MessageCreateEvent) {
        val channel = event.message.channel.block()
        channel?.let {channel ->
            channel.createMessage(getResponse(getWords(event))).block()
        }
    }

    override fun getResponse(contents: Map<String, String>?): String {
        return if(contents == null) {
            helpNotFoundResponse
        } else {
            if(contents["help"] == "") {
                helpBasicResponse
            } else {
                helpResponse.replace("{help}", contents["help"].toString())
            }
        }
    }

    override fun getWords(event: MessageCreateEvent): Map<String, String>? {
        return try {
            val words = event.message.content.split(" ")
            mapOf("help" to helpService.getHelpCommand(words[1].orEmpty()))
        }catch (ex: Exception) {
            null
        }
    }
}