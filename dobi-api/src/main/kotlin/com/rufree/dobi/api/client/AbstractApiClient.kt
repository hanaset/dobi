package com.rufree.dobi.api.client

import com.rufree.dobi.api.exception.ApiClientException
import org.slf4j.Logger
import retrofit2.Call

abstract class AbstractApiClient {

    protected fun <T> isSuccessful(call: Call<T>, logger: Logger, message: String): T {
        try {
            val res = call.execute()

            if (!res.isSuccessful || res.body() == null) {
                logger.error("$message ::: ${res.errorBody()?.byteStream()}")
                throw ApiClientException()
            }

            return res.body()!!
        } catch (e: Exception) {
            throw ApiClientException()
        }
    }
}