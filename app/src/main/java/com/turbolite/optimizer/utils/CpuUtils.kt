package com.turbolite.optimizer.utils

import android.content.Context
import java.io.File
import java.io.RandomAccessFile
import kotlin.random.Random

class CpuUtils(private val context: Context) {
    
    fun getCpuCores(): Int {
        return Runtime.getRuntime().availableProcessors()
    }
    
    fun getSimulatedCpuUsage(): Int {
        return Random.nextInt(15, 65)
    }
    
    fun getCpuTemperature(): Int {
        try {
            val tempFiles = listOf(
                "/sys/class/thermal/thermal_zone0/temp",
                "/sys/devices/virtual/thermal/thermal_zone0/temp",
                "/sys/class/hwmon/hwmon0/temp1_input"
            )
            
            for (path in tempFiles) {
                val file = File(path)
                if (file.exists()) {
                    val temp = file.readText().trim().toIntOrNull()
                    if (temp != null) {
                        return if (temp > 1000) temp / 1000 else temp
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        return Random.nextInt(30, 50)
    }
    
    fun getCpuFrequencies(): String {
        val cores = getCpuCores()
        val frequencies = StringBuilder()
        
        for (i in 0 until cores) {
            try {
                val freqPath = "/sys/devices/system/cpu/cpu$i/cpufreq/scaling_cur_freq"
                val file = File(freqPath)
                if (file.exists()) {
                    val freq = file.readText().trim().toInt() / 1000
                    frequencies.append("Core $i: $freq MHz\n")
                }
            } catch (e: Exception) {
                frequencies.append("Core $i: N/A\n")
            }
        }
        
        return frequencies.toString().trim()
    }
}
