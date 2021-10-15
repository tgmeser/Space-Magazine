package com.babyapps.spacemagazine.features.favorite.blog_fav

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.babyapps.spacemagazine.databinding.ItemBlogBinding
import com.babyapps.spacemagazine.domain.model.Blog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FavoriteBlogAdapter : RecyclerView.Adapter<FavoriteBlogAdapter.BlogViewHolder>() {

    inner class BlogViewHolder(val binding: ItemBlogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindImage(blog: Blog) {
            binding.apply {
                Glide.with(itemView).load(blog.imageUrl)
                    .apply(RequestOptions.overrideOf(160, 90)).centerCrop()
                    .into(ivBlog)
            }
        }
    }

    val diffCallback = object : DiffUtil.ItemCallback<Blog>() {

        override fun areItemsTheSame(
            oldItem: Blog,
            newItem: Blog
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Blog,
            newItem: Blog
        ): Boolean =
            oldItem == newItem

    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder =
        BlogViewHolder(
            ItemBlogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val blog = differ.currentList[position]

        var date = blog.publishedAt.substring(0,10)
        var time = blog.publishedAt.substring(11,16)
        holder.binding.apply {
            tvTitle.text = blog.title
            tvNewssite.text = " ${blog.newsSite}"
            tvSummary.text = blog.summary
            tvPublishDate.text = " ${date}"
            tvPublishTime.text = " ${time}"
            holder.bindImage(blog)
        }

        holder.binding.root.setOnClickListener { onItemClickListener?.let { it(blog) } }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private var onItemClickListener: ((Blog) -> Unit)? = null

    fun setOnItemClickListener(listener: (Blog) -> Unit) {
        onItemClickListener = listener
    }
}