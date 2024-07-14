package com.example.asm_mvvm.screens.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.asm_mvvm.R
import com.example.asm_mvvm.ui.theme.MyToolbar3

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Column {
                MyToolbar3(title = "Payment method")
            }
        }
    }
}