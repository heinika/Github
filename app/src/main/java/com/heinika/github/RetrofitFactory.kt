package com.heinika.github

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val GITHUB_API_BASE_URL = "https://api.github.com"

class RetrofitFactory private constructor() {

    private var retrofit: Retrofit

    init {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()
        retrofit = Retrofit.Builder()
            .baseUrl(GITHUB_API_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    companion object {
        @Volatile
        private var mRetrofitFactory: RetrofitFactory? = null

        val instance: RetrofitFactory
            get() {
                if (mRetrofitFactory == null) {
                    synchronized(RetrofitFactory::class.java) {
                        mRetrofitFactory = RetrofitFactory()
                    }
                }
                return mRetrofitFactory!!
            }

        fun getGithubApiService():GithubApiServices{
            return RetrofitFactory.instance.retrofit.create(GithubApiServices::class.java)
        }
    }


}
