package com.example.asm_mvvm.ui.theme.ComposeScreen

import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.asm_mvvm.R
import com.example.asm_mvvm.screens.activity.DetailProductActivity
import com.example.asm_mvvm.ui.theme.MyButton
import com.example.asm_mvvm.viewmodels.CartViewModel
import com.example.asm_mvvm.viewmodels.ProductViewModel
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun ListCart(cartViewModel: CartViewModel, productViewModel: ProductViewModel) {
    val cartState = cartViewModel.carts.observeAsState(initial = emptyList())
    val carts = cartState.value
    val context = LocalContext.current
    val icon: Painter = painterResource(id = R.drawable.cancel)
    val icon1: Painter = painterResource(id = R.drawable.icontru)
    var priceAll = 0.0

    val imageRequest = ImageRequest.Builder(context)
        .data(R.drawable.loading)
        .decoderFactory(ImageDecoderDecoder.Factory())
        .build()

    if (carts.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Hiện tại bạn chưa thêm sản phẩm nào")
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxHeight(0.72f)
        ) {
            items(carts.size) { index ->
                productViewModel.getProductById(carts[index].productId)
                val productsState = productViewModel.products.observeAsState(initial = emptyList())
                val products = productsState.value
                val priceProduct = products[index].price * carts[index].quantity
                priceAll += priceProduct

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp)
                        .padding(start = 15.dp, end = 15.dp, bottom = 20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor =
                        Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation =
                        3.dp
                    ),
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // image
                        Card(
                            modifier = Modifier
                                .weight(3f)
                                .aspectRatio(1f)
                                .padding(10.dp),
                            shape = RoundedCornerShape(15.dp),

                            ) {
                            AsyncImage(
                                model = products[index].image1,
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                        // content
                        Column(
                            modifier = Modifier
                                .weight(4f)
                                .padding(start = 10.dp),
                            verticalArrangement = Arrangement.SpaceBetween

                        ) {
                            Text(text = products[index].productName, fontSize = 20.sp)
                            Text(
                                text = "$ $priceProduct",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                            )
                            //
                            Row(
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Card(
                                    modifier = Modifier.size(35.dp),
                                    shape = RoundedCornerShape(10.dp),
                                    colors = CardDefaults.cardColors(Color.LightGray),
                                    onClick = {
                                        var newQuantity = carts[index].quantity
                                        newQuantity = newQuantity.plus(1)
                                        if (newQuantity < 100) {
                                            cartViewModel.updateQuantityCart(
                                                productId = products[index]._id,
                                                newQuantity,
                                                "",
                                                "",
                                                context,
                                                type = 0
                                            )
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Không thể mua 100 sản phẩm cùng loại 1 lúc",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                    }
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            Icons.Filled.Add,
                                            contentDescription = "Add icon",
                                            modifier = Modifier
                                                .size(30.dp)
                                        )
                                    }

                                }

                                Text(
                                    text = "" + carts[index].quantity,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight(400),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(start = 10.dp, end = 10.dp)
                                        .size(35.dp)
                                )

                                //
                                Card(
                                    modifier = Modifier.size(35.dp),
                                    shape = RoundedCornerShape(10.dp),
                                    colors = CardDefaults.cardColors(Color.LightGray),
                                    onClick = {
                                        var newQuantity2 = carts[index].quantity
                                        newQuantity2 = newQuantity2.minus(1)
                                        if (newQuantity2 > 0) {
                                            cartViewModel.updateQuantityCart(
                                                productId = products[index]._id,
                                                newQuantity2,
                                                "b",
                                                "",
                                                context,
                                                type = 0
                                            )
                                        } else {
                                            Toast.makeText(
                                                context,
                                                "Tối thiểu số lượng là 1",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                    }
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            painter = icon1,
                                            contentDescription = "",
                                            modifier = Modifier
                                                .size(30.dp)
                                        )
                                    }

                                }
                            }
                            //
                        }
                        //
                        Box(modifier = Modifier.weight(1f)) {
                            Icon(
                                painter = icon,
                                contentDescription = "",
                                modifier = Modifier
                                    .size(30.dp)
                                    .clickable {
                                        cartViewModel.deleteCart(carts[index].id, context)
                                    }
                            )
                        }

                    }
                }
                //
            }
        }

        InputCard(price = 10.0)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputCard(price:Double) {
    var inputcode by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(), contentAlignment = Alignment.Center) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    shape = RoundedCornerShape(10.dp), colors = CardDefaults.cardColors(
                        containerColor =
                        Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation =
                        3.dp
                    )
                ) {
                    BasicTextField(
                        value = inputcode,
                        onValueChange = { inputcode = it },
                        modifier = Modifier.fillMaxWidth(0.8f)
                            ,
                        decorationBox = { innerTextField ->
                            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                                value = inputcode,
                                visualTransformation = VisualTransformation.None,
                                innerTextField = innerTextField,
                                placeholder = { Text("Enter your promo code") },
                                singleLine = true,
                                enabled = true,
                                isError = false,
                                interactionSource = remember { MutableInteractionSource() },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    unfocusedBorderColor = Color.Transparent,
                                    focusedBorderColor = Color.Transparent
                                )
                            )
                        }
                    )
                }

                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(Color.LightGray),
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .align(Alignment.CenterEnd)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            Icons.Filled.KeyboardArrowRight,
                            contentDescription = "Check icon",
                            modifier = Modifier
                                .size(40.dp)
                        )
                    }

                }
            }
        }
        Total(price = price)
    }
}

@Composable
fun Total(price: Double) {
    val context = LocalContext.current
    Column {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),Arrangement.SpaceBetween) {
            Text(text = "Total:", fontSize = 25.sp, fontWeight = FontWeight.Bold, color = Color(
                0xFF408143
            ), modifier = Modifier.fillMaxWidth(0.5f))


            Text(text = "$ $price", fontSize = 25.sp, fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth(0.5f))
        }
        MyButton(title = "Check out", onClick = { /*TODO*/ }, mauChu = Color.White, mauNen = Color.DarkGray)
    }
}