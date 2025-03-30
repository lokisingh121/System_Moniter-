package com.example.systemmoniter.util

import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import kotlin.text.toDouble

//    function to read the cpu name it require path
 fun readCpuInfo(filepath:String,key:String):String{

//     is just read where hardware is written in file

    try {
        val file = File(filepath)
        var result = "Unknown"
        file.forEachLine { line->

            if(line.startsWith(key)){
                result = line.split(":").getOrNull(1)?:"unknown"
                return@forEachLine
            }

        }

        return result

    }catch (e:Exception){}


    return "Unknown"
}

//private var previousTotal: Long = 0
//private var previousIdle: Long = 0

// fun getCpuUsageInternal(): Double {
//
//     val cpuStats1 = readCpuStats()
//     Thread.sleep(1000) // Wait for 1 second
//     val cpuStats2 = readCpuStats()
//
//     if (cpuStats1 == null || cpuStats2 == null) {
//         return 0.0
//     }
//
//     val totalDiff = (cpuStats2.user + cpuStats2.nice + cpuStats2.system + cpuStats2.idle +
//             cpuStats2.iowait + cpuStats2.irq + cpuStats2.softirq + cpuStats2.steal +
//             cpuStats2.guest + cpuStats2.guestNice) -
//             (cpuStats1.user + cpuStats1.nice + cpuStats1.system + cpuStats1.idle +
//                     cpuStats1.iowait + cpuStats1.irq + cpuStats1.softirq + cpuStats1.steal +
//                     cpuStats1.guest + cpuStats1.guestNice)
//
//     val idleDiff = cpuStats2.idle - cpuStats1.idle
//
//     return if (totalDiff > 0) {
//         (totalDiff - idleDiff).toDouble() / totalDiff * 100
//     } else {
//         0.0
//     }
//
// }

//private fun readCpuStats(): CpuStats? {
//    try {
//        val file = File("/proc/stat")
//        val lines = file.readLines()
//        for (line in lines) {
//            if (line.startsWith("cpu ")) {
//                val parts = line.split("\\s+".toRegex()).filter { it.isNotEmpty() }
//                if (parts.size >= 11) {
//                    return CpuStats(
//                        user = parts[1].toLong(),
//                        nice = parts[2].toLong(),
//                        system = parts[3].toLong(),
//                        idle = parts[4].toLong(),
//                        iowait = parts[5].toLong(),
//                        irq = parts[6].toLong(),
//                        softirq = parts[7].toLong(),
//                        steal = parts[8].toLong(),
//                        guest = parts[9].toLong(),
//                        guestNice = parts[10].toLong()
//                    )
//                }
//            }
//        }
//    } catch (e: IOException) {
//        Log.e("CpuUsage", "Error reading /proc/stat", e)
//    }
//    return null
//}




 fun getTemp():String{

    return try {

        val file = File("/sys/class/thermal/thermal_zone0/temp")
        if (file.exists()){
            val temp = file.readText().trim().toFloatOrNull()?: return "Unknown"
            return "%.1f".format(temp/1000)
        }

        "Unknown"
    }catch (e:Exception){
        Log.e("CpuTemp", "Error reading CPU temperature: ${e.message}")
        "Error"
    }

}