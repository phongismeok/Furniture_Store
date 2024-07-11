package com.example.asm_mvvm.screens.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImage
import com.example.asm_mvvm.ui.theme.ComposeScreen.TransactionContent
import com.example.asm_mvvm.ui.theme.ComposeScreen.TransactionImage

import com.example.asm_mvvm.viewmodels.ProductViewModel

class DetailProductActivity : AppCompatActivity() {
    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        productViewModel = ViewModelProvider(this)[ProductViewModel::class]
        setContent {
            val idPro = intent.getStringExtra("ID_PRODUCT")
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
                            TransactionImage(
                                image1 = it.image1,
                                image2 = it.image2,
                                image3 = it.image3
                            )
                        }

                        Column( // phan content
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .padding(top = 35.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                        ) {
                            TransactionContent(
                                name = it.productName,
                                price = "$ " + it.price,
                                content = it.describe,
                                productViewModel,
                                it._id,
                                it.stateFavorites
                            )
                        }


                    } ?: run {
                        Text("Loading...")
                    }
                }
            }
        }
    }
}