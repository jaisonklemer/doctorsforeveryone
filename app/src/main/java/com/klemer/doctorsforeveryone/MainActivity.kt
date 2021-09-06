package com.klemer.doctorsforeveryone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.klemer.doctorsforeveryone.databinding.ActivityMainBinding
import com.klemer.doctorsforeveryone.view.HealthFragment
import com.klemer.doctorsforeveryone.view.HomeFragment
import com.klemer.doctorsforeveryone.view.ProfileFragment
import com.klemer.doctorsforeveryone.view.SchedulesFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNav()
        changeFrag(HomeFragment.newInstance())

    }

    private fun changeFrag(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
    }

    private fun bottomNav() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navHome -> {
                    changeFrag(HomeFragment.newInstance())
                }
                R.id.navSchedules -> {
                    changeFrag(SchedulesFragment.newInstance())
                }
                R.id.navHealth -> {
                    changeFrag(HealthFragment.newInstance())
                }
                R.id.navProfile -> {
                    changeFrag(ProfileFragment.newInstance())
                }
            }
            true
        }
    }
}