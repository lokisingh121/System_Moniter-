package com.example.systemmoniter.navigation

enum class SystemScreen {

    RamScreen,
    BatteryScreen,
    CpuScreen;

    companion object{
        fun fromRoute(route:String):SystemScreen =when(route.substringBefore("/")){

            RamScreen.name->RamScreen
                BatteryScreen.name->BatteryScreen
            CpuScreen.name->CpuScreen

            else -> throw java.lang.IllegalArgumentException("Route $route is not recognized")
        }


    }

}