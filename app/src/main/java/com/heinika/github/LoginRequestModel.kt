package com.heinika.github

import java.util.*

class LoginRequestModel {
    val scopes: List<String> = Arrays.asList("user", "repo", "gist", "notifications")
    var note:String? = null
    var clientId:String? = null
    var clientSecret: String? = null

    fun generate() : LoginRequestModel{
        val loginRequestModel = LoginRequestModel()
        loginRequestModel.note = BuildConfig.APPLICATION_ID
        loginRequestModel.clientId = BuildConfig.CLIENT_ID
        loginRequestModel.clientSecret = BuildConfig.CLIENT_SECRET
        return loginRequestModel
    }
}