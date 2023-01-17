package com.androidapp.newsclientappcleanarchitecture.view.main.fragmentClasses

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.newsclientappcleanarchitecture.R
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.utils.startReadFullNewsAct
import com.androidapp.newsclientappcleanarchitecture.view.adapters.CustomAdapter

class CustomFragment : Fragment(){

    companion object{
        const val KEY_ARTICLES_BY_CATEGORY = "key_articles_by_category"
        fun newsInstance(headlines: ArrayList<ArticleDetails>): Fragment {
            return CustomFragment().apply {
                arguments = bundleOf(
                    KEY_ARTICLES_BY_CATEGORY to headlines
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_custom, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val articles = getArticles()
        val articleAdapter = CustomAdapter(articles)
        articleAdapter.onArticleCLicked { selectedArticle ->
            startReadFullNewsAct(requireContext(), selectedArticle)
        }
        recyclerView.adapter = articleAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        return view
    }
    private fun getArticles() : ArrayList<ArticleDetails> {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requireArguments().getParcelableArrayList(KEY_ARTICLES_BY_CATEGORY, ArticleDetails::class.java)!!
            } else {
                @Suppress("DEPRECATION")
                requireArguments().getParcelableArrayList(KEY_ARTICLES_BY_CATEGORY)!!
            }
        }catch(exception: Exception){
            throw java.lang
                .IllegalStateException("Please use newsInstance() to start fragment")
        }
    }

}