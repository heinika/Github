package com.heinika.github

import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.anko.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException

class MainActivity : AppCompatActivity() {


    companion object {
        val MEDIA_TYPE_JSON = "application/json; charset=UTF-8".toMediaType()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonLogin.setOnClickListener {
            val username = editTextUserName.text
            val password = editTextPassword.text

            if (username.isEmpty()) {
                it.context.toast("请输入帐号!")
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                it.context.toast("请输入密码!")
                return@setOnClickListener
            }

            val type = "$username:$password"
            val base64 = Base64.encodeToString(type.toByteArray(), Base64.NO_WRAP).replace("\\+", "%2B")
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val jsonAdapter = moshi.adapter(LoginRequestModel::class.java)
            val postBody = jsonAdapter.toJson(LoginRequestModel().generate()).toString().toRequestBody()
            val githubApiServices = RetrofitFactory.getGithubApiService()
            githubApiServices.getToken("Basic $base64",postBody).enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {

                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.i("MainActivity",response.body())
                }
            })
        }
    }
}

