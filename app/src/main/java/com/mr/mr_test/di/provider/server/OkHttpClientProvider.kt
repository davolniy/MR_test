package com.mr.mr_test.di.provider.server

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

class OkHttpClientProvider  @Inject constructor(): Provider<OkHttpClient> {
    override fun get(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                return chain.proceed(
                    chain
                        .request()
                        .newBuilder()
                        .addHeader("Accept", "application/vnd.github.v3+json")
                        .build()
                )
            }
        })
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()
}