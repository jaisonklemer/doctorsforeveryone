package com.klemer.doctorsforeveryone.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.klemer.doctorsforeveryone.R
import com.klemer.doctorsforeveryone.adapter.HealthNewsAdapter
import com.klemer.doctorsforeveryone.databinding.HealthFragmentBinding
import com.klemer.doctorsforeveryone.interfaces.NetworkConnectionInterface
import com.klemer.doctorsforeveryone.model.HealthNewsResponse
import com.klemer.doctorsforeveryone.utils.checkForInternet
import com.klemer.doctorsforeveryone.view_model.HealthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HealthFragment : Fragment(R.layout.health_fragment), NetworkConnectionInterface {

    companion object {
        fun newInstance() = HealthFragment()
    }

    private lateinit var viewModel: HealthViewModel
    private lateinit var binding: HealthFragmentBinding

    private val adapter = HealthNewsAdapter { newsUrl ->
        startBrowser(newsUrl)
    }

    private val newsObserver = Observer<HealthNewsResponse> {
        binding.progressBarHealth.visibility = INVISIBLE
        adapter.submitList(it.articles)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HealthViewModel::class.java]
        binding = HealthFragmentBinding.bind(view)

        setupObservers()
        setupRecyclerView()

    }

    override fun onResume() {
        super.onResume()

        if (checkForInternet(requireContext()))
            viewModel.getNews()
    }


    private fun setupObservers() {
        viewModel.newsResponse.observe(viewLifecycleOwner, newsObserver)
    }

    private fun setupRecyclerView() {
        binding.rvHealthNews.adapter = adapter
        binding.rvHealthNews.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun startBrowser(url: String) {
        val browser = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browser)
    }

    override fun isConnected(connected: Boolean) {
        if (connected)
            viewModel.getNews()
    }

}