package com.androidapp.newsclientappcleanarchitecture.view.main.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidapp.newsclientappcleanarchitecture.R
import com.androidapp.newsclientappcleanarchitecture.databinding.FragmentHomeBinding
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.utils.Constants.Companion.DEFAULT_CATEGORY
import com.androidapp.newsclientappcleanarchitecture.utils.Constants.Companion.TOP_HEADLINES_COUNT
import com.androidapp.newsclientappcleanarchitecture.utils.getTimeDifference
import com.androidapp.newsclientappcleanarchitecture.utils.startReadFullNewsAct
import com.androidapp.newsclientappcleanarchitecture.view.adapters.CustomAdapter
import com.androidapp.newsclientappcleanarchitecture.view.main.hide
import com.androidapp.newsclientappcleanarchitecture.view.main.show
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), FragmentContract.View {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var articleAdapter: CustomAdapter

    @Inject
    lateinit var presenter: CustomPresenter

    companion object {
        fun homeNewInstance() = HomeFragment()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        binding.recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        articleAdapter = CustomAdapter(mutableListOf())
        binding.recyclerView.adapter = articleAdapter
        presenter.onViewReady(this, DEFAULT_CATEGORY)
        binding.homeSwipeRefresh.setOnRefreshListener {
            refreshList()
        }
        return view
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun showArticles(response: MutableList<ArticleDetails>) {
        binding.tvTopHeadlines.show()
        val topHeadlines = response.slice(0 until TOP_HEADLINES_COUNT)
        val headlines = response.slice(TOP_HEADLINES_COUNT until response.size)
            .toMutableList()
        displayToCarousel(topHeadlines)
        displayRemainingArticles(headlines)
    }

    override fun showShimmerLayout(isVisible: Boolean) {
        if (isVisible) {
            binding.tvTopHeadlines.hide()
            articleAdapter.clear()
            binding.shimmerLayout.visibility = View.VISIBLE
        } else {
            binding.shimmerLayout.visibility = View.GONE

        }
    }
    private fun refreshList() {
        showShimmerLayout(true)
        binding.homeCarousel.hide()
        binding.homeCarousel.autoPlay = false
        presenter.makeArticleRequest(DEFAULT_CATEGORY)
        binding.homeSwipeRefresh.isRefreshing = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun displayToCarousel(topHeadlines : List<ArticleDetails>){
        binding.homeCarousel.visibility = View.VISIBLE
        binding.homeCarousel.apply {
            size = topHeadlines.size
            autoPlay = true
            setCarouselViewListener { view, position ->
                val imageView = view.findViewById<ImageView>(R.id.iv_articleImage)
                Picasso.get()
                    .load(topHeadlines[position].urlToImage.toString())
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.breaking_news)
                    .into(imageView)

                val title = view.findViewById<TextView>(R.id.tv_title)
                title.text = topHeadlines[position].title

                val source = view.findViewById<TextView>(R.id.tv_source)
                source.text = topHeadlines[position].source.name

                val timePosted = view.findViewById<TextView>(R.id.tv_publishedAt)
                timePosted.text = getTimeDifference(topHeadlines[position].publishedAt)

                view.setOnClickListener {
                    startReadFullNewsAct(requireContext(), topHeadlines[position])
                }
            }
        }.show()
    }

    private fun displayRemainingArticles(headlines : List<ArticleDetails>){
        articleAdapter.updateArticleList(headlines)
        articleAdapter.onArticleCLicked { selectedArticle ->
            startReadFullNewsAct(requireContext(), selectedArticle)
        }
    }
}