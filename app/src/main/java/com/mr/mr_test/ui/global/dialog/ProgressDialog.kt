package com.mr.mr_test.ui.global.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.mr.mr_test.R


class ProgressDialog : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.ProgressDialogStyle)
        isCancelable = false
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return ProgressBar(context).apply {
            indeterminateDrawable.setTint(ContextCompat.getColor(context, R.color.white))
        }
    }
}
