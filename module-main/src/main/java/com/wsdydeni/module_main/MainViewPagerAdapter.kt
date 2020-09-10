package com.wsdydeni.module_main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.wsdydeni.module_background.BackgroundFragment
import com.wsdydeni.module_home.HomeFragment
import com.wsdydeni.module_me.MeFragment

const val HOME_PAGE_INDEX = 0
const val BACKGROUND_PAGE_INDEX = 1
const val ME_PAGE_INDEX = 2

class MainViewPagerAdapter(context: FragmentActivity) : FragmentStateAdapter(context) {

    private val tabFragmentsCreators: Map<Int,() -> Fragment> = mapOf(
        HOME_PAGE_INDEX to {HomeFragment()},
        BACKGROUND_PAGE_INDEX to {BackgroundFragment()},
        ME_PAGE_INDEX to {MeFragment()}
    )

    override fun getItemCount(): Int = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment =
        tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
}