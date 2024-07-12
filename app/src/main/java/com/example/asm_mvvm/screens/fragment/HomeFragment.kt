package com.example.asm_mvvm.screens.fragment

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.painter.Painter
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
import com.example.asm_mvvm.screens.activity.DetailProductActivity
import com.example.asm_mvvm.ui.theme.MyToolbar
import com.example.asm_mvvm.viewmodels.ProductViewModel
import com.example.asm_mvvm.viewmodels.TypeViewModel


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun HomeFragment() {
    val typeViewModel = TypeViewModel()
    val productViewModel = ProductViewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFEFD))
    ) {
        MyToolbar(title = "Home")
        ListType(typeViewModel = typeViewModel, productViewModel = productViewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListType(typeViewModel: TypeViewModel, productViewModel: ProductViewModel) {
    val context = LocalContext.current
    val typesState = typeViewModel.types.observeAsState(initial = emptyList())
    val types = typesState.value
    var selected by remember {
        mutableStateOf("")
    }
    var stateHot by remember {
        mutableStateOf("")
    }

    val imageRequest = ImageRequest.Builder(context)
        .data(R.drawable.loading)
        .decoderFactory(ImageDecoderDecoder.Factory())
        .build()

    if (types.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = imageRequest,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
    }else {
        LazyRow {
            items(types.size) { index ->
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 15.dp),
                    Arrangement.Center, Alignment.CenterHorizontally,
                ) {
                    Card(
                        modifier = Modifier
                            .width(70.dp)
                            .height(70.dp)
                            .padding(2.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selected == types[index].name)
                                Color(0xFF684646)
                            else
                                Color(0xFFF0E9E9)
                        ),
                        onClick = {
                            selected = types[index].name
                            stateHot = if (selected == "Popular") {
                                "Hot"
                            } else {
                                selected
                            }
                        }

                    ) {
                        AsyncImage(
                            model = types[index].image,
                            contentDescription = null,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                    Text(
                        text = types[index].name,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 10.dp),
                        color = if (selected == types[index].name)
                            Color.Black
                        else
                            Color.Gray
                    )
                }
            }
        }

        if (selected == "") {
            ListProduct(productViewModel = productViewModel)
        } else {
            if (stateHot == "Hot") {
                ListProductHot(productViewModel = productViewModel, type = 1)
            } else {
                ListProductType(productViewModel = productViewModel, type = stateHot)
            }
        }
    }


}

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListProductHot(productViewModel: ProductViewModel, type: Int) {
    productViewModel.getProductsByType(type)
    val productsState = productViewModel.products.observeAsState(initial = emptyList())
    val products = productsState.value
    val context = LocalContext.current
    val icon: Painter = painterResource(id = R.drawable.icbag)

    val imageRequest = ImageRequest.Builder(context)
        .data(R.drawable.loading)
        .decoderFactory(ImageDecoderDecoder.Factory())
        .build()

    if (products.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = imageRequest,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
    }else {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2), // chia theo số cột
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier
                .padding(top = 20.dp)
        ) {
            items(products.size) { index ->
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(top = 20.dp, start = 10.dp, end = 10.dp),
                    Arrangement.Center, Alignment.CenterHorizontally,
                ) {
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .width(200.dp)
                            .height(250.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        onClick = {
                            val intent = Intent(context, DetailProductActivity::class.java)
                            intent.putExtra("ID_PRODUCT", products[index]._id)
                            context.startActivity(intent)
                        }
                    ) {
                        Box(
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            AsyncImage(
                                model = products[index].image1,
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.fillMaxHeight()
                            )
                            Card(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .width(40.dp)
                                    .height(40.dp),
                                shape = RoundedCornerShape(5.dp),
                                onClick = {

                                }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(5.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = icon,
                                        contentDescription = null,
                                        modifier = Modifier.size(26.dp),
                                    )
                                }
                            }
                        }
                    }
                    Text(
                        text = products[index].productName,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 10.dp),
                        fontSize = 20.sp
                    )
                    Text(
                        text = "$ " + products[index].price,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 10.dp),
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }


}

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListProductType(productViewModel: ProductViewModel, type: String) {
    productViewModel.getProductsByTypeProduct(type)
    val productsState = productViewModel.products.observeAsState(initial = emptyList())
    val products = productsState.value
    val context = LocalContext.current
    val icon: Painter = painterResource(id = R.drawable.icbag)

    val imageRequest = ImageRequest.Builder(context)
        .data(R.drawable.loading)
        .decoderFactory(ImageDecoderDecoder.Factory())
        .build()

    if (products.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = imageRequest,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
    }else {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2), // chia theo số cột
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier
                .padding(top = 20.dp)
        ) {
            items(products.size) { index ->
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(top = 20.dp, start = 10.dp, end = 10.dp),
                    Arrangement.Center, Alignment.CenterHorizontally,
                ) {
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .width(200.dp)
                            .height(250.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        onClick = {
                            val intent = Intent(context, DetailProductActivity::class.java)
                            intent.putExtra("ID_PRODUCT", products[index]._id)
                            context.startActivity(intent)
                        }
                    ) {
                        Box(
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            AsyncImage(
                                model = products[index].image1,
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.fillMaxHeight()
                            )
                            Card(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .width(40.dp)
                                    .height(40.dp),
                                shape = RoundedCornerShape(5.dp),
                                onClick = {

                                }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(5.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = icon,
                                        contentDescription = null,
                                        modifier = Modifier.size(26.dp),
                                    )
                                }
                            }
                        }
                    }
                    Text(
                        text = products[index].productName,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 10.dp),
                        fontSize = 20.sp
                    )
                    Text(
                        text = "$ " + products[index].price,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 10.dp),
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }


}

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListProduct(productViewModel: ProductViewModel) {

    val productsState = productViewModel.products.observeAsState(initial = emptyList())
    val products = productsState.value
    val context = LocalContext.current
    val icon: Painter = painterResource(id = R.drawable.icbag)
    val imageRequest = ImageRequest.Builder(context)
        .data(R.drawable.loading)
        .decoderFactory(ImageDecoderDecoder.Factory())
        .build()

    if (products.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = imageRequest,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
    }else{
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2), // chia theo số cột
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier
                .padding(top = 20.dp)
        ) {
            items(products.size) { index ->
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(top = 20.dp, start = 10.dp, end = 10.dp),
                    Arrangement.Center, Alignment.CenterHorizontally,
                ) {
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .width(200.dp)
                            .height(250.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        onClick = {
                            val intent = Intent(context, DetailProductActivity::class.java)
                            intent.putExtra("ID_PRODUCT", products[index]._id)
                            context.startActivity(intent)
                        }
                    ) {
                        Box(
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            AsyncImage(
                                model = products[index].image1,
                                contentDescription = null,
                                contentScale = ContentScale.FillBounds,
                                modifier = Modifier.fillMaxHeight()
                            )
                            Card(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .width(40.dp)
                                    .height(40.dp),
                                shape = RoundedCornerShape(5.dp),
                                onClick = {

                                }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(5.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        painter = icon,
                                        contentDescription = null,
                                        modifier = Modifier.size(26.dp),
                                    )
                                }
                            }
                        }
                    }
                    Text(
                        text = products[index].productName,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 10.dp),
                        fontSize = 20.sp
                    )
                    Text(
                        text = "$ " + products[index].price,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 10.dp),
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}