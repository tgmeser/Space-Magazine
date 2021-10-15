package com.babyapps.spacemagazine.features.favorite.report_fav

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
import com.babyapps.spacemagazine.databinding.FragmentFavoriteReportsBinding
import com.babyapps.spacemagazine.features.favorite.article_fav.DeleteFavoriteArticlesFragmentDirections
import com.babyapps.spacemagazine.features.report.ReportsViewModel
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteReportsFragment : Fragment(R.layout.fragment_favorite_reports) {
    private lateinit var reportAdaper: FavoriteReportAdapter
    private val viewmodel: ReportsViewModel by viewModels()
    private lateinit var binding: FragmentFavoriteReportsBinding
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
        binding = FragmentFavoriteReportsBinding.bind(view)

        loadInterstitialAd()

        setRecyclerView()
        subscribeObservers()



        reportAdaper.setOnItemClickListener {
            showInterstitialAd()
            val bundle = Bundle().apply {
                putSerializable("favoriteReport", it)
            }
            findNavController().navigate(
                R.id.action_favoriteReportsFragment_to_favoriteReportFragment,
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
                val report = reportAdaper.differ.currentList[position]
                viewmodel.deleteReport(report)
                Snackbar.make(view, "Removed", Snackbar.LENGTH_LONG).apply {
                    setAction("Un Do") {
                        viewmodel.unDoReport(report)
                    }
                    show()
                }
            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.rvFavReport)
        }

    }
    private fun subscribeObservers() {
        viewmodel.emptyDb.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseView(it)
        })

        viewmodel.getFavoriteReports().observe(viewLifecycleOwner, Observer { reports ->
            viewmodel.checkDatabaseIsEmptyOrNot(reports)
            reportAdaper.differ.submitList(reports)

            menu?.getItem(0)?.isVisible = reports.isNotEmpty()

        })




    }

    private fun setRecyclerView() {
        binding.rvFavReport.apply {
            reportAdaper = FavoriteReportAdapter()
            adapter = reportAdaper
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
                val action = DeleteFavoriteReportsFragmentDirections.actionGlobalDeleteReportFavoritesragment()
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