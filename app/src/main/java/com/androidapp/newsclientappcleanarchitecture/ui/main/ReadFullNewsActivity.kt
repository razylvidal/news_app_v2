package com.androidapp.newsclientappcleanarchitecture.ui.main

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.androidapp.newsclientappcleanarchitecture.LogHelper
import com.androidapp.newsclientappcleanarchitecture.R
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.domain.usecase.FetchingDataUseCase

class ReadFullNewsActivity:AppCompatActivity() {

    private lateinit var url: String
    private lateinit var webView: WebView
    private  var newsData: List<ArticleDetails> = listOf()
    private var useCase: FetchingDataUseCase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_full_details)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        webView = findViewById(R.id.news_webview)
        url = intent.getStringExtra("url").toString()
       newsData.map {
           intent.getStringExtra("title").toString()
           intent.getStringExtra("author").toString()
           intent.getStringExtra("publishedAt").toString()
           intent.getStringExtra("description").toString()
           intent.getStringExtra("content").toString()
           intent.getStringExtra("image").toString()
           url
       }.toList()


        startWebView(url)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.action_read_full_news_act, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.saved_news ->{

                LogHelper.log("item clicked")
                return true
            }
            R.id.share_news -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT, "Hey, checkout this news : $url")
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Share with :"))
                return true
            }
            R.id.browse_news -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun startWebView(url: String){

        webView.apply {
            settings.apply {
                javaScriptEnabled = true
                builtInZoomControls = true
                useWideViewPort = true
                loadWithOverviewMode = true
                domStorageEnabled = true
                loadsImagesAutomatically = true
            }

            val alertDialog = AlertDialog.Builder(this@ReadFullNewsActivity).create()
            alertDialog.setTitle("Please wait")
            alertDialog.setMessage("Loading resources...")
            alertDialog.show()

            webView.webViewClient = object : WebViewClient() {

                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest,
                ): Boolean{
                    view.loadUrl(request.url.toString())
                    return true
                }

                override fun onPageFinished(view: WebView, url: String) {
                    if (alertDialog.isShowing) {
                        alertDialog.dismiss()
                    }
                }
            }

        }
        webView.loadUrl(url);
    }
}