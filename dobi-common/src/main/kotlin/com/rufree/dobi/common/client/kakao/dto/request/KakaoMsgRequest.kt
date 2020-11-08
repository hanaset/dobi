package com.rufree.dobi.common.client.kakao.dto.request

import com.google.gson.annotations.SerializedName

data class KakaoMsgRequest(
        @SerializedName("object_type")
        val objectType: String = "feed",
        val content: KakaoContent,
        val social: KakaoSocial,
        val buttons: List<KakaoButton>

)

data class KakaoContent(
        val title: String,
        val description: String,
        @SerializedName("image_url")
        val imageUrl: String,
        @SerializedName("image_width")
        val imageWidth: Long,
        @SerializedName("image_height")
        val imageHeight: Long,
        val link: KakaoMsgLink
)

data class KakaoButton(
        val title: String,
        val link: KakaoMsgLink
)

data class KakaoMsgLink(
        @SerializedName("web_url")
        val webUrl: String,
        @SerializedName("mobile_web_url")
        val mobileWebUrl: String,
        @SerializedName("android_execution_params")
        val androidExecutionParams: String = "",
        @SerializedName("ios_execution_params")
        val iosExecutionParams: String = ""
)

data class KakaoSocial(
        @SerializedName("like_count")
        val likeCount: Long = 0,
        @SerializedName("comment_count")
        val commentCount: Long = 0,
        @SerializedName("shared_count")
        val sharedCount: Long = 0,
        @SerializedName("view_count")
        val viewCount: Long = 0,
        @SerializedName("subscriber_count")
        val subscriberCount: Long = 0
)