package com.babyapps.spacemagazine.features.favorite.blog_fav

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.babyapps.spacemagazine.R
import com.babyapps.spacemagazine.databinding.FragmentFavoriteBlogBinding
import com.babyapps.spacemagazine.domain.model.Blog
import com.babyapps.spacemagazine.features.blog.BlogsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FavoriteBlogFragment : Fragment(R.layout.fragment_favorite_blog) {
    private lateinit var blogAdaper: FavoriteBlogAdapter
    private val viewmodel: BlogsViewModel by viewModels()
    private var menu: Menu? = null
    private lateinit var toSend: Blog
    val args: FavoriteBlogFragmentArgs by navArgs()

    private lateinit var binding: FragmentFavoriteBlogBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBlogBinding.bind(view)


        val blog = args.favoriteBlog
        toSend = blog

        binding.apply {
            webView.apply {
                webViewClient = WebViewClient()
                loadUrl(blog.url)

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