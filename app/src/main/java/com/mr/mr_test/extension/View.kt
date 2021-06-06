package com.mr.mr_test.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.mr.mr_test.R

fun View.visible(visible: Boolean, actionIfVisible: View.() -> Unit = {}): View {
    this.visibility = if (visible) View.VISIBLE else View.GONE
    if (visible) actionIfVisible.invoke(this)
    return this
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun TextView.setTextOrGone(text: String?): View {
    visible(!text.isNullOrEmpty()) {
        setText(text)
    }
    return this
}

fun Fragment.showEmptyLayout(
    emptyView: View,
    show: Boolean,
    @StringRes emptyTextRes: Int = R.string.not_found,
    @DrawableRes emptyImageRes: Int = R.drawable.ic_baseline_not_found_24,
    onEmptyActionClickListener: (View) -> Unit
) = emptyView.visible(show) {
    findViewById<TextView>(R.id.emptyText).run {
        setText(emptyTextRes)
    }
    findViewById<ImageView>(R.id.emptyImage).run {
        setImageDrawable(ContextCompat.getDrawable(context, emptyImageRes))
    }
    findViewById<Button>(R.id.emptyAction).run {
        setOnClickListener(onEmptyActionClickListener)
    }
}
