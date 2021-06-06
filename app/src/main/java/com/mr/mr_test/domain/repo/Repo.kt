package com.mr.mr_test.domain.repo

import com.mr.mr_test.domain.owner.Owner

data class Repo(
    val id: Long,
    val name: String,
    val owner: Owner?,
    val description: String,
    val repoUrl: String
)