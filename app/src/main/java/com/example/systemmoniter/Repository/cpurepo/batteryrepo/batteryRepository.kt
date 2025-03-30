package com.example.systemmoniter.Repository.cpurepo.batteryrepo

import com.example.systemmoniter.model.BatteryInfo
import com.example.systemmoniter.network.batteryapi.BatteryApi
import javax.inject.Inject
import android.content.Context


class batteryRepository @Inject constructor(private val api:BatteryApi) {


    suspend fun getBatteryInfo(): BatteryInfo{
        return api.getBatteryInfo()
    }

}