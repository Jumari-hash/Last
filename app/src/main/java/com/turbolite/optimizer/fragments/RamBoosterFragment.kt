package com.turbolite.optimizer.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.turbolite.optimizer.R
import com.turbolite.optimizer.databinding.FragmentRamBoosterBinding
import com.turbolite.optimizer.utils.MemoryUtils

class RamBoosterFragment : Fragment() {
    
    private var _binding: FragmentRamBoosterBinding? = null
    private val binding get() = _binding!!
    
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var memoryUtils: MemoryUtils
    private var isMonitoring = false
    
    private val updateRunnable = object : Runnable {
        override fun run() {
            updateMemoryInfo()
            if (isMonitoring) {
                handler.postDelayed(this, 2000)
            }
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRamBoosterBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        memoryUtils = MemoryUtils(requireContext())
        
        binding.btnBoostRam.setOnClickListener {
            boostRam()
        }
        
        startMonitoring()
    }
    
    private fun startMonitoring() {
        isMonitoring = true
        handler.post(updateRunnable)
    }
    
    private fun stopMonitoring() {
        isMonitoring = false
        handler.removeCallbacks(updateRunnable)
    }
    
    private fun updateMemoryInfo() {
        val memInfo = memoryUtils.getMemoryInfo()
        
        binding.tvRamPercentage.text = getString(R.string.percentage, memInfo.usedPercentage)
        binding.tvRamUsed.text = getString(R.string.mb_format, memInfo.usedMB.toFloat())
        binding.tvRamFree.text = getString(R.string.mb_format, memInfo.availMB.toFloat())
        binding.tvRamTotal.text = getString(R.string.mb_format, memInfo.totalMB.toFloat())
        
        binding.circularRamProgress.setProgress(memInfo.usedPercentage)
    }
    
    private fun boostRam() {
        binding.btnBoostRam.isEnabled = false
        binding.btnBoostRam.text = getString(R.string.ram_boosting)
        
        val pulseAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.pulse_animation)
        binding.circularRamProgress.startAnimation(pulseAnim)
        
        handler.postDelayed({
            System.gc()
            System.runFinalization()
            
            binding.circularRamProgress.clearAnimation()
            binding.btnBoostRam.text = getString(R.string.ram_boosted)
            
            updateMemoryInfo()
            
            handler.postDelayed({
                binding.btnBoostRam.text = getString(R.string.ram_boost)
                binding.btnBoostRam.isEnabled = true
            }, 1500)
            
        }, 2000)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        stopMonitoring()
        _binding = null
    }
}
