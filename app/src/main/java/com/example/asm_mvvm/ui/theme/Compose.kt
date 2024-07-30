package com.example.asm_mvvm.ui.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.asm_mvvm.R
import com.example.asm_mvvm.screens.activity.CartActivity
import com.example.asm_mvvm.screens.activity.LoginActivity

@Composable
fun HorizontalLine () {
    Divider(
        color = Color(0xFF0F0404),
        thickness = 1.dp,
        modifier = Modifier
            .fillMaxWidth()
    )
}

@Composable
fun Animation(image: Int,type:String) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(image))
    val progress by animateLottieCompositionAsState(composition)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(
            when (type) {
                "large" -> {
                    300.dp
                }
                "fairly" -> {
                    270.dp
                }
                "medium" -> {
                    200.dp
                }
                else -> {
                    170.dp
                }
            }
        )
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
    mauNen: Color,
    type : String = "no"
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = mauNen, contentColor = mauChu),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top =
            when (type) {
                "large" -> {
                    10.dp
                }
                "fairly" -> {
                    8.dp
                }
                "medium" -> {
                    6.dp
                }
                else -> {
                    4.dp
                }
            }, bottom =
            when (type) {
                "large" -> {
                    10.dp
                }
                "fairly" -> {
                    8.dp
                }
                "medium" -> {
                    6.dp
                }
                else -> {
                    4.dp
                }
            })
            .height(
                when (type) {
                    "large" -> {
                        70.dp
                    }
                    "fairly" -> {
                        65.dp
                    }
                    "medium" -> {
                        60.dp
                    }
                    else -> {
                        50.dp
                    }
                }
            ),
        shape = RoundedCornerShape(15.dp)
    ) {
        Text(
            text = title,
            color = mauChu,
            fontSize = when (type) {
                "large" -> {
                    22.sp
                }
                "fairly" -> {
                    20.sp
                }
                "medium" -> {
                    18.sp
                }
                else -> {
                    16.sp
                }
            },
            fontWeight = FontWeight(500)
        )
    }
}

@Composable
fun MyButtonCustom1(
    title: String,
    onClick: () -> Unit,
    mauChu: Color,
    mauNen: Color,
    type : String = "no"
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = mauNen, contentColor = mauChu),
        modifier = Modifier
            .fillMaxWidth()
            .height(
                when (type) {
                    "large" -> {
                        70.dp
                    }
                    "fairly" -> {
                        65.dp
                    }
                    "medium" -> {
                        60.dp
                    }
                    else -> {
                        50.dp
                    }
                }
            ),
        shape = RoundedCornerShape(bottomEnd = 10.dp, topEnd = 10.dp)
    ) {
        Text(
            text = title,
            color = mauChu,
            fontSize = when (type) {
                "large" -> {
                    22.sp
                }
                "fairly" -> {
                    20.sp
                }
                "medium" -> {
                    18.sp
                }
                else -> {
                    16.sp
                }
            },
            fontWeight = FontWeight(500)
        )
    }
}

@Composable
fun MyButton2(
    title: String,
    onClick: () -> Unit,
    mauChu: Color,
    mauNen: Color
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = mauNen, contentColor = mauChu),
        modifier = Modifier
            .padding(10.dp)
            .height(50.dp),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Text(
            text = title,
            color = mauChu,
            fontSize = 22.sp,
            fontWeight = FontWeight(500)
        )
    }
}

@Composable
fun MyButton3(
    title: String,
    onClick: () -> Unit,
    mauChu: Color,
    mauNen: Color,
    type: String
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = mauNen, contentColor = mauChu),
        modifier = Modifier
            .padding(10.dp)
            .height(
                when (type) {
                    "large" -> {
                        70.dp
                    }
                    "fairly" -> {
                        65.dp
                    }
                    "medium" -> {
                        60.dp
                    }
                    else -> {
                        50.dp
                    }
                }
            )
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, Color.Black)
    ) {
        Text(
            text = title,
            color = mauChu,
            fontSize =
            when (type) {
                "large" -> {
                    22.sp
                }
                "fairly" -> {
                    20.sp
                }
                "medium" -> {
                    18.sp
                }
                else -> {
                    16.sp
                }
            },
            fontWeight = FontWeight(500)
        )
    }
}

@Composable
fun MyButtonWithImage(
    title: String,
    onClick: () -> Unit,
    mauChu: Color,
    mauNen: Color,
    image: Int,
    type: String = "no"
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = mauNen, contentColor = mauChu),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 10.dp, end = 10.dp, top =
                when (type) {
                    "large" -> {
                        10.dp
                    }
                    "fairly" -> {
                        7.dp
                    }
                    "medium" -> {
                        6.dp
                    }
                    else -> {
                        5.dp
                    }
                },
                bottom =
                when (type) {
                    "large" -> {
                        10.dp
                    }
                    "fairly" -> {
                        8.dp
                    }
                    "medium" -> {
                        5.dp
                    }
                    else -> {
                        3.dp
                    }
                }
            )
            .height(
                when (type) {
                    "large" -> {
                        70.dp
                    }
                    "fairly" -> {
                        60.dp
                    }
                    "medium" -> {
                        55.dp
                    }
                    else -> {
                        50.dp
                    }
                }
            ),
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, Color.Black)

    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "anh nen",
            modifier = Modifier.size(
                when (type) {
                    "large" -> {
                        30.dp
                    }
                    "fairly" -> {
                        27.dp
                    }
                    "medium" -> {
                        22.dp
                    }
                    else -> {
                        20.dp
                    }
                }
            ),
            contentScale = ContentScale.Crop
        )
        Text(
            text = title,
            color = mauChu,
            fontSize =
            when (type) {
                "large" -> {
                    22.sp
                }
                "fairly" -> {
                    20.sp
                }
                "medium" -> {
                    18.sp
                }
                else -> {
                    16.sp
                }
            },
            fontWeight = FontWeight(500),
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyToolbar(title: String, type: String, place: String, textState: MutableState<String>,sizeScreen:String) {
    val isSearchActive = remember { mutableStateOf(false) }
    val context = LocalContext.current
    if (isSearchActive.value) {

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            singleLine = true,
            placeholder = { Text(place) },
            trailingIcon = {
                IconButton(onClick = {
                    textState.value = ""
                    isSearchActive.value = false
                }) {
                    Icon(painter = painterResource(id = R.drawable.cancel), contentDescription = "")
                }
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
        )
    } else {
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
                            modifier = Modifier
                                .size(
                                    when (sizeScreen){
                                        "large" -> {
                                            30.dp
                                        }
                                        "fairly" -> {
                                            28.dp
                                        }
                                        "medium" -> {
                                            26.dp
                                        }
                                        else -> {
                                            25.dp
                                        }
                                    }
                                )
                                .clickable {
                                    isSearchActive.value = true
                                },
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
                                fontSize =
                                when (sizeScreen){
                                    "large" -> {
                                        24.sp
                                    }
                                    "fairly" -> {
                                        22.sp
                                    }
                                    "medium" -> {
                                        20.sp
                                    }
                                    else -> {
                                        19.sp
                                    }
                                },
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
                            modifier = Modifier
                                .size(
                                    when (sizeScreen){
                                        "large" -> {
                                            30.dp
                                        }
                                        "fairly" -> {
                                            28.dp
                                        }
                                        "medium" -> {
                                            26.dp
                                        }
                                        else -> {
                                            25.dp
                                        }
                                    }
                                )
                                .clickable {
                                    val intent = Intent(context, CartActivity::class.java)
                                    intent.putExtra("TYPE", type)
                                    context.startActivity(intent)
                                }
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White // Màu nền của TopAppBar
            )
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyToolbar2(title: String,sizeScreen: String) {
    val context = LocalContext.current
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
                        modifier = Modifier.size(
                            when (sizeScreen){
                                "large" -> {
                                    30.dp
                                }
                                "fairly" -> {
                                    28.dp
                                }
                                "medium" -> {
                                    26.dp
                                }
                                else -> {
                                    25.dp
                                }
                            }
                        ),
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
                            fontSize =
                            when (sizeScreen){
                                "large" -> {
                                    24.sp
                                }
                                "fairly" -> {
                                    22.sp
                                }
                                "medium" -> {
                                    20.sp
                                }
                                else -> {
                                    19.sp
                                }
                            },
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                //exit
                Box(
                    modifier = Modifier
                        .weight(1.5f),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Icon(
                        Icons.Default.ExitToApp,
                        contentDescription = null,
                        modifier = Modifier
                            .size(
                                when (sizeScreen){
                                    "large" -> {
                                        30.dp
                                    }
                                    "fairly" -> {
                                        28.dp
                                    }
                                    "medium" -> {
                                        26.dp
                                    }
                                    else -> {
                                        25.dp
                                    }
                                }
                            )
                            .clickable {
                                val intent = Intent(context, LoginActivity::class.java)
                                context.startActivity(intent)
                            }
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White // Màu nền của TopAppBar
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyToolbar3(title: String,sizeScreen: String) {
    val context = LocalContext.current
    TopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 15.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // arrow
                Box(
                    modifier = Modifier
                        .weight(1.5f),
                    contentAlignment = Alignment.TopStart
                ) {
                    Icon(
                        Icons.Default.KeyboardArrowLeft,
                        contentDescription = null,
                        modifier = Modifier.size(
                            when (sizeScreen){
                                "large" -> {
                                    35.dp
                                }
                                "fairly" -> {
                                    33.dp
                                }
                                "medium" -> {
                                    31.dp
                                }
                                else -> {
                                    29.dp
                                }
                            }
                        ),
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
                            fontSize =
                            when (sizeScreen){
                                "large" -> {
                                    24.sp
                                }
                                "fairly" -> {
                                    22.sp
                                }
                                "medium" -> {
                                    20.sp
                                }
                                else -> {
                                    19.sp
                                }
                            },
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1.5f),
                    contentAlignment = Alignment.TopEnd
                ) {

                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White // Màu nền của TopAppBar
        )
    )
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun AnimationLoading() {
    val context = LocalContext.current
    val imageRequest = ImageRequest.Builder(context)
        .data(R.drawable.loading)
        .decoderFactory(ImageDecoderDecoder.Factory())
        .build()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imageRequest,
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
    }
}

@Composable
fun MyFloatingButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() }
    ) {
        Icon(Icons.Filled.Add, "Floating action button.", modifier = Modifier.size(30.dp))
    }
}

@Composable
fun CustomLineScreen(type: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                when (type) {
                    "large" -> {
                        15.dp
                    }
                    "fairly" -> {
                        10.dp
                    }
                    "medium" -> {
                        9.dp
                    }
                    else -> {
                        8.dp
                    }
                }
            ),
        horizontalArrangement = Arrangement.Center
    ) {
        Divider(
            color = Color(0xFF997777),
            thickness = 1.dp,
            modifier = Modifier.weight(0.1f)
        )

        Text(text = "or", fontSize = 18.sp)

        Divider(
            color = Color(0xFF997777),
            thickness = 1.dp,
            modifier = Modifier.weight(0.1f)
        )
    }
}

