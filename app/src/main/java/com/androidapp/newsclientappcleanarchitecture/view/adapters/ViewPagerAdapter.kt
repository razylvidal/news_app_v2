package com.androidapp.newsclientappcleanarchitecture.view.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.androidapp.newsclientappcleanarchitecture.domain.ArticleDetails
import com.androidapp.newsclientappcleanarchitecture.view.main.fragmentClasses.CustomFragment
import com.androidapp.newsclientappcleanarchitecture.view.main.fragmentClasses.HomeFragment
import kotlinx.coroutines.internal.artificialFrame

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(
    fragmentManager,
    lifecycle
) {
    var listOfArticles : ArrayList<ArticleDetails> = ArrayList()

    override fun getItemCount(): Int = 7

    override fun createFragment(position: Int): Fragment {

        when (position) {
            0 -> {
                return CustomFragment.newsInstance(listOfArticles)
            }
            1 -> {
                return CustomFragment.newsInstance(listOfArticles)
            }
//            2 -> {
//                return EntertainmentFragment()
//            }
//            3 -> {
//                return ScienceFragment()
//            }
//            4 -> {
//                return SportsFragment()
//            }
//            5 -> {
//                return TechFragment()
//            }
//            6 -> {
//                return HealthFragment()
//            }

            else -> return HomeFragment.newsInstance(listOfArticles)

        }
    }

}