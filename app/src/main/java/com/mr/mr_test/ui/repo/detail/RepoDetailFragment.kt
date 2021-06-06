package com.mr.mr_test.ui.repo.detail

import android.os.Bundle
import com.mr.mr_test.R
import com.mr.mr_test.di.Scopes
import com.mr.mr_test.presentation.repo.detail.RepoDetailInstanceParams
import com.mr.mr_test.presentation.repo.detail.RepoDetailPresenter
import com.mr.mr_test.ui.global.BaseFragment
import kotlinx.android.synthetic.main.appbar.*
import kotlinx.android.synthetic.main.layout_repo_detail.*
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import toothpick.Toothpick

class RepoDetailFragment : BaseFragment(), RepoDetailView {

    companion object {
        private const val ARG_REPO_URL = "arg_repo_url"
        private const val ARG_REPO_NAME = "arg_repo_name"

        fun newInstance(instanceParams: RepoDetailInstanceParams) = RepoDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_REPO_URL, instanceParams.repoUrl)
                putString(ARG_REPO_NAME, instanceParams.repoName)
            }
        }
    }

    @InjectPresenter
    lateinit var presenter: RepoDetailPresenter

    @ProvidePresenter
    fun providePresenter(): RepoDetailPresenter = Toothpick
        .openScopes(Scopes.MAIN_SCOPE, Scopes.REPO_SCOPE)
        .getInstance(RepoDetailPresenter::class.java)

    override val layoutRes = R.layout.layout_repo_detail

    private val repoUrl get() = arguments?.getString(ARG_REPO_URL).orEmpty()
    private val repoName get() = arguments?.getString(ARG_REPO_NAME).orEmpty()

    override fun onCreate(savedInstanceState: Bundle?) {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.MAIN_SCOPE, Scopes.REPO_SCOPE))
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        Toothpick.closeScope(Scopes.REPO_SCOPE)
        super.onDestroy()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        toolbar
            .setAppBarTitle(repoName)
            .addBackAction {
                presenter.onBackPressed()
            }
            .addAction(R.drawable.ic_baseline_share_24) {
                presenter.onShareClick(repoUrl)
            }

        webView.loadUrl(repoUrl)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }
}