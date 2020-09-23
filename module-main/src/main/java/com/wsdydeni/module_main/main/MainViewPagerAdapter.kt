package com.wsdydeni.module_main.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.wsdydeni.module_main.background.BackgroundFragment
import com.wsdydeni.module_main.home.HomeFragment
import com.wsdydeni.module_main.me.MeFragment

const val HOME_PAGE_INDEX = 0
const val BACKGROUND_PAGE_INDEX = 1
const val ME_PAGE_INDEX = 2

class MainViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val tabFragmentsCreators: Map<Int,() -> Fragment> = mapOf(
        HOME_PAGE_INDEX to { HomeFragment() },
        BACKGROUND_PAGE_INDEX to { BackgroundFragment() },
        ME_PAGE_INDEX to { MeFragment() })

    override fun getCount(): Int = tabFragmentsCreators.size

    override fun getItem(position: Int): Fragment = tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
}