package com.example.systemmoniter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.systemmoniter.navigation.SystemNavigation
import com.example.systemmoniter.navigation.SystemScreen
import com.example.systemmoniter.util.NAvItems
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SystemMonitorTheme {

                MainScreen()
            }
    }
}



@Composable
fun MainScreen() {

SystemNavigation()


}}

@Composable
fun BottomBar(navController: NavController, currentScreen: SystemScreen){


     val nvaListItem = listOf(NAvItems("Ram",ImageVector.vectorResource(R.drawable.baseline_memory_24),SystemScreen.RamScreen),
         NAvItems("Cpu",ImageVector.vectorResource(R.drawable.cpu),SystemScreen.CpuScreen),
         NAvItems("Battery",ImageVector.vectorResource(R.drawable.baseline_battery_6_bar_24),SystemScreen.BatteryScreen)
     )


    NavigationBar(modifier = Modifier, containerColor = Color(0xFF100E13)
    ) {


        nvaListItem.forEach{item->
            NavigationBarItem(
                modifier = Modifier.padding(top = 8.dp, ).size(60.dp),
                icon = { Icon(item.icon, contentDescription = "icon", tint = Color.White) },
                label = { Text(item.lable, modifier = Modifier, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, color = Color.White)},
                selected = if (currentScreen==item.Screen) true else false,
                onClick = {if(currentScreen!=item.Screen){
                    navController.navigate(item.Screen.name){
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = item.Screen == SystemScreen.RamScreen
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                }else{
                    navController.navigate(item.Screen.name)
                }
                }
            )
        }
    }


}


