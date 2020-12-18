package com.rufree.dobi.api.service.telegram

import com.rufree.dobi.api.constants.TelegramChat
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import javax.annotation.PostConstruct


@ConditionalOnProperty(prefix = "dobi.alarm.scheduler", name = ["enabled"], havingValue = "true")
@Service
class TelegramBotService(
    private val telegramBot: TelegramBot
) {

    private lateinit var bot: TelegramBotsApi

    @PostConstruct
    fun init() {
        this.bot = TelegramBotsApi(DefaultBotSession::class.java)
        this.bot.registerBot(telegramBot)
    }

    fun send(message: String, channelId: String?) {
        telegramBot.sendMessage(message, channelId)
    }

}

@ConditionalOnProperty(prefix = "dobi.alarm.scheduler", name = ["enabled"], havingValue = "true")
@Component
class TelegramBot(
    @Value("\${telegram.token}") private val token: String
): TelegramLongPollingBot() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun getBotToken(): String {
        return token
    }

    override fun getBotUsername(): String {
        return "NikeDrawAlarm"
    }

    override fun onUpdateReceived(update: Update) {

        // 입장 유저에 대한 인사 문구
//        val newUser = update.message.newChatMembers
//        val message = SendMessage()
//        message.enableHtml(true)
//        message.parseMode = "HTML"
//        newUser.forEach {
//            val name = "${it.lastName ?: ""}" + "${it.firstName}"
//            val text = "안녕하세요 <b>$name</b> 님.\n" +
//                    "클레이스왑 실시간 입출금 알람 방입니다.\n\n" +
//                    "실시간 자산 조회 : https://klaywatch.com\n"
//            message.text = text
//            message.chatId = update.message.chatId.toString()
//            execute(message)
//        }
    }

    fun sendMessage(data: String, channelId: String?) {
        val message = SendMessage()
        message.enableHtml(true)
        message.parseMode = "HTML"
        message.chatId = channelId ?: TelegramChat.ALARM_GROUP
        message.text = data
        execute(message)
    }
}