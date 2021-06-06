package com.mr.mr_test.interactor.repo

import com.mr.mr_test.model.repository.repo.RepoRepository
import javax.inject.Inject

class RepoInteractor @Inject constructor(
    private val repoRepository: RepoRepository
) {

    fun getReposList(since: Int) = repoRepository.getReposList(since)
}