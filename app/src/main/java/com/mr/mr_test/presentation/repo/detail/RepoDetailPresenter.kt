package com.mr.mr_test.presentation.repo.detail

import com.github.terrakok.cicerone.Router
import com.mr.mr_test.core.Screens
import com.mr.mr_test.presentation.global.BasePresenter
import com.mr.mr_test.ui.repo.detail.RepoDetailView
import javax.inject.Inject

class RepoDetailPresenter @Inject constructor(
    private val router: Router
) : BasePresenter<RepoDetailView>() {

    fun onShareClick(repoUrl: String) {
        router.navigateTo(Screens.ShareScreen(repoUrl))
    }

    fun onBackPressed() {
        router.exit()
    }
}