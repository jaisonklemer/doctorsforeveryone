package com.klemer.doctorsforeveryone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.klemer.doctorsforeveryone.databinding.ActivityMainBinding
import com.klemer.doctorsforeveryone.model.User
import com.klemer.doctorsforeveryone.utils.changeBehaviorForConnection
import com.klemer.doctorsforeveryone.utils.configSnackbar
import com.klemer.doctorsforeveryone.utils.replaceView
import com.klemer.doctorsforeveryone.view.*
import com.klemer.doctorsforeveryone.view_model.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var userViewModel: ProfileViewModel

    private val userObserver = Observer<User> {
        checkIfProfileIsCompleted(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        userViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        setContentView(binding.root)

        supportActionBar?.hide()
        setupBottomNavigation()
        bottomNav()
        replaceView(HomeFragment.newInstance())

        changeBehaviorForConnection(binding.root, this, R.id.container)

        setupObserver()

        userViewModel.getCurrentUser()

    }

    private fun bottomNav() {


        binding.bottomNavigation.setOnItemSelectedListener { item ->
            userViewModel.getCurrentUser()
            when (item.itemId) {
                R.id.navHome -> {
                    replaceView(HomeFragment.newInstance())
                }
                R.id.navSchedules -> {
                    replaceView(SchedulesFragment.newInstance())
                }
                R.id.navHealth -> {
                    replaceView(HealthFragment.newInstance())
                }
                R.id.navProfile -> {
                    replaceView(ProfileFragment.newInstance())
                }
                R.id.navAdmin -> {
                    replaceView(AdminFragment.newInstance())
                }
            }
            true
        }
    }

    private fun setupBottomNavigation() {
        val userAdmin = intent.getBooleanExtra("admin", false)
        if (userAdmin) {
            binding.bottomNavigation.menu.getItem(4).isVisible = true
        }
    }

    fun changeBottomSelectedItem(fragment: Fragment, position: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            delay(100)
            replaceView(fragment)
            binding.bottomNavigation.menu.getItem(position).isChecked = true
        }
    }

    private fun setupObserver() {
        userViewModel.user.observe(this, userObserver)
    }

    fun checkIfProfileIsCompleted(user: User) {
        if (!user.profile_completed) {
            configSnackbar(
                binding.bottomNavigation,
                getString(R.string.profile_must_be_completed),
                Snackbar.LENGTH_INDEFINITE,
                getString(R.string.complete)
            ) { callback ->
                if (callback) {
                    this.changeBottomSelectedItem(ProfileFragment.newInstance(), 3)
                }
            }
        }
    }
}