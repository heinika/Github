package com.heinika.github

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface GithubApiServices {
    @POST("authorizations")
    fun getToken(@Header("Authorization") basePassword: String, @Body requestBody: RequestBody): Call<String>
}