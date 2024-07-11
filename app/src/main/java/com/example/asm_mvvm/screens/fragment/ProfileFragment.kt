package com.example.asm_mvvm.screens.fragment

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.asm_mvvm.R
import com.example.asm_mvvm.ui.theme.ComposeScreen.MyInfo
import com.example.asm_mvvm.ui.theme.ComposeScreen.MyOptions
import com.example.asm_mvvm.ui.theme.MyToolbar2
import com.example.asm_mvvm.viewmodels.ProductViewModel
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