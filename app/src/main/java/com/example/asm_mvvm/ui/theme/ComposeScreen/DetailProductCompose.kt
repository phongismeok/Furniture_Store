package com.example.asm_mvvm.ui.theme.ComposeScreen

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.asm_mvvm.MainActivity
import com.example.asm_mvvm.R
import com.example.asm_mvvm.models.Cart
import com.example.asm_mvvm.request.CartRequest
import com.example.asm_mvvm.viewmodels.CartViewModel
import com.example.asm_mvvm.viewmodels.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionImage(image1: String, image2: String, image3: String) {
    val context = LocalContext.current
    var selected by remember {
        mutableStateOf("image1")
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        // image
        Card(
            shape = RoundedCornerShape(bottomStart = 50.dp),
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.8f)
                .align(Alignment.TopEnd)

        ) {
            AsyncImage(
                model = when (selected) {
                    "image1" -> {
                        image1
                    }

                    "image2" -> {
                        image2
                    }

                    else -> {
                        image3
                    }
                },
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .align(Alignment.End)
                    .fillMaxSize()
            )
        }
        //icon exit
        Box(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .align(Alignment.TopEnd)
                .padding(top = 100.dp)
        ) {
            Card(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp),
                colors = CardDefaults.cardColors(
                    containerColor =
                    Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation =
                    3.dp
                ),
                onClick = {
                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.KeyboardArrowLeft,
                        contentDescription = "",
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }

        //icon options
        Box(
            modifier = Modifier
                .fillMaxWidth(0.87f)
                .align(Alignment.TopEnd)
                .padding(top = 200.dp)
        ) {
            Card(
                modifier = Modifier
                    .width(70.dp)
                    .height(200.dp),
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(
                    containerColor =
                    Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation =
                    3.dp
                ),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    //
                    Card(
                        shape = CircleShape,
                        modifier = Modifier.size(40.dp),
                        colors = CardDefaults.cardColors(
                            if (selected == "image1") {
                                Color.Black
                            } else {
                                Color.LightGray
                            }
                        )
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Card(
                                shape = CircleShape,
                                modifier = Modifier.size(30.dp),
                                colors = CardDefaults.cardColors(
                                    Color.White
                                ),
                                onClick = {
                                    selected = "image1"
                                }
                            ) {

                            }
                        }

                    }
                    //
                    Card(
                        shape = CircleShape,
                        modifier = Modifier.size(40.dp),
                        colors = CardDefaults.cardColors(
                            if (selected == "image2") {
                                Color.Black
                            } else {
                                Color.LightGray
                            }
                        )
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Card(
                                shape = CircleShape,
                                modifier = Modifier.size(30.dp),
                                colors = CardDefaults.cardColors(
                                    Color.Green
                                ),
                                onClick = {
                                    selected = "image2"
                                }
                            ) {

                            }
                        }

                    }
                    //
                    Card(
                        shape = CircleShape,
                        modifier = Modifier.size(40.dp),
                        colors = CardDefaults.cardColors(
                            if (selected == "image3") {
                                Color.Black
                            } else {
                                Color.LightGray
                            }
                        )
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Card(
                                shape = CircleShape,
                                modifier = Modifier.size(30.dp),
                                colors = CardDefaults.cardColors(
                                    Color.Gray
                                ),
                                onClick = {
                                    selected = "image3"
                                }
                            ) {

                            }
                        }

                    }
                    //
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionContent(
    name: String,
    price: String,
    content: String,
    productViewModel: ProductViewModel,
    id: String,
    state: Int,
    cartViewModel: CartViewModel,
) {
    val icon1: Painter = painterResource(id = R.drawable.icontru)
    val icon2: Painter = painterResource(id = R.drawable.bookmark)
    cartViewModel.getCartsByProductId(id)
    val cartState = cartViewModel.carts.observeAsState(initial = emptyList())
    val cart = cartState.value
    val context = LocalContext.current

    var bienDem by rememberSaveable {
        mutableIntStateOf(1)
    }
    Column(modifier = Modifier.fillMaxHeight()) {
        Text(text = name, fontSize = 28.sp, fontFamily = FontFamily.Serif)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = price, fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(Color.LightGray),
                    onClick = {
                        if (bienDem < 100) {
                            bienDem++
                        } else {
                            Toast.makeText(
                                context,
                                "Tối đa 99 sản phẩm 1 lần mua",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    },
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Check icon",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(5.dp)
                    )
                }

                Text(
                    text = "" + bienDem,
                    fontSize = 27.sp,
                    fontWeight = FontWeight(400),
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .width(40.dp),
                    textAlign = TextAlign.Center
                )

                //
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(Color.LightGray),
                    onClick = {
                        if (bienDem > 1) {
                            bienDem--
                        } else {
                            Toast.makeText(context, "Tối thiểu số lượng là 1", Toast.LENGTH_SHORT)
                                .show()
                        }

                    },
                ) {
                    Icon(
                        painter = icon1,
                        contentDescription = "",
                        modifier = Modifier
                            .size(40.dp)
                            .padding(5.dp)
                    )
                }
            }
            //
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(onClick = {

            }, colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "",
                        modifier = Modifier
                            .size(30.dp),
                        tint = Color(0xFFF57C00)
                    )
                    Text(
                        text = "4.5",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 5.dp)
                    )
                }
            }

            Text(
                text = "(50 reviews)",
                fontSize = 17.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Column(modifier = Modifier.fillMaxHeight(0.7f)) {
                Text(
                    text = content,
                    fontSize = 20.sp,
                    modifier = Modifier.verticalScroll(rememberScrollState())
                )
            }
            Row(
                modifier = Modifier
                    .height(80.dp)
                    .padding(top = 10.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(
                        if (state == 0) {
                            Color.LightGray
                        } else {
                            Color.DarkGray
                        }
                    ),
                    modifier = Modifier
                        .width(70.dp)
                        .height(70.dp),
                    onClick = {
                        if (state == 0) {
                            productViewModel.updateStateFavorites(
                                id,
                                1,
                                "yêu thích thành công",
                                "yêu thích thất bại",
                                context = context
                            )
                        } else {
                            productViewModel.updateStateFavorites(
                                id,
                                0,
                                "Hủy yêu thích thành công",
                                "Hủy yêu thích thất bại",
                                context = context
                            )
                        }

                    }
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            painter = icon2,
                            contentDescription = "",
                            modifier = Modifier
                                .size(45.dp)
                                .padding(5.dp)
                        )
                    }
                }

                Button(
                    onClick = {
                        if (cart.isEmpty()) {
                            val cartBody = CartRequest(
                                productId = id,
                                quantity = bienDem
                            )

                            cartViewModel.addProductToCart(
                                id = id,
                                cartBody,
                                "Thêm vào giỏ hàng thành công",
                                "Thêm vào giỏ hàng thất bại",
                                context
                            )
                        } else {
                            val quantityBefore = cart[0].quantity
                            val quantitySuggest = 99 - quantityBefore
                            if (bienDem + quantityBefore < 100) {
                                cartViewModel.updateQuantityCart(
                                    productId = id,
                                    bienDem + quantityBefore,
                                    "Thêm vào giỏ hàng thành công",
                                    "Thêm vào giỏ hàng thất bại",
                                    context,
                                    type = 1
                                )
                            } else if (bienDem + quantityBefore >= 100) {
                                Toast.makeText(
                                    context,
                                    "Bạn chỉ có thể mua thêm $quantitySuggest sản phẩm này",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(7f)
                        .height(70.dp)
                        .padding(start = 10.dp),
                    shape = RoundedCornerShape(10)
                ) {
                    Text(
                        text = "Add to cart",
                        fontSize = 25.sp,
                        fontWeight = FontWeight(500)
                    )
                }
            }
        }

    }
}



