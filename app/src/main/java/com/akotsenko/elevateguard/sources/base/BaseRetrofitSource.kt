package com.akotsenko.elevateguard.sources.base

import com.akotsenko.elevateguard.model.AppException
import com.akotsenko.elevateguard.model.BackendException
import com.akotsenko.elevateguard.model.ConnectionException
import com.akotsenko.elevateguard.model.ParseBackendResponseException
import com.akotsenko.elevateguard.sources.RetrofitClient
import com.squareup.moshi.Moshi
import retrofit2.*
import java.io.IOException

open class BaseRetrofitSource {

    val retrofit: Retrofit = RetrofitClient.instance

    private val moshi: Moshi = Moshi.Builder().build()
    private val errorAdapter = moshi.adapter(ErrorResponseBody::class.java)

    suspend fun <T> wrapRetrofitExceptions(block: suspend () -> T): T {
        return try {
            block()
        } catch (e: AppException) {
            throw e
        }  catch (e: HttpException) {
            throw createBackendException(e)
        } catch (e: IOException) {
            throw ConnectionException(e)
        }
    }

    private fun createBackendException(e: HttpException): Exception {
        return try {
            val errorBody: ErrorResponseBody = errorAdapter.fromJson(
                e.response()!!.errorBody()!!.string()
            )!!
            BackendException(e.code(), errorBody.message!!)
        } catch (e: Exception) {
            throw ParseBackendResponseException(e)
        }
    }

    data class ErrorResponseBody(
        val message: String?
    )

}