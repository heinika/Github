package com.heinika.github.login

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.heinika.github.MainApplication
import com.heinika.github.R
import com.heinika.github.database.GithubDatabase
import kotlinx.android.synthetic.main.login_fragment.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.login_fragment, container, false)
        val loginViewModel = ViewModelProviders.of(
            this,
            LoginViewModelFactory(LoginRepository(GithubDatabase.getDatabase(context!!)))
        ).get(LoginViewModel::class.java)
        val sharedPreferences =
            MainApplication.instance.getSharedPreferences("github", Context.MODE_PRIVATE)
        val email: String = sharedPreferences.getString("email", "")!!
        if (email.isNotEmpty()) {
            val action = LoginFragmentDirections.actionLoginFragmentToUserFragment(email)
            findNavController().navigate(action)
        }
        bingData(view, loginViewModel);
        return view
    }

    private fun bingData(view: View, loginViewModel: LoginViewModel) {
        loginViewModel.usernameError.observe(this, Observer {
            view.textInputUsername.error = it
        })
        loginViewModel.passwordError.observe(this, Observer {
            view.textInputPassWord.error = it
        })
        loginViewModel.email.observe(this, Observer {
            if (!it.isNullOrEmpty()) {
                val action = LoginFragmentDirections.actionLoginFragmentToUserFragment(it)
                findNavController().navigate(action)
            }
        })

        view.buttonLogin.onClick {
            loginViewModel.login(
                view.editTextUserName.text,
                view.editTextPassword.text
            )
        }
        view.editTextUserName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                loginViewModel.onUsernameTextChange(s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        view.editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                loginViewModel.onPasswordTextChange(s)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }


}