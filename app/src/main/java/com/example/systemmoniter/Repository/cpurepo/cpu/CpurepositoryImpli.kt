package com.example.systemmoniter.Repository.cpurepo.cpu

import com.example.systemmoniter.model.CpuFreq
import com.example.systemmoniter.model.CpuInfo
import com.example.systemmoniter.network.cpiapi.cpuApi
import javax.inject.Inject

class CpurepositoryImpli @Inject constructor(private val api: cpuApi): CpiRepository {

    override suspend fun getcpuInfo():CpuInfo{
          return  api.getCpuInfo()
    }

//    override suspend fun getcpuUsage() :Double{
//        return  api.getCpuUsage()
//    }

    override suspend fun getcpuFreq():List<CpuFreq>  {
        return api.getCpuFrequency()
    }

    override suspend fun getcpuTemp():String {
       return  api.getCpuTemperature()
    }

}