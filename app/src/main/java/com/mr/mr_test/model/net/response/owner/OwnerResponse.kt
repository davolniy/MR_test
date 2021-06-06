package com.mr.mr_test.model.net.response.owner

import com.google.gson.annotations.SerializedName

data class OwnerResponse(
    @SerializedName("login") val name: String?,
    @SerializedName("avatar_url") val avatarUrl: String?
)