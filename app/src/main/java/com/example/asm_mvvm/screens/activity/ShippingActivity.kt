package com.example.asm_mvvm.screens.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.asm_mvvm.SharedPreferencesManager
import com.example.asm_mvvm.ui.theme.AnimationLoading
import com.example.asm_mvvm.ui.theme.MyFloatingButton
import com.example.asm_mvvm.ui.theme.MyToolbar3
import com.example.asm_mvvm.viewmodels.ShippingViewModel
import com.example.asm_mvvm.viewmodels.UserViewModel

class ShippingActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SharedPreferencesManager.init(applicationContext)
        val priceNhan = intent.getStringExtra("PRICE") ?: ""
        val getId = intent.getStringExtra("CLICK") ?: ""

        setContent {
            SizeShippingScreen(price = priceNhan, id = getId)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun ListShip(id: String,sizeScreen: String) {
    val shippingViewModel = ShippingViewModel()
    val userViewModel = UserViewModel()

    val account = userViewModel.getEmailFromSharedPreferences() ?: ""

    val shipState = shippingViewModel.ships.observeAsState(initial = emptyList())
    val ships = shipState.value
    val context = LocalContext.current

    LaunchedEffect(account) {
        shippingViewModel.getShipAddressByAccount(account)
    }

    var dataClick by remember { mutableStateOf("") }
    var dataOld by remember { mutableStateOf(id) }
    var isChecked by remember { mutableStateOf(false) }

    if (ships.isEmpty()) {
        if (id == "no") {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "hãy thêm địa chỉ nhận hàng nhé", fontSize = 22.sp)
            }
        } else {
            AnimationLoading()
        }

    } else {
        LazyColumn {
            items(ships.size) { index ->

                isChecked = if (id == ships[index].id && dataClick == "") {
                    true
                } else {
                    dataClick == ships[index].id
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    //
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = {
                                dataClick = ships[index].id
                                shippingViewModel.updateSelectShipping(
                                    ships[index].id,
                                    1,
                                    "Thay đổi địa chỉ thành công",
                                    "Thay đổi địa chỉ thất bại",
                                    context,
                                    1
                                )

                                shippingViewModel.updateSelectShipping(
                                    dataOld,
                                    0,
                                    "Thay đổi địa chỉ thành công",
                                    "Thay đổi địa chỉ thất bại",
                                    context,
                                    0
                                )

                                dataOld = ships[index].id

                            }
                        )
                        Text(
                            text = "Use as the shipping address",
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                    }
                    //
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = CardDefaults.cardColors(
                            containerColor =
                            Color.White
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation =
                            3.dp
                        ),
                    ) {
                        TitleShip(tittle = ships[index].name, sizeScreen = sizeScreen)
                        Divider(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth()
                        )
                        Text(
                            text = ships[index].address,
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
                            modifier = Modifier.padding(
                                start = 15.dp,
                                end = 15.dp,
                                top = 5.dp,
                                bottom = 5.dp
                            )
                        )
                        Divider(
                            modifier = Modifier
                                .height(1.dp)
                                .fillMaxWidth()
                        )
                        Text(
                            text = ships[index].addressDetail,
                            fontSize =
                            when (sizeScreen) {
                                "large" -> {
                                    19.sp
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
                            modifier = Modifier.padding(
                                start = 15.dp,
                                end = 15.dp,
                                top = 5.dp,
                                bottom = 5.dp
                            )
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun TitleShip(tittle: String,sizeScreen: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = tittle,
            fontSize =
            when (sizeScreen) {
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
                    17.sp
                }
            },
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(top = 15.dp, bottom = 15.dp),
            fontWeight = FontWeight(500)
        )
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
                            25.dp
                        }
                    }
                )
                .fillMaxWidth(0.3f)
        )

    }
}

@Composable
fun ClickBackShip(price: String) {
    val context = LocalContext.current
    BackHandler {
        val intent = Intent(context, CheckOutActivity::class.java)
        context.startActivity(intent)
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun ScreenShip (sizeScreen:String,priceNhan:String,getId:String) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize()) {
        MyToolbar3(title = "Shipping address","")
        Box(modifier = Modifier.fillMaxSize()) {
            ListShip(id = getId,sizeScreen = sizeScreen)
            Box(
                contentAlignment = Alignment.BottomEnd, modifier = Modifier
                    .padding(end = 20.dp, bottom = 70.dp)
                    .fillMaxSize()
            ) {
                MyFloatingButton {
                    val intent =
                        Intent(context, AddShippingActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }
        ClickBackShip(price = priceNhan)
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun SizeShippingScreen(price:String,id:String) {
    val context = LocalContext.current
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    val screenHeightPx = displayMetrics.heightPixels
    val density = displayMetrics.density

    val screenHeightDp = screenHeightPx / density

    if (screenHeightDp > 890) {
        // large
        ScreenShip(sizeScreen = "large",price,id)
    } else if (screenHeightDp > 800) {
        // fairly
        ScreenShip(sizeScreen = "fairly",price,id)
    } else if (screenHeightDp > 714) {
        // medium
        ScreenShip(sizeScreen = "medium",price,id)
    } else {
        // smail
        ScreenShip(sizeScreen = "smail",price,id)
    }
}

