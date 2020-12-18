package com.rufree.dobi.api.rest

import com.rufree.dobi.api.client.NikeApiClient
import com.rufree.dobi.api.client.NikeWebClientFactory
import com.rufree.dobi.api.client.NikeJsoupClient
import com.rufree.dobi.api.rest.support.RestSupport
import com.rufree.dobi.api.service.NikeApplyService
import com.rufree.dobi.api.service.NikeEventParseService
import com.rufree.dobi.api.service.NikeEventService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/test")
class TestController(
    private val nikeEventService: NikeEventService,
    private val nikeEventParseService: NikeEventParseService,
    private val nikeApplyService: NikeApplyService
) : RestSupport() {

    @GetMapping("/parse")
    fun parse(): ResponseEntity<*> {
        nikeEventParseService.parseEventItem()
        return response("ok")
    }

    @GetMapping("/checking")
    fun checking(): ResponseEntity<*> {
        nikeEventService.checkApplying()
        return response("ok")
    }

    @GetMapping("/login")
    fun login(
        @RequestParam("id") id: String,
        @RequestParam("pwd") pwd: String
    ): ResponseEntity<*> {

        return response(
            nikeApplyService.applyPersonal(
                id,
                pwd,
                "/launch/t/men/fw/nike-sportswear/DC3041-002/xqti31/nike-overbreak"
            )
        )
    }
}