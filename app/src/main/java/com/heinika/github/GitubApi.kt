package com.heinika.github

import com.heinika.github.model.TokenResultModel
import com.heinika.github.model.UserModel
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface GithubApiServices {
    @POST("authorizations")
    fun getToken(@Header("Authorization") basePassword: String, @Body requestBody: RequestBody): Call<TokenResultModel>

    @GET("user")
    fun getUser(@Header("Authorization") token: String): Call<UserModel>
}