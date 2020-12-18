package com.rufree.dobi.api.exception

open class DobiApiException(
    val code: ErrorCode,
    override val message: String? = null
) : RuntimeException(
        message ?: code.message
)

class DobiApiExistUserException: DobiApiException(ErrorCode.EXIST_USER)
class DobiApiNotFoundUserException: DobiApiException(ErrorCode.NOT_FOUND_USER)
class DiscordException: DobiApiException(ErrorCode.DISCORD_ERROR)
class DiscordBadCommandException: DobiApiException(ErrorCode.DISCORD_BAD_COMMAND)
class ApiClientException: DobiApiException(ErrorCode.API_ERROR)
