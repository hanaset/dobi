package com.rufree.dobi.api.rest.support

import com.rufree.dobi.api.rest.dto.response.PageResponse
import com.rufree.dobi.api.rest.dto.response.Response
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import java.nio.charset.StandardCharsets

val MEDIA_TYPE_APPLICATION_JSON_UTF8 = MediaType("application", "json", StandardCharsets.UTF_8)
const val MEDIA_TYPE_APPLICATION_JSON_UTF8_VALUE = "application/json;charset=UTF-8"

abstract class RestSupport {

    protected open fun <T> response(data: T): ResponseEntity<*> {
        return ResponseEntity
            .ok()
            .contentType(MEDIA_TYPE_APPLICATION_JSON_UTF8)
            .body(data)
    }

    protected open fun <T> responseV2(data: T): ResponseEntity<*> {
        return ResponseEntity
            .ok()
            .contentType(MEDIA_TYPE_APPLICATION_JSON_UTF8)
            .body(
                Response(data = data)
            )
    }

    protected open fun voidResponse(): ResponseEntity<Void> {
        return ResponseEntity(HttpStatus.OK)
    }

    protected open fun <T> pageResponse(page: Page<T>): ResponseEntity<*> {
        return ResponseEntity
            .ok()
            .contentType(MEDIA_TYPE_APPLICATION_JSON_UTF8)
            .body(
                PageResponse(
                    data = page.content,
                    page = page.number,
                    size = page.size,
                    totalPages = page.totalPages,
                    totalElements = page.totalElements,
                    firstPage = page.isFirst,
                    lastPage = page.isLast
                )
            )
    }
}