package com.babyapps.spacemagazine.features

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.babyapps.spacemagazine.R
import com.babyapps.spacemagazine.databinding.FragmentAppInfoBinding

class AppInfoFragment: Fragment(R.layout.fragment_app_info) {
    private lateinit var binding: FragmentAppInfoBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentAppInfoBinding.bind(view)

        binding.tvMail.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto: muctebaeser@gmail.com")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Your Subject")
            intent.putExtra(Intent.EXTRA_TEXT, "Your ideas...")
            startActivity(intent)
        }

    }
}