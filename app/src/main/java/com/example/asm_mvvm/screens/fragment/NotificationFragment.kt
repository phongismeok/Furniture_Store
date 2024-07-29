package com.example.asm_mvvm.screens.fragment

import android.util.DisplayMetrics
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.asm_mvvm.ui.theme.MyToolbar
import com.example.asm_mvvm.viewmodels.NotificationViewModel
import com.example.asm_mvvm.viewmodels.UserViewModel

@Composable
fun NotificationFragment() {
    val context = LocalContext.current
    val textState = remember { mutableStateOf("") }

    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    val screenHeightPx = displayMetrics.heightPixels
    val density = displayMetrics.density
    val screenHeightDp = screenHeightPx / density

    Column(modifier = Modifier.fillMaxSize()) {
        if (screenHeightDp > 890) {
            // large
            MyToolbar(title = "Notifications", "notification", "Search notification", textState, sizeScreen = "large")
            ListNotification(textState.value, type = "large")
        } else if (screenHeightDp > 800) {
            // fairly
            MyToolbar(title = "Notifications", "notification", "Search notification", textState, sizeScreen = "fairly")
            ListNotification(textState.value, type = "fairly")
        } else if (screenHeightDp > 714) {
            // medium
            MyToolbar(title = "Notifications", "notification", "Search notification", textState, sizeScreen = "medium")
            ListNotification(textState.value, type = "medium")
        } else {
            // smail
            MyToolbar(title = "Notifications", "notification", "Search notification", textState, sizeScreen = "smail")
            ListNotification(textState.value, type = "smail")
        }
    }
}

@Composable
fun ListNotification(dataSearch: String, type: String) {
    val context = LocalContext.current

    val notificationViewModel = NotificationViewModel()
    val userViewModel = UserViewModel()
    SharedPreferencesManager.init(context)

    val notificationState =
        notificationViewModel.notifications.observeAsState(initial = emptyList())
    val notifications = notificationState.value

    val account = userViewModel.getEmailFromSharedPreferences() ?: ""
    if (dataSearch == "") {
        notificationViewModel.getNotificationByAccount(account)
    } else {
        notificationViewModel.searchNotification(dataSearch, account)
    }

    LazyColumn {
        items(notifications.size) { index ->
            ContentNotification(
                image = notifications[index].image,
                title = notifications[index].title,
                content = notifications[index].content,
                click = notifications[index].state,
                type = type,
                deleteClick = {
                    notificationViewModel.deleteNotifications(
                        notifications[index].id,
                        account,
                        context
                    )
                }
            )
        }
    }
}

@Composable
fun ContentNotification(
    image: String,
    title: String,
    content: String,
    click: Int,
    type: String,
    deleteClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(10.dp), modifier = Modifier.padding(5.dp),
        colors =
        if (click == 1) {
            CardDefaults.cardColors(
                containerColor =
                Color.White
            )
        } else {
            CardDefaults.cardColors(
                containerColor =
                Color.LightGray
            )
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation =
            3.dp
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    when (type) {
                        "large" -> {
                            120.dp
                        }

                        "fairly" -> {
                            115.dp
                        }

                        "medium" -> {
                            110.dp
                        }

                        else -> {
                            105.dp
                        }
                    }
                )
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceAround,
        ) {
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(
                            when (type) {
                                "large" -> {
                                    100.dp
                                }

                                "fairly" -> {
                                    95.dp
                                }

                                "medium" -> {
                                    90.dp
                                }

                                else -> {
                                    85.dp
                                }
                            }
                        )
                        .aspectRatio(1f), contentScale = ContentScale.Crop

                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(start = 10.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Column {
                    Text(
                        text = title, fontSize =
                        when (type) {
                            "large" -> {
                                20.sp
                            }

                            "fairly" -> {
                                18.sp
                            }

                            "medium" -> {
                                17.sp
                            }

                            else -> {
                                16.sp
                            }
                        }, fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = content, fontSize =
                        when (type) {
                            "large" -> {
                                19.sp
                            }

                            "fairly" -> {
                                17.sp
                            }

                            "medium" -> {
                                16.sp
                            }

                            else -> {
                                15.sp
                            }
                        }
                    )
                }
            }
            Icon(
                painter = painterResource(id = R.drawable.baseline_delete_24),
                contentDescription = "delete",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        deleteClick()
                    }
                    .align(Alignment.CenterVertically),
            )
        }


    }
}
