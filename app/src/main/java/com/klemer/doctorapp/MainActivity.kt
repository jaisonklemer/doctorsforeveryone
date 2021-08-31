package com.klemer.doctorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.klemer.doctorapp.utils.replaceView
import com.klemer.doctorapp.view.SignInFragment
import com.klemer.doctorapp.view.SignUpFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}