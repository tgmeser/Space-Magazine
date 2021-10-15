package com.babyapps.spacemagazine.features.blog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.babyapps.spacemagazine.R
import com.babyapps.spacemagazine.databinding.FragmentBlogsBinding
import com.babyapps.spacemagazine.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class BlogsFragment : Fragment(R.layout.fragment_blogs) {
    private lateinit var binding: FragmentBlogsBinding
    private lateinit var blogAdapter: BlogAdapter
    private val viewModel: BlogsViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBlogsBinding.bind(view)


        subscribeObservers()

        blogAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("blogDto", it)
            }
            findNavController().navigate(
                R.id.action_blogsFragment_to_blogFragment, bundle
            )
        }

        binding.apply {
            swipeToRefresh.setOnRefreshListener {
                subscribeObservers()
                blogAdapter.setOnItemClickListener {
                    val bundle = Bundle().apply {
                        putSerializable("blogDto", it)
                    }
                    findNavController().navigate(
                        R.id.action_blogsFragment_to_blogFragment, bundle
                    )
                }

                swipeToRefresh.isRefreshing=false

            }
        }
    }


    override fun onResume() {
        super.onResume()
    }
    private fun subscribeObservers() {
        binding.apply {
            rvBlogs.apply {
                blogAdapter = BlogAdapter()
                adapter = blogAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        DividerItemDecoration.VERTICAL
                    )
                )
                setHasFixedSize(true)
            }
            viewModel.blogs.observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Resource.Success -> {
                        displayProgressBar(false)
                        displayErrorAsset(false)
                        blogAdapter.differ.submitList(result.data)

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
        binding.pbBlogs.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun displayErrorAsset(isDisplayed: Boolean) {
        binding.tvError.visibility = if (isDisplayed) View.VISIBLE else View.GONE
        binding.ivError.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    private fun updateData() {

    }
}