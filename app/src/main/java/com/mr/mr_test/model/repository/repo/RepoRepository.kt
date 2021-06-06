package com.mr.mr_test.model.repository.repo

import com.mr.mr_test.domain.repo.Repo
import com.mr.mr_test.model.net.LinkData
import io.reactivex.rxjava3.core.Single

interface RepoRepository {
    fun getReposList(since: Int): Single<LinkData<List<Repo>>>
}