package com.babyapps.spacemagazine.features.favorite.report_fav

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.babyapps.spacemagazine.databinding.ItemReportBinding
import com.babyapps.spacemagazine.domain.model.Report
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FavoriteReportAdapter : RecyclerView.Adapter<FavoriteReportAdapter.ReportViewHolder>() {

    inner class ReportViewHolder(val binding: ItemReportBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindImage(report: Report) {
            binding.apply {
                Glide.with(itemView).load(report.imageUrl)
                    .apply(RequestOptions.overrideOf(160, 90)).centerCrop()
                    .into(ivReport)
            }
        }
    }

    val diffCallback = object : DiffUtil.ItemCallback<Report>() {

        override fun areItemsTheSame(
            oldItem: Report,
            newItem: Report
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: Report,
            newItem: Report
        ): Boolean =
            oldItem == newItem

    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder =
        ReportViewHolder(
            ItemReportBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val report = differ.currentList[position]
        var date = report.publishedAt.substring(0,10)
        var time = report.publishedAt.substring(11,16)
        holder.binding.apply {
            tvTitle.text = report.title
            tvNewssite.text = " ${report.newsSite}"
            tvSummary.text = report.summary
            tvPublishDate.text = " ${date}"
            tvPublishTime.text = " ${time}"
            holder.bindImage(report)
        }

        holder.binding.root.setOnClickListener { onItemClickListener?.let { it(report) } }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private var onItemClickListener: ((Report) -> Unit)? = null

    fun setOnItemClickListener(listener: (Report) -> Unit) {
        onItemClickListener = listener
    }
}