package com.mr.mr_test.ui.global.list

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.mr.mr_test.R
import com.mr.mr_test.extension.inflate
import com.mr.mr_test.extension.setTextOrGone
import com.mr.mr_test.extension.visible
import com.mr.mr_test.presentation.repo.list.RepoWrapper
import kotlinx.android.synthetic.main.item_repo.view.*

class RepoItemAdapterDelegate(
    private val onHeaderClick: (Long) -> Unit,
    private val onDetailButtonClick: (String, String) -> Unit
) : AdapterDelegate<MutableList<RepoWrapper>>() {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
        = ViewHolder(parent.inflate(R.layout.item_repo))

    override fun isForViewType(items: MutableList<RepoWrapper>, position: Int): Boolean {
        return true
    }

    override fun onBindViewHolder(
        items: MutableList<RepoWrapper>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) = (holder as ViewHolder).bind(items[position])

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        (holder as ViewHolder).isItemBound = false
    }

    private inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private lateinit var item: RepoWrapper

        private var isExpanded = false
        var isItemBound = false

        init {
            with(view) {
                repoHeader.setOnClickListener {
                    onHeaderClick.invoke(item.repo.id)
                }
                repoDetailButton.setOnClickListener {
                    onDetailButtonClick.invoke(item.repo.name, item.repo.repoUrl)
                }
            }
        }

        fun bind(item: RepoWrapper) {
            this.item = item
            val repo = item.repo

            val expandedChanged = isItemBound
                    && isExpanded != item.isExpanded

            isItemBound = true
            isExpanded = item.isExpanded

            with(itemView) {
                expandArrow.run {
                    val arrowRotation = if (item.isExpanded) 180f else 0f
                    if (expandedChanged) {
                        animate().rotation(arrowRotation).start()
                    } else {
                        this.rotation = arrowRotation
                    }
                }
                repoId.text = String.format(context.getString(R.string.repo_id_title), repo.id)
                repoName.text = repo.name
                repoOwnerName.text = String.format(context.getString(R.string.repo_owner_title), repo.owner?.name)
                repoOwnerAvatar.run {
                    Glide
                        .with(context)
                        .load(repo.owner?.avatarUrl)
                        .placeholder(R.drawable.ic_baseline_avatar_placeholder_24)
                        .into(this)
                }
                repoDescriptionContainer.run {
                    if (expandedChanged) {
                        val transition = Slide(Gravity.TOP).apply {
                            duration = 250
                            addTarget(repoDescriptionContainer)
                        }
                        TransitionManager.beginDelayedTransition(itemView.repoLayout, transition)
                    }

                    visible(item.isExpanded) {
                        repoDescription.setTextOrGone(repo.description)
                    }
                }
            }
        }
    }
}