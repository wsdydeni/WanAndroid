package com.wsdydeni.module_main.ui.adpater

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.wsdydeni.module_main.ui.background.BackgroundFragment
import com.wsdydeni.module_main.ui.home.HomeFragment
import com.wsdydeni.module_main.ui.me.MeFragment

class MainViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val tabFragmentsCreators: Map<Int,() -> Fragment> = mapOf(
        0 to { HomeFragment() },
        1 to { BackgroundFragment() },
        2 to { MeFragment() })

    override fun getCount(): Int = tabFragmentsCreators.size

    override fun getItem(position: Int): Fragment = tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
}