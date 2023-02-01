package com.androidapp.newsclientappcleanarchitecture.view.saveNews

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidapp.newsclientappcleanarchitecture.databinding.ActivitySavedNewsBinding
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.view.adapters.CustomAdapter
import com.androidapp.newsclientappcleanarchitecture.utils.startReadFullNewsAct
import com.androidapp.newsclientappcleanarchitecture.view.main.hide
import com.androidapp.newsclientappcleanarchitecture.view.main.show
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SavedNewsActivity : AppCompatActivity(), SavedNewsContract.View {

    private var newsData: MutableList<ArticleDetails> = mutableListOf()
    private lateinit var adapter: CustomAdapter
    private lateinit var binding: ActivitySavedNewsBinding


    @Inject
    lateinit var presenter: SavedNewsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.rvSavedNews.layoutManager = layoutManager

        //dbInstance = (application as NewsApp).database

        adapter = CustomAdapter(newsData)

        adapter.onArticleCLicked { articleData ->
            startReadFullNewsAct(this@SavedNewsActivity, articleData)
        }
        binding.rvSavedNews.adapter = adapter
        showSavedNews()

        val swipeToDelete = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val selectedArticle = newsData[viewHolder.adapterPosition]
                val position = viewHolder.adapterPosition
                adapter.removeArticle(position)
                presenter.handleArticleToRemove(selectedArticle)
                showSnackBar(position, selectedArticle)
            }
        }
        val touchHelper = ItemTouchHelper(swipeToDelete)
        touchHelper.attachToRecyclerView(binding.rvSavedNews)

        adapter.onMultiSelected {
            presenter.handleArticleToRemove(newsData[it])
        }

        adapter.onToolbarHidden { isHidden ->
            if(isHidden)
                binding.tbSavedNews.hide()
            else
                binding.tbSavedNews.show()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun showSavedNews() {
        presenter.handleSavedArticlesFromDB().observe(this) {
            newsData.clear()
            newsData.addAll(it)
            if(newsData.isEmpty())
                binding.tvMessage.show()
            else
                binding.tvMessage.hide()
            adapter.notifyDataSetChanged()
        }
    }

    override fun showSnackBar(position: Int, selectedArticle: ArticleDetails) {
        Snackbar.make(
            binding.rvSavedNews,
            "Article Removed!", Snackbar.LENGTH_SHORT
        ).setAction("Undo"){
            presenter.handleArticleToInsert(selectedArticle)
            adapter.undoArticleRemoved(position,selectedArticle)
        }.show()
    }

    override fun onDestroy() {
        presenter.onSavedNewsViewDestroyed()
        super.onDestroy()
    }


//    private fun removeMultipleArticles(position: Int) {
////        setDialogBackground(Color.GRAY, position)
////        val alertDialog = AlertDialog.Builder(this@SavedNewsActivity).apply {
////            setPositiveButton("Yes") { _, _ ->
////                presenter.handleArticleToRemove(dbInstance, newsData[position])
////                adapter.notifyItemRemoved(position)
////            }
////            setNegativeButton("No") { _, _ ->
////                setDialogBackground(Color.TRANSPARENT, position)
////            }
////        }.create()
////        showAlertDialog(alertDialog, true)
//    }

//    private fun setDialogBackground(color: Int, position: Int) {
//        binding.rvSavedNews.findViewHolderForAdapterPosition(position)
//            ?.itemView?.setBackgroundColor(color)
//    }

//    override fun showAlertDialog(alertDialog: AlertDialog, isVisible: Boolean) {
//        if (isVisible) alertDialog.show(
//            "Alert!",
//            "Remove this news?",
//            false
//        )
//        else alertDialog.dismiss()
//    }

}

