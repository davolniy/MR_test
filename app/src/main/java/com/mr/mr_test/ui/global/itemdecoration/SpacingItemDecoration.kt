package com.mr.mr_test.ui.global.itemdecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration

class SpacingItemDecorationBuilder() {
    private var spanCount = 1
    private var spacing = ItemDecorationSpacing()
    private var edges = ItemDecorationSpacing()
    private var orientation = LinearLayoutManager.VERTICAL

    fun setSpanCount(spanCount: Int): SpacingItemDecorationBuilder {
        this.spanCount = spanCount
        return this
    }

    fun setSpacing(spacing: Int): SpacingItemDecorationBuilder {
        this.spacing = ItemDecorationSpacing(spacing)
        return this
    }

    fun setSpacing(
        top: Int = 0,
        right: Int = 0,
        bottom: Int = 0,
        left: Int = 0
    ): SpacingItemDecorationBuilder {
        this.spacing = ItemDecorationSpacing(top = top, right = right, bottom = bottom, left = left)
        return this
    }

    fun setEdges(edges: Int): SpacingItemDecorationBuilder {
        this.edges = ItemDecorationSpacing(edges)
        return this
    }

    fun setEdges(
        top: Int = 0,
        right: Int = 0,
        bottom: Int = 0,
        left: Int = 0
    ): SpacingItemDecorationBuilder {
        this.edges = ItemDecorationSpacing(top = top, right = right, bottom = bottom, left = left)
        return this
    }

    fun setOrientation(orientation: Int): SpacingItemDecorationBuilder {
        this.orientation = orientation
        return this
    }

    fun build(): SpacingItemDecoration {
        return SpacingItemDecoration(
            spanCount = spanCount,
            spacing = spacing,
            edges = edges,
            orientation = orientation
        )
    }
}

class ItemDecorationSpacing(
    val top: Int = 0,
    val right: Int = 0,
    val bottom: Int = 0,
    val left: Int = 0
) {
    constructor(offset: Int = 0) : this(offset, offset, offset, offset)
}

class SpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: ItemDecorationSpacing,
    private val edges: ItemDecorationSpacing = ItemDecorationSpacing(),
    private val orientation: Int = LinearLayoutManager.VERTICAL
) : ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0

        val column = position % spanCount

        var isFirstRow = position < spanCount
        var isLastRow = if (itemCount % spanCount == 0) position >= itemCount - spanCount else position >= itemCount - itemCount % spanCount
        var isFirstInRow = column == 0
        var isLastInRow = column == spanCount - 1

        val spacingTop = spacing.top
        val spacingBottom = spacing.bottom
        val spacingLeft = spacing.left
        val spacingRight = spacing.right

        val edgeTop = edges.top
        val edgeBottom = edges.bottom
        val edgeLeft = edges.left
        val edgeRight = edges.right

        if (orientation == LinearLayoutManager.HORIZONTAL) {
            isFirstRow = column == 0
            isLastRow = column == spanCount - 1
            isFirstInRow = position < spanCount
            isLastInRow = if (itemCount % spanCount == 0) position >= itemCount - spanCount else position >= itemCount - itemCount % spanCount
        }

        if (isFirstRow) {
            if (edgeTop > 0) {
                outRect.top = edgeTop
            }

            if (!isLastRow) {
                outRect.bottom = spacingBottom
            }
        }

        if (isLastRow) {
            if (edgeBottom > 0) {
                outRect.bottom = edgeBottom
            }

            if (!isFirstRow) {
                outRect.top = spacingTop
            }
        }

        if (!isFirstRow && !isLastRow) {
            outRect.top = spacingTop
            outRect.bottom = spacingBottom
        }

        if (isFirstInRow) {
            if (edgeLeft > 0) {
                outRect.left = edgeLeft
            }

            if (!isLastInRow) {
                outRect.right = spacingRight
            }
        }

        if (isLastInRow) {
            if (edgeRight > 0) {
                outRect.right = edgeRight
            }

            if (!isFirstInRow) {
                outRect.left = spacingLeft
            }
        }

        if (!isFirstInRow && !isLastInRow) {
            outRect.left = spacingLeft
            outRect.right = spacingRight
        }
    }
}