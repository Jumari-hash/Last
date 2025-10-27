package com.turbolite.optimizer.utils

import android.app.ActivityManager
import android.content.Context

data class MemoryInfo(
    val totalMB: Long,
    val usedMB: Long,
    val availMB: Long,
    val usedPercentage: Int
)

class MemoryUtils(private val context: Context) {
    
    fun getMemoryInfo(): MemoryInfo {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memInfo)
        
        val totalMB = memInfo.totalMem / (1024 * 1024)
        val availMB = memInfo.availMem / (1024 * 1024)
        val usedMB = totalMB - availMB
        val usedPercentage = ((usedMB.toFloat() / totalMB) * 100).toInt()
        
        return MemoryInfo(
            totalMB = totalMB,
            usedMB = usedMB,
            availMB = availMB,
            usedPercentage = usedPercentage
        )
    }
}
