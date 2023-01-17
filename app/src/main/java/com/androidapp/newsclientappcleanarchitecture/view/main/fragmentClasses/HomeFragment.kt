package com.androidapp.newsclientappcleanarchitecture.view.main.fragmentClasses

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils.lastIndexOf
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.newsclientappcleanarchitecture.R
import com.androidapp.newsclientappcleanarchitecture.databinding.FragmentHomeBinding
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.utils.Constants.Companion.TOP_HEADLINES_COUNT
import com.androidapp.newsclientappcleanarchitecture.utils.getPublishedDate
import com.androidapp.newsclientappcleanarchitecture.utils.getTimeDifference
import com.androidapp.newsclientappcleanarchitecture.utils.startReadFullNewsAct
import com.androidapp.newsclientappcleanarchitecture.view.adapters.CustomAdapter
import com.androidapp.newsclientappcleanarchitecture.view.adapters.NewsAdapter
import com.jama.carouselview.CarouselView
import com.jama.carouselview.enums.IndicatorAnimationType
import com.jama.carouselview.enums.OffsetType
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    companion object{
        const val KEY_ARTICLES = "key_articles"
        fun newsInstance(headlines: ArrayList<ArticleDetails>): Fragment {
            return HomeFragment().apply {
                arguments = bundleOf(
                    KEY_ARTICLES to headlines
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        val view = binding.root
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val articles = getArticles()
        val topHeadlines = articles.slice(0 until TOP_HEADLINES_COUNT)
        val headlines = articles.slice(TOP_HEADLINES_COUNT until articles.size)
        val articleAdapter = CustomAdapter(headlines)
        binding.recyclerView.adapter = articleAdapter

        binding.homeCarousel.apply {
            size = topHeadlines.size
            autoPlay = true
            indicatorAnimationType = IndicatorAnimationType.SCALE
            carouselOffset = OffsetType.CENTER

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

        return view
    }

    private fun getArticles() : ArrayList<ArticleDetails> {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requireArguments().getParcelableArrayList(KEY_ARTICLES, ArticleDetails::class.java)!!
            } else {
                @Suppress("DEPRECATION")
                requireArguments().getParcelableArrayList(KEY_ARTICLES)!!
            }
        }catch(exception: Exception){
            throw java.lang
                .IllegalStateException("Please use newsInstance() to start fragment")
        }
    }
}