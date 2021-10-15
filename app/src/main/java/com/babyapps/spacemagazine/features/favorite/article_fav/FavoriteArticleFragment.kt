package com.babyapps.spacemagazine.features.favorite.article_fav

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.babyapps.spacemagazine.R
import com.babyapps.spacemagazine.databinding.*
import com.babyapps.spacemagazine.domain.model.Article
import com.babyapps.spacemagazine.features.article.ArticlesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteArticleFragment : Fragment(R.layout.fragment_favorite_article) {
    private lateinit var articleAdaper: FavoriteArticleAdapter
    private val viewmodel: ArticlesViewModel by viewModels()
    private var menu: Menu? = null
    private lateinit var toSend: Article
    val args: FavoriteArticleFragmentArgs by navArgs()

    private lateinit var binding: FragmentFavoriteArticleBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteArticleBinding.bind(view)


        val article = args.favoriteArticle
        toSend = article

        binding.apply {
            webView.apply {
                webViewClient = WebViewClient()
                loadUrl(article.url)

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