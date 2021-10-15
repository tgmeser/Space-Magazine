package com.babyapps.spacemagazine.features.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.babyapps.spacemagazine.data.remote.dto.ArticleDto
import com.babyapps.spacemagazine.databinding.ItemArticleBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindImage(articleDto: ArticleDto) {
            binding.apply {
                Glide.with(itemView).load(articleDto.imageUrl)
                    .apply(RequestOptions.overrideOf(160, 90)).centerCrop()
                    .into(ivArticle)
            }
        }
    }

    val diffCallback = object : DiffUtil.ItemCallback<ArticleDto>() {

        override fun areItemsTheSame(
            oldItem: ArticleDto,
            newItem: ArticleDto
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ArticleDto,
            newItem: ArticleDto
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

    private var onItemClickListener: ((ArticleDto) -> Unit)? = null

    fun setOnItemClickListener(listener: (ArticleDto) -> Unit) {
        onItemClickListener = listener
    }
}