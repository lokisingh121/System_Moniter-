package com.example.systemmoniter.network.batteryapi

import android.content.Context
import com.example.systemmoniter.model.BatteryInfo
import javax.inject.Inject
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager

class battertApiImpli @Inject constructor(private val context: Context):BatteryApi {

    override suspend fun getBatteryInfo(): BatteryInfo {


        val batteryIntent = context.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val level = batteryIntent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = batteryIntent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
        val status = batteryIntent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        val temperature = batteryIntent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) ?: 0

        val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL
        val batteryPct = if (level >= 0 && scale > 0) (level / scale.toFloat()) * 100 else -1f
        val tempCelsius = temperature / 10f

        return BatteryInfo(
            battertpercentage = batteryPct,
            ischarging = isCharging,
            temperature = tempCelsius
        )
    }


}