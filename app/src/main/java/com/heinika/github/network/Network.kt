package com.heinika.github.network

import com.heinika.github.HeaderInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val GITHUB_API_BASE_URL = "https://api.github.com"

class Network private constructor() {

    private var retrofit: Retrofit

    init {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(HeaderInterceptor())
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl(GITHUB_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    companion object {
        @Volatile
        private var mNetwork: Network? = null

        private val instance: Network
            get() {
                if (mNetwork == null) {
                    synchronized(Network::class.java) {
                        mNetwork = Network()
                    }
                }
                return mNetwork!!
            }

        val githubApi: GithubApiServices = instance.retrofit.create(GithubApiServices::class.java)
    }
}
