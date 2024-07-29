package com.example.asm_mvvm.screens.fragment

import android.content.Intent
import android.os.Build
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.asm_mvvm.R
import com.example.asm_mvvm.SharedPreferencesManager
import com.example.asm_mvvm.request.CartRequest
import com.example.asm_mvvm.screens.activity.DetailProductActivity
import com.example.asm_mvvm.ui.theme.AnimationLoading
import com.example.asm_mvvm.ui.theme.MyButton2
import com.example.asm_mvvm.ui.theme.MyToolbar
import com.example.asm_mvvm.viewmodels.CartViewModel
import com.example.asm_mvvm.viewmodels.ProductViewModel
import com.example.asm_mvvm.viewmodels.TypeViewModel
import com.example.asm_mvvm.viewmodels.UserViewModel

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun HomeFragment() {
    val context = LocalContext.current
    val textState = remember { mutableStateOf("") }

    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    val screenHeightPx = displayMetrics.heightPixels
    val density = displayMetrics.density
    val screenHeightDp = screenHeightPx / density

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFEFD)),
    ) {
        if (screenHeightDp > 890) {
            // large
            MyToolbar(title = "Home", type = "home", "Search product", textState, sizeScreen = "large")
            ListType(textState.value, type = "large")
        } else if (screenHeightDp > 800) {
            // fairly
            MyToolbar(title = "Home", type = "home", "Search product", textState, sizeScreen = "fairly")
            ListType(textState.value, type = "fairly")
        } else if (screenHeightDp > 714) {
            // medium
            MyToolbar(title = "Home", type = "home", "Search product", textState, sizeScreen = "medium")
            ListType(textState.value, type = "medium")
        } else {
            // smail
            MyToolbar(title = "Home", type = "home", "Search product", textState, sizeScreen = "smail")
            ListType(textState.value, type = "smail")
        }

    }
}

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListType(textSearch: String, type: String) {
    val typeViewModel = TypeViewModel()

    val typesState = typeViewModel.types.observeAsState(initial = emptyList())
    val types = typesState.value

    var selected by remember {
        mutableStateOf("")
    }

    if (types.isEmpty()) {
        AnimationLoading()
    } else {
        LazyRow {
            items(types.size) { index ->
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(top = 5.dp, start = 10.dp, end = 10.dp, bottom = 15.dp),
                    Arrangement.Center, Alignment.CenterHorizontally,
                ) {
                    Card(
                        modifier = Modifier
                            .size(
                                when (type) {
                                    "large" -> {
                                        75.dp
                                    }

                                    "fairly" -> {
                                        70.dp
                                    }

                                    "medium" -> {
                                        65.dp
                                    }

                                    else -> {
                                        60.dp
                                    }
                                }
                            )
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

        when (selected) {
            "" -> {
                ListProduct(type = "", dataSearch = textSearch, sizeScreen = type)
            }

            "Popular" -> {
                ListProduct(type = "Popular", dataSearch = textSearch, sizeScreen = type)
            }

            else -> {
                ListProduct(type = selected, dataSearch = textSearch, sizeScreen = type)
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListProduct(type: String, dataSearch: String, sizeScreen: String) {

    val productViewModel = ProductViewModel()
    val context = LocalContext.current

    val productsState = productViewModel.products.observeAsState(initial = emptyList())
    val products = productsState.value

    if (dataSearch == "") {
        when (type) {
            "" -> {
                productViewModel.getProduct()
            }

            "Popular" -> {
                productViewModel.getProductsByType(1)
            }

            else -> {
                productViewModel.getProductsByTypeProduct(type)
            }
        }
    } else {
        productViewModel.searchProduct(dataSearch)
    }


    val icon: Painter = painterResource(id = R.drawable.icbag)

    if (products.isEmpty()) {
        AnimationLoading()
    } else {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2), // chia theo số cột
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier
                .padding(top = 5.dp)
        ) {
            items(products.size) { index ->
                var showDialog by remember { mutableStateOf(false) }
                if (showDialog) {
                    DialogAddToCart(
                        onDismissRequest = { showDialog = false },
                        productId = products[index].id,
                        productName = products[index].productName,
                        image = products[index].image1,
                        price = products[index].price
                    )
                }

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
                            .height(
                                when (sizeScreen) {
                                    "large" -> {
                                        250.dp
                                    }

                                    "fairly" -> {
                                        240.dp
                                    }

                                    "medium" -> {
                                        230.dp
                                    }

                                    else -> {
                                        200.dp
                                    }
                                }
                            ),
                        shape = RoundedCornerShape(15.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        onClick = {
                            val intent = Intent(context, DetailProductActivity::class.java)
                            intent.putExtra("ID_PRODUCT", products[index].id)
                            intent.putExtra("SCREEN", "home")
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
                                        modifier = Modifier
                                            .size(26.dp)
                                            .clickable {
                                                showDialog = true
                                            },
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

@Composable
fun DialogAddToCart(
    onDismissRequest: () -> Unit,
    productId: String,
    productName: String,
    image: String,
    price: Double,
) {
    val cartViewModel = CartViewModel()
    val userViewModel = UserViewModel()


    val cartState = cartViewModel.carts2.observeAsState()
    val cart = cartState.value
    val context = LocalContext.current
    SharedPreferencesManager.init(context)

    val account = userViewModel.getEmailFromSharedPreferences() ?: ""
    cartViewModel.getCartsByProductId(account, productId)

    var quantityInput by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .width(400.dp)
                .height(300.dp),
            shape = RoundedCornerShape(16.dp),
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
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Card(border = BorderStroke(1.dp, Color.Black)) {
                    AsyncImage(
                        model = image,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .size(100.dp)
                    )
                }

                Text(text = productName, fontSize = 20.sp)
                OutlinedTextField(
                    value = quantityInput,
                    onValueChange = { quantityInput = it },
                    label = { Text("Số lượng") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    shape = RoundedCornerShape(10.dp),
                )
                MyButton2(title = "Add to cart", onClick = {
                    if (quantityInput == "") {
                        Toast.makeText(context, "Bạn chưa nhập số lượng", Toast.LENGTH_SHORT).show()
                    } else {
                        try {
                            val quantity = quantityInput.toInt()
                            if (quantity in 1..99) {
                                if (cart != null) {
                                    val quantityBefore = cart.quantity
                                    val quantitySuggest = 99 - quantityBefore

                                    if (quantity <= quantitySuggest) {
                                        cartViewModel.updateQuantityCart(
                                            id = cart.id,
                                            quantity + quantityBefore,
                                            account = account,
                                            "Thêm vào giỏ hàng thành công",
                                            "Thêm vào giỏ hàng thất bại",
                                            context,
                                            type = 1
                                        )
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Bạn chỉ có thể mua thêm $quantitySuggest sản phẩm này",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                } else {
                                    val cartBody = CartRequest(
                                        productId = productId,
                                        productName = productName,
                                        quantity = quantity,
                                        image = image,
                                        price = price,
                                        account = account
                                    )
                                    cartViewModel.addProductToCart(
                                        account,
                                        cartBody,
                                        "Thêm vào giỏ hàng thành công",
                                        "Thêm vào giỏ hàng thất bại",
                                        context
                                    )
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Bạn không thể mua 100 sản phẩm cùng loại",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Bạn phải nhập số", Toast.LENGTH_SHORT).show()
                        }
                    }
                }, mauChu = Color.Black, mauNen = Color.LightGray)
            }
        }
    }
}
