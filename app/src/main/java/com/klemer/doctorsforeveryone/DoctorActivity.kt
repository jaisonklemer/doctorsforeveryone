package com.klemer.doctorsforeveryone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.klemer.doctorsforeveryone.databinding.ActivityDoctorBinding
import com.klemer.doctorsforeveryone.utils.replaceView
import com.klemer.doctorsforeveryone.view.DoctorFragment

class DoctorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDoctorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        setupDoctorFragment()
    }


    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun getDoctorParams(): String? {
        return intent.getStringExtra("doctor_id")

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun setupDoctorFragment() {
        val fragment = DoctorFragment.newInstance()
        val args = Bundle().apply { putString("doctor_id", getDoctorParams()) }
        fragment.arguments = args

        replaceView(fragment, R.id.root_container)
    }
}

