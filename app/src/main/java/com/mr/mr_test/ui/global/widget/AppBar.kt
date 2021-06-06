package com.mr.mr_test.ui.global.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.MaterialToolbar
import com.mr.mr_test.R
import com.mr.mr_test.extension.visible
import kotlinx.android.synthetic.main.layout_appbar.view.*

class AppBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : MaterialToolbar(context, attrs, defStyleAttr) {

    companion object {
        enum class ActionContainerSide {
            LEFT,
            RIGHT
        }
    }

    var title: String = ""

    init {
        inflate(context, R.layout.layout_appbar, this)

        val array = context.obtainStyledAttributes(attrs, R.styleable.AppBar)

        title = array.getString(R.styleable.AppBar_title).orEmpty()

        array.recycle()
    }

    fun addBackAction(action: (View) -> Unit = {}): AppBar {
        return addAction(R.drawable.ic_baseline_arrow_back_24, ActionContainerSide.LEFT, action)
    }

    fun addAction(iconRes: Int, actionContainerSide: ActionContainerSide = ActionContainerSide.RIGHT, action: (View) -> Unit = {}): AppBar {
        val actionsContainer = when(actionContainerSide) {
            ActionContainerSide.RIGHT -> {
                rightActionsContainer
            }
            ActionContainerSide.LEFT -> {
                leftActionsContainer
            }
        }

        val view = LayoutInflater.from(context).inflate(R.layout.appbar_action_item, actionsContainer, false).apply {
            setOnClickListener { action.invoke(this) }

            findViewById<ImageView>(R.id.actionIcon).run {
                val iconDrawable = ContextCompat.getDrawable(context, iconRes)
                val tint = ContextCompat.getColor(context, R.color.white)

                iconDrawable?.setTint(tint)

                setImageDrawable(iconDrawable)
            }
        }

        actionsContainer.visible(true) {
            actionsContainer.addView(view, 0)
        }

        return this
    }

    fun setAppBarTitle(@StringRes titleRes: Int): AppBar {
        appBarTitle.setText(titleRes)

        return this
    }

    fun setAppBarTitle(title: String): AppBar {
        appBarTitle.text = title

        return this
    }
}