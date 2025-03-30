package com.example.systemmoniter.Repository.cpurepo.cpu

import com.example.systemmoniter.model.CpuFreq
import com.example.systemmoniter.model.CpuInfo

interface CpiRepository {

    suspend fun getcpuInfo():CpuInfo
//    suspend fun getcpuUsage():Double
    suspend fun getcpuFreq():List<CpuFreq>
    suspend fun getcpuTemp():String

}