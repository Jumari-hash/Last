package com.turbolite.optimizer.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.turbolite.optimizer.R
import com.turbolite.optimizer.databinding.FragmentCpuMonitorBinding
import com.turbolite.optimizer.utils.CpuUtils

class CpuMonitorFragment : Fragment() {
    
    private var _binding: FragmentCpuMonitorBinding? = null
    private val binding get() = _binding!!
    
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var cpuUtils: CpuUtils
    private var isMonitoring = false
    
    private val updateRunnable = object : Runnable {
        override fun run() {
            updateCpuInfo()
            if (isMonitoring) {
                handler.postDelayed(this, 1500)
            }
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCpuMonitorBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        cpuUtils = CpuUtils(requireContext())
        
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
    
    private fun updateCpuInfo() {
        val cpuCores = cpuUtils.getCpuCores()
        val cpuUsage = cpuUtils.getSimulatedCpuUsage()
        val cpuTemp = cpuUtils.getCpuTemperature()
        val cpuFrequencies = cpuUtils.getCpuFrequencies()
        
        binding.tvCpuCores.text = cpuCores.toString()
        binding.tvCpuPercentage.text = getString(R.string.percentage, cpuUsage)
        binding.circularCpuProgress.setProgress(cpuUsage)
        
        if (cpuTemp > 0) {
            binding.tvCpuTemp.text = getString(R.string.temp_format, cpuTemp)
        } else {
            binding.tvCpuTemp.text = "--°C"
        }
        
        binding.tvCpuFrequency.text = cpuFrequencies.ifEmpty { "Frequency info not available" }
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        stopMonitoring()
        _binding = null
    }
}
