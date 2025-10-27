package com.turbolite.optimizer.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.turbolite.optimizer.R
import com.turbolite.optimizer.databinding.FragmentStorageCleanerBinding
import com.turbolite.optimizer.utils.StorageUtils

class StorageCleanerFragment : Fragment() {
    
    private var _binding: FragmentStorageCleanerBinding? = null
    private val binding get() = _binding!!
    
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var storageUtils: StorageUtils
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStorageCleanerBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        storageUtils = StorageUtils(requireContext())
        
        updateStorageInfo()
        
        binding.btnScan.setOnClickListener {
            scanCache()
        }
        
        binding.btnClean.setOnClickListener {
            cleanCache()
        }
    }
    
    private fun updateStorageInfo() {
        val storageInfo = storageUtils.getStorageInfo()
        
        binding.tvStoragePercentage.text = getString(R.string.percentage, storageInfo.usedPercentage)
        binding.tvStorageUsed.text = getString(R.string.gb_format, storageInfo.usedGB)
        binding.tvStorageTotal.text = getString(R.string.gb_format, storageInfo.totalGB)
        binding.progressStorage.progress = storageInfo.usedPercentage
    }
    
    private fun scanCache() {
        binding.btnScan.isEnabled = false
        binding.btnScan.text = getString(R.string.storage_scanning)
        binding.progressScanning.visibility = View.VISIBLE
        
        handler.postDelayed({
            val cacheSize = storageUtils.getCacheSize()
            val cacheSizeStr = storageUtils.formatSize(cacheSize)
            
            binding.tvCacheSize.text = cacheSizeStr
            binding.progressScanning.visibility = View.GONE
            binding.btnScan.text = getString(R.string.storage_scan)
            binding.btnScan.isEnabled = true
            
            Toast.makeText(requireContext(), "Scan complete: $cacheSizeStr found", Toast.LENGTH_SHORT).show()
        }, 2000)
    }
    
    private fun cleanCache() {
        binding.btnClean.isEnabled = false
        
        val success = storageUtils.clearCache()
        
        handler.postDelayed({
            if (success) {
                binding.tvCacheSize.text = "0 MB"
                Toast.makeText(requireContext(), "Cache cleared successfully!", Toast.LENGTH_SHORT).show()
                updateStorageInfo()
            } else {
                Toast.makeText(requireContext(), "Failed to clear cache", Toast.LENGTH_SHORT).show()
            }
            binding.btnClean.isEnabled = true
        }, 1000)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
