package com.example.asm_mvvm.screens.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.asm_mvvm.MainActivity
import com.example.asm_mvvm.R
import com.example.asm_mvvm.ui.theme.MyButton
import com.example.asm_mvvm.ui.theme.MyButton3

class PaymentSuccessActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SizePaymentSuccessScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun ScreenPaymentSuccess (sizeScreen : String) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Success!",
            fontSize =
            when (sizeScreen) {
                "large" -> {
                    50.sp
                }

                "fairly" -> {
                    45.sp
                }

                "medium" -> {
                    40.sp
                }

                else -> {
                    35.sp
                }
            },
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(20.dp)
        )

        val imageRequest = ImageRequest.Builder(context)
            .data(R.drawable.truck)
            .decoderFactory(ImageDecoderDecoder.Factory())
            .build()

        AsyncImage(
            model = imageRequest,
            contentDescription = null,
            modifier = Modifier
                .size(250.dp)
                .padding(10.dp)
        )
        Text(
            text = "Your order will be delivered soon.",
            fontSize = 18.sp,
            color = Color.Gray
        )
        Text(
            text = "Thank you for choosing our app!",
            fontSize = 18.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        MyButton(title = "Track your orders", onClick = {
            val intent = Intent(context, MyOrderActivity::class.java)
            context.startActivity(intent)
        }, mauChu = Color.White, mauNen = Color.Black, type = sizeScreen)
        MyButton3(title = "BACK TO HOME", onClick = {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }, mauChu = Color.Black, mauNen = Color.White, type = sizeScreen)
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun SizePaymentSuccessScreen() {
    val context = LocalContext.current
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
//    val screenWidthPx = displayMetrics.widthPixels
    val screenHeightPx = displayMetrics.heightPixels
    val density = displayMetrics.density

//    val screenWidthDp = screenWidthPx / density
    val screenHeightDp = screenHeightPx / density

    if (screenHeightDp > 890) {
        // large
        ScreenPaymentSuccess(sizeScreen = "large")
    } else if (screenHeightDp > 800) {
        // fairly
        ScreenPaymentSuccess(sizeScreen = "fairly")
    } else if (screenHeightDp > 714) {
        // medium
        ScreenPaymentSuccess(sizeScreen = "medium")
    } else {
        // smail
        ScreenPaymentSuccess(sizeScreen = "smail")
    }
}