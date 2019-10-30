package com.heinika.github.model

import com.heinika.github.BuildConfig
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
class LoginRequestModel {
    val scopes: List<String>? = Arrays.asList("user", "repo", "gist", "notifications")
    var note: String? = null
    @Json(name = "client_id")
    var clientId: String? = null
    @Json(name = "client_secret")
    var clientSecret: String? = null

    fun generate(): LoginRequestModel {
        val loginRequestModel = LoginRequestModel()
        loginRequestModel.note = BuildConfig.APPLICATION_ID
        loginRequestModel.clientId = BuildConfig.CLIENT_ID
        loginRequestModel.clientSecret =
            BuildConfig.CLIENT_SECRET
        return loginRequestModel
    }
}