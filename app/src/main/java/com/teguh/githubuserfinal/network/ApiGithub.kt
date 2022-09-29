package com.teguh.githubuserfinal.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private var retrofit: Retrofit? = null
private const val BASE_URL = "https://api.github.com/"

private fun getRetrofitInstance(): Retrofit? {
    val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .build()
    if (retrofit == null) {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    return retrofit
}

object GithubApi {
    val retrofitService = getRetrofitInstance()?.create(ApiInterface::class.java)
}