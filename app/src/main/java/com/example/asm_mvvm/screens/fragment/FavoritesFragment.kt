package com.example.asm_mvvm.screens.fragment

import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
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
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.asm_mvvm.R
import com.example.asm_mvvm.SharedPreferencesManager
import com.example.asm_mvvm.request.CartRequest
import com.example.asm_mvvm.screens.activity.DetailProductActivity
import com.example.asm_mvvm.screens.activity.LoginActivity
import com.example.asm_mvvm.ui.theme.AnimationLoading
import com.example.asm_mvvm.ui.theme.MyButton
import com.example.asm_mvvm.ui.theme.MyButton2
import com.example.asm_mvvm.ui.theme.MyToolbar
import com.example.asm_mvvm.viewmodels.CartViewModel
import com.example.asm_mvvm.viewmodels.FavoritesViewModel
import com.example.asm_mvvm.viewmodels.ProductViewModel
import com.example.asm_mvvm.viewmodels.TypeViewModel
import com.example.asm_mvvm.viewmodels.UserViewModel


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun FavoritesFragment() {
    val textState = remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize()) {
        MyToolbar(title = "Favorites", type = "favorites", "Search favorites", textState)
        ListFavorites(textState.value)
    }
}


@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun ListFavorites(dataSearch: String) {
    val context = LocalContext.current
    SharedPreferencesManager.init(context)
    val favoritesViewModel = FavoritesViewModel()
    val userViewModel = UserViewModel()

    val icon: Painter = painterResource(id = R.drawable.cancel)
    val icon2: Painter = painterResource(id = R.drawable.icbag)

    val favoriteState = favoritesViewModel.favorites.observeAsState(initial = emptyList())
    val favorites = favoriteState.value
    val account = userViewModel.getEmailFromSharedPreferences()

    if (dataSearch == "") {
        // goi phuong thuc call data binh thuong
        if (account != null) {
            favoritesViewModel.getFavoritesByAccount(account)
        }
    } else {
        // goi phuong thuc search
        if (account != null) {
            favoritesViewModel.searchFavorites(dataSearch, account)
        }
    }

    var showDialog by remember { mutableStateOf(false) }

    if (favorites.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val imageRequest = ImageRequest.Builder(context)
                .data(R.drawable.like)
                .decoderFactory(ImageDecoderDecoder.Factory())
                .build()
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = imageRequest,
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )
                Text(text = "Danh sách yêu thích trống", fontSize = 22.sp)
            }

        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxHeight()
        ) {
            items(favorites.size) { index ->
                if (showDialog) {
                    DialogAddToCartFv(
                        onDismissRequest = { showDialog = false },
                        productId = favorites[index].id,
                        productName = favorites[index].productName,
                        image = favorites[index].image,
                        price = favorites[index].price
                    )
                }

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
                                    model = favorites[index].image,
                                    contentDescription = null,
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier.fillMaxHeight()
                                )
                            }
                            //
                            Column(modifier = Modifier.padding(start = 15.dp)) {
                                Text(text = favorites[index].productName, fontSize = 20.sp)
                                Text(
                                    text = "$ " + favorites[index].price,
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
                                    // goi phuong thuc xoa favorites
                                    if (account != null) {
                                        favoritesViewModel.deleteFavorites(
                                            favorites[index].id,
                                            account,
                                            context
                                        )
                                    }
                                })
                            Spacer(modifier = Modifier.weight(1f))
                            Icon(
                                painter = icon2,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(30.dp)
                                    .clickable {
                                        showDialog = true
                                    }
                            )
                        }
                        //
                    }
                }

            }
        }
    }

}

@Composable
fun DialogAddToCartFv(
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
                .width(350.dp)
                .height(150.dp),
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