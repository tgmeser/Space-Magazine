package com.babyapps.spacemagazine.features.favorite.report_fav

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.babyapps.spacemagazine.R
import com.babyapps.spacemagazine.databinding.FragmentFavoriteReportBinding
import com.babyapps.spacemagazine.domain.model.Report
import com.babyapps.spacemagazine.features.report.ReportsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteReportFragment : Fragment(R.layout.fragment_favorite_report) {
    private lateinit var reportAdaper: FavoriteReportAdapter
    private val viewmodel: ReportsViewModel by viewModels()
    private var menu: Menu? = null
    private lateinit var toSend: Report
    val args: FavoriteReportFragmentArgs by navArgs()

    private lateinit var binding: FragmentFavoriteReportBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteReportBinding.bind(view)


        val report = args.favoriteReport
        toSend = report

        binding.apply {
            webView.apply {
                webViewClient = WebViewClient()
                loadUrl(report.url)

            }


        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.share_menu, menu)
        this.menu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.miShare -> {

                var intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "${toSend?.url}")
                    putExtra(Intent.EXTRA_SUBJECT, "${toSend?.title}")
                    type = "text/plain"
                }
                startActivity(intent)

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}