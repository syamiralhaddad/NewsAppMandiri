package com.rakamin.mandirinews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.rakamin.mandirinews.R
import com.squareup.picasso.Picasso


class DetailsActivity: AppCompatActivity() {
    companion object {
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_TITLE = "extra_title"
        const val EXTRA_DESCRIPTION = "extra_description"
        const val EXTRA_SOURCENAME = "extra_sourcename"
        const val EXTRA_PUBLISHEDAT = "extra_publishedat"
    }

    private lateinit var ivImage: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvDesription: TextView
    private lateinit var tvSourceName: TextView
    private lateinit var tvPublishedAt: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        ivImage = findViewById(R.id.iv_details_image)
        tvTitle = findViewById(R.id.tv_details_title)
        tvDesription = findViewById(R.id.tv_details_description)
        tvSourceName = findViewById(R.id.tv_details_source_name)
        tvPublishedAt = findViewById(R.id.tv_details_published_at)

        tvTitle.text = intent.getStringExtra(EXTRA_TITLE)
        tvDesription.text = intent.getStringExtra(EXTRA_DESCRIPTION)
        tvSourceName.text = intent.getStringExtra(EXTRA_SOURCENAME)

        val publishedAt = intent.getStringExtra(EXTRA_PUBLISHEDAT)
        val yy = publishedAt?.subSequence(0,4)
        val mm = when(publishedAt?.subSequence(5,7)) {
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
            else -> "- " + publishedAt?.subSequence(5,7)
        }
        val dd = publishedAt?.subSequence(8,10)
        tvPublishedAt.text = "$dd $mm, $yy"

        Picasso.get()
            .load(intent.getStringExtra(EXTRA_IMAGE))
            .placeholder(R.drawable.image_default_news)
            .into(ivImage)
    }
}