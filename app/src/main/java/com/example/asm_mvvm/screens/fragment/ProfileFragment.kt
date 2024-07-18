package com.example.asm_mvvm.screens.fragment

import android.content.Intent
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.asm_mvvm.screens.activity.CartActivity
import com.example.asm_mvvm.screens.activity.MyOrderActivity
import com.example.asm_mvvm.screens.activity.MyReviewActivity
import com.example.asm_mvvm.screens.activity.SettingActivity
import com.example.asm_mvvm.screens.activity.ShippingActivity
import com.example.asm_mvvm.ui.theme.MyToolbar2
import com.example.asm_mvvm.viewmodels.ShippingViewModel
import com.example.asm_mvvm.viewmodels.UserViewModel

@Composable
fun ProfileFragment(){
    val context = LocalContext.current
    SharedPreferencesManager.init(context)
    val userViewModel = UserViewModel()

    Column(modifier = Modifier.fillMaxSize()) {
        MyToolbar2(title = "Profile")
        MyInfo(userViewModel = userViewModel)
        MyOptions(tittle = "My orders", content = "haha") {
            val intent = Intent(context, MyOrderActivity::class.java)
            context.startActivity(intent)
        }
        MyOptions(tittle = "Shipping Addresses", content = "haha") {
            val intent = Intent(context, ShippingActivity::class.java)
                context.startActivity(intent)
        }
        MyOptions(tittle = "Payment Method", content = "haha") {
            Toast
                .makeText(
                    context,
                    "Chức năng tạm chưa phát triển",
                    Toast.LENGTH_SHORT
                )
                .show()
        }
        MyOptions(tittle = "My reviews", content = "haha") {
            val intent = Intent(context, MyReviewActivity::class.java)
            context.startActivity(intent)
        }
        MyOptions(tittle = "Setting", content = "haha") {
            val intent = Intent(context, SettingActivity::class.java)
            context.startActivity(intent)
        }
    }
}

@Composable
fun MyInfo(userViewModel: UserViewModel) {
    val account = userViewModel.getEmailFromSharedPreferences()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp)
    ) {
        if (account != null) {
            val user by userViewModel.user.observeAsState()

            LaunchedEffect(account) {
                if (account != "") {
                    userViewModel.getUserByUsername(account)
                }
            }
            user?.let {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = it[0].avatar,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .size(80.dp).clip(CircleShape)
                    )
                    Column(modifier = Modifier.padding(start = 15.dp)) {
                        Text(text = it[0].username, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(text = it[0].name, fontSize = 22.sp)
                    }
                }
            } ?: run {
                Text("Loading...", fontSize = 22.sp)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyOptions(tittle: String, content: String, onClick: () -> Unit) {
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
            .height(120.dp)
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
                    fontSize = 23.sp,
                    fontWeight = FontWeight(550),
                    color = Color.Black
                )
                Text(text = content, modifier = Modifier.padding(top = 5.dp))
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