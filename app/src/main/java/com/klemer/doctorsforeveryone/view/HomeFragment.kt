package com.klemer.doctorsforeveryone.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.klemer.doctorsforeveryone.MainActivity
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.adapter.CategoryAdapter
import com.klemer.doctorsforeveryone.adapter.DoctorAdapter
import com.klemer.doctorsforeveryone.databinding.HomeFragmentBinding
import com.klemer.doctorsforeveryone.model.Category
import com.klemer.doctorsforeveryone.model.Doctor
import com.klemer.doctorsforeveryone.model.User
import com.klemer.doctorsforeveryone.utils.configSnackbar
import com.klemer.doctorsforeveryone.utils.hideKeyboard
import com.klemer.doctorsforeveryone.view_model.CategoryViewModel
import com.klemer.doctorsforeveryone.view_model.DoctorViewModel
import com.klemer.doctorsforeveryone.view_model.HomeViewModel


class HomeFragment : Fragment(R.layout.home_fragment) {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewModelCategory: CategoryViewModel
    private lateinit var viewModelDoctor: DoctorViewModel
    private lateinit var binding: HomeFragmentBinding
    private lateinit var currentUser: User
    private lateinit var doctorsList: List<Doctor>
    private lateinit var filteredDoctors: List<Doctor>

    private var searchCategory = String()

    private var adapterCategory = CategoryAdapter {
        binding.headerFragment.includeSearch.searchDoctors.clearFocus()
        binding.headerFragment.includeSearch.searchDoctors.setText("")

        requireContext().hideKeyboard(requireView())

        binding.lottieAnimationView.visibility = VISIBLE

        if (it.order.toInt() != 0) {
            searchCategory = it.name
            viewModelDoctor.fetchDoctorByCategory(it.name)
        } else {
            searchCategory = ""
            viewModelDoctor.fetchDoctor()
        }
    }

    private var adapterDoctor = DoctorAdapter {
        if (currentUser.profile_completed) {
            showBottomSheetDialog(it)
        } else {
            (requireActivity() as MainActivity).checkIfProfileIsCompleted(currentUser)
        }
    }

    private val observerCategoryGetAll = Observer<List<Category>> {
        adapterCategory.refresh(it)
    }

    private val observerDoctorGetALL = Observer<List<Doctor>?> {
        doctorsList = it

        binding.lottieAnimationView.visibility = GONE
        if (!it.isNullOrEmpty()) {
            adapterDoctor.refresh(it)
        } else {
            adapterDoctor.clear()
            val view =
                (requireActivity() as MainActivity).findViewById<BottomNavigationView>(R.id.bottomNavigation)
            configSnackbar(view, getString(R.string.no_doctor_found))
        }
    }

    private val observerError = Observer<String> {
        val view =
            (requireActivity() as MainActivity).findViewById<BottomNavigationView>(R.id.bottomNavigation)
        configSnackbar(view, getString(R.string.no_doctor_found), Snackbar.LENGTH_INDEFINITE)
    }

    private val currentUserObserver = Observer<User> {
        currentUser = it
        binding.headerFragment.nameUser.text = "Olá ${it.name}"
    }

    override fun onStart() {
        super.onStart()
        viewModelCategory.fetchCategory()
        viewModelDoctor.fetchDoctor()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadComponents(view)
        setupOservers()
        executeComponents()
        binding.headerFragment.includeSearch.searchDoctors.clearFocus()
    }

    private fun loadComponents(view2: View) {
        binding = HomeFragmentBinding.bind(view2)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModelCategory = ViewModelProvider(this).get(CategoryViewModel::class.java)
        viewModelDoctor = ViewModelProvider(this).get(DoctorViewModel::class.java)
    }

    private fun setupOservers() {
        viewModelCategory.categoryGet.observe(viewLifecycleOwner, observerCategoryGetAll)
        viewModelDoctor.doctorGet.observe(viewLifecycleOwner, observerDoctorGetALL)
        viewModelDoctor.error.observe(viewLifecycleOwner, observerError)
        viewModel.currentUser.observe(viewLifecycleOwner, currentUserObserver)
    }

    private fun executeComponents() {
        viewModel.getCurrentUser()
        setupRecyclersView()
        setupSearchListener()
        bindingAppBarOnScroll()
        bindingToShowProfile()
    }

    private fun setupRecyclersView() {
        setupRecyclerViewCategory()
        setupRecyclerViewDoctor()
    }


    private fun setupSearchListener() {
        binding.headerFragment.includeSearch.searchDoctors.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                text?.let {
                    if (it.length >= 2) {
                        filteredDoctors = if (searchCategory.isEmpty()) {
                            doctorsList.filter { doctor ->
                                doctor.name.startsWith(it.toString(), true)
                            }
                        } else {
                            doctorsList.filter { doctor ->
                                doctor.name.startsWith(
                                    it.toString(),
                                    true
                                ) && doctor.category == searchCategory
                            }
                        }

                        if (filteredDoctors.isEmpty()) {
                            binding.tvNoDoctorFound.visibility = VISIBLE
                        } else {
                            binding.tvNoDoctorFound.visibility = GONE
                        }

                        adapterDoctor.refresh(filteredDoctors)

                    } else if (it.isEmpty()) {
                        binding.tvNoDoctorFound.visibility = GONE
                        adapterDoctor.refresh(doctorsList)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun setupRecyclerViewCategory() = with(binding.include.recyclerViewCategory) {
        adapter = adapterCategory
        layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                requireActivity().hideKeyboard()
            }
        })
    }

    private fun setupRecyclerViewDoctor() = with(binding.recyclerViewDoctorsList) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = adapterDoctor
    }

    private fun showBottomSheetDialog(doctor: Doctor) {
        val bottomSheet = BottomSheetFragment.newInstance()
        val arguments = Bundle()
        arguments.putSerializable("doctor", doctor)
        bottomSheet.arguments = arguments
        bottomSheet.show(parentFragmentManager, "dialog_doctors")
    }

    private fun bindingAppBarOnScroll() {
        binding.appbarLayout.addOnOffsetChangedListener(OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset < -binding.animToolbar.height)
                requireActivity().hideKeyboard()
        })
    }

    private fun bindingToShowProfile() {
        binding.headerFragment.clickToShowProfile.setOnClickListener {
            (requireActivity() as MainActivity).changeBottomSelectedItem(
                ProfileFragment.newInstance(),
                3
            )
        }
    }
}