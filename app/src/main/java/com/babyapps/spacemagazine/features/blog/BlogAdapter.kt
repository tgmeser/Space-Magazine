package com.babyapps.spacemagazine.features.blog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.babyapps.spacemagazine.data.remote.dto.BlogDto
import com.babyapps.spacemagazine.databinding.ItemBlogBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class BlogAdapter : RecyclerView.Adapter<BlogAdapter.BlogViewHolder>() {

    inner class BlogViewHolder(val binding: ItemBlogBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindImage(blogDto: BlogDto) {
            binding.apply {
                Glide.with(itemView).load(blogDto.imageUrl)
                    .apply(RequestOptions.overrideOf(160, 90)).centerCrop()
                    .into(ivBlog)
            }
        }
    }

    val diffCallback = object : DiffUtil.ItemCallback<BlogDto>() {

        override fun areItemsTheSame(
            oldItem: BlogDto,
            newItem: BlogDto
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: BlogDto,
            newItem: BlogDto
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

    private var onItemClickListener: ((BlogDto) -> Unit)? = null

    fun setOnItemClickListener(listener: (BlogDto) -> Unit) {
        onItemClickListener = listener
    }
}