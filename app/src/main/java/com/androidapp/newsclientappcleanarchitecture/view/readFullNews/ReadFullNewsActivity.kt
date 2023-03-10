package com.androidapp.newsclientappcleanarchitecture.view.readFullNews

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.androidapp.newsclientappcleanarchitecture.R
import com.androidapp.newsclientappcleanarchitecture.databinding.ActivityNewsFullDetailsBinding
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.utils.openInBrowser
import com.androidapp.newsclientappcleanarchitecture.utils.startShareNewsAct
import com.androidapp.newsclientappcleanarchitecture.view.main.show
import com.androidapp.newsclientappcleanarchitecture.view.main.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReadFullNewsActivity : AppCompatActivity(), ReadFullNewsContract.View {

    private lateinit var articleData: ArticleDetails

    @Inject
    lateinit var presenter: ReadFullNewsPresenter
    private lateinit var binding: ActivityNewsFullDetailsBinding
    //private lateinit var dbInstance: SavedArticlesDatabase

    companion object {
        const val KEY_ARTICLE_DETAILS = "key_article_details"
        fun getIntent(context: Context, articleDetails: ArticleDetails): Intent {
            return Intent(context, ReadFullNewsActivity::class.java).apply {
                putExtra(KEY_ARTICLE_DETAILS, articleDetails)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsFullDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.readFullNewsToolbar)
        //dbInstance = (application as NewsApp).database

        articleData = getArticleDetails()
        startWebView(articleData.url)
        refreshWebView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_read_full_news_act, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save_news -> {
                presenter.handleArticleToInsert(articleData)
                showToast("News saved successfully!")
                return true
            }
            R.id.action_share_news -> {
                startShareNewsAct(articleData.url, this@ReadFullNewsActivity)
                return true
            }
            R.id.action_browse_news -> {
                openInBrowser(articleData.url, this@ReadFullNewsActivity)
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun startWebView(url: String) {
        binding.newsWebview.apply {
            settings.apply {
                javaScriptEnabled = true
                builtInZoomControls = true
                useWideViewPort = true
                loadWithOverviewMode = true
                domStorageEnabled = true
                loadsImagesAutomatically = true
            }
            val alertDialog = AlertDialog.Builder(this@ReadFullNewsActivity).apply {
                setNegativeButton("Cancel") { _, _ ->
                    this@ReadFullNewsActivity.finish()
                }
            }.create()
            showAlertDialog(alertDialog, true)

            binding.newsWebview.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest,
                ): Boolean {
                    view.loadUrl(request.url.toString())
                    return true
                }

                override fun onPageFinished(view: WebView, url: String) {
                    if (alertDialog.isShowing) {
                        showAlertDialog(alertDialog, false)
                    }
                }
            }

        }
        binding.newsWebview.loadUrl(url)
    }

    override fun showAlertDialog(alertDialog: AlertDialog, isVisible: Boolean) {
        if (isVisible) {
            alertDialog.show(
                "Please Wait",
                "Loading Resources...",
                false
            )
        }
        else alertDialog.dismiss()
    }

    override fun showToast(message: String) {
        toast(this@ReadFullNewsActivity, message)
    }

    private fun refreshWebView() {
        binding.srlRefreshWeb.setOnRefreshListener {
            startWebView(articleData.url)
            binding.srlRefreshWeb.isRefreshing = false
        }
    }

    private fun getArticleDetails(): ArticleDetails {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(KEY_ARTICLE_DETAILS, ArticleDetails::class.java)!!
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra(KEY_ARTICLE_DETAILS)!!
            }
        } catch (exception: Exception) {
            throw java.lang
                .IllegalStateException("Please use ReadFullNewsAct.getIntent() to start activity")
        }
    }

    override fun onDestroy() {
        presenter.onReadFullNewsViewDestroyed()
        super.onDestroy()
    }
}