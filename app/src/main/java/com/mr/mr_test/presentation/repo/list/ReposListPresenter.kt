package com.mr.mr_test.presentation.repo.list

import com.github.terrakok.cicerone.Router
import com.mr.mr_test.core.Screens
import com.mr.mr_test.domain.repo.Repo
import com.mr.mr_test.interactor.repo.RepoInteractor
import com.mr.mr_test.presentation.global.BasePresenter
import com.mr.mr_test.presentation.global.Paginator
import com.mr.mr_test.presentation.repo.detail.RepoDetailInstanceParams
import com.mr.mr_test.ui.global.notifier.SystemMessageNotifier
import com.mr.mr_test.ui.repo.list.ReposListView
import moxy.InjectViewState
import javax.inject.Inject

@InjectViewState
class ReposListPresenter @Inject constructor(
    private val repoInteractor: RepoInteractor,
    private val router: Router,
    private val systemMessageNotifier: SystemMessageNotifier
) : BasePresenter<ReposListView>() {

    private var showData = false
    private val defaultExpanded = false
    private var expandedStates = mutableMapOf<Long, Boolean>()
    private var currentData = mutableListOf<Repo>()

    val paginator = Paginator(
        { repoInteractor.getReposList(it) },
        ReposListViewController()
    )

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        refresh()
    }

    override fun onDestroy() {
        super.onDestroy()
        paginator.release()
    }

    fun refresh() {
        paginator.refresh()
    }

    fun loadNextPage() {
        paginator.loadNewPage()
    }

    fun onRepoHeaderClick(repoId: Long) {
        expandedStates[repoId] = !(expandedStates[repoId] ?: defaultExpanded)
        val index = currentData.indexOfFirst { it.id == repoId }
        if (index >= 0) {
            viewState.updateItem(getItemWrapper(currentData[index]), index)
        }
    }

    fun onRepoDetailClick(repoName: String, repoUrl: String) {
        router.navigateTo(
            Screens.RepoDetailScreen(
                RepoDetailInstanceParams(
                    repoName = repoName,
                    repoUrl = repoUrl
                )
            )
        )
    }

    fun onBackPressed() {
        router.exit()
    }

    private fun getItemWrapper(repo: Repo) =
        RepoWrapper(repo, expandedStates[repo.id] ?: defaultExpanded)

    private fun showData() {
        viewState.showData(showData, currentData.map {
            getItemWrapper(it)
        })
    }

    private inner class ReposListViewController : Paginator.ViewController<Repo> {
        override fun showEmptyProgress(show: Boolean) {
            viewState.showEmptyProgress(show)
        }

        override fun showEmptyError(show: Boolean, error: Throwable?) {
            if (error != null) {
                systemMessageNotifier.send(error.message.orEmpty())
            }
            showEmptyView(show)
        }

        override fun showEmptyView(show: Boolean) {
            viewState.showEmptyView(show)
        }

        override fun showData(show: Boolean, data: List<Repo>) {
            showData = show
            currentData.clear()
            currentData.addAll(data)
            showData()
        }

        override fun showErrorMessage(error: Throwable) {
            systemMessageNotifier.send(error.message.orEmpty())
        }

        override fun showRefreshProgress(show: Boolean) {
            viewState.showRefreshProgress(show)
        }

        override fun showPageProgress(show: Boolean) {
            viewState.showPageProgress(show)
        }
    }
}