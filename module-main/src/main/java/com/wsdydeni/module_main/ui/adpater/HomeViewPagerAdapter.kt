package com.wsdydeni.module_main.ui.adpater

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.wsdydeni.module_main.ui.home.ArticleFragment
import com.wsdydeni.module_main.ui.home.ProjectFragment

class HomeViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)  {

    private val tabFragmentsCreators: Map<Int,() -> Fragment> = mapOf (
        0 to { ArticleFragment() },
        1 to { ProjectFragment() }
    )

    override fun getCount(): Int = tabFragmentsCreators.size

    override fun getItem(position: Int): Fragment = tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
}