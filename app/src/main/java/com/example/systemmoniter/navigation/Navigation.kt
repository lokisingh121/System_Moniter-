package com.example.systemmoniter.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.systemmoniter.BottomBar
import com.example.systemmoniter.Screens.Battery_Screen.BatteryScreen
import com.example.systemmoniter.Screens.Cpu_Screen.CpuScreen
import com.example.systemmoniter.Screens.Ram_Screen.RamScreen


@Composable
fun SystemNavigation(){


    val navController= rememberNavController()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = currentBackStackEntry?.destination?.route?.let { SystemScreen.fromRoute(it) } ?: SystemScreen.RamScreen

    Scaffold(topBar = { topbar() },bottomBar = {

        BottomBar(navController,currentScreen)

    }) {innerPadding->
        NavHost(navController=navController, startDestination = SystemScreen.RamScreen.name, modifier = Modifier.padding(innerPadding)){

            composable(SystemScreen.RamScreen.name) {
                RamScreen(navController=navController)
            }
            composable(SystemScreen.BatteryScreen.name) {
                BatteryScreen(navController=navController)
            }
            composable(SystemScreen.CpuScreen.name) {
                CpuScreen(navController=navController)
            }

        }

    }





}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topbar(){

    TopAppBar(title = { Text("System_Moniter") },
        modifier = Modifier.background(brush = Brush.horizontalGradient(colors = listOf(Color(0xff70e1f5),
            Color(0xFFF3D4B5)
        )))
       , colors = TopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
            navigationIconContentColor = Color.Transparent,
            titleContentColor = Color.Black,
            actionIconContentColor = Color.Transparent
        ))
}