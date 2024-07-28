package com.example.asm_mvvm.screens.activity

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.asm_mvvm.R
import com.example.asm_mvvm.SharedPreferencesManager
import com.example.asm_mvvm.request.NotificationRequest
import com.example.asm_mvvm.ui.theme.MyButton
import com.example.asm_mvvm.ui.theme.MyToolbar3
import com.example.asm_mvvm.viewmodels.CartViewModel
import com.example.asm_mvvm.viewmodels.NotificationViewModel
import com.example.asm_mvvm.viewmodels.ShippingViewModel
import com.example.asm_mvvm.viewmodels.UserViewModel

class CheckOutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferencesManager.init(applicationContext)
        val priceNhan = intent.getStringExtra("PRICE")
        setContent {
            SizeCheckOutScreen(price = priceNhan!!)
        }
    }
}

@Composable
fun Title(title: String, type: Int, sizeScreen: String) {
    val shippingViewModel = ShippingViewModel()
    val userViewModel = UserViewModel()
    val account = userViewModel.getEmailFromSharedPreferences() ?: ""
    val shipState = shippingViewModel.ships2.observeAsState()
    val ships = shipState.value
    shippingViewModel.getShipAddressBySelect(select = 1, account = account)
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp), Arrangement.SpaceAround
    ) {
        Text(
            text = title,
            fontSize = when (sizeScreen) {
                "large" -> {
                    25.sp
                }

                "fairly" -> {
                    23.sp
                }

                "medium" -> {
                    21.sp
                }

                else -> {
                    19.sp
                }
            },
            color = Color(0xFF408143),
            modifier = Modifier.fillMaxWidth(0.5f)
        )
        Spacer(modifier = Modifier)
        Icon(
            Icons.Default.Edit, contentDescription = null, modifier = Modifier
                .size(
                    when (sizeScreen) {
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
                            24.dp
                        }
                    }
                )
                .fillMaxWidth(0.5f)
                .clickable {
                    when (type) {
                        1 -> {
                            val intent = Intent(context, ShippingActivity::class.java)
                            context.startActivity(intent)
                            if (ships != null) {
                                val id = ships.id
                                intent.putExtra("CLICK", id)
                                context.startActivity(intent)
                            } else {
                                intent.putExtra("CLICK", "no")
                                context.startActivity(intent)
                            }

                        }

                        2 -> {
                            Toast
                                .makeText(
                                    context,
                                    "Chức năng tạm chưa phát triển",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }

                        else -> {
                            Toast
                                .makeText(
                                    context,
                                    "Chức năng tạm chưa phát triển",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    }
                })
    }
}

@Composable
fun ContentShippingAddress(sizeScreen: String) {
    val shippingViewModel = ShippingViewModel()
    val userViewModel = UserViewModel()

    val account = userViewModel.getEmailFromSharedPreferences() ?: ""
    val shipState = shippingViewModel.ships2.observeAsState()
    val ships = shipState.value
    shippingViewModel.getShipAddressBySelect(1, account)

    if (ships == null) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Bạn chưa chọn địa chỉ ship", fontSize = 20.sp)
        }
    } else {
        Card(
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier.padding(
                top =
                when (sizeScreen) {
                    "large" -> {
                        20.dp
                    }

                    "fairly" -> {
                        17.dp
                    }

                    "medium" -> {
                        15.dp
                    }

                    else -> {
                        13.dp
                    }
                }, start = 20.dp, end = 20.dp, bottom =
                when (sizeScreen) {
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
                }
            ),
            colors = CardDefaults.cardColors(
                containerColor =
                Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation =
                3.dp
            )
        ) {
            Column {
                Text(
                    text = ships.name,
                    fontSize =
                    when (sizeScreen) {
                        "large" -> {
                            25.sp
                        }

                        "fairly" -> {
                            23.sp
                        }

                        "medium" -> {
                            21.sp
                        }

                        else -> {
                            19.sp
                        }
                    },
                    modifier = Modifier.padding(top = 10.dp, start = 20.dp, bottom = 10.dp)
                )
                Divider(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                )
                Text(
                    text = ships.address,
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
                            14.sp
                        }
                    },
                    modifier = Modifier.padding(top = 10.dp, start = 20.dp, bottom = 20.dp)
                )
            }
        }
    }

}

@Composable
fun ContentPayment(number: String, sizeScreen: String) {
    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.padding(
            top =
            when (sizeScreen) {
                "large" -> {
                    20.dp
                }

                "fairly" -> {
                    17.dp
                }

                "medium" -> {
                    15.dp
                }

                else -> {
                    13.dp
                }
            }, start = 20.dp, end = 20.dp, bottom =
            when (sizeScreen) {
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
            }
        ),
        colors = CardDefaults.cardColors(
            containerColor =
            Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation =
            3.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    when (sizeScreen) {
                        "large" -> {
                            100.dp
                        }

                        "fairly" -> {
                            80.dp
                        }

                        "medium" -> {
                            60.dp
                        }

                        else -> {
                            50.dp
                        }
                    }
                ),
            horizontalArrangement = Arrangement.Center,
            Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.iconcard),
                contentDescription = "anh nen",
                modifier = Modifier
                    .height(
                        when (sizeScreen) {
                            "large" -> {
                                60.dp
                            }

                            "fairly" -> {
                                50.dp
                            }

                            "medium" -> {
                                40.dp
                            }

                            else -> {
                                35.dp
                            }
                        }
                    )
                    .weight(0.4f),
                contentScale = ContentScale.Fit
            )
            Text(
                text = number, fontSize =
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
                        14.sp
                    }
                }, modifier = Modifier
                    .padding(top = 10.dp, start = 10.dp, bottom = 10.dp)
                    .weight(0.6f)
            )
        }
    }
}

@Composable
fun ContentDeliveryMethod(speed: String, sizeScreen: String) {
    Card(
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier.padding(
            top =
            when (sizeScreen) {
                "large" -> {
                    20.dp
                }

                "fairly" -> {
                    17.dp
                }

                "medium" -> {
                    15.dp
                }

                else -> {
                    13.dp
                }
            }, start = 20.dp, end = 20.dp, bottom =
            when (sizeScreen) {
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
            }
        ),
        colors = CardDefaults.cardColors(
            containerColor =
            Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation =
            3.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    when (sizeScreen) {
                        "large" -> {
                            100.dp
                        }

                        "fairly" -> {
                            80.dp
                        }

                        "medium" -> {
                            60.dp
                        }

                        else -> {
                            50.dp
                        }
                    }
                ),
            horizontalArrangement = Arrangement.Center,
            Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = 20.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.anhdhl),
                    contentDescription = "anh nen",
                    modifier = Modifier
                        .height(
                            when (sizeScreen) {
                                "large" -> {
                                    50.dp
                                }

                                "fairly" -> {
                                    40.dp
                                }

                                "medium" -> {
                                    35.dp
                                }

                                else -> {
                                    30.dp
                                }
                            }
                        )
                        .width(
                            when (sizeScreen) {
                                "large" -> {
                                    130.dp
                                }

                                "fairly" -> {
                                    120.dp
                                }

                                "medium" -> {
                                    115.dp
                                }

                                else -> {
                                    110.dp
                                }
                            }
                        )
                        .background(Color.Yellow),
                    contentScale = ContentScale.Fit
                )
            }

            Text(
                text = speed, fontSize =
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
                        14.sp
                    }
                }, modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
                    .weight(0.5f), fontWeight = FontWeight(550)
            )
        }
    }
}

@Composable
fun DeMuc2(tittle: String, price: String, sizeScreen: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                when (sizeScreen) {
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
                }
            ), Arrangement.SpaceBetween
    ) {
        Text(
            text = tittle,
            fontSize =
            when (sizeScreen) {
                "large" -> {
                    25.sp
                }

                "fairly" -> {
                    23.sp
                }

                "medium" -> {
                    21.sp
                }

                else -> {
                    19.sp
                }
            },
            color = Color(0xFF408143),
            modifier = Modifier.fillMaxWidth(0.4f)
        )
        Text(
            text = price,
            fontSize =
            when (sizeScreen) {
                "large" -> {
                    25.sp
                }

                "fairly" -> {
                    23.sp
                }

                "medium" -> {
                    21.sp
                }

                else -> {
                    19.sp
                }
            },
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(0.6f),
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun DeMuc3(tittle: String, price: String, sizeScreen: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                when (sizeScreen) {
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
                }
            ), Arrangement.SpaceBetween
    ) {
        Text(
            text = tittle,
            fontSize =
            when (sizeScreen) {
                "large" -> {
                    25.sp
                }

                "fairly" -> {
                    23.sp
                }

                "medium" -> {
                    21.sp
                }

                else -> {
                    19.sp
                }
            },
            color = Color(0xFF408143),
            modifier = Modifier.fillMaxWidth(0.4f)
        )
        Text(
            text = price,
            fontSize =
            when (sizeScreen) {
                "large" -> {
                    25.sp
                }

                "fairly" -> {
                    23.sp
                }

                "medium" -> {
                    21.sp
                }

                else -> {
                    19.sp
                }
            },
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(0.6f),
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun ContentTotal(pricePro: Double, priceShip: Double, sizeScreen: String) {
    val priceAll = pricePro + priceShip
    val context = LocalContext.current

    val notificationViewModel = NotificationViewModel()
    val shippingViewModel = ShippingViewModel()
    val carViewModel = CartViewModel()
    val userViewModel = UserViewModel()

    val account = userViewModel.getEmailFromSharedPreferences() ?: ""

    val shipState = shippingViewModel.ships.observeAsState(initial = emptyList())
    val ships = shipState.value

    val cartState = carViewModel.carts.observeAsState(initial = emptyList())
    val carts = cartState.value

    carViewModel.getCartByAccount(account)
    shippingViewModel.getShipAddressBySelect(1, account)
    Card(
        shape = RoundedCornerShape(5.dp), modifier = Modifier
            .padding(
                top =
                when (sizeScreen) {
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
                }, start = 20.dp, end = 20.dp, bottom =
                when (sizeScreen) {
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
                }
            )
            .fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor =
            Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation =
            3.dp
        )
    ) {
        Column {
            DeMuc2(tittle = "Order:", price = "$ $pricePro", sizeScreen = sizeScreen)
            DeMuc2(tittle = "Delivery:", price = "$ $priceShip", sizeScreen = sizeScreen)
            DeMuc3(tittle = "Total:", price = "$ $priceAll", sizeScreen = sizeScreen)
        }
    }
    MyButton(title = "SUBMIT ORDER", onClick = {
        if (ships.isEmpty()) {
            Toast
                .makeText(
                    context,
                    "Bạn chưa chọn địa chỉ nhận hàng",
                    Toast.LENGTH_SHORT
                )
                .show()
        } else {
            if (pricePro == 0.0) {
                Toast
                    .makeText(
                        context,
                        "Bạn chưa mua gì",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            } else {
                for (cart in carts) {
                    val priceOnePro = cart.price * cart.quantity.toDouble()
                    val notificationBody = NotificationRequest(
                        title = "Đặt hàng thành công",
                        content = "bạn đã mua ${cart.quantity} ${cart.productName} với giá $priceOnePro",
                        state = 1,
                        image = cart.image,
                        account = account
                    )
                    notificationViewModel.addNotification(account, notificationBody)
                    carViewModel.deleteCart(cart.id, account, context, 0)
                }

                val intent = Intent(context, PaymentSuccessActivity::class.java)
                context.startActivity(intent)
            }

        }

    }, mauChu = Color.White, mauNen = Color.Black, type = sizeScreen)
}

@Composable
fun ScreenCheckOut(type: String, price: String?) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        MyToolbar3(title = "Check out")
        Title(title = "Shipping Address", 1, sizeScreen = type)
        ContentShippingAddress(sizeScreen = type)
        Title(title = "Payment", 2, sizeScreen = type)
        ContentPayment(number = "1234 5678 9012 3456", sizeScreen = type)
        Title(title = "Delivery method", 3, sizeScreen = type)
        ContentDeliveryMethod(speed = "Fast (2-3days)", sizeScreen = type)
        if (price != null) {
            ContentTotal(pricePro = price.toDouble(), priceShip = 5.0, sizeScreen = type)
        }
    }
}


@Composable
fun ClickBackCheckOut() {
    val context = LocalContext.current
    BackHandler {
        val intent = Intent(context, CartActivity::class.java)
        context.startActivity(intent)
    }
}

@Composable
fun SizeCheckOutScreen(price: String) {
    ClickBackCheckOut()
    val context = LocalContext.current
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    val screenHeightPx = displayMetrics.heightPixels
    val density = displayMetrics.density

    val screenHeightDp = screenHeightPx / density

    if (screenHeightDp > 890) {
        // large
        ScreenCheckOut(type = "large", price = price)
    } else if (screenHeightDp > 800) {
        // fairly
        ScreenCheckOut(type = "medium", price = price)
    } else if (screenHeightDp > 714) {
        // medium
        ScreenCheckOut(type = "medium", price = price)
    } else {
        // smail
        ScreenCheckOut(type = "smail", price = price)
    }
}