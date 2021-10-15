package com.babyapps.spacemagazine.features.article

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.babyapps.spacemagazine.R
import com.babyapps.spacemagazine.databinding.FragmentArticlesBinding
import com.babyapps.spacemagazine.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticlesFragment : Fragment(R.layout.fragment_articles) {
    private lateinit var binding: FragmentArticlesBinding
    private lateinit var articleAdapter: ArticleAdapter
    private val viewModel: ArticlesViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArticlesBinding.bind(view)

        subscribeObservers()

        articleAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("articleDto", it)
            }
            findNavController().navigate(
                R.id.action_articlesFragment_to_articleFragment, bundle
            )
        }

        binding.apply {
            swipeToRefresh.setOnRefreshListener {
                subscribeObservers()
                articleAdapter.setOnItemClickListener {
                    val bundle = Bundle().apply {
                        putSerializable("articleDto", it)
                    }
                    findNavController().navigate(
                        R.id.action_articlesFragment_to_articleFragment, bundle
                    )
                }

                swipeToRefresh.isRefreshing=false

            }
        }
    }


    private fun subscribeObservers() {
        binding.apply {
            rvArticles.apply {
                articleAdapter = ArticleAdapter()
                adapter = articleAdapter
                layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(
                    DividerItemDecoration(
                        requireContext(),
                        DividerItemDecoration.VERTICAL
                    )
                )
                setHasFixedSize(true)
            }
            viewModel.articles.observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Resource.Success -> {
                        displayProgressBar(false)
                        displayErrorAsset(false)
                        articleAdapter.differ.submitList(result.data)

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
        binding.pbArticles.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }
    private fun displayErrorAsset(isDisplayed: Boolean) {
        binding.tvError.visibility = if (isDisplayed) View.VISIBLE else View.GONE
        binding.ivError.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }


}