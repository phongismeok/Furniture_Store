package com.example.asm_mvvm.screens.fragment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.asm_mvvm.R
import com.example.asm_mvvm.ui.theme.MyButton
import com.example.asm_mvvm.ui.theme.MyToolbar
import com.example.asm_mvvm.viewmodels.ProductViewModel
import com.example.asm_mvvm.viewmodels.TypeViewModel


@Composable
fun FavoritesFragment() {
    val productViewModel = ProductViewModel()
    Column(modifier = Modifier.fillMaxSize()) {
        MyToolbar(title = "Favorites")
        Column(modifier = Modifier.fillMaxHeight(0.85f)) {
            ListFavorites(productViewModel = productViewModel)
        }
        MyButton(
            title = "Add all to my cart",
            onClick = { /*TODO*/ },
            mauChu = Color.White,
            mauNen = Color.Gray
        )
    }

}


@Composable
fun ListFavorites(productViewModel: ProductViewModel) {
    val icon: Painter = painterResource(id = R.drawable.cancel)
    val icon2: Painter = painterResource(id = R.drawable.icbag)
    productViewModel.getProductsByStateFavorites(1)
    val productsState = productViewModel.products.observeAsState(initial = emptyList())
    val products = productsState.value
    val context = LocalContext.current
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier
            .padding(top = 10.dp)
    ) {
        items(products.size) { index ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .padding(10.dp),
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
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    //
                    Row {
                        Card(
                            modifier = Modifier
                                .width(140.dp)
                                .height(140.dp),
                            shape = RoundedCornerShape(15.dp),
                        ) {
                            AsyncImage(
                                model = products[index].image1,
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.fillMaxHeight()
                            )
                        }
                        //
                        Column(modifier = Modifier.padding(start = 15.dp)) {
                            Text(text = products[index].productName, fontSize = 20.sp)
                            Text(
                                text = "$ " + products[index].price,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                        //
                    }

                    Column {
                        Icon(painter = icon, contentDescription = "", modifier = Modifier
                            .size(30.dp)
                            .clickable {
                                productViewModel.updateStateFavorites(
                                    products[index]._id,
                                    0,
                                    "Hủy yêu thích thành công",
                                    "Hủy yêu thích thất bại",
                                    context = context
                                )
                            })
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            painter = icon2,
                            contentDescription = null,
                            modifier = Modifier
                                .size(30.dp)
                                .clickable {

                                }
                        )
                    }
                    //
                }
            }

        }
    }
}