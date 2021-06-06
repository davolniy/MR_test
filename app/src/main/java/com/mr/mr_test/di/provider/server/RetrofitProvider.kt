package com.mr.mr_test.di.provider.server

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Provider

class RetrofitProvider @Inject constructor(
    private val okHttpClient: OkHttpClient,
    private val gson: Gson
) : Provider<Retrofit> {

    override fun get(): Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
}
