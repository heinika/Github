package com.heinika.github

import android.content.Context
import android.util.Base64
import com.heinika.github.model.LoginRequestModel
import com.heinika.github.network.Network
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import retrofit2.await

class HeaderInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val token = getToken() ?: return chain.proceed(request);
        request =
            request.newBuilder().url(request.url).addHeader("Authorization", "token $token").build()
        return chain.proceed(request)
    }

    private fun getToken(): String? {
        val sharedPreferences = MainApplication.instance.getSharedPreferences("github", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "error")
        return if ("error" == token) {
            null
        } else {
            token
        }
    }
}