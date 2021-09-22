package com.klemer.doctorsforeveryone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.klemer.doctorsforeveryone.repository.UserRepository
import com.klemer.doctorsforeveryone.utils.replaceView
import com.klemer.doctorsforeveryone.view.SignInFragment
import com.klemer.doctorsforeveryone.view_model.SignInViewModel
import com.google.android.material.snackbar.Snackbar
import com.klemer.doctorsforeveryone.utils.checkForInternet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class StartActivity : AppCompatActivity() {

    private lateinit var viewModel: SignInViewModel
    private val userRepository = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        loadComponents()
        removeActionBar()

        CoroutineScope(Dispatchers.Main).launch {
            verifyConnection()
        }
    }

    private fun loadComponents() {
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
    }

    private fun removeActionBar() {
        supportActionBar?.hide()
    }

    private suspend fun executeComponents() {
        if (viewModel.currentUser() != null) {
            withContext(Dispatchers.IO) {
                try {
                    val result = userRepository.getUser(viewModel.currentUser()!!.uid)
                    Intent(this@StartActivity, MainActivity::class.java).let { newIntent ->
                        newIntent.putExtra("admin", result.get("admin") as Boolean)
                        startActivity(newIntent)
                        finish()
                    }
                } catch (e: Exception) {
                    println(e.localizedMessage)
                }
            }

        } else {
            replaceView(SignInFragment.newInstance(), R.id.containerStart)
        }
    }

    private suspend fun verifyConnection() {
        if (checkForInternet(this)) {
            executeComponents()
        } else {
            replaceView(SignInFragment.newInstance(), R.id.containerStart)
            Snackbar.make(
                this.findViewById(R.id.containerStart),
                "Sem conexao com a internet!",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }
}