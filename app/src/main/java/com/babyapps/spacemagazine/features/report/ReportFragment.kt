package com.babyapps.spacemagazine.features.report

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.babyapps.spacemagazine.R
import com.babyapps.spacemagazine.data.remote.dto.ReportDto
import com.babyapps.spacemagazine.databinding.FragmentReportBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportFragment : Fragment(R.layout.fragment_report) {
    private lateinit var binding: FragmentReportBinding
    private var menu: Menu? = null

    private lateinit var toSend: ReportDto
    private val viewmodel: ReportsViewModel by viewModels()

    val args: ReportFragmentArgs by navArgs()
    private var checkedEmpty = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentReportBinding.bind(view)

        val report = args.reportDto
        toSend = report
        viewmodel.isSaved(report.url).observe(viewLifecycleOwner, Observer { reports ->
            checkedEmpty = reports.isEmpty()
        })
        binding.apply {
            webView.apply {
                webViewClient = WebViewClient()
                loadUrl(report.url)

            }

            fab.setOnClickListener {
                if (checkedEmpty) {
                    viewmodel.insertIntoFavorites(report)
                    //Snackbar.make(view, "Saved successfully", Snackbar.LENGTH_SHORT).show()
                    Snackbar.make(view, "Saved into favorites", Snackbar.LENGTH_LONG).show()
                } else {
                    Snackbar.make(view, "You have already saved !", Snackbar.LENGTH_LONG).show()

                }

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