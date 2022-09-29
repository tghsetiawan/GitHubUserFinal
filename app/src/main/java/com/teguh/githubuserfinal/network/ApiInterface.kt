package com.teguh.githubuserfinal.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    //function search user github
    @GET("/search/users")
    suspend fun searchGithubUser(@Query("q") q: String?): SearchUserBase

    //function search user by username
    @GET("/users/{username}")
    suspend fun searchDetailGithubUser(@Path("username") username: String?): UserRespons

    //function follower
    @GET("/users/{username}/followers")
    suspend fun followerGithubUser(@Path("username") username: String?): List<UserFollower>

    //function following
    @GET("/users/{username}/following")
    suspend fun followingGithubUser(@Path("username") username: String?): List<UserFollowing>
}