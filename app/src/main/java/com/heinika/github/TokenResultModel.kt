package com.heinika.github

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenResultModel(
    val app: App?,
    val created_at: String?,
    val fingerprint: Any?,
    val hashed_token: String?,
    val id: Int?,
    val note: String?,
    val note_url: Any?,
    val scopes: List<String>?,
    val token: String?,
    val token_last_eight: String?,
    val updated_at: String?,
    val url: String?
)

@JsonClass(generateAdapter = true)
data class App(
    val client_id: String?,
    val name: String?,
    val url: String?
)