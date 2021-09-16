package com.klemer.doctorsforeveryone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.klemer.doctorsforeveryone.databinding.ActivityMainBinding
import com.klemer.doctorsforeveryone.utils.replaceView
import com.klemer.doctorsforeveryone.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()

        bottomNav()
        replaceView(HomeFragment.newInstance())
    }

    private fun bottomNav() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navHome -> {
                    replaceView(HomeFragment.newInstance())
                    // Insert test category
//                    replaceView(CategoryFragment.newInstance())
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
}