package com.babyapps.spacemagazine.features.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.babyapps.spacemagazine.R
import com.babyapps.spacemagazine.databinding.FragmentArticlesBinding
import com.babyapps.spacemagazine.databinding.FragmentBlogsBinding
import com.babyapps.spacemagazine.databinding.FragmentFavoritesBinding
import com.babyapps.spacemagazine.databinding.FragmentReportsBinding

class FavoritesFragment: Fragment(R.layout.fragment_favorites) {
    private lateinit var binding: FragmentFavoritesBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding= FragmentFavoritesBinding.bind(view)

        binding.apply {
            ivArticleFavs.setOnClickListener {
                findNavController().navigate(FavoritesFragmentDirections.actionFavoritesFragmentToFavoriteArticlesFragment())
            }
            ivBlogFavs.setOnClickListener {
                findNavController().navigate(FavoritesFragmentDirections.actionFavoritesFragmentToFavoriteBlogsFragment())

            }
            ivReportFavs.setOnClickListener {

                findNavController().navigate(FavoritesFragmentDirections.actionFavoritesFragmentToFavoriteReportsFragment())

            }
        }
    }
}