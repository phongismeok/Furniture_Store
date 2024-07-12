package com.example.asm_mvvm.screens.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.asm_mvvm.R
import com.example.asm_mvvm.ui.theme.ComposeScreen.ListCart
import com.example.asm_mvvm.ui.theme.MyToolbar3
import com.example.asm_mvvm.viewmodels.CartViewModel
import com.example.asm_mvvm.viewmodels.ProductViewModel
import com.example.asm_mvvm.viewmodels.UserViewModel

class CartActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cartViewModel = CartViewModel()
        val productViewModel = ProductViewModel()

        enableEdgeToEdge()
        setContent {
            Column {
                MyToolbar3(title = "My cart")
                ListCart(cartViewModel = cartViewModel, productViewModel = productViewModel)
            }
        }
    }
}