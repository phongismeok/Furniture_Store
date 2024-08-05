package com.example.asm_mvvm.screens.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.asm_mvvm.R
import com.example.asm_mvvm.SharedPreferencesManager
import com.example.asm_mvvm.request.CommentRequest
import com.example.asm_mvvm.viewmodels.CommentViewModel
import com.example.asm_mvvm.viewmodels.ProductViewModel
import com.example.asm_mvvm.viewmodels.UserViewModel

class RatingActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val idPro = intent.getStringExtra("IDPRODETAIL") ?: ""
        val screen = intent.getStringExtra("SCREEN") ?: ""
        val commentViewModel = CommentViewModel()
        setContent {
            Column(modifier = Modifier.fillMaxSize()) {
                ScreenRating(idPro = idPro, commentViewModel = commentViewModel, screen = screen)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun ScreenRating(idPro: String, commentViewModel: CommentViewModel,screen: String) {
    val context = LocalContext.current
    val isLoading by commentViewModel.isLoading.observeAsState(true)
    val isFailed by commentViewModel.isFailed.observeAsState(false)

    val commentState = commentViewModel.comments.observeAsState(initial = emptyList())
    val comment = commentState.value

    commentViewModel.getCommentByProductId(idPro)

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
        ) {
            ContentProduct(idPro = idPro, screen = screen)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
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
                                    .fillMaxHeight(0.8f)
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

                            InputComment(idPro = idPro, commentViewModel = commentViewModel)
                        }

                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(top = 5.dp),
                        modifier = Modifier
                            .fillMaxHeight(0.8f)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        items(comment.size) { index ->
                            CustomComment(
                                name = comment[index].account,
                                content = comment[index].content,
                                avatar = comment[index].avatar,
                                rate = comment[index].rate.toString()
                            )
                        }

                    }
                    InputComment(idPro = idPro, commentViewModel = commentViewModel)
                }
            }
        }

    }
}

@SuppressLint("DefaultLocale")
@Composable
fun ContentProduct(idPro: String,screen:String) {
    val context = LocalContext.current
    val productViewModel = ProductViewModel()
    val product by productViewModel.product.observeAsState()
    productViewModel.getProductById(idPro)

    val commentViewModel = CommentViewModel()

    val isLoading by commentViewModel.isLoading.observeAsState(true)
    val isFailed by commentViewModel.isFailed.observeAsState(false)

    val commentState = commentViewModel.comments.observeAsState(initial = emptyList())
    val comment = commentState.value

    commentViewModel.getCommentByProductId(idPro)

    var rateAll = 0.0

    val rateSize = comment.size
    for (cmt in comment) {
        rateAll += cmt.rate
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_keyboard_arrow_left_24),
            contentDescription = "",
            modifier = Modifier.size(40.dp).clickable {
                val intent = Intent(context,DetailProductActivity::class.java)
                intent.putExtra("ID_PRODUCT", idPro)
                intent.putExtra("SCREEN", screen)
                context.startActivity(intent)
            }
        )

        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(start = 20.dp)
        ) {
            AsyncImage(
                model = product?.image1,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .aspectRatio(1f), contentScale = ContentScale.Crop

            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 15.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Column {
                Text(
                    text = product?.productName ?: "",
                    fontSize = 22.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${product?.price} $",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Row {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "",
                        modifier = Modifier
                            .size(30.dp),
                        tint = Color(0xFFFFEB3B)
                    )
                    if (isLoading) {
                        Text(text = "Loading...")
                    } else if (isFailed) {
                        Text(text = "Error")
                    } else {
                        Text(
                            text = if (rateSize == 0) {
                                "..."
                            } else {
                                String.format("%.1f", rateAll / rateSize)
                            },
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 5.dp, top = 2.dp)
                        )
                    }
                }
            }
        }


    }
}

@Composable
fun CustomComment(name: String, content: String, avatar: String, rate: String) {
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

@Composable
fun InputComment(idPro: String, commentViewModel: CommentViewModel) {
    val context = LocalContext.current
    SharedPreferencesManager.init(context)
    val userViewModel = UserViewModel()

    val userState = userViewModel.user.observeAsState()
    val user = userState.value

    val account = userViewModel.getEmailFromSharedPreferences() ?: ""
    userViewModel.getUserByUsername(account)
    var content by rememberSaveable { mutableStateOf("") }
    var rate by rememberSaveable { mutableStateOf("") }

    var rate1star by rememberSaveable { mutableIntStateOf(0) }
    var rate2star by rememberSaveable { mutableIntStateOf(0) }
    var rate3star by rememberSaveable { mutableIntStateOf(0) }
    var rate4star by rememberSaveable { mutableIntStateOf(0) }
    var rate5star by rememberSaveable { mutableIntStateOf(0) }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 10.dp, top = 5.dp)
        ) {
            Text(text = "Rate: ", fontSize = 22.sp)
            Icon(
                Icons.Default.Star,
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        rate = "1"
                        rate1star = 1
                        rate2star = 0
                        rate3star = 0
                        rate4star = 0
                        rate5star = 0
                    },
                tint =
                if (rate1star == 1) {
                    Color(0xFFF57C00)
                } else {
                    Color.LightGray
                }
            )
            Icon(
                Icons.Default.Star,
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        rate = "2"
                        rate1star = 1
                        rate2star = 1
                        rate3star = 0
                        rate4star = 0
                        rate5star = 0
                    },
                tint =
                if (rate2star == 1) {
                    Color(0xFFF57C00)
                } else {
                    Color.LightGray
                }
            )
            Icon(
                Icons.Default.Star,
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        rate = "3"
                        rate1star = 1
                        rate2star = 1
                        rate3star = 1
                        rate4star = 0
                        rate5star = 0
                    },
                tint =
                if (rate3star == 1) {
                    Color(0xFFF57C00)
                } else {
                    Color.LightGray
                }
            )
            Icon(
                Icons.Default.Star,
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        rate = "4"
                        rate1star = 1
                        rate2star = 1
                        rate3star = 1
                        rate4star = 1
                        rate5star = 0
                    },
                tint =
                if (rate4star == 1) {
                    Color(0xFFF57C00)
                } else {
                    Color.LightGray
                }
            )
            Icon(
                Icons.Default.Star,
                contentDescription = "",
                modifier = Modifier
                    .size(25.dp)
                    .clickable {
                        rate = "5"
                        rate1star = 1
                        rate2star = 1
                        rate3star = 1
                        rate4star = 1
                        rate5star = 1
                    },
                tint =
                if (rate5star == 1) {
                    Color(0xFFF57C00)
                } else {
                    Color.LightGray
                }
            )
        }
        OutlinedTextField(
            value = content,
            onValueChange = {
                content = it
            },
            label = {
                Text("Comment")
            },
            trailingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.baseline_send_24),
                    contentDescription = "Send",
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            val commentBody = CommentRequest(
                                productId = idPro,
                                content = content,
                                rate = rate.toDouble(),
                                account = account,
                                avatar = user!!.avatar,
                            )
                            commentViewModel.addCommentToProduct(
                                idPro,
                                commentBody,
                                "Đánh giá thành công",
                                "Đánh giá thất bại",
                                context
                            )
                            content = ""
                        }
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp, top = 5.dp),
            shape = RoundedCornerShape(10.dp)
        )
    }

}