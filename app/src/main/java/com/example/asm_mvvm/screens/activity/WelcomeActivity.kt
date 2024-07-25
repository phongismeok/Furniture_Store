package com.example.asm_mvvm.screens.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
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
import com.example.asm_mvvm.ui.theme.AnimationBigScreen
import com.example.asm_mvvm.ui.theme.MyButton
import com.example.asm_mvvm.ui.theme.MyButtonSmailScreen

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                if (maxWidth >= 720.dp) {
                    BigScreenTablet()
                } else if (maxWidth < 720.dp && maxWidth > 448.dp) {
                    SmailScreenTablet()
                } else if (maxWidth <= 448.dp && maxWidth > 360.dp) {
                    BigPhoneScreen()
                } else {
                    SmailPhoneScreen()
                }
            }
        }
    }
}

@Composable
fun BigPhoneScreen () {
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
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp),
            color = Color.Gray,
            fontFamily = FontFamily.Serif
        )
        AnimationBigScreen(image = R.raw.animation)
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
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }, mauChu = Color.White, mauNen = Color.Blue)

        MyButton(title = "Sign Up", onClick = {
            val intent = Intent(context, SignUpActivity::class.java)
            context.startActivity(intent)
        }, mauChu = Color.White, mauNen = Color.Red)
    }
}

@Composable
fun SmailPhoneScreen () {
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
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 10.dp),
            color = Color.Gray,
            fontFamily = FontFamily.Serif
        )
        Animation(image = R.raw.animation)
        Text(
            text = "Welcome!",
            fontSize = 18.sp,
            fontWeight = FontWeight(550),
            modifier = Modifier.padding(bottom = 10.dp),
            color = Color.Black
        )
        Text(
            text = "Welcome to our modern online furniture store! " +
                    "Explore our diverse selection of elegant decorations " +
                    "and luxurious furniture, all chosen to beautify and " +
                    "comfort your home.",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 20.dp),
            color = Color.Black
        )

        MyButtonSmailScreen(title = "Log in", onClick = {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }, mauChu = Color.White, mauNen = Color.Blue)

        MyButtonSmailScreen(title = "Sign Up", onClick = {
            val intent = Intent(context, SignUpActivity::class.java)
            context.startActivity(intent)
        }, mauChu = Color.White, mauNen = Color.Red)
    }
}

@Composable
fun BigScreenTablet () {
    val context = LocalContext.current
    Column {

    }
}

@Composable
fun SmailScreenTablet () {
    val context = LocalContext.current
    Column {

    }
}



