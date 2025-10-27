package com.turbolite.optimizer.utils

import android.content.Context
import android.os.Environment
import android.os.StatFs
import java.io.File

data class StorageInfo(
    val totalGB: Double,
    val usedGB: Double,
    val availableGB: Double,
    val usedPercentage: Int
)

class StorageUtils(private val context: Context) {
    
    fun getStorageInfo(): StorageInfo {
        val statFs = StatFs(Environment.getDataDirectory().path)
        
        val totalBytes = statFs.blockCountLong * statFs.blockSizeLong
        val availableBytes = statFs.availableBlocksLong * statFs.blockSizeLong
        val usedBytes = totalBytes - availableBytes
        
        val totalGB = bytesToGB(totalBytes)
        val usedGB = bytesToGB(usedBytes)
        val availableGB = bytesToGB(availableBytes)
        val usedPercentage = ((usedBytes.toFloat() / totalBytes) * 100).toInt()
        
        return StorageInfo(
            totalGB = totalGB,
            usedGB = usedGB,
            availableGB = availableGB,
            usedPercentage = usedPercentage
        )
    }
    
    fun getCacheSize(): Long {
        var cacheSize = 0L
        
        cacheSize += getDirSize(context.cacheDir)
        
        context.externalCacheDir?.let {
            cacheSize += getDirSize(it)
        }
        
        return cacheSize
    }
    
    fun clearCache(): Boolean {
        return try {
            deleteDir(context.cacheDir)
            context.externalCacheDir?.let { deleteDir(it) }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
    
    fun formatSize(bytes: Long): String {
        return when {
            bytes >= 1024 * 1024 * 1024 -> String.format("%.2f GB", bytes / (1024.0 * 1024.0 * 1024.0))
            bytes >= 1024 * 1024 -> String.format("%.2f MB", bytes / (1024.0 * 1024.0))
            bytes >= 1024 -> String.format("%.2f KB", bytes / 1024.0)
            else -> "$bytes B"
        }
    }
    
    private fun getDirSize(dir: File): Long {
        var size = 0L
        dir.listFiles()?.forEach { file ->
            size += if (file.isDirectory) {
                getDirSize(file)
            } else {
                file.length()
            }
        }
        return size
    }
    
    private fun deleteDir(dir: File): Boolean {
        dir.listFiles()?.forEach { file ->
            if (file.isDirectory) {
                deleteDir(file)
            }
            file.delete()
        }
        return true
    }
    
    private fun bytesToGB(bytes: Long): Double {
        return bytes / (1024.0 * 1024.0 * 1024.0)
    }
}
