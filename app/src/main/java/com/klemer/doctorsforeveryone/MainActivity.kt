package com.klemer.doctorsforeveryone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.annotation.LayoutRes
import androidx.core.view.children
import androidx.fragment.app.Fragment
import com.klemer.doctorsforeveryone.databinding.ActivityMainBinding
import com.klemer.doctorsforeveryone.utils.replaceView
import com.klemer.doctorsforeveryone.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
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

    fun changeBottomSelectedItem(fragment: Fragment, position: Int) {
        GlobalScope.launch(Dispatchers.Main) {
            delay(100)
            replaceView(fragment)
            binding.bottomNavigation.menu.getItem(position).isChecked = true
        }
    }
}