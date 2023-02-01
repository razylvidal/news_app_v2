package com.androidapp.newsclientappcleanarchitecture.view.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidapp.newsclientappcleanarchitecture.databinding.FragmentCustomBinding
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.utils.startReadFullNewsAct
import com.androidapp.newsclientappcleanarchitecture.view.adapters.CustomAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CustomFragment : Fragment(), FragmentContract.View{

    @Inject
    lateinit var presenter: CustomPresenter
    private lateinit var binding: FragmentCustomBinding
    private lateinit var articleAdapter : CustomAdapter

    companion object{
        const val KEY_CATEGORY = "key_category"
        fun customNewInstance(category: String): Fragment {
            return CustomFragment().apply {
                arguments = bundleOf(
                    KEY_CATEGORY to category
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomBinding.inflate(layoutInflater, container, false)
        val view = binding.root

        binding.rvCustomFragment.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        articleAdapter = CustomAdapter(mutableListOf())

        presenter.onViewReady(this, getCurrentCategory())

        binding.customSwipeRefresh.setOnRefreshListener {
            refreshList()
        }
        return view
    }

    private fun getCurrentCategory() : String {
        return try {
            requireArguments().getString(KEY_CATEGORY)!!
        }catch(exception: Exception){
            throw java.lang
                .IllegalStateException("Please use newsInstance() to start fragment")
        }
    }

    override fun showArticles(response: MutableList<ArticleDetails>) {
        articleAdapter.clear()
        articleAdapter.updateArticleList(response)
        articleAdapter.onArticleCLicked { selectedArticle ->
            startReadFullNewsAct(requireContext(), selectedArticle)
        }
        binding.rvCustomFragment.adapter = articleAdapter
    }

    override fun showShimmerLayout(isVisible: Boolean) {
        if(isVisible){
            articleAdapter.clear()
            binding.customShimmerLayout.visibility = View.VISIBLE
        }
        else
            binding.customShimmerLayout.visibility = View.GONE
    }

    private fun refreshList() {
        showShimmerLayout(true)
        presenter.makeArticleRequest(getCurrentCategory())
        binding.customSwipeRefresh.isRefreshing = false
    }
}