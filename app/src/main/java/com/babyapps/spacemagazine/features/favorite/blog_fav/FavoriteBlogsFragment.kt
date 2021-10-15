package com.babyapps.spacemagazine.features.favorite.blog_fav

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.babyapps.spacemagazine.R
import com.babyapps.spacemagazine.databinding.FragmentFavoriteBlogsBinding
import com.babyapps.spacemagazine.features.blog.BlogsViewModel
import com.babyapps.spacemagazine.features.favorite.article_fav.DeleteFavoriteArticlesFragmentDirections
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteBlogsFragment : Fragment(R.layout.fragment_favorite_blogs) {
    private lateinit var blogAdaper: FavoriteBlogAdapter
    private val viewmodel: BlogsViewModel by viewModels()
    private lateinit var binding: FragmentFavoriteBlogsBinding
    private var menu: Menu? = null
    private var mInterstitialAd: InterstitialAd? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBlogsBinding.bind(view)


        setRecyclerView()
        subscribeObservers()
        loadInterstitialAd()



        blogAdaper.setOnItemClickListener {
            showInterstitialAd()
            val bundle = Bundle().apply {
                putSerializable("favoriteBlog", it)
            }
            findNavController().navigate(
                R.id.action_favoriteBlogsFragment_to_favoriteBlogFragment,
                bundle
            )
        }

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {


            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val blog = blogAdaper.differ.currentList[position]
                viewmodel.deleteBlog(blog)
                Snackbar.make(view, "Removed", Snackbar.LENGTH_LONG).apply {
                    setAction("Un Do") {
                        viewmodel.unDoBlog(blog)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvFavBlog)
        }

    }
    private fun subscribeObservers() {
        viewmodel.emptyDb.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseView(it)
        })

        viewmodel.getFavoriteBlogs().observe(viewLifecycleOwner, Observer { blogs ->
            viewmodel.checkDatabaseIsEmptyOrNot(blogs)
            blogAdaper.differ.submitList(blogs)

            menu?.getItem(0)?.isVisible = blogs.isNotEmpty()

        })




    }

    private fun setRecyclerView() {
        binding.rvFavBlog.apply {
            blogAdaper = FavoriteBlogAdapter()
            adapter = blogAdaper
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            setHasFixedSize(true)
        }
    }

    private fun showEmptyDatabaseView(emptyDb: Boolean) {
        if (emptyDb) {
            binding.ivEmptyDb.visibility = View.VISIBLE
            binding.tvEmptyDb.visibility = View.VISIBLE


        } else {
            binding.ivEmptyDb.visibility = View.GONE
            binding.tvEmptyDb.visibility = View.GONE

        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.favorite_menu, menu)
        this.menu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.miDeleteFavorites -> {

                // showEmptyDatabaseView(true)
                // menu?.getItem(0)?.isVisible = false
                // showEmptyDatabaseView(true)
                val action = DeleteFavoriteBlogsFragmentDirections.actionGlobalDeleteBlogFavoritesragment()
                findNavController().navigate(action)
                //viewmodel.deleteAllArticles()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    private fun loadInterstitialAd() {
        var adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context,
            "ca-app-pub-2987212294567736/2629608823",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            })

    }

    private fun showInterstitialAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent()
                }

                override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                    super.onAdFailedToShowFullScreenContent(adError)
                }

                override fun onAdShowedFullScreenContent() {
                    mInterstitialAd = null
                }

            }
            mInterstitialAd?.show(requireActivity())

        }


    }
}