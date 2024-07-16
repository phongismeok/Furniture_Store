package com.example.asm_mvvm.response

import com.example.asm_mvvm.models.District

data class DistrictResponse(
    val code: Int,
    val message: String,
    val data: List<District>
)
