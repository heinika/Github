package com.heinika.github

import android.util.Base64
import android.util.Log
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class LoginService {

    fun login() {
        val username = "heinika"
        val password = "chenlijin123"
        val type = "$username:$password"
        val base64 = Base64.encodeToString(type.toByteArray(), Base64.NO_WRAP).replace("\\+", "%2B")
        Log.i("MainActivity", base64)
        //Authorization: Basic aGVpbmlrYTpjaGVubGlqaW4xMjM=
        val okHttpClient = OkHttpClient()
        val postBody: String =
            "{\"client_id\":\"1c8674b18b92b699f894\",\"client_secret\":\"a90ee4fd7d248e3093694c2c10748ffd3d23e029\",\"note\":\"com.heinika.github\",\"scopes\":[\"user\",\"repo\",\"gist\",\"notifications\"]}".trimMargin()
        val request = Request.Builder().url("https://api.github.com/authorizations")
            .header("Accept", "application/json")
            .header("Authorization", "Basic aGVpbmlrYTpjaGVubGlqaW4xMjM=")
            .post(postBody.toRequestBody(MainActivity.MEDIA_TYPE_JSON))
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    Log.i("MainActivity", response.body?.string())
                }
            }
        })
    }
}