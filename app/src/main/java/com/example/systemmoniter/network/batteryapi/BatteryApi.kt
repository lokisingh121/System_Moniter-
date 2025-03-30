package com.example.systemmoniter.network.batteryapi

import com.example.systemmoniter.model.BatteryInfo

interface BatteryApi {
    suspend fun getBatteryInfo():BatteryInfo
}