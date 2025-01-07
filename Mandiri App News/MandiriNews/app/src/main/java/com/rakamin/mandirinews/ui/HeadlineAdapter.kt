package com.rakamin.mandirinews.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rakamin.mandirinews.R
import com.rakamin.mandirinews.data.News
import com.squareup.picasso.Picasso

class HeadlineAdapter: RecyclerView.Adapter<HeadlineAdapter.HeadlineViewHolder>() {
    private val headline = ArrayList<News>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setHeadline(news: ArrayList<News>) {
        headline.clear()
        headline.addAll(news)
        notifyDataSetChanged()
    }

    inner class HeadlineViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun item(news: News) {
            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(news)
            }

            val tvTitle: TextView = itemView.findViewById(R.id.tv_headline_title)
            val tvSourceName: TextView = itemView.findViewById(R.id.tv_headline_source_name)
            val tvPublishedAt: TextView = itemView.findViewById(R.id.tv_headline_published_at)
            val ivImage: ImageView = itemView.findViewById(R.id.iv_headline_image)

            val yy = news.publishedAt.subSequence(0,4)
            val mm = when(news.publishedAt.subSequence(5,7)) {
                "01" -> "Jan"
                "02" -> "Feb"
                "03" -> "Mar"
                "04" -> "Apr"
                "05" -> "Mei"
                "06" -> "Jun"
                "07" -> "Jul"
                "08" -> "Agust"
                "09" -> "Sept"
                "10" -> "Okt"
                "11" -> "Nov"
                "12" -> "Des"
                else -> "- " + news.publishedAt.subSequence(5,7)
            }
            val dd = news.publishedAt.subSequence(8,10)

            tvTitle.text = news.title
            tvPublishedAt.text = "$dd $mm, $yy"
            tvSourceName.text = news.source.name

            Picasso.get()
                .load(news.urlToImage)
                .placeholder(R.drawable.image_default_news)
                .into(ivImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadlineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_headline, parent, false)
        return HeadlineViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeadlineViewHolder, position: Int) {
        holder.item(headline[position])
    }

    override fun getItemCount(): Int = headline.size

    interface OnItemClickCallback {
        fun onItemClicked(news: News)
    }
}