package com.babyapps.spacemagazine.features.report

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.babyapps.spacemagazine.R
import com.babyapps.spacemagazine.databinding.FragmentReportsBinding
import com.babyapps.spacemagazine.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportsFragment : Fragment(R.layout.fragment_reports) {

    private lateinit var binding: FragmentReportsBinding
    private lateinit var reportAdapter: ReportAdapter
    private val viewModel: ReportsViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReportsBinding.bind(view)

        subscribeObservers()

        reportAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("reportDto", it)
            }
            findNavController().navigate(
                R.id.action_reportsFragment_to_reportFragment, bundle
            )
        }

        binding.apply {
            swipeToRefresh.setOnRefreshListener {
                subscribeObservers()
                reportAdapter.setOnItemClickListener {
                    val bundle = Bundle().apply {
                        putSerializable("reportDto", it)
                    }
                    findNavController().navigate(
                        R.id.action_reportsFragment_to_reportFragment, bundle
                    )
                }

                swipeToRefresh.isRefreshing=false

            }
        }
    }

    private fun subscribeObservers() {
        binding.apply {
            rvReports.apply {
                reportAdapter = ReportAdapter()
                adapter = reportAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        DividerItemDecoration.VERTICAL
                    )
                )
                setHasFixedSize(true)
            }
            viewModel.reports.observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Resource.Success -> {
                        displayProgressBar(false)
                        displayErrorAsset(false)
                        reportAdapter.differ.submitList(result.data)

                    }
                    is Resource.Error -> {
                        displayProgressBar(false)
                        displayErrorAsset(true)
                    }
                    is Resource.Loading -> {
                        displayProgressBar(true)
                        displayErrorAsset(false)
                    }
                }
            })
        }
    }


    private fun displayProgressBar(isDisplayed: Boolean) {
        binding.pbReports.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayErrorAsset(isDisplayed: Boolean) {
        binding.tvError.visibility = if (isDisplayed) View.VISIBLE else View.GONE
        binding.ivError.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }
}