package com.example.asm_mvvm.screens.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.asm_mvvm.SharedPreferencesManager
import com.example.asm_mvvm.screens.activity.MyOrderActivity
import com.example.asm_mvvm.screens.activity.MyReviewActivity
import com.example.asm_mvvm.screens.activity.SettingActivity
import com.example.asm_mvvm.screens.activity.ShippingActivity
import com.example.asm_mvvm.ui.theme.MyToolbar2
import com.example.asm_mvvm.viewmodels.ShippingViewModel
import com.example.asm_mvvm.viewmodels.UserViewModel

@Composable
fun ProfileFragment() {
    val context = LocalContext.current

    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    val screenHeightPx = displayMetrics.heightPixels
    val density = displayMetrics.density
    val screenHeightDp = screenHeightPx / density

    if (screenHeightDp > 890) {
        // large
        ScreenProfile(type = "large")
    } else if (screenHeightDp > 800) {
        // fairly
        ScreenProfile(type = "fairly")
    } else if (screenHeightDp > 714) {
        // medium
        ScreenProfile(type = "medium")
    } else {
        // smail
        ScreenProfile(type = "smail")
    }

}

@Composable
fun ScreenProfile(type: String) {
    val context = LocalContext.current
    SharedPreferencesManager.init(context)
    val userViewModel = UserViewModel()
    val shippingViewModel = ShippingViewModel()
    val account = userViewModel.getEmailFromSharedPreferences() ?: ""

    val shipState = shippingViewModel.ship.observeAsState()
    val ship = shipState.value
    shippingViewModel.getShipAddressBySelect(select = 1, account = account)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        MyToolbar2(title = "Profile", sizeScreen = type)
        MyInfo(userViewModel = userViewModel, type = type)
        MyOptions(tittle = "My orders", type = type) {
            val intent = Intent(context, MyOrderActivity::class.java)
            context.startActivity(intent)
        }
        MyOptions(tittle = "Shipping Addresses", type = type) {
            val intent = Intent(context, ShippingActivity::class.java)
            if (ship != null) {
                val id = ship.id
                intent.putExtra("CLICK", id)
                context.startActivity(intent)
            } else {
                intent.putExtra("CLICK", "no")
                context.startActivity(intent)
            }
            context.startActivity(intent)
        }
        MyOptions(tittle = "Payment Method", type = type) {
            Toast
                .makeText(
                    context,
                    "Chức năng tạm chưa phát triển",
                    Toast.LENGTH_SHORT
                )
                .show()
        }
        MyOptions(tittle = "My reviews", type = type) {
            val intent = Intent(context, MyReviewActivity::class.java)
            context.startActivity(intent)
        }
        MyOptions(tittle = "Setting", type = type) {
            val intent = Intent(context, SettingActivity::class.java)
            context.startActivity(intent)
        }
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun MyInfo(userViewModel: UserViewModel, type: String) {
    val account = userViewModel.getEmailFromSharedPreferences()

    val isLoading by userViewModel.isLoading.observeAsState(true)
    val isFailed by userViewModel.isFailed.observeAsState(false)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp)
    ) {
        if (account != null) {
            val user by userViewModel.user.observeAsState()
                if (account != "") {
                    userViewModel.getUserByUsername(account)
                }
            if (isLoading){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }else if(isFailed){
                Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                    Text("Error", fontSize = 22.sp)
                }
            }else{
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = user?.avatar,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                    )
                    Column(modifier = Modifier.padding(start = 15.dp)) {
                        Text(
                            text = user?.username ?: "", fontSize =
                            when (type) {
                                "large" -> {
                                    20.sp
                                }

                                "fairly" -> {
                                    19.sp
                                }

                                "medium" -> {
                                    18.sp
                                }

                                else -> {
                                    17.sp
                                }
                            }, fontWeight = FontWeight.Bold
                        )
                        user?.name?.let {
                            Text(
                                text = it, fontSize =
                                when (type) {
                                    "large" -> {
                                        21.sp
                                    }

                                    "fairly" -> {
                                        20.sp
                                    }

                                    "medium" -> {
                                        19.sp
                                    }

                                    else -> {
                                        18.sp
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyOptions(type: String, tittle: String, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor =
            Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation =
            3.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(
                when (type) {
                    "large" -> {
                        120.dp
                    }

                    "fairly" -> {
                        110.dp
                    }

                    "medium" -> {
                        100.dp
                    }

                    else -> {
                        90.dp
                    }
                }
            )
            .padding(10.dp),
        shape = RectangleShape,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            Arrangement.SpaceBetween,
            Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 25.dp)
            ) {
                Text(
                    text = tittle,
                    fontSize =
                    when (type) {
                        "large" -> {
                            23.sp
                        }

                        "fairly" -> {
                            21.sp
                        }

                        "medium" -> {
                            19.sp
                        }

                        else -> {
                            17.sp
                        }
                    },
                    fontWeight = FontWeight(550),
                    color = Color.Black
                )
            }
            Box {
                Icon(
                    Icons.Filled.KeyboardArrowRight, contentDescription = null, modifier = Modifier
                        .size(50.dp)
                        .padding(end = 10.dp)
                )
            }
        }
    }
}