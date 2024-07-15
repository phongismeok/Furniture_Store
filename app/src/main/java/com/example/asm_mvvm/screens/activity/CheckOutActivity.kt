package com.example.asm_mvvm.screens.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.asm_mvvm.R
import com.example.asm_mvvm.SharedPreferencesManager
import com.example.asm_mvvm.ui.theme.MyButton
import com.example.asm_mvvm.ui.theme.MyToolbar3
import com.example.asm_mvvm.viewmodels.ShippingViewModel
import com.example.asm_mvvm.viewmodels.UserViewModel

class CheckOutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        SharedPreferencesManager.init(applicationContext)
        val priceNhan = intent.getStringExtra("PRICE")
        setContent {

            Column {
                MyToolbar3(title = "Check out")
                Title(title = "Shipping Address",1)
                ContentShippingAddress()
                Title(title = "Payment",2)
                ContentPayment(number = "1234 5678 9012 3456")
                Title(title = "Delivery method",3)
                ContentDeliveryMethod(speed = "Fast (2-3days)")
                if (priceNhan != null) {
                    ContentTotal(pricePro = priceNhan.toDouble(), priceShip = 5.0)
                }
                ClickBackCheckOut()
            }
        }
    }
}

@Composable
fun Title (title:String,type:Int) {
    val shippingViewModel = ShippingViewModel()
    val userViewModel = UserViewModel()
    val account = userViewModel.getEmailFromSharedPreferences() ?: ""
    val shipState = shippingViewModel.ships.observeAsState(initial = emptyList())
    val ships = shipState.value
    shippingViewModel.getShipAddressBySelect(1, account = account)
    val context = LocalContext.current
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(top = 5.dp), Arrangement.SpaceAround) {
        Text(text = title, fontSize = 25.sp, color = Color(0xFF408143), modifier = Modifier.fillMaxWidth(0.5f))
        Spacer(modifier = Modifier)
        Icon(
            Icons.Default.Edit , contentDescription = null, modifier = Modifier
                .size(30.dp)
                .fillMaxWidth(0.5f)
                .clickable {
                    when (type) {
                        1 -> {
                            val intent = Intent(context, ShippingActivity::class.java)
                            val id = ships[0].id
                            if(id != ""){
                                intent.putExtra("CLICK",id)
                                context.startActivity(intent)
                            }else{
                                intent.putExtra("CLICK","")
                                context.startActivity(intent)
                            }
                        }

                        2 -> {
                            val intent = Intent(context, PaymentActivity::class.java)
                            context.startActivity(intent)
                        }

                        else -> {
                            Toast
                                .makeText(
                                    context,
                                    "Chức năng tạm chưa phát triển",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                    }
                })
    }
}

@Composable
fun ContentShippingAddress () {
    val shippingViewModel = ShippingViewModel()
    val userViewModel = UserViewModel()

    val account = userViewModel.getEmailFromSharedPreferences() ?: ""
    val shipState = shippingViewModel.ships.observeAsState(initial = emptyList())
    val ships = shipState.value
    shippingViewModel.getShipAddressBySelect(1,account)
    
    if(ships.isEmpty()){
        Column (modifier = Modifier.fillMaxWidth().height(100.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(text = "Bạn chưa chọn địa chỉ ship", fontSize = 20.sp)
        }
    }else{
        Card(shape = RoundedCornerShape(5.dp), modifier = Modifier.padding(top = 20.dp, start = 30.dp, end = 30.dp, bottom = 20.dp),colors = CardDefaults.cardColors(
            containerColor =
            Color.White
        ),
            elevation = CardDefaults.cardElevation(
                defaultElevation =
                3.dp
            )) {
            Column {
                Text(text = ships[0].name, fontSize = 25.sp, modifier = Modifier.padding(top = 10.dp, start = 20.dp, bottom = 10.dp))
                Divider(modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth())
                Text(text = ships[0].address, fontSize = 20.sp, modifier = Modifier.padding(top = 10.dp, start = 20.dp, bottom = 20.dp))
            }
        }
    }
    
}

@Composable
fun ContentPayment (number:String) {
    Card(shape = RoundedCornerShape(5.dp), modifier = Modifier.padding(top = 20.dp, start = 30.dp, end = 30.dp, bottom = 20.dp),colors = CardDefaults.cardColors(
        containerColor =
        Color.White
    ),
        elevation = CardDefaults.cardElevation(
            defaultElevation =
            3.dp
        )) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp), horizontalArrangement = Arrangement.Center, Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.iconcard),
                contentDescription = "anh nen",
                modifier = Modifier
                    .height(60.dp)
                    .weight(0.4f),
                contentScale = ContentScale.Fit
            )
            Text(text = number, fontSize = 20.sp, modifier = Modifier
                .padding(top = 10.dp, start = 10.dp, bottom = 10.dp)
                .weight(0.6f))
        }
    }
}

@Composable
fun ContentDeliveryMethod (speed:String) {
    Card(shape = RoundedCornerShape(5.dp), modifier = Modifier.padding(top = 20.dp, start = 30.dp, end = 30.dp, bottom = 20.dp),colors = CardDefaults.cardColors(
        containerColor =
        Color.White
    ),
        elevation = CardDefaults.cardElevation(
            defaultElevation =
            3.dp
        )) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp), horizontalArrangement = Arrangement.Center, Alignment.CenterVertically) {
            Box(modifier = Modifier
                .weight(0.5f)
                .padding(start = 20.dp)){
                Image(
                    painter = painterResource(id = R.drawable.anhdhl),
                    contentDescription = "anh nen",
                    modifier = Modifier
                        .height(50.dp)
                        .width(130.dp)
                        .background(Color.Yellow)
                    ,
                    contentScale = ContentScale.Fit
                )
            }

            Text(text = speed, fontSize = 20.sp, modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .weight(0.5f), fontWeight = FontWeight(550)
            )
        }
    }
}

@Composable
fun DeMuc2 (tittle:String,price:String) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),Arrangement.SpaceBetween) {
        Text(text = tittle, fontSize = 25.sp, color = Color(0xFF408143), modifier = Modifier.fillMaxWidth(0.4f))
        Text(text = price, fontSize = 25.sp, color = Color.Black, modifier = Modifier.fillMaxWidth(0.6f), textAlign = TextAlign.End)
    }
}

@Composable
fun DeMuc3 (tittle:String,price:String) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),Arrangement.SpaceBetween) {
        Text(text = tittle, fontSize = 25.sp, color = Color(0xFF408143), modifier = Modifier.fillMaxWidth(0.4f))
        Text(text = price, fontSize = 25.sp, color = Color.Black, modifier = Modifier.fillMaxWidth(0.6f), fontWeight = FontWeight.Bold, textAlign = TextAlign.End)
    }
}

@Composable
fun ContentTotal (pricePro:Double,priceShip:Double) {
    val priceAll = pricePro + priceShip
    Card(shape = RoundedCornerShape(5.dp), modifier = Modifier
        .padding(top = 20.dp, start = 30.dp, end = 30.dp, bottom = 20.dp)
        .fillMaxWidth(),colors = CardDefaults.cardColors(
        containerColor =
        Color.White
    ),
        elevation = CardDefaults.cardElevation(
            defaultElevation =
            3.dp
        )) {
        Column {
            DeMuc2(tittle = "Order:", price = "$ $pricePro")
            DeMuc2(tittle = "Delivery:", price = "$ $priceShip")
            DeMuc3(tittle = "Total:", price = "$ $priceAll")
        }
    }
    MyButton(title = "SUBMIT ORDER", onClick = { /*TODO*/ }, mauChu = Color.White, mauNen = Color.Black)
}


@Composable
fun ClickBackCheckOut () {
    val context = LocalContext.current
    BackHandler {
        val intent = Intent(context, CartActivity::class.java)
        context.startActivity(intent)
    }
}