package com.example.asm_mvvm.screens.fragment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import coil.compose.AsyncImage
import com.example.asm_mvvm.SharedPreferencesManager
import com.example.asm_mvvm.ui.theme.MyToolbar
import com.example.asm_mvvm.viewmodels.NotificationViewModel
import com.example.asm_mvvm.viewmodels.UserViewModel

@Composable
fun NotificationFragment() {
    val textState = remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize()) {
        MyToolbar(title = "Notifications", "notification", "Search notification", textState)
        ListNotification(textState.value)
    }
}

@Composable
fun ListNotification (dataSearch:String) {
    val context = LocalContext.current

    val notificationViewModel = NotificationViewModel()
    val userViewModel = UserViewModel()
    SharedPreferencesManager.init(context)

    val notificationState =
        notificationViewModel.notifications.observeAsState(initial = emptyList())
    val notifications = notificationState.value

    val account = userViewModel.getEmailFromSharedPreferences() ?: ""
    if(dataSearch == ""){
        notificationViewModel.getNotificationByAccount(account)
    }else{
        notificationViewModel.searchNotification(dataSearch,account)
    }

    LazyColumn {
        items(notifications.size) { index ->
            ContentNotification(
                image = notifications[index].image,
                title = notifications[index].title,
                content = notifications[index].content,
                click = notifications[index].state
            )
        }
    }
}

@Composable
fun ContentNotification(image: String, title: String, content: String,click:Int) {
    Card(
        shape = RoundedCornerShape(10.dp), modifier = Modifier.padding(5.dp),
        colors =
        if(click == 1) {
            CardDefaults.cardColors(
                containerColor =
                Color.White
            )
        }else{
            CardDefaults.cardColors(
                containerColor =
                Color.LightGray
            )
        }
        ,
        elevation = CardDefaults.cardElevation(
            defaultElevation =
            3.dp
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Card(shape = RoundedCornerShape(10.dp), modifier = Modifier.align(Alignment.CenterVertically)) {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .aspectRatio(1f)

                )
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 10.dp)) {
                Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text(text = content, fontSize = 18.sp)
            }
        }


    }
}
