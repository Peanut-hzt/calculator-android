package com.fange520.calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fange520.calculator.databinding.ActivityMainBinding
import com.fange520.calculator.ui.calculator.CalculatorFragment
import com.fange520.calculator.ui.converter.ConverterFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewPager.adapter = ViewPagerAdapter()

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "科学计算器"
                1 -> "换算计算器"
                else -> ""
            }
        }.attach()
    }

    private inner class ViewPagerAdapter : FragmentStateAdapter(this) {
        override fun getItemCount(): Int = 2

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> CalculatorFragment()
                1 -> ConverterFragment()
                else -> CalculatorFragment()
            }
        }
    }
}
