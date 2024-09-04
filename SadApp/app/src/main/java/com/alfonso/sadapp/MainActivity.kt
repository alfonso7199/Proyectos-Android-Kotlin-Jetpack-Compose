package com.alfonso.sadapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Alignment
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberAsyncImagePainter
import com.alfonso.sadapp.ui.theme.SadAppTheme
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import kotlinx.coroutines.delay
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this) {}

        setContent {
            SadApp()
        }
    }
}

@Composable
fun AdMobBannerAd() {
    val context = LocalContext.current
    AndroidView(
        factory = {
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = "ca-app-pub-1820895983044372/1094034013"
                loadAd(AdRequest.Builder().build())
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    )
}
@Composable
fun SadApp() {
    val darkTheme = isSystemInDarkTheme()
    SadAppTheme (darkTheme = darkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SadAppScreen()
        }
    }
}


@Composable
fun SadAppScreen() {
    val currentTime = remember { mutableStateOf(LocalDateTime.now()) }
    
    LaunchedEffect(key1 = true) {
        while (true) {
            currentTime.value = LocalDateTime.now()
            delay(1000L)
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Image(painter = painterResource(id = R.drawable.cloud)
            , contentDescription = "Background image"
            , contentScale = ContentScale.Crop
            , modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(alpha = 0.25f))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 50.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = R.drawable.sadface)
                ,contentDescription = null)

            Text(text = stringResource(R.string.time_until_next_monday),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,

            )

            Spacer(modifier = Modifier.height(100.dp))

            Text(text = formatTimeToNextMonday(currentTime.value),
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold)
        }
        AdMobBannerAd()
    }
}

fun formatTimeToNextMonday(currentTime: LocalDateTime): String {
    // Si hoy es lunes y no ha pasado la medianoche, cuenta hoy como el próximo lunes
    val nextMonday = if (currentTime.dayOfWeek == DayOfWeek.MONDAY && currentTime.hour < 24) {
        currentTime.withHour(0).withMinute(0).withSecond(0).plusWeeks(1)
    } else {
        currentTime.with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0).plusWeeks(1)
    }
    val timeRemaining = ChronoUnit.SECONDS.between(currentTime, nextMonday)
    val hours = timeRemaining / 3600
    val minutes = (timeRemaining % 3600) / 60
    val seconds = timeRemaining % 60
    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SadApp()
}