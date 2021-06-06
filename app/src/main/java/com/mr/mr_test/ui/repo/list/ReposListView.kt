package com.mr.mr_test.ui.repo.list

import com.mr.mr_test.domain.repo.Repo
import com.mr.mr_test.presentation.repo.list.RepoWrapper
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.SkipStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ReposListView : MvpView {
    fun showEmptyProgress(show: Boolean)
    fun showEmptyView(show: Boolean)
    fun showData(show: Boolean, data: List<RepoWrapper>)
    fun updateItem(item: RepoWrapper, position: Int)
    fun showRefreshProgress(show: Boolean)
    fun showPageProgress(show: Boolean)
}