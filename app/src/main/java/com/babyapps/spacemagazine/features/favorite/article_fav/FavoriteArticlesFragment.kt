package com.babyapps.spacemagazine.features.favorite.article_fav

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
import com.babyapps.spacemagazine.databinding.*
import com.babyapps.spacemagazine.features.article.ArticlesViewModel
import com.babyapps.spacemagazine.features.favorite.FavoritesFragmentDirections
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteArticlesFragment : Fragment(R.layout.fragment_favorite_articles) {
    private lateinit var articleAdaper: FavoriteArticleAdapter
    private val viewmodel: ArticlesViewModel by viewModels()
    private lateinit var binding: FragmentFavoriteArticlesBinding
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
        binding = FragmentFavoriteArticlesBinding.bind(view)


        setRecyclerView()
        subscribeObservers()
        loadInterstitialAd()



        articleAdaper.setOnItemClickListener {
            showInterstitialAd()
            val bundle = Bundle().apply {
                putSerializable("favoriteArticle", it)
            }
            findNavController().navigate(
                R.id.action_favoriteArticlesFragment_to_favoriteArticleFragment,
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
                val article = articleAdaper.differ.currentList[position]
                viewmodel.deleteArticle(article)
                Snackbar.make(view, "Removed", Snackbar.LENGTH_LONG).apply {
                    setAction("Un Do") {
                        viewmodel.unDoArticle(article)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvFavArticle)
        }

    }
    private fun subscribeObservers() {
        viewmodel.emptyDb.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseView(it)
        })

        viewmodel.getFavoriteArticles().observe(viewLifecycleOwner, Observer { articles ->
            viewmodel.checkDatabaseIsEmptyOrNot(articles)
            articleAdaper.differ.submitList(articles)

            menu?.getItem(0)?.isVisible = articles.isNotEmpty()

        })




    }

    private fun setRecyclerView() {
        binding.rvFavArticle.apply {
            articleAdaper = FavoriteArticleAdapter()
            adapter = articleAdaper
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
                val action =DeleteFavoriteArticlesFragmentDirections.actionGlobalDeleteFavoritesragment()
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