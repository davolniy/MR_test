package com.mr.mr_test.ui.global.notifier

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.AsyncSubject
import io.reactivex.rxjava3.subjects.PublishSubject

class SystemMessageNotifier {

    private val notifierSubject = PublishSubject.create<String>()

    val notifier: Observable<String> = notifierSubject

    fun send(message: String) = notifierSubject.onNext(message)
}
