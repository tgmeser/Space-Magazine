package com.babyapps.spacemagazine.features.report

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.babyapps.spacemagazine.data.remote.dto.ReportDto
import com.babyapps.spacemagazine.databinding.ItemReportBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class ReportAdapter : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    inner class ReportViewHolder(val binding: ItemReportBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindImage(reportDto: ReportDto) {
            binding.apply {
                Glide.with(itemView).load(reportDto.imageUrl)
                    .apply(RequestOptions.overrideOf(160, 90)).centerCrop()
                    .into(ivReport)
            }
        }
    }

    val diffCallback = object : DiffUtil.ItemCallback<ReportDto>() {

        override fun areItemsTheSame(
            oldItem: ReportDto,
            newItem: ReportDto
        ): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: ReportDto,
            newItem: ReportDto
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

    private var onItemClickListener: ((ReportDto) -> Unit)? = null

    fun setOnItemClickListener(listener: (ReportDto) -> Unit) {
        onItemClickListener = listener
    }
}