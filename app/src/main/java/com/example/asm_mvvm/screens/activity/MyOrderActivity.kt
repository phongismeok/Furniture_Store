package com.example.asm_mvvm.screens.activity

import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.asm_mvvm.R
import com.example.asm_mvvm.SharedPreferencesManager
import com.example.asm_mvvm.ui.theme.HorizontalLine
import com.example.asm_mvvm.ui.theme.MyToolbar3
import com.example.asm_mvvm.viewmodels.OrderViewModel
import com.example.asm_mvvm.viewmodels.UserViewModel

class MyOrderActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SizeMyOrderScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun ScreenMyOrder(sizeScreen: String) {
    val context = LocalContext.current
    SharedPreferencesManager.init(context)

    val orderViewModel = OrderViewModel()
    val userViewModel = UserViewModel()

    val isLoading by orderViewModel.isLoading.observeAsState(true)
    val isFailed by orderViewModel.isFailed.observeAsState(false)

    val selected = remember { mutableStateOf("Processing") }
    val account = userViewModel.getEmailFromSharedPreferences() ?: ""

    val orderState = orderViewModel.orders.observeAsState(initial = emptyList())
    val orders = orderState.value

    orderViewModel.getOrderByState(account, selected.value)

    Column(modifier = Modifier.fillMaxSize()) {

        MyToolbar3(title = "My order", sizeScreen = sizeScreen)

        ListOptionOrder(selected = selected, sizeScreen = sizeScreen)

        if (isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (isFailed) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Error", fontSize = 22.sp)
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (orders.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.no_order),
                            contentDescription = "",
                            modifier = Modifier.size(150.dp)
                        )
                        Text(
                            text = "Hiện tại chưa có đơn hàng nào",
                            fontSize = 22.sp,
                            modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 30.dp)
                        )
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(orders.size) { index ->
                            ContentOrder(
                                namePrd = orders[index].productName,
                                date = orders[index].createdAt!!,
                                state = orders[index].state,
                                image = orders[index].image,
                                quantity = orders[index].quantity,
                                price = orders[index].price,
                                idOrder = orders[index].id,
                                selected = selected,
                                account = account,
                                orderViewModel = orderViewModel,
                                sizeScreen = sizeScreen
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ListOptionOrder(sizeScreen: String, selected: MutableState<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                when (sizeScreen) {
                    "large" -> {
                        60.dp
                    }

                    "fairly" -> {
                        55.dp
                    }

                    "medium" -> {
                        50.dp
                    }

                    else -> {
                        45.dp
                    }
                }
            )
            .padding(top = 10.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Processing", fontSize =
            when (sizeScreen) {
                "large" -> {
                    22.sp
                }

                "fairly" -> {
                    21.sp
                }

                "medium" -> {
                    20.sp
                }

                else -> {
                    19.sp
                }
            }, fontWeight =
            if (selected.value == "Processing") {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            }, modifier = Modifier.clickable {
                selected.value = "Processing"
            })

            if (selected.value == "Processing") {
                Divider(
                    color = Color(0xFF020000),
                    thickness = 4.dp,
                    modifier = Modifier
                        .width(70.dp)
                        .padding(top = 10.dp)
                )
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Delivered", fontSize =
            when (sizeScreen) {
                "large" -> {
                    22.sp
                }

                "fairly" -> {
                    21.sp
                }

                "medium" -> {
                    20.sp
                }

                else -> {
                    19.sp
                }
            }, fontWeight =
            if (selected.value == "Delivered") {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            }, modifier = Modifier.clickable {
                selected.value = "Delivered"
            })

            if (selected.value == "Delivered") {
                Divider(
                    color = Color(0xFF020000),
                    thickness = 4.dp,
                    modifier = Modifier
                        .width(70.dp)
                        .padding(top = 10.dp)
                )
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Canceled", fontSize =
            when (sizeScreen) {
                "large" -> {
                    22.sp
                }

                "fairly" -> {
                    21.sp
                }

                "medium" -> {
                    20.sp
                }

                else -> {
                    19.sp
                }
            }, fontWeight =
            if (selected.value == "Canceled") {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            }, modifier = Modifier.clickable {
                selected.value = "Canceled"
            })

            if (selected.value == "Canceled") {
                Divider(
                    color = Color(0xFF020000),
                    thickness = 4.dp,
                    modifier = Modifier
                        .width(70.dp)
                        .padding(top = 10.dp)
                )
            }
        }
    }
}

@Composable
fun ContentOrder(
    namePrd: String,
    date: String,
    state: String,
    image: String,
    quantity: Int,
    price: Double,
    idOrder: String,
    selected: MutableState<String>,
    account: String,
    orderViewModel: OrderViewModel,
    sizeScreen: String,
) {
    val context = LocalContext.current
    val totalPrice = quantity.toDouble() * price

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(
                when (sizeScreen) {
                    "large" -> {
                        220.dp
                    }

                    "fairly" -> {
                        210.dp
                    }

                    "medium" -> {
                        200.dp
                    }

                    else -> {
                        190.dp
                    }
                }
            )
            .padding(10.dp), shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor =
            Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation =
            3.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = namePrd,
                    fontSize =
                    when (sizeScreen) {
                        "large" -> {
                            20.sp
                        }

                        "fairly" -> {
                            18.sp
                        }

                        "medium" -> {
                            16.sp
                        }

                        else -> {
                            15.sp
                        }
                    },
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )
                Text(
                    text = date,
                    fontSize =
                    when (sizeScreen) {
                        "large" -> {
                            18.sp
                        }

                        "fairly" -> {
                            17.sp
                        }

                        "medium" -> {
                            15.sp
                        }

                        else -> {
                            14.sp
                        }
                    },
                    color = Color.Red
                )
            }

            HorizontalLine()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Text(
                        text = state,
                        fontSize =
                        when (sizeScreen) {
                            "large" -> {
                                20.sp
                            }

                            "fairly" -> {
                                18.sp
                            }

                            "medium" -> {
                                16.sp
                            }

                            else -> {
                                15.sp
                            }
                        },
                        color = Color(0xFFFF9800),
                        modifier = Modifier.align(Alignment.Start)
                    )
                    // image
                    Card(
                        modifier = Modifier
                            .size(
                                when (sizeScreen) {
                                    "large" -> {
                                        100.dp
                                    }

                                    "fairly" -> {
                                        90.dp
                                    }

                                    "medium" -> {
                                        80.dp
                                    }

                                    else -> {
                                        70.dp
                                    }
                                }
                            )
                            .padding(top = 5.dp, bottom = 5.dp),
                        shape = RoundedCornerShape(5.dp)
                    ) {
                        AsyncImage(
                            model = image,
                            contentDescription = null,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier.fillMaxSize()
                        )
                    }


                }

                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Row(modifier = Modifier.align(Alignment.End)) {
                        Text(
                            text = "quantity:", fontSize =
                            when (sizeScreen) {
                                "large" -> {
                                    20.sp
                                }

                                "fairly" -> {
                                    18.sp
                                }

                                "medium" -> {
                                    16.sp
                                }

                                else -> {
                                    15.sp
                                }
                            }
                        )
                        Text(
                            text = " $quantity",
                            fontSize =
                            when (sizeScreen) {
                                "large" -> {
                                    20.sp
                                }

                                "fairly" -> {
                                    18.sp
                                }

                                "medium" -> {
                                    16.sp
                                }

                                else -> {
                                    15.sp
                                }
                            },
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row {
                        Text(
                            text = "total amount:", fontSize =
                            when (sizeScreen) {
                                "large" -> {
                                    20.sp
                                }

                                "fairly" -> {
                                    18.sp
                                }

                                "medium" -> {
                                    16.sp
                                }

                                else -> {
                                    15.sp
                                }
                            }
                        )
                        Text(
                            text = " $totalPrice",
                            fontSize =
                            when (sizeScreen) {
                                "large" -> {
                                    20.sp
                                }

                                "fairly" -> {
                                    18.sp
                                }

                                "medium" -> {
                                    16.sp
                                }

                                else -> {
                                    15.sp
                                }
                            },
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Button(
                        onClick = {
                            if (selected.value == "Canceled") {
                                orderViewModel.deleteOrder(
                                    idOrder,
                                    account,
                                    context
                                )
                            } else {
                                orderViewModel.updateStateOrder(
                                    idOrder,
                                    "Canceled",
                                    selected.value,
                                    R.string.update_order_success_en,
                                    R.string.update_order_fail_en,
                                    context,
                                    account
                                )
                            }
                        },
                        shape = RoundedCornerShape(0.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        ),
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(
                            text = "Cancel", fontSize =
                            when (sizeScreen) {
                                "large" -> {
                                    15.sp
                                }

                                "fairly" -> {
                                    14.sp
                                }

                                "medium" -> {
                                    13.sp
                                }

                                else -> {
                                    12.sp
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun SizeMyOrderScreen() {
    val context = LocalContext.current
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    val screenHeightPx = displayMetrics.heightPixels
    val density = displayMetrics.density

    val screenHeightDp = screenHeightPx / density

    if (screenHeightDp > 890) {
        // large
        ScreenMyOrder(sizeScreen = "large")
    } else if (screenHeightDp > 800) {
        // fairly
        ScreenMyOrder(sizeScreen = "fairly")
    } else if (screenHeightDp > 714) {
        // medium
        ScreenMyOrder(sizeScreen = "medium")
    } else {
        // smail
        ScreenMyOrder(sizeScreen = "smail")
    }
}