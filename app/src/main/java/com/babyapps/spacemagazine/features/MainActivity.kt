package com.babyapps.spacemagazine.features

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import com.babyapps.spacemagazine.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Hide action bar
        //supportActionBar?.hide()

        startMagazineActivity()


    }
    private fun startMagazineActivity() {


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MagazineActivity::class.java))
            finish()
            // Your Code
        }, 4000)

    }
}