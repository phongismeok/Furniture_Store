package com.example.asm_mvvm.screens.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily


import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.asm_mvvm.R
import com.example.asm_mvvm.ui.theme.Animation
import com.example.asm_mvvm.ui.theme.MyButton

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "FURNITURE STORE",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 50.dp),
                    color = Color.Gray
                )
                Animation(image = R.raw.animation)
                Text(
                    text = "Welcome!",
                    fontSize = 26.sp,
                    fontWeight = FontWeight(550),
                    modifier = Modifier.padding(bottom = 10.dp),
                    color = Color.Black
                )
                Text(
                    text = "Welcome to our modern online furniture store! " +
                            "Explore our diverse selection of elegant decorations " +
                            "and luxurious furniture, all chosen to beautify and " +
                            "comfort your home.",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 20.dp),
                    color = Color.Black
                )

                MyButton(title = "Log in", onClick = {
                    val intent = Intent(this@WelcomeActivity, LoginActivity::class.java)
                    this@WelcomeActivity.startActivity(intent)
                }, mauChu = Color.White, mauNen = Color.Blue)

                MyButton(title = "Sign Up", onClick = {
                    val intent = Intent(this@WelcomeActivity, SignUpActivity::class.java)
                    this@WelcomeActivity.startActivity(intent)
                }, mauChu = Color.White, mauNen = Color.Red)

            }
        }

    }
}

