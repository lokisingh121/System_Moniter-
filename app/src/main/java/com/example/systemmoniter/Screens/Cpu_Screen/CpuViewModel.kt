package com.example.systemmoniter.Screens.Cpu_Screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.systemmoniter.Repository.cpurepo.cpu.CpiRepository
import com.example.systemmoniter.model.CpuFreq
import com.example.systemmoniter.model.CpuInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CpuViewModel @Inject constructor(private val repository: CpiRepository):ViewModel(){


////     this is the best practice to wrap something in a state
//    private val _CPuUsage = MutableStateFlow<Double>(0.0)
////    this is a read only so in init functions i used _cpuinfo this value cause this is actual state
//    val Cpuusage:StateFlow<Double> = _CPuUsage.asStateFlow()

    private val _CpuInfo = MutableStateFlow<CpuInfo?>(null)
    val Cpuinfo:StateFlow<CpuInfo?> = _CpuInfo.asStateFlow()

    private val _CpuFreq = MutableStateFlow<List<CpuFreq?>>(emptyList())
    val Cpufreq :StateFlow<List<CpuFreq?>> = _CpuFreq.asStateFlow()

    private val _Cputemp = MutableStateFlow("Unknown")
    val Cputemp :StateFlow<String> = _Cputemp.asStateFlow()


    init {
        getcpuinfo()
        getcpufreq()
//        getcpuusage()
        getcputemp()
    }

    private fun getcpuinfo(){

      viewModelScope.launch {
          _CpuInfo.value = repository.getcpuInfo()
      }

    }



    private fun getcpufreq(){

        viewModelScope.launch(Dispatchers.IO) {

            while (isActive){
                _CpuFreq.value = repository.getcpuFreq()
                delay(1000)
            }
        }




    }

//
//private fun getcpuusage(){
//
//    viewModelScope.launch(Dispatchers.IO) {
//
//        while (isActive){
//            _CPuUsage.value = repository.getcpuUsage()
//            delay(2000)
//        }
//
//
//    }}

    private fun getcputemp(){

        viewModelScope.launch(Dispatchers.IO) {
            while (isActive) {
                _Cputemp.value = repository.getcpuTemp()
                delay(1000)
            }
        }




    }
}









