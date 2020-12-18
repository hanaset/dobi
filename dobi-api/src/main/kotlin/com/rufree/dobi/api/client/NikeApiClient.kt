package com.rufree.dobi.api.client

import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import javax.annotation.PostConstruct


@Component
class NikeApiClient : AbstractApiClient() {

    private val logger = LoggerFactory.getLogger(this::class.java)

    private lateinit var nikeApiClientService: NikeApiClientService

    @PostConstruct
    fun init() {

        val okHttpClient = OkHttpClient.Builder()
            .followRedirects(true)
            .build()

        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .baseUrl("https://www.nike.com/")
            .build()

        nikeApiClientService = retrofit.create(NikeApiClientService::class.java)
    }


    fun addCart(csrtToken: String, productId: Long): Any {

        val body = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("csrfToken", csrtToken)
            .addFormDataPart("productId", productId.toString())
            .build()

        return isSuccessful(nikeApiClientService.addCart(body), logger, "NikeApiClient::addCart()")
    }

    fun login(): String {
        return isSuccessful(nikeApiClientService.login(), logger, "NikeApiClient::login()")
    }
}

interface NikeApiClientService {
    @Headers("X-Requested-With:XMLHttpRequest")
    @POST("/kr/ko_kr/cart/add")
    fun addCart(@Body body: RequestBody): Call<String>

    @POST("/kr/ko_kr/login_post.htm")
    fun login(): Call<String>

}