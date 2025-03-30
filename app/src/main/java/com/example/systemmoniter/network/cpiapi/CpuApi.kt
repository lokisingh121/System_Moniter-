package com.example.systemmoniter.network.cpiapi

import com.example.systemmoniter.model.CpuFreq
import com.example.systemmoniter.model.CpuInfo

interface cpuApi {

//    for what type mu cpu is
    suspend fun getCpuInfo():CpuInfo
//how much cpu usage is there
//    suspend fun getCpuUsage(): Double

    suspend fun getCpuFrequency():List<CpuFreq>

    suspend fun getCpuTemperature():String


}