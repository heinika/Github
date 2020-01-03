package com.heinika.github

import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.heinika.github.database.GithubDatabase
import com.heinika.github.model.LoginRequestModel
import com.heinika.github.model.TokenResultModel
import com.heinika.github.model.UserModel
import com.heinika.github.model.toUserEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.android.synthetic.main.github_login_fragment.*
import kotlinx.android.synthetic.main.github_login_fragment.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.github_login_fragment, container, false)
        view.buttonLogin.setOnClickListener {
            val username = editTextUserName.text
            val password = editTextPassword.text

            if (username != null) {
                if (username.isEmpty()) {
                    view.textInputUsername.error = "please input your username!"
                    return@setOnClickListener
                }
            }

            if (password != null) {
                if (password.isEmpty()) {
                    view.textInputPassWord.error = "please input your password!"
                    return@setOnClickListener
                }
            }

            val type = "$username:$password"
            val base64 =
                Base64.encodeToString(type.toByteArray(), Base64.NO_WRAP).replace("\\+", "%2B")
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val jsonAdapter = moshi.adapter(LoginRequestModel::class.java)
            val postBody =
                jsonAdapter.toJson(LoginRequestModel().generate()).toString().toRequestBody()
            val githubApiServices = RetrofitFactory.getGithubApiService()
            githubApiServices.getToken("Basic $base64", postBody)
                .enqueue(object : Callback<TokenResultModel> {
                    override fun onFailure(call: Call<TokenResultModel>, t: Throwable) {
                        Log.i("MainActivity", t.toString())
                        view.textInputPassWord.error = t.message
                    }

                    override fun onResponse(
                        call: Call<TokenResultModel>,
                        response: Response<TokenResultModel>
                    ) {
                        if (response.body() == null) {
                            view.textInputPassWord.error = "username or password is error"
                        } else {
                            view.textInputPassWord.error = null
                            response.body()!!.token?.let { token ->
                                githubApiServices.getUser("token $token")
                                    .enqueue(object : Callback<UserModel> {
                                        override fun onFailure(
                                            call: Call<UserModel>,
                                            t: Throwable
                                        ) {
                                            Log.i("MainActivity", t.toString())
                                        }

                                        override fun onResponse(
                                            call: Call<UserModel>,
                                            response: Response<UserModel>
                                        ) {
                                            activity?.applicationContext?.let { context ->
                                                val userEntityDao = GithubDatabase.getDatabase(context).userEntityDao()
                                                val coroutineScope = CoroutineScope(Job())
                                                coroutineScope.launch (Dispatchers.IO){
                                                    userEntityDao.insert(toUserEntity(response.body()!!))
                                                }
                                            }
                                            val action =
                                                LoginFragmentDirections.actionLoginFragmentToUserFragment(response.body()!!.email!!)
                                            NavHostFragment.findNavController(this@LoginFragment)
                                                .navigate(action)
                                        }
                                    })
                            }
                        }
                    }
                })
        }

        return view
    }
}