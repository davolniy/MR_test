package com.mr.mr_test.ui.global

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.mr.mr_test.R
import com.mr.mr_test.extension.visible
import com.mr.mr_test.ui.global.dialog.ProgressDialog
import moxy.MvpAppCompatFragment

abstract class BaseFragment : MvpAppCompatFragment() {

    companion object {
        private const val PROGRESS_TAG = "bf_progress"
    }

    @get:LayoutRes
    protected abstract val layoutRes: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutRes, container, false)

    open fun onBackPressed() {}

    protected fun showProgressDialog(progress: Boolean) {
        val fragment = childFragmentManager.findFragmentByTag(PROGRESS_TAG)
        if (fragment != null && !progress) {
            (fragment as ProgressDialog).dismissAllowingStateLoss()
            childFragmentManager.executePendingTransactions()
        } else if (fragment == null && progress) {
            ProgressDialog().show(childFragmentManager, PROGRESS_TAG)
            childFragmentManager.executePendingTransactions()
        }
    }
}