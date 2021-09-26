package com.klemer.doctorsforeveryone

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.klemer.doctorsforeveryone.databinding.ActivitySplashBinding
import com.klemer.doctorsforeveryone.repository.UserRepository
import com.klemer.doctorsforeveryone.utils.checkForInternet
import com.klemer.doctorsforeveryone.view_model.SignInViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: SignInViewModel
    private val userRepository = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadComponents()

        removeActionBar()
        initializeSplash()
        binding.tvTryAgain.setOnClickListener { initializeSplash() }
    }

    private fun initializeSplash() {
        binding.noConnLayout.visibility = View.GONE
        binding.lottieAnimationView.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.Main).launch {
            waitSplashScreenTimer()
            executeComponents()
        }
    }

    private fun loadComponents() {
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
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

    private suspend fun executeComponents() {
        if (checkForInternet(this)) {
            if (viewModel.currentUser() != null) {
                withContext(Dispatchers.Main) {
                    try {
                        val result = userRepository.getUser(viewModel.currentUser()!!.uid)
                        Intent(this@SplashActivity, MainActivity::class.java).let { newIntent ->
                            var admin = false
                            if (result["admin"] != null) {
                                admin = result["admin"] as Boolean
                            }

                            newIntent.putExtra("admin", admin)
                            newIntent.putExtra("complete", result["profile_completed"] as Boolean)
                            startActivity(newIntent)
                            finish()
                        }
                    } catch (e: Exception) {
                        println(e.localizedMessage)
                    }
                }

            } else {
                callNewActivity()
            }
        } else {
            binding.noConnLayout.visibility = View.VISIBLE
            binding.lottieAnimationView.visibility = View.GONE
        }

    }
}