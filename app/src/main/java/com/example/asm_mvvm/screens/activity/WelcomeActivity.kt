package com.example.asm_mvvm.screens.activity

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.asm_mvvm.R
import com.example.asm_mvvm.ui.theme.Animation
import com.example.asm_mvvm.ui.theme.MyButton


class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SizeWelcomeScreen()
        }
    }
}

@Composable
fun ScreenWelcome(type: String) {
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "FURNITURE STORE",
            fontSize =
            when (type) {
                "large" -> {
                    30.sp
                }

                "fairly" -> {
                    27.sp
                }

                "medium" -> {
                    25.sp
                }

                else -> {
                    20.sp
                }
            },
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp),
            color = Color.Gray,
            fontFamily = FontFamily.Serif
        )
        Animation(image = R.raw.animation, type = "large")
        Text(
            text = "Welcome!",
            fontSize =
            when (type) {
                "large" -> {
                    29.sp
                }

                "fairly" -> {
                    26.sp
                }

                "medium" -> {
                    24.sp
                }

                else -> {
                    20.sp
                }
            },
            fontWeight = FontWeight(550),
            modifier = Modifier.padding(bottom = 10.dp),
            color = Color.Black
        )
        Text(
            text = "Welcome to our modern online furniture store! " +
                    "Explore our diverse selection of elegant decorations " +
                    "and luxurious furniture, all chosen to beautify and " +
                    "comfort your home.",
            fontSize =
            when (type) {
                "large" -> {
                    18.sp
                }

                "fairly" -> {
                    16.sp
                }

                "medium" -> {
                    15.sp
                }

                else -> {
                    13.sp
                }
            },
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 20.dp),
            color = Color.Black
        )

        MyButton(title = "Log in", onClick = {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }, mauChu = Color.White, mauNen = Color.Blue, type)

        MyButton(title = "Sign Up", onClick = {
            val intent = Intent(context, SignUpActivity::class.java)
            context.startActivity(intent)
        }, mauChu = Color.White, mauNen = Color.Red, type)
    }
}

@Composable
fun SizeWelcomeScreen() {
    val context = LocalContext.current
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
//    val screenWidthPx = displayMetrics.widthPixels
    val screenHeightPx = displayMetrics.heightPixels
    val density = displayMetrics.density

//    val screenWidthDp = screenWidthPx / density
    val screenHeightDp = screenHeightPx / density

    if (screenHeightDp > 890) {
        // large
        ScreenWelcome(type = "large")
    } else if (screenHeightDp > 800) {
        // fairly
        ScreenWelcome(type = "medium")
    } else if (screenHeightDp > 714) {
        // medium
        ScreenWelcome(type = "medium")
    } else {
        // smail
        ScreenWelcome(type = "smail")
    }
}


