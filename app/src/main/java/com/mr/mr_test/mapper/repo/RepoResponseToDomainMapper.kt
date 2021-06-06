package com.mr.mr_test.mapper.repo

import com.mr.mr_test.domain.repo.Repo
import com.mr.mr_test.mapper.Mapper
import com.mr.mr_test.mapper.owner.OwnerResponseToDomainMapper
import com.mr.mr_test.model.net.response.repo.RepoResponse
import javax.inject.Inject

class RepoResponseToDomainMapper @Inject constructor(
    private val ownerResponseToDomainMapper: OwnerResponseToDomainMapper
) : Mapper<RepoResponse, Repo>() {
    override fun map(source: RepoResponse) = Repo(
        id = source.id ?: 0L,
        name = source.name.orEmpty(),
        owner =  source.owner?.let { ownerResponseToDomainMapper.map(it) },
        description = source.description.orEmpty(),
        repoUrl = source.repoUrl.orEmpty()
    )
}