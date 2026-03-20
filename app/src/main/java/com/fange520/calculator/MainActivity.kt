package com.fange520.calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fange520.calculator.ui.calculator.CalculatorFragment
import com.fange520.calculator.ui.converter.ConverterFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        val viewPager: androidx.viewpager2.widget.ViewPager2 = findViewById(R.id.viewPager)

        viewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "科学计算器"
                1 -> "换算计算器"
                else -> ""
            }
        }.attach()
    }

    private class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> com.fange520.calculator.ui.calculator.CalculatorFragment()
                1 -> com.fange520.calculator.ui.converter.ConverterFragment()
                else -> CalculatorFragment()
            }
        }
    }
}
