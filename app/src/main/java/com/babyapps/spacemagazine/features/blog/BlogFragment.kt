package com.babyapps.spacemagazine.features.blog

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.babyapps.spacemagazine.R
import com.babyapps.spacemagazine.data.remote.dto.BlogDto
import com.babyapps.spacemagazine.databinding.FragmentBlogBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class BlogFragment : Fragment(R.layout.fragment_blog) {
    private lateinit var binding: FragmentBlogBinding
    private var menu: Menu? = null

    private lateinit var toSend: BlogDto
    private val viewmodel: BlogsViewModel by viewModels()

    val args: BlogFragmentArgs by navArgs()
    private var checkedEmpty = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBlogBinding.bind(view)

        val blog = args.blogDto
        toSend = blog
        viewmodel.isSaved(blog.url).observe(viewLifecycleOwner, Observer { blogs ->
            checkedEmpty = blogs.isEmpty()
        })
        binding.apply {
            webView.apply {
                webViewClient = WebViewClient()
                loadUrl(blog.url)

            }

            fab.setOnClickListener {
                if (checkedEmpty) {
                    viewmodel.insertIntoFavorites(blog)
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