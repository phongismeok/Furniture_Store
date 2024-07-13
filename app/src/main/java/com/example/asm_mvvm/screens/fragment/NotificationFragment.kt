package com.example.asm_mvvm.screens.fragment

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.example.asm_mvvm.ui.theme.MyToolbar

@Composable
fun NotificationFragment(){
    Column {
        MyToolbar(title = "Notifications","notification")
    }
}