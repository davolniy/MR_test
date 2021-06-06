package com.mr.mr_test.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.google.android.material.snackbar.Snackbar
import com.mr.mr_test.ui.global.notifier.SystemMessageNotifier
import io.reactivex.rxjava3.disposables.Disposable
import moxy.MvpAppCompatActivity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

abstract class BaseActivity: MvpAppCompatActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var systemMessageNotifier: SystemMessageNotifier

    protected abstract val layoutRes: Int
    protected abstract val navigator: Navigator

    private var messageNotifierDisposable = Disposable.disposed()

    protected open fun showSystemNotification(message: String) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
        messageNotifierDisposable = systemMessageNotifier.notifier
            .throttleFirst(50, TimeUnit.MILLISECONDS)
            .subscribe({
                showSystemNotification(it)
            }, { })
    }

    override fun onPause() {
        messageNotifierDisposable.dispose()
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}