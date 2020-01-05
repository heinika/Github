package com.heinika.github.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Base64
import com.heinika.github.MainApplication
import com.heinika.github.database.GithubDatabase
import com.heinika.github.database.UserEntity
import com.heinika.github.model.LoginRequestModel
import com.heinika.github.network.Network
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.await

class UserRepository(private val githubDatabase: GithubDatabase) {

    fun getUserEntityByEmail(email: String): UserEntity {
        return githubDatabase.userEntityDao.getUserEntityByEmail(email)
    }

    fun refreshUserInfo(context: Context) {
//        return Network.githubApi.getUser("TOKEN")
    }

}