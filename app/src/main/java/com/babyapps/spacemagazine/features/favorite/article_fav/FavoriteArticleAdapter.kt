package com.babyapps.spacemagazine.features.favorite.article_fav

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.babyapps.spacemagazine.databinding.ItemArticleBinding
import com.babyapps.spacemagazine.domain.model.Article
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class FavoriteArticleAdapter : RecyclerView.Adapter<FavoriteArticleAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindImage(article: Article) {
            binding.apply {
                Glide.with(itemView).load(article.imageUrl)
                    .apply(RequestOptions.overrideOf(160, 90)).centerCrop()
                    .into(ivArticle)
            }
        }
    }

    val diffCallback = object : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Article,
            newItem: Article
        ): Boolean =
            oldItem == newItem

    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(
            ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]

        var date = article.publishedAt.substring(0,10)
        var time = article.publishedAt.substring(11,16)
        holder.binding.apply {
            tvTitle.text = article.title
            tvNewssite.text = " ${article.newsSite}"
            tvSummary.text = article.summary
            tvPublishDate.text = " ${date}"
            tvPublishTime.text = " ${time}"
            holder.bindImage(article)
        }

        holder.binding.root.setOnClickListener { onItemClickListener?.let { it(article) } }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private var onItemClickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit) {
        onItemClickListener = listener
    }
}