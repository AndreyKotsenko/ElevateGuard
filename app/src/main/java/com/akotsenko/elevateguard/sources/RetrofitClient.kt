package com.akotsenko.elevateguard.sources

import com.akotsenko.elevateguard.Const
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private var retrofit: Retrofit? = null

    class AcceptHeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()

            // Добавляем заголовок Accept к каждому запросу
            val modifiedRequest = originalRequest.newBuilder()
                .header("Accept", "application/json")
                .build()

            return chain.proceed(modifiedRequest)
        }
    }

    val client = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .followRedirects(false)
        .followSslRedirects(false)
        .addInterceptor(AcceptHeaderInterceptor())
        .build()

    private const val BASE_URL = Const.BASE_URL

    val instance: Retrofit
        get() {
            if(retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
}