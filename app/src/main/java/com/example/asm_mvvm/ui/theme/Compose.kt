package com.example.asm_mvvm.ui.theme

import androidx.compose.animation.core.Animation
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition


import com.example.asm_mvvm.R


@Composable
fun Animation(image: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(image))
    val progress by animateLottieCompositionAsState(composition)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(200.dp)
    ) {
        LottieAnimation(
            composition = composition,
            progress = progress,
        )
    }
}

@Composable
fun MyButton(
    title: String,
    onClick: () -> Unit,
    mauChu: Color,
    mauNen: Color
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = mauNen, contentColor = mauChu),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(70.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(
            text = title,
            color = mauChu,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun MyButtonWithImage(
    title: String,
    onClick: () -> Unit,
    mauChu: Color,
    mauNen: Color,
    image: Int
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = mauNen, contentColor = mauChu),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(70.dp),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, Color.Black)

    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "anh nen",
            modifier = Modifier.size(30.dp),
            contentScale = ContentScale.Crop
        )
        Text(
            text = title,
            color = mauChu,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyToolbar(title: String) {
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // search
                Box(
                    modifier = Modifier
                        .weight(1.5f),
                    contentAlignment = Alignment.TopStart
                ) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp),
                    )
                }
                // tittle
                Box(
                    modifier = Modifier
                        .weight(5f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = title,
                            color = Color.Black,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                //cart
                Box(
                    modifier = Modifier
                        .weight(1.5f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White // Màu nền của TopAppBar
        )
    )
}