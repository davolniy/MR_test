package com.mr.mr_test.presentation.repo.list

import com.mr.mr_test.domain.repo.Repo

data class RepoWrapper(
    val repo: Repo,
    val isExpanded: Boolean
)