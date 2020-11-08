package com.rufree.dobi.common.client.kakao

import com.rufree.dobi.common.client.kakao.dto.request.KakaoMsgRequest
import com.rufree.dobi.common.client.kakao.dto.response.KakaoUserResponse
import retrofit2.Call
import retrofit2.http.*

interface KakaoClientService {

    @GET("/v2/user/me")
    @Headers("content-type: application/x-www-form-urlencoded;charset=utf-8")
    fun getUserInfo(@Header("Authorization") token: String): Call<KakaoUserResponse>

    @POST("/v2/api/talk/memo/default/send")
    @Headers("content-type: application/x-www-form-urlencoded;charset=utf-8")
    fun sendMessage(@Header("Authorization") token: String, @Query("template_object") template: String): Call<Any>

    @POST("/v2/api/talk/memo/default/send")
    @Headers("content-type: application/x-www-form-urlencoded;charset=utf-8")
    fun sendMessage2(@Header("Authorization") token: String, @Query("template_object") template: KakaoMsgRequest): Call<Any>
}