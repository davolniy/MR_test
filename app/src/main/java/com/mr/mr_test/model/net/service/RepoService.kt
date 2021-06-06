package com.mr.mr_test.model.net.service

import com.mr.mr_test.core.ApiMethods
import com.mr.mr_test.model.net.response.repo.RepoResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepoService {

    @GET(ApiMethods.Repo.GetReposList)
    fun getReposList(
        @Query("since") since: Int
    ): Single<Response<List<RepoResponse>>>

    @GET(ApiMethods.Repo.GetRepo)
    fun getRepo(
        @Path("owner") ownerName: String,
        @Path("repo") repoName: String
    ): Single<RepoResponse>
}