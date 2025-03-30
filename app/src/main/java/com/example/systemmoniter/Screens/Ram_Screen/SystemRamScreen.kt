package com.example.systemmoniter.Screens.Ram_Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.systemmoniter.R
import com.example.systemmoniter.components.RamIndicator
import com.example.systemmoniter.components.button

@Composable
fun RamScreen(navController: NavController){

    Surface(modifier = Modifier.fillMaxSize().background(brush = Brush.linearGradient(listOf(Color(
        0xff70e1f5
    ),Color(0xffffd194)
    ))), color = Color.Transparent) {
        val gobuttonon = remember { mutableStateOf(true) }
        val context = LocalContext.current

       val showRamcircle = remember { mutableStateOf(false) }
        Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){


            if (gobuttonon.value){
                button(R.drawable.baseline_memory_24) {

                    gobuttonon.value=false
//                    this will dtermine weather to show  ram circle or not
                    showRamcircle.value=true
                }
            }


            if (showRamcircle.value){

                RamIndicator(
                    context = context
                ){
                    showRamcircle.value=false
                    gobuttonon.value=true
                }
            }





        }
    }
}






