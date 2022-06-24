package com.github.fernandospr.encryption.sample.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.github.fernandospr.encryption.sample.databinding.TabLayoutBinding
import com.google.android.material.tabs.TabLayoutMediator

abstract class TabLayoutActivity : AppCompatActivity() {

    abstract val tabNameToFragmentLambdaList: List<Pair<String, () -> Fragment>>

    private lateinit var binding: TabLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = TabLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setHomeButtonEnabled(true)

        binding.pager.adapter =
            FragmentsAdapter(this, tabNameToFragmentLambdaList.map { it.second })
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            tab.text = tabNameToFragmentLambdaList[position].first
        }.attach()
    }

    class FragmentsAdapter(
        activity: FragmentActivity,
        private val fragmentLambdaList: List<() -> Fragment>
    ) : FragmentStateAdapter(activity) {

        override fun getItemCount() = fragmentLambdaList.size

        override fun createFragment(position: Int) = fragmentLambdaList[position]()
    }
}