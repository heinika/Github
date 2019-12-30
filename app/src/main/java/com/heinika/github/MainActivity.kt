package com.heinika.github

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import okhttp3.MediaType.Companion.toMediaType

class MainActivity : AppCompatActivity() {


    companion object {
        val MEDIA_TYPE_JSON = "application/json; charset=UTF-8".toMediaType()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.github_main_activity)
    }
}

