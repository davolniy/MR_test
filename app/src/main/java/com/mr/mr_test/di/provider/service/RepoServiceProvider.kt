package com.mr.mr_test.di.provider.service

import com.mr.mr_test.model.net.service.RepoService
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Provider

class RepoServiceProvider @Inject constructor(
    private val retrofit: Retrofit
) : Provider<RepoService> {

    override fun get(): RepoService = retrofit.create(RepoService::class.java)
}