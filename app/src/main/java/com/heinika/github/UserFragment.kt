package com.heinika.github

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.heinika.github.database.GithubDatabase
import kotlinx.android.synthetic.main.user_info.view.*
import kotlinx.coroutines.*


class UserFragment : Fragment() {
    val args: UserFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.user_info, container, false)
        val coroutineScope = CoroutineScope(Job())
        coroutineScope.launch(Dispatchers.Main) {
            val avatar = getUser(args.email)
            view.textViewEmail.text = avatar
        }

        return view
    }

    suspend fun getUser(email: String): String? = withContext(Dispatchers.IO) {
        val userEntityDao = GithubDatabase.getDatabase(context!!).userEntityDao
        return@withContext userEntityDao.getUserEntityByEmail(args.email).avatarUrl
    }
}