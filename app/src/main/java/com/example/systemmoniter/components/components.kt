package com.example.systemmoniter.components

import android.app.ActivityManager
import android.content.Context
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.systemmoniter.R
import com.example.systemmoniter.util.RamUsage
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

import kotlinx.coroutines.delay


@Composable
fun button(icon:Int,ondone:()->Unit){

    var scale = remember { mutableStateOf(1f) }

    val animatedScale by animateFloatAsState(
        targetValue = scale.value,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "Heartbeat Animation"
    )

    LaunchedEffect(Unit) {
        while (true) {
            scale.value = 1.1f
            delay(500)
            scale.value = 1f
            delay(1000)
        }
    }

    // Animate the shining effect
    val infiniteTransition = rememberInfiniteTransition(label = "Shining Effect")
    val shineProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Shine Animation"
    )

    // Define the dynamic gradient colors
    val gradientBrush = Brush.sweepGradient(
        colors = listOf(
            Color.Transparent, // No shine at start
            Color(0xFFEFE0D3).copy(alpha = shineProgress), // Shine effect
            Color(0xFFE8D405).copy(alpha = 1f - shineProgress), // Fading effect
            Color.Transparent // No shine at end
        )
    )

    Box(
        modifier = Modifier
            .size(150.dp)
            .scale(animatedScale)
            .clip(CircleShape)
            .border(
                width = 6.dp,
                brush = gradientBrush, // Apply the animated gradient
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape),
            enabled = true,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF101015),
                contentColor = Color.White
            ),
            onClick = { ondone.invoke() },
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp)
        ) {
            Icon(
                painter = painterResource(icon),
                contentDescription = "icon",
                modifier = Modifier.size(60.dp)
            )
        }
    }


}


@Composable
fun RamIndicator(context: Context, ondone: () -> Unit ) {

    var ram = remember { mutableStateOf(RamUsage(0f,0f,0f)) }

    LaunchedEffect(Unit) {
        while (true) {
            ram.value = getRamUsage(context) // Fetch RAM percentage
            delay(500) // Update every second
        }
    }

    val animatedProgress by animateFloatAsState(
        targetValue = ram.value.usageram,
        animationSpec = tween(1000, easing = FastOutSlowInEasing),
        label = "ram_animation"
    )



    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(modifier = Modifier.padding(8.dp).fillMaxWidth(), horizontalArrangement = Arrangement.Absolute.SpaceBetween) {

            CirclePregress(animatedProgress)

            Spacer(modifier = Modifier.width(20.dp))


            val totalRamGB = (ram.value.totalRam / (1024 * 1024 * 1024)).toInt() // Fixed value
            val availableRamGB = ram.value.availableRam / (1024 * 1024 * 1024) // Dynamic
            val usedPercentage = "%.2f".format(ram.value.usageram)

            CardItemRam(totalRamGB,availableRamGB,usedPercentage.toFloat())

        }
        Spacer(modifier = Modifier.height(30.dp))
        GraphRam(context)

//        Button(onClick = { ondone.invoke() },) {
//            Text("Go Back")
//        }

    }
}

@Composable
 fun CirclePregress(animatedProgress: Float) {
    Box(
        modifier = Modifier.size(180.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(200.dp)) {
            drawArc(
                color = Color.Black.copy(alpha = 0.6f),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = 16f, cap = StrokeCap.Round)
            )

            drawArc(
                color = if (animatedProgress.toInt() <= 40) Color(0xFF6F6FE5)
                else if (animatedProgress.toInt() in 41..70)
                    Color(0xFF3A7ED3)
                else Color(0xFFD24C84),
                startAngle = -90f,
                sweepAngle = animatedProgress * 3.6f, // Convert percentage to degrees
                useCenter = false,
                style = Stroke(width = 28f, cap = StrokeCap.Round)
            )
        }

        Text(
            text = "${animatedProgress.toInt()}%",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black
        )
    }
}

//i modified my fun to a data class so that it will give also value of card
fun getRamUsage(context:Context):RamUsage{
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

    val MemoryInfo = ActivityManager.MemoryInfo()
    activityManager.getMemoryInfo(MemoryInfo)

    val totalram = MemoryInfo.totalMem.toFloat()
    val availablememory = MemoryInfo.availMem

    val usedmemory = totalram - availablememory
    val usedper = (usedmemory/totalram)*100f

    return RamUsage(totalRam = totalram, availableRam = availablememory.toFloat(), usageram = usedper)

}



@Composable
fun CardItemRam(totalRamGB:Int,availableRamGB:Float ,usedPercentage:Float){
    Card(modifier = Modifier.padding(8.dp) .fillMaxWidth()
        , elevation =CardDefaults.cardElevation(defaultElevation = 15.dp),colors = CardDefaults.cardColors(containerColor = Color(0xFF3D3B3F))) {
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)){ append("Total RAM:")}

                withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                    append("${totalRamGB+1}GB")}

            },modifier = Modifier.padding(start = 10.dp, top = 10.dp,), color = Color.White
        )

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)){ append("Reserved ram:")}

                withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                    append("1GB")}

            },modifier = Modifier.padding(start = 10.dp, top = 10.dp), color = Color.White
        )



        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)){ append("Free ram:")}

                withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                    append(" ${"%.2f".format(availableRamGB)}GB")}

            },modifier = Modifier.padding(start = 10.dp, top = 10.dp),color = Color.White
        )

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)){ append("Used Ram:")}

                withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                    append("$usedPercentage%")}

            },modifier = Modifier.padding(start = 10.dp, top = 10.dp), color = Color.White)


}}


@Composable
fun GraphRam(context: Context){

    val ramData = remember { mutableStateOf(listOf<Pair<Float, Float>>()) }
    val lasttime = remember { mutableStateOf(0f) }

    LaunchedEffect(Unit) {

        while (true){
            val ramInfo = getRamUsage(context)

            val newdata = ramData.value + (lasttime.value to ramInfo.usageram)
            lasttime.value+=1
            ramData.value = newdata.takeLast(30)

            delay(1000)
        }
    }


        Text("RAM Usage Over Time", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Medium)
        RAmUsageshow(ramData.value)



}

@Composable
fun RAmUsageshow(ramData: List<Pair<Float, Float>>) {

    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(270.dp)
            .padding(16.dp),
        factory = { context ->
            LineChart(context).apply {
                description.isEnabled = false // Hide description text
                // Set background to black
                xAxis.apply {
                    position = XAxis.XAxisPosition.BOTTOM
                    setDrawGridLines(true)
                    textColor = Color.Black.hashCode() // Make X-axis labels white
                    gridColor = Color.Black.hashCode() // Set grid lines to gray for better visibility
                }
                axisLeft.apply {
                    setDrawGridLines(true)
                    textColor = Color.Black.hashCode() // Make Y-axis labels white
                    gridColor = Color.Black.hashCode() // Grid lines in gray
                }
                axisRight.apply {
                    isEnabled = true // Hide the right axis for a cleaner look
                }
                legend.apply {
                    textColor = Color.Black.hashCode() // Legend text in white
                    isEnabled = true
                }
                setTouchEnabled(true)
                setPinchZoom(true)
                animateX(20000) // Animate the chart
            }
        },
        update = { chart ->
            val entries = ramData.map { (time, usage) ->
                Entry(time, usage)
            }

            val dataSet = LineDataSet(entries, "RAM Usage").apply {
                color = Color.Blue.hashCode() // Changed line color to cyan for better contrast
                valueTextColor = Color.Black.hashCode() // Make value labels white
                setDrawValues(true)
                setDrawCircles(true)
                circleColors = listOf(Color.Blue.hashCode()) // Make data points stand out
                mode = LineDataSet.Mode.CUBIC_BEZIER // Smooth curve
            }

            chart.data = LineData(dataSet)
//            chart.notifyDataSetChanged()
//            chart.animateX(10)
            chart.invalidate() // Refresh the chart
        }
    )




}

