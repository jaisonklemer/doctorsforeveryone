package com.klemer.doctorsforeveryone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.klemer.doctorsforeveryone.utils.replaceView
import com.klemer.doctorsforeveryone.view.SignInFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceView(SignInFragment.newInstance(), R.id.container)
    }
}