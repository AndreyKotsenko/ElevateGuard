package com.akotsenko.elevateguard.sources.base

import com.akotsenko.elevateguard.sources.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

open class BaseRetrofitSource {

    val retrofit: Retrofit = RetrofitClient.instance
}