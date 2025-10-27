package com.turbolite.optimizer.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.turbolite.optimizer.R
import com.turbolite.optimizer.databinding.FragmentBatterySaverBinding

class BatterySaverFragment : Fragment() {
    
    private var _binding: FragmentBatterySaverBinding? = null
    private val binding get() = _binding!!
    
    private val batteryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.let { updateBatteryInfo(it) }
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBatterySaverBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = requireContext().registerReceiver(null, intentFilter)
        batteryStatus?.let { updateBatteryInfo(it) }
    }
    
    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        requireContext().registerReceiver(batteryReceiver, intentFilter)
    }
    
    override fun onPause() {
        super.onPause()
        try {
            requireContext().unregisterReceiver(batteryReceiver)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    private fun updateBatteryInfo(intent: Intent) {
        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
        val batteryPct = (level / scale.toFloat() * 100).toInt()
        
        val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1)
        val statusText = when (status) {
            BatteryManager.BATTERY_STATUS_CHARGING -> "Charging"
            BatteryManager.BATTERY_STATUS_DISCHARGING -> "Discharging"
            BatteryManager.BATTERY_STATUS_FULL -> "Full"
            BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "Not Charging"
            else -> "Unknown"
        }
        
        val health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1)
        val healthText = when (health) {
            BatteryManager.BATTERY_HEALTH_GOOD -> "Good"
            BatteryManager.BATTERY_HEALTH_OVERHEAT -> "Overheat"
            BatteryManager.BATTERY_HEALTH_DEAD -> "Dead"
            BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> "Over Voltage"
            BatteryManager.BATTERY_HEALTH_COLD -> "Cold"
            else -> "Unknown"
        }
        
        val temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1) / 10
        val voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1)
        
        binding.tvBatteryPercentage.text = getString(R.string.percentage, batteryPct)
        binding.circularBatteryProgress.setProgress(batteryPct)
        binding.tvBatteryStatus.text = statusText
        binding.tvBatteryHealth.text = healthText
        binding.tvBatteryTemp.text = getString(R.string.temp_format, temperature)
        binding.tvBatteryVoltage.text = "$voltage mV"
        
        val healthColor = when (health) {
            BatteryManager.BATTERY_HEALTH_GOOD -> requireContext().getColor(R.color.neon_green)
            BatteryManager.BATTERY_HEALTH_OVERHEAT -> requireContext().getColor(R.color.electric_orange)
            else -> requireContext().getColor(R.color.text_secondary)
        }
        binding.tvBatteryHealth.setTextColor(healthColor)
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
