package com.mr.mr_test.mapper.owner

import com.mr.mr_test.domain.owner.Owner
import com.mr.mr_test.mapper.Mapper
import com.mr.mr_test.model.net.response.owner.OwnerResponse
import javax.inject.Inject

class OwnerResponseToDomainMapper @Inject constructor() : Mapper<OwnerResponse, Owner>() {
    override fun map(source: OwnerResponse) = Owner(
        name = source.name.orEmpty(),
        avatarUrl = source.avatarUrl
    )
}