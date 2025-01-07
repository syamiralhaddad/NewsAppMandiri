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

class EverythingAdapter: RecyclerView.Adapter<EverythingAdapter.EverythingViewHolder>() {
    private val everything = ArrayList<News>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setEverything(news: ArrayList<News>) {
        everything.clear()
        everything.addAll(news)
        notifyDataSetChanged()
    }

    inner class EverythingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun items(news: News) {
            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(news)
            }

            val tvTitle: TextView = itemView.findViewById(R.id.tv_everything_title)
            val tvSourceName: TextView = itemView.findViewById(R.id.tv_everything_source_name)
            val tvPublishedAt: TextView = itemView.findViewById(R.id.tv_everything_published_at)
            val ivImage: ImageView = itemView.findViewById(R.id.iv_everything_image)

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EverythingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_everything, parent, false)
        return EverythingViewHolder(view)
    }

    override fun onBindViewHolder(holder: EverythingViewHolder, position: Int) {
        holder.items(everything[position])
    }

    override fun getItemCount(): Int = everything.size

    interface OnItemClickCallback {
        fun onItemClicked(news: News)
    }
}