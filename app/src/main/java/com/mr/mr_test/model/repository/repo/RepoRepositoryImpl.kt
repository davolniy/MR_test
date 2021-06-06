package com.mr.mr_test.model.repository.repo

import com.mr.mr_test.domain.repo.Repo
import com.mr.mr_test.mapper.repo.RepoResponseToDomainMapper
import com.mr.mr_test.model.net.LinkData
import com.mr.mr_test.model.net.PageLinks
import com.mr.mr_test.model.net.service.RepoService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RepoRepositoryImpl @Inject constructor(
    private val repoService: RepoService,
    private val repoResponseToDomainMapper: RepoResponseToDomainMapper
) : RepoRepository {

    override fun getReposList(since: Int): Single<LinkData<List<Repo>>> =
        repoService
            .getReposList(since)
            .map {
                val data = repoResponseToDomainMapper.map(it.body().orEmpty())
                val linkHeader = it.headers()["Link"]
                var nextId: Int? = 0

                if (!linkHeader.isNullOrEmpty()) {
                    nextId = PageLinks(linkHeader).next
                }

                return@map LinkData(data, nextId ?: 0)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}