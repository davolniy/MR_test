package com.mr.mr_test.entry

import com.github.terrakok.cicerone.Router
import com.mr.mr_test.core.Screens
import com.mr.mr_test.presentation.global.BasePresenter
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(
    private val router: Router
) : BasePresenter<MainView>() {

    fun startRoot() = router.newRootScreen(Screens.ReposListScreen())

    fun onBackPressed() = router.finishChain()
}