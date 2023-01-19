package com.androidapp.newsclientappcleanarchitecture.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.androidapp.newsclientappcleanarchitecture.utils.LogHelper
import com.androidapp.newsclientappcleanarchitecture.view.main.fragmentClasses.CustomFragment
import com.androidapp.newsclientappcleanarchitecture.view.main.fragmentClasses.HomeFragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(
    fragmentManager,
    lifecycle
) {
    var listOfCategories: List<String> = listOf()

    override fun getItemCount(): Int = 7

    override fun createFragment(position: Int): Fragment {
        LogHelper.log("viewPagerPosition", position.toString())
        return when (position) {
            0 -> {
                HomeFragment.getInstance()
            }
            1 -> {
                CustomFragment.newsInstance(listOfCategories[position])
            }
            else -> CustomFragment.newsInstance(listOfCategories[position])

        }
    }

}