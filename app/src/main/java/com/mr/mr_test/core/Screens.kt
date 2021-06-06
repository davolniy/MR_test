package com.mr.mr_test.core

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.mr.mr_test.presentation.repo.detail.RepoDetailInstanceParams
import com.mr.mr_test.ui.repo.detail.RepoDetailFragment
import com.mr.mr_test.ui.repo.list.ReposListFragment

object Screens {

    fun ReposListScreen() = object : FragmentScreen {
        override fun createFragment(factory: FragmentFactory): Fragment {
            return ReposListFragment()
        }
    }

    fun RepoDetailScreen(repoDetailInstanceParams: RepoDetailInstanceParams) =
        object : FragmentScreen {
            override fun createFragment(factory: FragmentFactory): Fragment {
                return RepoDetailFragment.newInstance(repoDetailInstanceParams)
            }
        }

    fun ShareScreen(repoUrl: String) = object : ActivityScreen {
        override fun createIntent(context: Context): Intent {
            return Intent.createChooser(
                Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, repoUrl)
                    type = "text/plain"
                }, null
            )
        }
    }
}