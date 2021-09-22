package com.klemer.doctorsforeveryone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.klemer.doctorsforeveryone.utils.replaceView
import com.klemer.doctorsforeveryone.view.SignInFragment


class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_start)
        replaceView(SignInFragment.newInstance(), R.id.containerStart)
        removeActionBar()
    }

    private fun removeActionBar() {
        supportActionBar?.hide()
    }
}