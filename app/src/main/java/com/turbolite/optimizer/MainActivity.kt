package com.turbolite.optimizer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.turbolite.optimizer.databinding.ActivityMainBinding
import com.turbolite.optimizer.fragments.BatterySaverFragment
import com.turbolite.optimizer.fragments.CpuMonitorFragment
import com.turbolite.optimizer.fragments.RamBoosterFragment
import com.turbolite.optimizer.fragments.StorageCleanerFragment

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupViewPager()
    }
    
    private fun setupViewPager() {
        val adapter = OptimizerPagerAdapter(this)
        binding.viewPager.adapter = adapter
        
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.tab_ram)
                1 -> getString(R.string.tab_cpu)
                2 -> getString(R.string.tab_storage)
                3 -> getString(R.string.tab_battery)
                else -> ""
            }
        }.attach()
    }
    
    private inner class OptimizerPagerAdapter(activity: AppCompatActivity) : 
        FragmentStateAdapter(activity) {
        
        override fun getItemCount(): Int = 4
        
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> RamBoosterFragment()
                1 -> CpuMonitorFragment()
                2 -> StorageCleanerFragment()
                3 -> BatterySaverFragment()
                else -> RamBoosterFragment()
            }
        }
    }
}
