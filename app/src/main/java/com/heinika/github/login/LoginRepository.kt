package com.heinika.github.login

import android.content.Context
import android.util.Base64
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.heinika.github.MainApplication
import com.heinika.github.database.GithubDatabase
import com.heinika.github.database.UserEntity
import com.heinika.github.model.LoginRequestModel
import com.heinika.github.model.TokenResultModel
import com.heinika.github.model.UserModel
import com.heinika.github.network.Network
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.*
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import retrofit2.await
import java.lang.Exception

class LoginRepository(var database: GithubDatabase) {

    fun login(username: String, password: String, loginViewModel: LoginViewModel) {
        val coroutineScope = CoroutineScope(Job())
        coroutineScope.launch {
            val isSaved = saveToken(username, password)
            if (isSaved) {
                val response: Response<UserModel> = Network.githubApi.getUser().execute()
                if (response.isSuccessful) {
                    database.userEntityDao.insert(response.body()!!.toUserEntity())
                    val sharedPreferences =
                        MainApplication.instance.getSharedPreferences("github", Context.MODE_PRIVATE)
                    sharedPreferences.edit().putString("email", response.body()!!.email).apply()
                    toUserFragment(loginViewModel._email, response.body()!!.toUserEntity().email)
                } else {
                    setErrorValue(loginViewModel._passwordError, "get user info error")
                }
            } else {
                setErrorValue(loginViewModel._passwordError, "username or password is error")
            }
        }
    }

    private suspend fun setErrorValue(_error: MutableLiveData<String>, message: String) =
        withContext(Dispatchers.Main) {
            _error.value = message
        }

    private suspend fun toUserFragment(_email: MutableLiveData<String>, email: String) =
        withContext(Dispatchers.Main) {
            _email.value = email
        }

    suspend fun saveToken(username: String, password: String): Boolean =
        withContext(Dispatchers.IO) {
            val type = "$username:$password"
            val base64 =
                Base64.encodeToString(type.toByteArray(), Base64.NO_WRAP).replace("\\+", "%2B")
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val jsonAdapter = moshi.adapter(LoginRequestModel::class.java)
            val postBody =
                jsonAdapter.toJson(LoginRequestModel().generate()).toString().toRequestBody()
            val response: Response<TokenResultModel> =
                Network.githubApi.getToken("Basic $base64", postBody).execute()
            return@withContext if (response.isSuccessful) {
                val sharedPreferences =
                    MainApplication.instance.getSharedPreferences("github", Context.MODE_PRIVATE)
                sharedPreferences.edit().putString("token", response.body()!!.token).apply()
                true
            } else {
                false
            }
        }
}