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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.asm_mvvm.R
import com.example.asm_mvvm.SharedPreferencesManager
import com.example.asm_mvvm.ui.theme.MyToolbar3
import com.example.asm_mvvm.viewmodels.CommentViewModel
import com.example.asm_mvvm.viewmodels.UserViewModel


class MyReviewActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(modifier = Modifier.fillMaxSize()) {
                SizeMyReviewScreen()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun ListReview(sizeScreen: String) {
    val context = LocalContext.current
    SharedPreferencesManager.init(context)
    val commentViewModel = CommentViewModel()
    val userViewModel = UserViewModel()

    val isLoading by commentViewModel.isLoading.observeAsState(true)
    val isFailed by commentViewModel.isFailed.observeAsState(false)

    val account = userViewModel.getEmailFromSharedPreferences() ?: ""

    val commentState = commentViewModel.comments.observeAsState(initial = emptyList())
    val comment = commentState.value

    commentViewModel.getCommentByAccount(account)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
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
            if (comment.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val imageRequest = ImageRequest.Builder(context)
                        .data(R.drawable.comment)
                        .decoderFactory(ImageDecoderDecoder.Factory())
                        .build()
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            AsyncImage(
                                model = imageRequest,
                                contentDescription = null,
                                modifier = Modifier.size(150.dp)
                            )
                            Text(text = "Chưa có đánh giá nào", fontSize = 22.sp)
                        }

                    }

                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(top = 5.dp),
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    items(comment.size) { index ->
                        CustomCommentMyReview(
                            name = comment[index].account,
                            content = comment[index].content,
                            avatar = comment[index].avatar,
                            rate = comment[index].rate.toString(),
                            onDelete = {
                                commentViewModel.deleteComment(comment[index].id,context)
                                commentViewModel.getCommentByAccount(account)
                            }
                        )
                    }

                }
            }
        }
    }

}

@Composable
fun CustomCommentMyReview(name: String, content: String, avatar: String, rate: String,onDelete: () -> Unit) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp),
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
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 20.dp,
                            bottom = 10.dp
                        )
                    ) {
                        Text(text = name, fontSize = 22.sp)
                        Text(text = content, fontSize = 16.sp)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(text = "Rate: ", fontSize = 16.sp)
                            Text(text = rate, fontSize = 16.sp)
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(25.dp)
                                    .padding(start = 5.dp),
                                tint = Color(0xFFF57C00)
                            )
                        }
                    }
                    Image(
                        painter = painterResource(id = R.drawable.baseline_delete_24),
                        contentDescription = "",
                        modifier = Modifier.clickable {
                            onDelete()
                        }
                    )
                }
            }
            Card(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .clip(CircleShape)
                    .size(50.dp),
                colors = CardDefaults.cardColors(
                    containerColor =
                    Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation =
                    3.dp
                ),
                shape = RoundedCornerShape(10.dp)
            ) {
                AsyncImage(
                    model = avatar,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .clip(
                            CircleShape
                        )
                        .fillMaxSize()
                )
            }

        }

    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun SizeMyReviewScreen() {
    val context = LocalContext.current
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    val screenHeightPx = displayMetrics.heightPixels
    val density = displayMetrics.density

    val screenHeightDp = screenHeightPx / density

    if (screenHeightDp > 890) {
        // large
        MyToolbar3(title = "My Review", sizeScreen = "large")
        ListReview(sizeScreen = "large")
    } else if (screenHeightDp > 800) {
        // fairly
        MyToolbar3(title = "My Review", sizeScreen = "fairly")
        ListReview(sizeScreen = "fairly")
    } else if (screenHeightDp > 714) {
        // medium
        MyToolbar3(title = "My Review", sizeScreen = "medium")
        ListReview(sizeScreen = "medium")
    } else {
        // smail
        MyToolbar3(title = "My Review", sizeScreen = "smail")
        ListReview(sizeScreen = "smail")
    }
}