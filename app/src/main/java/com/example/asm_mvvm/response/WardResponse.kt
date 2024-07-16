package com.example.asm_mvvm.response

import com.example.asm_mvvm.models.Ward

data class WardResponse (
    val code: Int,
    val message: String,
    val data: List<Ward>
)