package com.example.systemmoniter.Screens.Battery_Screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.systemmoniter.Repository.cpurepo.batteryrepo.batteryRepository
import com.example.systemmoniter.model.BatteryInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.content.Context
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@HiltViewModel
class batteryViewmodel @Inject constructor(private val repository: batteryRepository):ViewModel(){

    private val _batteryinfo = MutableStateFlow<BatteryInfo?>(null)
    val batteryinfo:StateFlow<BatteryInfo?> = _batteryinfo.asStateFlow()


    init {
        getbatteryinfo()
    }

    private fun getbatteryinfo() {
        viewModelScope.launch {

            while (isActive){
                _batteryinfo.value = repository.getBatteryInfo()
                delay(5000)
            }

        }
    }


}