package com.example.asm_mvvm.screens.fragment

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.asm_mvvm.ui.theme.MyToolbar

@Composable
fun NotificationFragment(){
    val textState = remember { mutableStateOf("") }
    Column {
        MyToolbar(title = "Notifications","notification","Search notification",textState)
    }
}