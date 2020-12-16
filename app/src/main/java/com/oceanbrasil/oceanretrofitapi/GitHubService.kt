package com.oceanbrasil.oceanretrofitapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubService {

    @GET("users")
    fun getUsers(
    ): Call<GitHubRepositoriesResult>

    @GET("search/repositories")
    fun searchRepositories(
        @Query("q") query: String
    ): Call<GitHubRepositoriesResult>
}