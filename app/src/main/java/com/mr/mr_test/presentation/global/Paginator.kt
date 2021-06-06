package com.mr.mr_test.presentation.global

import com.mr.mr_test.model.net.LinkData
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable

open class Paginator<T>(
    private val requestFactory: (Int) -> Single<LinkData<List<T>>>,
    private val viewController: ViewController<T>
) {
    private val firstId = 0
    var currentId = 0
    private var requestDisposable: Disposable = Disposable.disposed()
    private var currentState: State<T> = Empty()
    private val currentData = mutableListOf<T>()
    
    fun refresh() {
        currentState.refresh()
    }

    fun loadNewPage() {
        currentState.loadNewPage()
    }

    fun release() {
        currentState = Released()
        requestDisposable.dispose()
    }

    private fun loadPage(page: Int) {
        requestDisposable.dispose()
        requestDisposable = requestFactory.invoke(page)
            .subscribe({
                currentState.newData(it.data, it.nextId)
            }, {
                currentState.fail(it)
            })
    }

    private inner class Empty : State<T> {

        override fun refresh() {
            currentState = EmptyProgress()
            viewController.showEmptyProgress(true)
            loadPage(firstId)
        }
    }

    private inner class EmptyProgress : State<T> {

        override fun newData(data: List<T>, nextId: Int) {
            if (data.isNotEmpty()) {
                currentState = Data()
                currentData.clear()
                currentData.addAll(data)
                currentId = nextId
                viewController.showData(true, currentData)
                viewController.showEmptyProgress(false)
            } else {
                currentState = EmptyData()
                viewController.showData(false)
                viewController.showEmptyProgress(false)
                viewController.showEmptyView(true)
            }
        }

        override fun fail(error: Throwable) {
            currentState = EmptyError()
            viewController.showEmptyProgress(false)
            viewController.showEmptyError(true, error)
            viewController.showData(false)
        }
    }

    private inner class EmptyError : State<T> {

        override fun refresh() {
            currentState = EmptyProgress()
            viewController.showEmptyError(false)
            viewController.showEmptyProgress(true)
            loadPage(firstId)
        }
    }

    private inner class EmptyData : State<T> {

        override fun refresh() {
            currentState = EmptyProgress()
            viewController.showEmptyView(false)
            viewController.showEmptyProgress(true)
            loadPage(firstId)
        }
    }

    private inner class Data : State<T> {

        override fun refresh() {
            currentState = Refresh()
            viewController.showRefreshProgress(true)
            loadPage(firstId)
        }

        override fun loadNewPage() {
            currentState = PageProgress()
            viewController.showPageProgress(true)
            loadPage(currentId)
        }
    }

    private inner class Refresh : State<T> {

        override fun refresh() {
            loadPage(firstId)
        }

        override fun newData(data: List<T>, nextId: Int) {
            if (data.isNotEmpty()) {
                currentState = Data()
                currentData.clear()
                currentData.addAll(data)
                currentId = nextId
                viewController.showRefreshProgress(false)
                viewController.showData(true, currentData)
            } else {
                currentState = EmptyData()
                currentData.clear()
                viewController.showData(false)
                viewController.showRefreshProgress(false)
                viewController.showEmptyView(true)
            }
        }

        override fun fail(error: Throwable) {
            currentState = Data()
            viewController.showRefreshProgress(false)
            viewController.showErrorMessage(error)
        }
    }

    private inner class PageProgress : State<T> {

        override fun refresh() {
            currentState = Refresh()
            viewController.showPageProgress(false)
            viewController.showRefreshProgress(true)
            loadPage(firstId)
        }

        override fun newData(data: List<T>, nextId: Int) {
            if (data.isNotEmpty()) {
                currentState = Data()
                currentData.addAll(data)
                currentId = nextId
                viewController.showPageProgress(false)
                viewController.showData(true, currentData)
            } else {
                currentState = AllData()
                viewController.showPageProgress(false)
            }
        }

        override fun fail(error: Throwable) {
            currentState = Data()
            viewController.showPageProgress(false)
            viewController.showErrorMessage(error)
        }
    }

    private inner class AllData : State<T> {

        override fun refresh() {
            currentState = Refresh()
            viewController.showRefreshProgress(true)
            loadPage(firstId)
        }
    }

    private inner class Released : State<T>

    interface ViewController<T> {
        fun showEmptyProgress(show: Boolean)
        fun showEmptyError(show: Boolean, error: Throwable? = null)
        fun showEmptyView(show: Boolean)
        fun showData(show: Boolean, data: List<T> = emptyList())
        fun showErrorMessage(error: Throwable)
        fun showRefreshProgress(show: Boolean)
        fun showPageProgress(show: Boolean)
    }

    protected interface State<T> {
        fun refresh() {}
        fun loadNewPage() {}
        fun newData(data: List<T>, nextId: Int) {}
        fun fail(error: Throwable) {}
    }
}