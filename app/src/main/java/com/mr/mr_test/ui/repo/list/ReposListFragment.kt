package com.mr.mr_test.ui.repo.list

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.mr.mr_test.R
import com.mr.mr_test.di.Scopes
import com.mr.mr_test.extension.doOnOverCome
import com.mr.mr_test.extension.showEmptyLayout
import com.mr.mr_test.extension.visible
import com.mr.mr_test.presentation.repo.list.RepoWrapper
import com.mr.mr_test.presentation.repo.list.ReposListPresenter
import com.mr.mr_test.ui.global.BaseFragment
import com.mr.mr_test.ui.global.itemdecoration.SpacingItemDecorationBuilder
import com.mr.mr_test.ui.global.list.RepoItemAdapterDelegate
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.layout_base_list.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick

class ReposListFragment : BaseFragment(), ReposListView {

    override val layoutRes = R.layout.layout_repos_list

    @InjectPresenter
    lateinit var presenter: ReposListPresenter

    @ProvidePresenter
    fun providePresenter(): ReposListPresenter = Toothpick
        .openScopes(Scopes.MAIN_SCOPE, Scopes.REPOS_LIST_SCOPE)
        .getInstance(ReposListPresenter::class.java)

    private val adapter by lazy { ReposListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.MAIN_SCOPE, Scopes.REPOS_LIST_SCOPE))
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        Toothpick.closeScope(Scopes.REPOS_LIST_SCOPE)
        super.onDestroy()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar.setAppBarTitle(R.string.repo_list_app_bar_title)

        recyclerView.run {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ReposListFragment.adapter

            val offset = context.resources.getDimensionPixelOffset(R.dimen.mediumMargin)
            addItemDecoration(
                SpacingItemDecorationBuilder()
                    .setSpacing(offset / 2)
                    .setEdges(offset)
                    .build()
            )
            itemAnimator = null
        }

        swipeRefreshLayout.setOnRefreshListener { presenter.refresh() }
    }

    override fun showEmptyProgress(show: Boolean) {
        progressLayout.visible(show)

        swipeRefreshLayout.run {
            visible(!show)
            isRefreshing = false
        }
    }

    override fun showEmptyView(show: Boolean) {
        showEmptyLayout(emptyLayout, show) { presenter.refresh() }
    }

    override fun showData(show: Boolean, data: List<RepoWrapper>) {
        adapter.setData(data)
    }

    override fun updateItem(item: RepoWrapper, position: Int) {
        adapter.updateItem(item, position)
    }

    override fun showRefreshProgress(show: Boolean) {
        swipeRefreshLayout.run {
            post {
                isRefreshing = show
            }
        }
    }

    override fun showPageProgress(show: Boolean) {
        showProgressDialog(show)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    private inner class ReposListAdapter : ListDelegationAdapter<MutableList<RepoWrapper>>() {

        init {
            items = mutableListOf()
            delegatesManager.addDelegate(RepoItemAdapterDelegate(
                { repoId ->
                    presenter.onRepoHeaderClick(repoId)
                },
                { repoName, repoUrl ->
                    presenter.onRepoDetailClick(repoName, repoUrl)
                }
            ))
            setHasStableIds(true)
        }

        fun setData(data: List<RepoWrapper>) {
            items.clear()
            items.addAll(data)

            notifyDataSetChanged()
        }

        fun updateItem(item: RepoWrapper, position: Int) {
            items[position] = item

            notifyItemChanged(position)
        }

        override fun getItemId(position: Int): Long = items[position].repo.id

        override fun onBindViewHolder(
            holder: RecyclerView.ViewHolder,
            position: Int,
            payloads: MutableList<Any?>
        ) {
            super.onBindViewHolder(holder, position, payloads)

            position.doOnOverCome(items.size) {
                presenter.loadNextPage()
            }
        }
    }
}