package com.klemer.doctorsforeveryone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.klemer.doctorsforeveryone.utils.replaceView
import com.klemer.doctorsforeveryone.view.SignInFragment
import com.klemer.doctorsforeveryone.view_model.SignInViewModel

class StartActivity : AppCompatActivity() {

    private lateinit var viewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
//        TODO: Remove signOut after
//        viewModel.signOut()

        if (viewModel.currentUser() != null) {
            Intent(this, MainActivity::class.java).apply {
                startActivity(this)
            }
        } else {
            replaceView(SignInFragment.newInstance(),R.id.containerStart)
        }

    }
}