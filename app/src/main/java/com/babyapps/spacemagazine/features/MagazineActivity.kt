package com.babyapps.spacemagazine.features

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.babyapps.spacemagazine.R
import com.babyapps.spacemagazine.databinding.ActivityMagazineBinding
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint
import android.content.ActivityNotFoundException
import android.view.View


@AndroidEntryPoint
class MagazineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMagazineBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMagazineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Hide action bar
        //supportActionBar?.hide()

        setSupportActionBar(binding.toolbar)
        setBottomBanner()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.articlesFragment,
                R.id.blogsFragment,
                R.id.reportsFragment,
                R.id.favoritesFragment,
            ), binding.drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.bottomNav.setupWithNavController(navController)
        binding.navView.setupWithNavController(navController)
        binding.navView.itemIconTintList = null
        binding.bottomNav.itemIconTintList = null


        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.contactUs -> sendEmail()
                R.id.shareApp -> shareApp()
                R.id.rateApp -> rateMe()

                R.id.appInfoFragment -> navController.navigate(R.id.appInfoFragment)
                R.id.termsAndConditions -> goTermsAndConditions()
                R.id.privacyPolicy -> goPrivacyPolicy()
            }
            binding.drawerLayout.closeDrawers()

            true
        }


    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    private fun rateMe() {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$packageName")
                )
            )
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=$packageName")
                )
            )
        }
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto: muctebaeser@gmail.com")
        intent.putExtra(Intent.EXTRA_SUBJECT, "Your Subject")
        intent.putExtra(Intent.EXTRA_TEXT, "Your ideas...")
        startActivity(intent)
    }


    private fun shareApp() {
        var intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.com.babyapps.spacemagazine")
            putExtra(Intent.EXTRA_SUBJECT, "Try Space Magazine")
            type = "text/plain"
        }
        startActivity(intent)
    }

    private fun goTermsAndConditions() {
        var intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://sites.google.com/view/spacemagazinetermsconditions")
        )
        startActivity(intent)
    }

    private fun goPrivacyPolicy() {
        var intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://sites.google.com/view/spacemagazineprivacypolicy")
        )
        startActivity(intent)
    }


    private fun setBottomBanner() {
        MobileAds.initialize(this) {}

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                // Code to be executed when an ad request fails.
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

        }

    }
}