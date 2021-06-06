package com.mr.mr_test.di.module

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.google.gson.Gson
import com.mr.mr_test.di.provider.server.GsonProvider
import com.mr.mr_test.di.provider.server.OkHttpClientProvider
import com.mr.mr_test.di.provider.server.RetrofitProvider
import com.mr.mr_test.di.provider.service.RepoServiceProvider
import com.mr.mr_test.interactor.repo.RepoInteractor
import com.mr.mr_test.model.net.service.RepoService
import com.mr.mr_test.model.repository.repo.RepoRepositoryImpl
import com.mr.mr_test.ui.global.notifier.SystemMessageNotifier
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import toothpick.config.Module

class AppModule : Module() {
    init {
        //Network
        bind(Gson::class.java).toProvider(GsonProvider::class.java).providesSingleton()
        bind(OkHttpClient::class.java).toProvider(OkHttpClientProvider::class.java).providesSingleton()
        bind(Retrofit::class.java).toProvider(RetrofitProvider::class.java).singleton()

        //Routing
        val cicerone = Cicerone.create(Router())
        bind(Router::class.java).toInstance(cicerone.router)
        bind(NavigatorHolder::class.java).toInstance(cicerone.getNavigatorHolder())

        //Messaging
        bind(SystemMessageNotifier::class.java).toInstance(SystemMessageNotifier())
    }
}