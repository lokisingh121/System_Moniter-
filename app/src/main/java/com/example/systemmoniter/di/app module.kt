package com.example.systemmoniter.di

import android.content.Context
import com.example.systemmoniter.Repository.cpurepo.batteryrepo.batteryRepository
import com.example.systemmoniter.Repository.cpurepo.cpu.CpiRepository
import com.example.systemmoniter.Repository.cpurepo.cpu.CpurepositoryImpli
import com.example.systemmoniter.network.batteryapi.BatteryApi
import com.example.systemmoniter.network.batteryapi.battertApiImpli
import com.example.systemmoniter.network.cpiapi.CpuApiImpli
import com.example.systemmoniter.network.cpiapi.cpuApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object app_module {


//    i register my api here means i created the instance of  cpuapiimpli which means where ever i inject cpuApi i will be able to use the functioin of cpuapiimpl
    @Provides
    fun ProvideCpiApi(): cpuApi = CpuApiImpli()


    @Provides
    fun provideCpuRepository(api: cpuApi): CpiRepository = CpurepositoryImpli(api)

    @Provides
    fun provideBatteryApi(@ApplicationContext context: Context):BatteryApi = battertApiImpli(context)

    @Provides
    fun provideBatteryRepository(api: BatteryApi):batteryRepository = batteryRepository(api)

}


