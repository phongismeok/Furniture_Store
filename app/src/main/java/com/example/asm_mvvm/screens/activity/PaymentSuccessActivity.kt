package com.example.asm_mvvm.screens.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.asm_mvvm.MainActivity
import com.example.asm_mvvm.R
import com.example.asm_mvvm.ui.theme.MyButton
import com.example.asm_mvvm.ui.theme.MyButton2
import com.example.asm_mvvm.ui.theme.MyButton3

class PaymentSuccessActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Success!",
                    fontSize = 50.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(20.dp)
                )

                val imageRequest = ImageRequest.Builder(this@PaymentSuccessActivity)
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
                    val intent = Intent(this@PaymentSuccessActivity, MyOrderActivity::class.java)
                    startActivity(intent)
                }, mauChu = Color.White, mauNen = Color.Black)
                MyButton3(title = "BACK TO HOME", onClick = {
                    val intent = Intent(this@PaymentSuccessActivity, MainActivity::class.java)
                    startActivity(intent)
                }, mauChu = Color.Black, mauNen = Color.White)
            }
        }
    }
}