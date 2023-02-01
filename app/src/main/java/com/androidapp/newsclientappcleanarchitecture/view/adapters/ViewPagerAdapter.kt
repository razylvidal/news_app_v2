package com.androidapp.newsclientappcleanarchitecture.view.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.androidapp.newsclientappcleanarchitecture.view.main.fragments.CustomFragment
import com.androidapp.newsclientappcleanarchitecture.view.main.fragments.HomeFragment

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
        return when (position) {
            0 -> {
                HomeFragment.homeNewInstance()
            }
            1 -> {
                CustomFragment.customNewInstance(listOfCategories[position])
            }
            else -> CustomFragment.customNewInstance(listOfCategories[position])

        }
    }

}