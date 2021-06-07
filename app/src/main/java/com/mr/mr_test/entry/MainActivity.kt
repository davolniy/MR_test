package com.mr.mr_test.entry

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.Command
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.google.android.material.snackbar.Snackbar
import com.mr.mr_test.R
import com.mr.mr_test.di.Scopes
import com.mr.mr_test.di.module.AppModule
import com.mr.mr_test.di.module.MainModule
import com.mr.mr_test.ui.global.BaseFragment
import com.mr.mr_test.ui.global.routing.AppNavigator
import com.mr.mr_test.ui.main.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick

class MainActivity : BaseActivity(), MainView {

    override val layoutRes = R.layout.activity_main

    override val navigator = object : AppNavigator(this, R.id.mainContainer) {
        override fun setupFragmentTransaction(
            screen: FragmentScreen,
            fragmentTransaction: FragmentTransaction,
            currentFragment: Fragment?,
            nextFragment: Fragment
        ) {
            fragmentTransaction.apply {
                setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
            }
        }

        override fun applyCommands(commands: Array<out Command>) {
            super.applyCommands(commands)
        }
    }

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter = Toothpick
        .openScopes(Scopes.APP_SCOPE, Scopes.MAIN_SCOPE)
        .getInstance(MainPresenter::class.java)

    private val currentFragment
        get() = supportFragmentManager.findFragmentById(R.id.mainContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.MAIN_SCOPE).apply {
            Toothpick.inject(this@MainActivity, this)
        }.installModules(MainModule())
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            presenter.startRoot()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isFinishing)
            Toothpick.closeScope(Scopes.MAIN_SCOPE)
    }

    override fun onBackPressed() {
        when (val currentFragment = currentFragment) {
            is BaseFragment -> currentFragment.onBackPressed()
            else -> presenter.onBackPressed()
        }
    }

    override fun showSystemNotification(message: String) {
        Snackbar.make(mainContainer, message, Snackbar.LENGTH_LONG).show()
    }
}