package com.example.asm_mvvm.screens.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.example.asm_mvvm.SharedPreferencesManager
import com.example.asm_mvvm.request.CartRequest
import com.example.asm_mvvm.request.FavoritesRequest
import com.example.asm_mvvm.ui.theme.MyButtonCustom1
import com.example.asm_mvvm.viewmodels.CartViewModel
import com.example.asm_mvvm.viewmodels.CommentViewModel
import com.example.asm_mvvm.viewmodels.FavoritesViewModel
import com.example.asm_mvvm.viewmodels.ProductViewModel
import com.example.asm_mvvm.viewmodels.UserViewModel

class DetailProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val idPro = intent.getStringExtra("ID_PRODUCT")
        val screen = intent.getStringExtra("SCREEN")
        setContent {
            SizeDetailScreen(idPro = idPro, screen = screen)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionImage(image1: String, image2: String, image3: String, screen: String, type: String) {
    val context = LocalContext.current
    var selected by remember {
        mutableStateOf("image1")
    }

    BackHandler {
        if (screen == "home") {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        } else {
            val intent = Intent(context, CartActivity::class.java)
            intent.putExtra("TYPE", "")
            context.startActivity(intent)
        }
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
                .padding(
                    top =
                    when (type) {
                        "large" -> {
                            100.dp
                        }

                        "fairly" -> {
                            70.dp
                        }

                        "medium" -> {
                            40.dp
                        }

                        else -> {
                            20.dp
                        }
                    }
                )
        ) {
            Card(
                modifier = Modifier
                    .size(50.dp),
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
                .padding(
                    top =
                    when (type) {
                        "large" -> {
                            200.dp
                        }

                        "fairly" -> {
                            170.dp
                        }

                        "medium" -> {
                            130.dp
                        }

                        else -> {
                            120.dp
                        }
                    }
                )
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

@SuppressLint("DefaultLocale")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionContent(
    name: String,
    price: Double,
    content: String,
    image: String,
    id: String,
    type: String
) {
    val icon1: Painter = painterResource(id = R.drawable.icontru)
    val icon2: Painter = painterResource(id = R.drawable.bookmark)

    val cartViewModel = CartViewModel()

    val cartState = cartViewModel.cart.observeAsState()
    val cart = cartState.value
    val context = LocalContext.current

    val favoritesViewModel = FavoritesViewModel()
    val favoriteState = favoritesViewModel.favorite.observeAsState()
    val favorites = favoriteState.value

    val userViewModel = UserViewModel()

    SharedPreferencesManager.init(context)
    val account = userViewModel.getEmailFromSharedPreferences() ?: ""

    cartViewModel.getCartByProductId(account, id)
    favoritesViewModel.getFavoritesByProductId(account, id)

    val commentViewModel = CommentViewModel()

    val isLoading by commentViewModel.isLoading.observeAsState(true)
    val isFailed by commentViewModel.isFailed.observeAsState(false)

    val commentState = commentViewModel.comments.observeAsState(initial = emptyList())
    val comment = commentState.value

    commentViewModel.getCommentByProductId(id)
    var rateAll = 0.0

    val rateSize = comment.size
    for (cmt in comment){
        rateAll += cmt.rate
    }

    var bienDem by rememberSaveable {
        mutableIntStateOf(1)
    }
    Column(modifier = Modifier.fillMaxHeight()) {
        Text(
            text = name, fontSize =
            when (type) {
                "large" -> {
                    28.sp
                }

                "fairly" -> {
                    26.sp
                }

                "medium" -> {
                    24.sp
                }

                else -> {
                    22.sp
                }
            }, fontFamily = FontFamily.Serif
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$ $price", fontSize =
                when (type) {
                    "large" -> {
                        30.sp
                    }

                    "fairly" -> {
                        28.sp
                    }

                    "medium" -> {
                        26.sp
                    }

                    else -> {
                        24.sp
                    }
                }, fontWeight = FontWeight.Bold
            )
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
                            .size(
                                when (type) {
                                    "large" -> {
                                        40.dp
                                    }

                                    "fairly" -> {
                                        38.dp
                                    }

                                    "medium" -> {
                                        35.dp
                                    }

                                    else -> {
                                        30.dp
                                    }
                                }
                            )
                            .padding(
                                when (type) {
                                    "large" -> {
                                        5.dp
                                    }

                                    "fairly" -> {
                                        4.dp
                                    }

                                    "medium" -> {
                                        3.dp
                                    }

                                    else -> {
                                        2.dp
                                    }
                                }
                            )
                    )
                }

                Text(
                    text = "" + bienDem,
                    fontSize =
                    when (type) {
                        "large" -> {
                            27.sp
                        }

                        "fairly" -> {
                            25.sp
                        }

                        "medium" -> {
                            23.sp
                        }

                        else -> {
                            21.sp
                        }
                    },
                    fontWeight = FontWeight(400),
                    modifier = Modifier
                        .padding(
                            start =
                            when (type) {
                                "large" -> {
                                    10.dp
                                }

                                "fairly" -> {
                                    8.dp
                                }

                                "medium" -> {
                                    6.dp
                                }

                                else -> {
                                    4.dp
                                }
                            }, end =
                            when (type) {
                                "large" -> {
                                    10.dp
                                }

                                "fairly" -> {
                                    8.dp
                                }

                                "medium" -> {
                                    6.dp
                                }

                                else -> {
                                    4.dp
                                }
                            }
                        )
                        .width(
                            when (type) {
                                "large" -> {
                                    40.dp
                                }

                                "fairly" -> {
                                    38.dp
                                }

                                "medium" -> {
                                    35.dp
                                }

                                else -> {
                                    30.dp
                                }
                            }
                        ),
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
                            .size(
                                when (type) {
                                    "large" -> {
                                        40.dp
                                    }

                                    "fairly" -> {
                                        38.dp
                                    }

                                    "medium" -> {
                                        35.dp
                                    }

                                    else -> {
                                        30.dp
                                    }
                                }
                            )
                            .padding(
                                when (type) {
                                    "large" -> {
                                        5.dp
                                    }

                                    "fairly" -> {
                                        4.dp
                                    }

                                    "medium" -> {
                                        3.dp
                                    }

                                    else -> {
                                        2.dp
                                    }
                                }
                            )
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
                val intent = Intent(context,RatingActivity::class.java)
                intent.putExtra("IDPRODETAIL",id)

                context.startActivity(intent)
            }, colors = CardDefaults.cardColors(containerColor = Color.White)) {
                if(isLoading){
                    Text(text = "Loading...")
                }else if(isFailed){
                    Text(text = "Error")
                }else{
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = "",
                            modifier = Modifier
                                .size(30.dp),
                            tint = Color(0xFFF57C00)
                        )
                        Text(
                            text = if(rateSize == 0){
                                "..."
                            }else{
                                String.format("%.1f", rateAll / rateSize)
                            },
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 5.dp)
                        )
                    }
                }

            }

            Text(
                text = "($rateSize reviews)",
                fontSize = 17.sp,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Column(modifier = Modifier.fillMaxHeight(0.45f)) {
                Text(
                    text = content,
                    fontSize =
                    when (type) {
                        "large" -> {
                            20.sp
                        }

                        "fairly" -> {
                            19.sp
                        }

                        "medium" -> {
                            18.sp
                        }

                        else -> {
                            17.sp
                        }
                    },
                    modifier = Modifier.verticalScroll(rememberScrollState())
                )
            }
            Row(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                if (favorites != null) {
                    Card(
                        shape = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp),
                        colors = CardDefaults.cardColors(
                            Color.DarkGray
                        ),
                        modifier = Modifier.size(
                            when (type) {
                                "large" -> {
                                    70.dp
                                }

                                "fairly" -> {
                                    65.dp
                                }

                                "medium" -> {
                                    60.dp
                                }

                                else -> {
                                    50.dp
                                }
                            }
                        ),
                        onClick = {
                            val idFv = favorites.id
                            favoritesViewModel.deleteFavorites(idFv, account, context)
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
                } else {
                    Card(
                        shape = RoundedCornerShape(topStart = 5.dp, bottomStart = 5.dp),
                        colors = CardDefaults.cardColors(
                            Color.LightGray
                        ),
                        modifier = Modifier
                            .size(
                                when (type) {
                                    "large" -> {
                                        70.dp
                                    }

                                    "fairly" -> {
                                        65.dp
                                    }

                                    "medium" -> {
                                        60.dp
                                    }

                                    else -> {
                                        50.dp
                                    }
                                }
                            ),
                        onClick = {
                            val fvBody = FavoritesRequest(
                                productId = id,
                                productName = name,
                                image = image,
                                price = price,
                                account = account
                            )
                            // phuong thuc add to favorites
                            favoritesViewModel.addProductToFavorites(
                                account,
                                fvBody,
                                "Thêm vào danh sách yêu thích thành công",
                                "Thêm vào danh sách thất bại",
                                context
                            )
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
                }

                MyButtonCustom1(title = "Add to cart", onClick = {
                    if (cart != null) {
                        val quantityBefore = cart.quantity
                        val quantitySuggest = 99 - quantityBefore
                        if (bienDem + quantityBefore < 100) {
                            cartViewModel.updateQuantityCart(
                                cart.id,
                                bienDem + quantityBefore,
                                account = account,
                                "Thêm vào giỏ hàng thành công",
                                "Thêm vào giỏ hàng thất bại",
                                context,
                                toastText = true
                            )
                        } else if (bienDem + quantityBefore >= 100) {
                            Toast.makeText(
                                context,
                                "Bạn chỉ có thể mua thêm $quantitySuggest sản phẩm này",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        val cartBody = CartRequest(
                            productId = id,
                            productName = name,
                            quantity = bienDem,
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
                }, mauChu = Color.White, mauNen = Color.Black, type = type)
            }
        }

    }
}

@Composable
fun DetailScreen(type: String, idPro: String?, screen: String?) {
    val productViewModel = ProductViewModel()

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (idPro != null) {
            val product by productViewModel.product.observeAsState()

            LaunchedEffect(idPro) {
                if (idPro != "") {
                    productViewModel.getProductById(idPro)
                }
            }
            product?.let {
                Column( // phan image
                    modifier = Modifier
                        .fillMaxHeight(0.55f)
                        .fillMaxWidth()
                ) {
                    if (screen != null) {
                        TransactionImage(
                            image1 = it.image1,
                            image2 = it.image2,
                            image3 = it.image3,
                            screen = screen,
                            type = type
                        )
                    }
                }

                Column( // phan content
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    TransactionContent(
                        name = it.productName,
                        price = it.price,
                        content = it.describe,
                        image = it.image1,
                        it.id,
                        type = type
                    )
                }
            } ?: run {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Loading...", fontSize = 22.sp)
                }
            }
        }
    }
}

@Composable
fun SizeDetailScreen(idPro: String?, screen: String?) {
    val context = LocalContext.current
    val displayMetrics: DisplayMetrics = context.resources.displayMetrics
    val screenHeightPx = displayMetrics.heightPixels
    val density = displayMetrics.density

    val screenHeightDp = screenHeightPx / density

    if (screenHeightDp > 890) {
        // large
        DetailScreen(type = "large", idPro, screen)
    } else if (screenHeightDp > 800) {
        // fairly
        DetailScreen(type = "fairly", idPro, screen)
    } else if (screenHeightDp > 714) {
        // medium
        DetailScreen(type = "medium", idPro, screen)
    } else {
        // smail
        DetailScreen(type = "smail", idPro, screen)
    }
}