package com.klemer.doctorsforeveryone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.klemer.doctorsforeveryone.databinding.ActivitySplashBinding
import kotlinx.coroutines.*

class SplashActivity : AppCompatActivity() {

    lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        removeActionBar()

        val retornoAsync = CoroutineScope(Dispatchers.Main).async {
            waitSplashScreenTimer()
        }
        CoroutineScope(Dispatchers.Main).launch {

            val result = retornoAsync.await()

            if (result) {
                callNewActivity()
            }
        }
    }
    private suspend fun waitSplashScreenTimer(): Boolean {
        delay(3000)
        return true
    }
    private fun callNewActivity() {
        Intent(this, StartActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }
    private fun removeActionBar() {
        supportActionBar?.hide()
    }
}