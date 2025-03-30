package com.example.systemmoniter.network.cpiapi

import com.example.systemmoniter.model.CpuFreq
import com.example.systemmoniter.model.CpuInfo
import com.example.systemmoniter.util.getTemp
import com.example.systemmoniter.util.readCpuInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class CpuApiImpli @Inject constructor(): cpuApi {

    override suspend fun getCpuInfo(): CpuInfo {
        return CpuInfo(
//            thsi readcpuinfo in utils
            model = readCpuInfo("/proc/cpuinfo", "Hardware"),
            architecture = System.getProperty("os.arch")?:"Unknown",
            cores =Runtime.getRuntime().availableProcessors()
        )
    }



//    override suspend fun getCpuUsage(): Double {
////        this function is in utility it return cpu usage i will use it in circulat bat
//        return getCpuUsageInternal()
//
//    }

    override suspend fun getCpuFrequency(): List<CpuFreq> = withContext(Dispatchers.IO) {
        (0 until Runtime.getRuntime().availableProcessors()).map { core ->
            val possiblePaths = listOf(
                "/sys/devices/system/cpu/cpu$core/cpufreq/scaling_cur_freq",
                "/sys/devices/system/cpu/cpu$core/cpufreq/cpuinfo_cur_freq"
            )

            val freq = possiblePaths.firstNotNullOfOrNull { path ->
                val file = File(path)
                if (file.exists()) file.readText().trim().toLongOrNull()?.div(1000) else null
            } ?: 0L

            CpuFreq(core.toString(), freq)
        }
    }


    override suspend fun getCpuTemperature(): String {
        return getTemp()
    }




}