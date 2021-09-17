package com.klemer.doctorsforeveryone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.klemer.doctorsforeveryone.repository.UserRepository
import com.klemer.doctorsforeveryone.utils.replaceView
import com.klemer.doctorsforeveryone.view.SignInFragment
import com.klemer.doctorsforeveryone.view_model.SignInViewModel

class StartActivity : AppCompatActivity() {

    private lateinit var viewModel: SignInViewModel
    private val userRepository = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        loadComponents()
        executeComponents()
        removeActionBar()
//        singOut()


    }

    private fun loadComponents() {
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
    }

    private fun singOut() {
//        TODO: Remove signOut after
        viewModel.signOut()
    }

    private fun removeActionBar() {
        supportActionBar?.hide()
    }

    private fun executeComponents() {
        if (viewModel.currentUser() != null) {
            userRepository.getUser(viewModel.currentUser()!!.uid) {
                Intent(this, MainActivity::class.java).apply {
                    this.putExtra("admin", it?.admin)
                    startActivity(this)
                }
            }

        } else {
            replaceView(SignInFragment.newInstance(), R.id.containerStart)
        }
    }
}