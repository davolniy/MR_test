package com.mr.mr_test.presentation.global

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpPresenter
import moxy.MvpView

abstract class BasePresenter<V : MvpView> : MvpPresenter<V>() {

    private val compositeDisposable = CompositeDisposable()

    override fun onDestroy() {
        compositeDisposable.dispose()
    }

    fun Disposable.untilDestroy() {
        compositeDisposable.add(this)
    }
}
