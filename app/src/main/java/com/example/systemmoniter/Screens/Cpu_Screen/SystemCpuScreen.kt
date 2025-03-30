package com.example.systemmoniter.Screens.Cpu_Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.systemmoniter.R
import com.example.systemmoniter.components.button
import com.example.systemmoniter.model.CpuFreq
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

@Composable
fun CpuScreen(navController: NavController,viewModel: CpuViewModel = hiltViewModel()){

    Surface(modifier = Modifier.fillMaxSize().background(brush = Brush.linearGradient(listOf(Color(
        0xff70e1f5
    ),Color(0xffffd194)
    ))), color = Color.Transparent, ) {



        val Cpuinfo = viewModel.Cpuinfo.collectAsState()
        val cpuf = viewModel.Cpufreq.collectAsState()
        val cput = viewModel.Cputemp.collectAsState().value

        val cpubutton = remember { mutableStateOf(true) }

        val cpucontent = remember { mutableStateOf(false) }





            Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){

                if(cpubutton.value){
                    button(R.drawable.cpu) {
                        cpubutton.value=false
                        cpucontent.value=true
                    }
                }

                if (cpucontent.value){
                    Row (modifier = Modifier.padding(8.dp),horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically){

                        Column(modifier = Modifier.weight(1f).padding(4.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {

                            Card(modifier = Modifier.fillMaxWidth().padding(10.dp), elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),colors = CardDefaults.cardColors(containerColor = Color(0xFF3D3B3F))) {


                                Column(modifier = Modifier.padding(10.dp), ) {
                                    Text("Cores: ${Cpuinfo.value?.cores}", modifier = Modifier.padding(start = 10.dp, top = 10.dp), fontWeight = FontWeight.SemiBold,color = Color.White)

                                    Text("Cpu Model: ${Cpuinfo.value?.model?.split(" ")?.get(1)}", modifier = Modifier.padding(start = 10.dp, top = 10.dp), fontWeight = FontWeight.SemiBold,color = Color.White)
                                    Text("Architure: ${Cpuinfo.value?.architecture}", modifier = Modifier.padding(start = 10.dp, top = 10.dp), fontWeight = FontWeight.SemiBold,color = Color.White)

                                }

                            }



                            Card(modifier = Modifier.padding(10.dp).fillMaxWidth().height(130.dp).padding(10.dp).height(
                                IntrinsicSize.Min), elevation = CardDefaults.cardElevation(defaultElevation = 15.dp),colors = CardDefaults.cardColors(containerColor = Color(0xFF3D3B3F)
                            )) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize() // Makes sure the text takes up the entire card
                                        .padding(10.dp),
                                    contentAlignment = Alignment.Center // Centers text in the box
                                ) {
                                    Text(
                                        text = buildAnnotatedString {
                                            append("${cput.split(".")[0]}") // Large temperature number
                                            withStyle(style = SpanStyle(fontSize = 24.sp)) { // Smaller °C
                                                append("°C")
                                            }
                                        },
                                        fontSize = 48.sp, // Large font size for number
                                        fontWeight = FontWeight.Bold,color = Color.White
                                    )
                                }                    }

                        }


                        Box( modifier = Modifier
                            .weight(1.5f) // Used weight instead of fixed width for better responsiveness
                            .padding(16.dp)
                            .clip(RoundedCornerShape(10.dp))
                        ){
                            LazyColumn (modifier = Modifier.fillMaxWidth().padding(16.dp).height(400.dp).clip(RoundedCornerShape(10.dp))){items(cpuf.value){cpu->

                                if (cpu != null) {
                                    CollectCpuFreq(cpu)
                                } } }
                        }





                    }


//            displaying cpu info
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // Used weight instead of fixed height to make it dynamic
                        .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {  chart(viewModel)}






                }

                }                }


        }









@Composable
fun chart( viewModel: CpuViewModel) {
    Text("Average Cpu Frequency")
    var charData = remember { mutableStateOf(listOf<Entry>()) }
    var cartDatafreq = viewModel.Cpufreq.collectAsState()
    var time = remember { mutableStateOf(0f) }

    LaunchedEffect (cartDatafreq.value){
        val avgfreq = cartDatafreq.value.map { it?.frequency ?: 0 }.average().toFloat()
        charData.value = charData.value.takeLast(30)+ Entry(time.value++,avgfreq)
    }

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(16.dp),
        factory = { context ->
            LineChart(context).apply {
                description.isEnabled = true
                setTouchEnabled(true)
                setPinchZoom(true)

                axisLeft.apply {
                    setDrawGridLines(true)
                    textColor =  Color.Black.toArgb()
                }

                axisRight.isEnabled = true
                xAxis.apply {
                    setDrawGridLines(true)
                    textColor =  Color.Black.toArgb()
                }
                legend.textColor =  Color.Black.toArgb()
                animateX(20000)
            }
        },
        update = { chart ->
            val dataSet = LineDataSet(charData.value, "CPU Frequency (MHz)").apply {
                color =  Color.Blue.toArgb()
                valueTextColor = Color.Black.toArgb()
                setDrawCircles(true)
                setDrawValues(true)
                mode = LineDataSet.Mode.CUBIC_BEZIER
            }

            chart.data = LineData(dataSet)
            chart.invalidate()
        }
    )
}

@Composable
fun CollectCpuFreq(cpu: CpuFreq) {

    Card(modifier = Modifier.padding(8.dp).fillMaxWidth(), elevation = CardDefaults.cardElevation(defaultElevation = 10.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF3D3B3F))) {
        Text("Core  ${cpu.Core}" , modifier = Modifier.padding(5.dp), fontWeight = FontWeight.SemiBold, color = Color.White)
        Text(" Freq: ${cpu.frequency}MHz" , modifier = Modifier.padding(5.dp), fontWeight = FontWeight.SemiBold, color = Color.White)



    }


}

