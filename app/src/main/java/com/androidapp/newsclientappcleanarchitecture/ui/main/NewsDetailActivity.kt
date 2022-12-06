package com.androidapp.newsclientappcleanarchitecture.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.androidapp.newsclientappcleanarchitecture.R
import com.squareup.picasso.Picasso

class NewsDetailActivity: AppCompatActivity() {

    private lateinit var title: String
    private lateinit var author: String
    private lateinit var publishedAt: String
    private lateinit var description: String
    private lateinit var content: String
    private lateinit var imageUrl: String
    private lateinit var url: String
    private lateinit var titleTV: TextView
    private lateinit var authorTV: TextView
    private lateinit var publishedAtTV: TextView
    private lateinit var subDescriptionTV: TextView
    private lateinit var contentTV: TextView
    private lateinit var newsIV: ImageView
    private lateinit var readNewsBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_full_details)

        titleTV = findViewById(R.id.idTVTitle)
        authorTV = findViewById(R.id.idTVAuthor)
        publishedAtTV = findViewById(R.id.idTVPublishedAt)
        subDescriptionTV = findViewById(R.id.idTVSubDescription)
        contentTV = findViewById(R.id.idTVContent)
        newsIV = findViewById(R.id.idIVNews)
        readNewsBtn = findViewById(R.id.idBtnReadNews)

        title = intent.getStringExtra("title").toString()
        author = intent.getStringExtra("author").toString()
        publishedAt = intent.getStringExtra("publishedAt").toString()
        description = intent.getStringExtra("description").toString()
        content = intent.getStringExtra("content").toString()
        imageUrl = intent.getStringExtra("image").toString()
        url = intent.getStringExtra("url").toString()

        titleTV.text = title
        authorTV.text = author
        publishedAtTV.text = StringBuilder().append("Date of Published: ").append(publishedAt.substring(0,10))
        subDescriptionTV.text = description
        contentTV.text = content
        Picasso.get().load(imageUrl)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.no_image_available)
            .into(newsIV)

        readNewsBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}
