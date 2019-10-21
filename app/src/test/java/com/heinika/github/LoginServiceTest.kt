package com.heinika.github

import com.heinika.github.MainActivity.Companion.MEDIA_TYPE_JSON
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Test

internal class LoginServiceTest {

    @Test
    fun login() {
        val username = "heinika"
        val password = "chenlijin123"
        val type = "$username:$password"
        val base64 = "Basic aGVpbmlrYTpjaGVubGlqaW4xMjM="
        //Authorization: Basic aGVpbmlrYTpjaGVubGlqaW4xMjM=
        val okHttpClient = OkHttpClient()
        val postBody: String =
            "{\"client_id\":\"1c8674b18b92b699f894\",\"client_secret\":\"a90ee4fd7d248e3093694c2c10748ffd3d23e029\",\"note\":\"com.heinika.github\",\"scopes\":[\"user\",\"repo\",\"gist\",\"notifications\"]}".trimMargin()
        val request = Request.Builder().url("https://api.github.com/authorizations")
            .header("Accept","application/json")
            .header("Authorization","Basic aGVpbmlrYTpjaGVubGlqaW4xMjM=")
            .post(postBody.toRequestBody(MEDIA_TYPE_JSON))
            .build()
//        okHttpClient.newCall(request).enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                if (response.isSuccessful) {
//                    print(response.body?.string())
//                }
//            }
//        })
        val response = okHttpClient.newCall(request).execute()
        print(response.body?.string())
    }
}