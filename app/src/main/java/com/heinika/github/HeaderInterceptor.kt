package com.heinika.github

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder().url(request.url).addHeader("Authorization", "").build()
        return chain.proceed(request)
    }
}