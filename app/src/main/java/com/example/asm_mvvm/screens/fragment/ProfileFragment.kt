package com.example.asm_mvvm.screens.fragment

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.asm_mvvm.ui.theme.MyToolbar2
import com.example.asm_mvvm.viewmodels.UserViewModel

@Composable
fun ProfileFragment(){
    val userViewModel = UserViewModel()
    Column(modifier = Modifier.fillMaxSize()) {
        MyToolbar2(title = "Profile")
        MyInfo(userViewModel = userViewModel)
        MyOptions(tittle = "My orders", content = "haha") {

        }
        MyOptions(tittle = "Shipping Addresses", content = "haha") {

        }
        MyOptions(tittle = "Payment Method", content = "haha") {

        }
        MyOptions(tittle = "My reviews", content = "haha") {

        }
        MyOptions(tittle = "Setting", content = "haha") {

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