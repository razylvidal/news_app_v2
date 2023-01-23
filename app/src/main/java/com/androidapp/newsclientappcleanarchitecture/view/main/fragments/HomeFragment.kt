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
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), FragmentContract.View {

    private lateinit var binding: FragmentHomeBinding
    @Inject
    lateinit var presenter : CustomPresenter

    companion object{
        fun getInstance() = HomeFragment()
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
        presenter.onViewReady(this, DEFAULT_CATEGORY)

        return view
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun showArticles(response: MutableList<ArticleDetails>) {
        binding.shimmerLayout.visibility = View.GONE
        binding.tvTopHeadlines.visibility = View.VISIBLE
        val topHeadlines = response.slice(0 until TOP_HEADLINES_COUNT)
        val headlines = response.slice(TOP_HEADLINES_COUNT until response.size).toMutableList()

        val articleAdapter = CustomAdapter(headlines)
        articleAdapter.onArticleCLicked { selectedArticle ->
            startReadFullNewsAct(requireContext(), selectedArticle)
        }
        binding.recyclerView.adapter = articleAdapter
        binding.homeCarousel.apply {
            size = topHeadlines.size
            setCarouselViewListener { view, position ->
                val imageView = view.findViewById<ImageView>(R.id.iv_articleImage)
                Picasso.get()
                    .load(topHeadlines[position].urlToImage)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.no_image_available)
                    .into(imageView)
                val title = view.findViewById<TextView>(R.id.tv_title)
                title.text = topHeadlines[position].title

                val source = view.findViewById<TextView>(R.id.tv_source)
                source.text = topHeadlines[position].source.name

                val timePosted = view.findViewById<TextView>(R.id.tv_publishedAt)
                timePosted.text = getTimeDifference(topHeadlines[position].publishedAt)

                view.setOnClickListener{
                    startReadFullNewsAct(requireContext(), topHeadlines[position])
                }
            }
            show()
        }
    }
}