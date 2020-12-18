package com.rufree.dobi.api.rest.dto.response

import java.time.ZonedDateTime

data class PageResponse<T>(
        val data: T,

        val page: Int,

        val size: Int,

        val totalPages: Int,

        val totalElements: Long,

        val firstPage : Boolean,

        val lastPage : Boolean
)

data class Response<T> (
        val code: String = "0",
        val message: String? = null,
        val data: T,
        val timestamp: Long = ZonedDateTime.now().toEpochSecond()
)