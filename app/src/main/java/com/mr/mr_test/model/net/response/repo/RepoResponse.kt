package com.mr.mr_test.model.net.response.repo

import com.google.gson.annotations.SerializedName
import com.mr.mr_test.model.net.response.owner.OwnerResponse

data class RepoResponse(
    @SerializedName("id") val id: Long?,
    @SerializedName("name") val name: String?,
    @SerializedName("owner") val owner: OwnerResponse?,
    @SerializedName("description") val description: String?,
    @SerializedName("html_url") val repoUrl: String?
)