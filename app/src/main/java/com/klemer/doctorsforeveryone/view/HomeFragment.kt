package com.klemer.doctorsforeveryone.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.INVISIBLE
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
    private var searchCategory = String()

    private var adapterCategory = CategoryAdapter {
        searchCategory = it.name
        if (!binding.headerFragment.includeSearch.searchDoctors.text.isNullOrEmpty()) {
            binding.headerFragment.includeSearch.searchDoctors.clearFocus()
            binding.headerFragment.includeSearch.searchDoctors.setText("")
        } else {
            viewModelDoctor.fetchDoctorByCategory(it.name)
        }
        requireActivity().hideKeyboard()
    }
    private var adapterDoctor = DoctorAdapter {
        showBottomSheetDialog(it)
    }

    private val observerCategoryGetAll = Observer<List<Category>> {
        adapterCategory.refresh(it)
    }

    private val observerDoctorGetALL = Observer<List<Doctor>?> {
        binding.progressBarHome.visibility = INVISIBLE
        if (!it.isNullOrEmpty()) {
            adapterDoctor.refresh(it)
        } else {
            adapterDoctor.clear()
            val view =
                (requireActivity() as MainActivity).findViewById<BottomNavigationView>(R.id.bottomNavigation)
            requireActivity().hideKeyboard()
            configSnackbar(view, "Nenhum especialista encontrado!")
        }
    }

    private val observerError = Observer<String> {
        val view =
            (requireActivity() as MainActivity).findViewById<BottomNavigationView>(R.id.bottomNavigation)
        requireActivity().hideKeyboard()
        configSnackbar(view, "Nenhum especialista encontrado!", Snackbar.LENGTH_INDEFINITE, true)
    }

    private val currentUserObserver = Observer<User> {
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

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.let {
                    if (it.length > 2) {
                        if (searchCategory.isNullOrEmpty()) {
                            viewModelDoctor.fetchDoctorByName(it.toString())
                        } else {
                            viewModelDoctor.fetchDoctorWithCategory(it.toString(), searchCategory)
                        }
                    } else if (it.isEmpty()) {
                        if (!searchCategory.isNullOrEmpty()) {
                            viewModelDoctor.fetchDoctorByCategory(searchCategory)
                            requireActivity().hideKeyboard()
                        } else {
                            viewModelDoctor.fetchDoctor()
                            requireActivity().hideKeyboard()
                        }
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun setupRecyclerViewCategory() = with(binding.include.recyclerViewCategory) {
        //Lista de categorias
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
}