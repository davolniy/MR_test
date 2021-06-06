package com.mr.mr_test.di.module

import com.mr.mr_test.di.provider.server.RetrofitProvider
import com.mr.mr_test.di.provider.service.RepoServiceProvider
import com.mr.mr_test.model.net.service.RepoService
import com.mr.mr_test.model.repository.repo.RepoRepository
import com.mr.mr_test.model.repository.repo.RepoRepositoryImpl
import retrofit2.Retrofit
import toothpick.config.Module

class MainModule : Module() {
    init {
        //Services
        bind(RepoService::class.java).toProvider(RepoServiceProvider::class.java).providesSingleton()

        //Repository
        bind(RepoRepository::class.java).to(RepoRepositoryImpl::class.java).singleton()
    }
}