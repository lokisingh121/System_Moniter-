package com.example.systemmoniter.Screens.Battery_Screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.systemmoniter.R
import com.example.systemmoniter.components.button
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun BatteryScreen(navController: NavController,viewmodel: batteryViewmodel=hiltViewModel()) {

    val batteryt  = viewmodel.batteryinfo.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize().background(
            brush = Brush.verticalGradient(
                listOf(
                    Color(
                        0xff70e1f5
                    ), Color(0xFFFFDAAC)
                )
            )
        ), color = Color.Transparent
    ) {

        val batterybutton = remember { mutableStateOf(true) }
        val batterycontent = remember { mutableStateOf(false) }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {


            if (batterybutton.value) {
                button(R.drawable.baseline_battery_6_bar_24) {
                    batterybutton.value = false
                    batterycontent.value = true
                }
            }



            if (batterycontent.value) {


                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {




                    Card(modifier = Modifier.padding(8.dp).height(200.dp).width(120.dp), elevation = CardDefaults.cardElevation(defaultElevation = 10.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF3D3B3F))) {

                        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                            BatteryIndicator(
                                viewmodel.batteryinfo.value?.ischarging ?: false,
                                viewmodel.batteryinfo.value?.battertpercentage ?: 0f
                            )
                        }




                    }
                            Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

                                Card(modifier = Modifier.padding(8.dp), elevation = CardDefaults.cardElevation(defaultElevation = 10.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF3D3B3F))) {
                                    Text("Temp: ${batteryt.value?.temperature}°C" , modifier = Modifier.padding(5.dp), fontWeight = FontWeight.SemiBold, color = Color.White)
                                    Text(" Battery: ${batteryt.value?.battertpercentage}%" , modifier = Modifier.padding(5.dp), fontWeight = FontWeight.SemiBold, color = Color.White)



                                }

                                Card(modifier = Modifier.padding(10.dp)
                                    , elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),colors = CardDefaults.cardColors(containerColor = Color(0xFF3D3B3F)
                                )) {
                                    Box(
                                        modifier = Modifier
                                            // Makes sure the text takes up the entire card
                                            .padding(10.dp),
                                        contentAlignment = Alignment.Center // Centers text in the box
                                    ) {
                                        Text(
                                            text = buildAnnotatedString {
                                                append("${batteryt.value?.temperature}") // Large temperature number
                                                withStyle(style = SpanStyle(fontSize = 24.sp)) { // Smaller °C
                                                    append("°C")
                                                }
                                            },
                                            fontSize = 35.sp, // Large font size for number
                                            fontWeight = FontWeight.Bold,color = Color.White
                                        )
                                    }                    }
                            }



                }







            }
        }
    }


}

@Composable
fun BatteryIndicator(isCharging: Boolean, batteryPercentage: Float) {
    val batteryHeight = 300f  // Fixed height for battery shape
    val batteryWidth = 150f   // Fixed width
    val capHeight = 40f  // Small top cap height

    // Animate the fluid level when charging
    val animatedPercentage = remember { Animatable(batteryPercentage) }
    LaunchedEffect(batteryPercentage) {
        animatedPercentage.animateTo(
            targetValue = batteryPercentage,
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
        )
    }

    Box(
        modifier = Modifier.size(60.dp), // Box for better positioning
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(60.dp, 120.dp)) {
            val fluidHeight = (animatedPercentage.value / 100) * batteryHeight

            // Draw battery outline
            drawRoundRect(
                color = Color.Gray,
                size = Size(batteryWidth, batteryHeight),
                cornerRadius = CornerRadius(10f, 10f)

            )

            // Draw top cap
            drawRoundRect(
                color = Color.Gray,
                size = Size(batteryWidth * 0.6f, capHeight),
                topLeft = Offset((batteryWidth - batteryWidth * 0.6f) / 2, -capHeight),
                cornerRadius = CornerRadius(10f, 10f)

            )

            // Draw battery fluid (fills from bottom up)
            drawRoundRect(
                color = if (isCharging) Color.Green else Color.Yellow,
                size = Size(batteryWidth - 8f, fluidHeight),
                topLeft = Offset(4f, batteryHeight - fluidHeight),
                        cornerRadius = CornerRadius(10f, 10f)

            )
        }

        // Charging icon overlay
        if (isCharging) {
            Text("⚡", fontSize = MaterialTheme.typography.headlineMedium.fontSize, color = Color.White)
        }
    }
}

