package com.heinika.github

import java.util.*

class LoginRequestModel {
    val scopes: List<String> = Arrays.asList("user", "repo", "gist", "notifications")
    val note:String = "com.heinika.github"
    val clientId:String = "1c8674b18b92b699f894"
    val clientSecret: String = "a90ee4fd7d248e3093694c2c10748ffd3d23e029"
}