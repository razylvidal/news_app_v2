package com.androidapp.newsclientappcleanarchitecture.ui.main

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.androidapp.newsclientappcleanarchitecture.LogHelper
import com.androidapp.newsclientappcleanarchitecture.R
import com.androidapp.newsclientappcleanarchitecture.data.database.ArticleDBViewModel
import com.androidapp.newsclientappcleanarchitecture.data.database.ArticleEntity
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails

class ReadFullNewsActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var articleData: ArticleDetails
    private lateinit var viewModel: ArticleDBViewModel

    companion object{
        const val KEY_ARTICLE_DETAILS = "key_article_details"
        fun getIntent(context: Context, articleDetails: ArticleDetails): Intent {
            return Intent(context, ReadFullNewsActivity::class.java).apply {
                putExtra(KEY_ARTICLE_DETAILS, articleDetails)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_full_details)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        webView = findViewById(R.id.news_webview)

        viewModel = ViewModelProvider(this)[ArticleDBViewModel::class.java]

        articleData = getArticleDetails()

        startWebView(articleData.url.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.action_read_full_news_act, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save_news -> {
                this.let { viewModel.addNewsToDB(this, articleData) }
                Toast.makeText(this@ReadFullNewsActivity,
                    "News saved successfully!", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.action_share_news -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT, "Hey, checkout this news : ${articleData.url}")
                intent.type = "text/plain"
                startActivity(Intent.createChooser(intent, "Share with :"))
                return true
            }
            R.id.action_browse_news -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(articleData.url))
                startActivity(intent)
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun startWebView(url: String) {

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
                ): Boolean {
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

    private fun getArticleDetails() : ArticleDetails {
        return intent.getParcelableExtra(KEY_ARTICLE_DETAILS)
            ?:throw java.lang
                .IllegalStateException("Please use ReadFullNewsActivity.getIntent() to start activity")
    }

}