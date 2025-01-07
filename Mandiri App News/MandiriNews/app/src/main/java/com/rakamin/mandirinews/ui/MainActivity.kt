package com.rakamin.mandirinews.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rakamin.mandirinews.R
import com.rakamin.mandirinews.data.News

class MainActivity: AppCompatActivity() {
    private lateinit var adapterHeadline: HeadlineAdapter
    private lateinit var adapterEverything: EverythingAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var progressBarHeadline: ProgressBar
    private lateinit var progressBarEverything: ProgressBar
    private lateinit var recyclerViewHeadline: RecyclerView
    private lateinit var recyclerViewEverything: RecyclerView
    private lateinit var viewModel: MainViewModel
    private var page = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapterHeadline = HeadlineAdapter()
        adapterEverything = EverythingAdapter()
        progressBarHeadline = findViewById(R.id.pb_headline)
        progressBarEverything = findViewById(R.id.pb_everything)
        recyclerViewHeadline = findViewById(R.id.rv_headline)
        recyclerViewEverything = findViewById(R.id.rv_everything)
        layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

        recyclerViewHeadline.setHasFixedSize(true)
        recyclerViewHeadline.layoutManager = LinearLayoutManager(this)
        recyclerViewHeadline.adapter = adapterHeadline

        recyclerViewEverything.setHasFixedSize(true)
        recyclerViewEverything.layoutManager = layoutManager
        recyclerViewEverything.adapter = adapterEverything

        viewModel.setHeadline()
        viewModel.getHeadline().observe(this) {
            adapterHeadline.setHeadline(it)
            progressBarHeadline.visibility = View.GONE
        }
        adapterHeadline.setOnItemClickCallback(object: HeadlineAdapter.OnItemClickCallback {
            override fun onItemClicked(news: News) {
                getDetails(news.urlToImage, news.title, news.description,
                    news.source.name, news.publishedAt)
            }
        })

        scrollEverything()
        recyclerViewEverything.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                progressBarEverything.visibility = View.VISIBLE
                val visibleItemCount = layoutManager.childCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()
                val total = adapterEverything.itemCount

                if(visibleItemCount + pastVisibleItem >= total) {
                    page += 2
                    scrollEverything()
                }
            }
        })
        adapterEverything.setOnItemClickCallback(object: EverythingAdapter.OnItemClickCallback {
            override fun onItemClicked(news: News) {
                getDetails(news.urlToImage, news.title, news.description,
                    news.source.name, news.publishedAt)
            }
        })
    }

    private fun scrollEverything() {
        Handler().postDelayed({
            viewModel.setEverything(page)
            viewModel.getEverything().observe(this) {
                adapterEverything.setEverything(it)
                progressBarEverything.visibility = View.GONE
            }
        }, 4000)
    }

    private fun getDetails(urlToImage: String, title: String, description: String, sourceName: String, publishedAt: String) {
        Intent(this@MainActivity, DetailsActivity::class.java).also {
            it.putExtra(DetailsActivity.EXTRA_IMAGE, urlToImage)
            it.putExtra(DetailsActivity.EXTRA_TITLE, title)
            it.putExtra(DetailsActivity.EXTRA_DESCRIPTION, description)
            it.putExtra(DetailsActivity.EXTRA_SOURCENAME, sourceName)
            it.putExtra(DetailsActivity.EXTRA_PUBLISHEDAT, publishedAt)
            startActivity(it)
        }
    }
}