package com.klemer.doctorsforeveryone

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.klemer.doctorsforeveryone.databinding.ActivityMainBinding
import com.klemer.doctorsforeveryone.interfaces.BackStackBottomNavigation
import com.klemer.doctorsforeveryone.model.User
import com.klemer.doctorsforeveryone.utils.*
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

    fun bottomNav() {

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            userViewModel.getCurrentUser()
//            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.navHome -> {
//                    fragment = HomeFragment.newInstance()
                    replaceView(HomeFragment.newInstance(), addBackStack = true)
                }
                R.id.navSchedules -> {
//                    fragment = SchedulesFragment.newInstance()
                    replaceView(SchedulesFragment.newInstance(), addBackStack = true)
                }
                R.id.navHealth -> {
//                    fragment = HealthFragment.newInstance()
                    replaceView(HealthFragment.newInstance(), addBackStack = true)
                }
                R.id.navProfile -> {
//                    fragment = ProfileFragment.newInstance()
                    replaceView(ProfileFragment.newInstance(), addBackStack = true)
                }
                R.id.navAdmin -> {
//                    fragment = AdminFragment.newInstance()
                    replaceView(AdminFragment.newInstance(), addBackStack = true)
                }
            }
//            supportFragmentManager
//                .beginTransaction()
//                .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out)
//                .replace(R.id.container, fragment!!)
//                .addToBackStack(fragment.tag)
//                .commit()
            true
        }
    }

    private fun setupBottomNavigation() {
        val userAdmin = intent.getBooleanExtra("admin", false)
        if (userAdmin) {
            binding.bottomNavigation.menu.getItem(4).isVisible = true
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

    //    var count = 0
//    override fun onBackPressed() {
//        count += 1
//        if (count > 3) {
//            count = 0
//            replaceView(HomeFragment.newInstance(), addBackStack = true)
//        } else {
//            supportFragmentManager.popBackStackImmediate(
//                HomeFragment.javaClass.name,
//                POP_BACK_STACK_INCLUSIVE
//            )
//        }
//        if (supportFragmentManager.backStackEntryCount > 0) {
////            supportFragmentManager.popBackStackImmediate()
//            supportFragmentManager.popBackStackImmediate(
//                HomeFragment.javaClass.name,
//                POP_BACK_STACK_INCLUSIVE
//            )
//        }

//        if (supportFragmentManager.backStackEntryCount > 4)
//            supportFragmentManager.popBackStackImmediate(
//                HomeFragment.javaClass.name,
//                POP_BACK_STACK_INCLUSIVE
//            )


//    }

    fun changeBottomSelectedItem(
        fragment: Fragment,
        position: Int,
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            delay(100)
            replaceView(fragment)
            binding.bottomNavigation.menu.getItem(position).isChecked = true
        }
    }
}