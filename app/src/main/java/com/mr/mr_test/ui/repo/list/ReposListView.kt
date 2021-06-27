package com.mr.mr_test.ui.repo.list

import com.mr.mr_test.presentation.repo.list.RepoWrapper
import moxy.MvpView
import moxy.viewstate.strategy.*

@StateStrategyType(AddToEndSingleStrategy::class)
interface ReposListView : MvpView {
    fun showEmptyProgress(show: Boolean)
    @StateStrategyType(SingleStateStrategy::class)
    fun showEmptyView(show: Boolean)
    fun showData(show: Boolean, data: List<RepoWrapper>)
    fun showRefreshProgress(show: Boolean)
    fun showPageProgress(show: Boolean)
}