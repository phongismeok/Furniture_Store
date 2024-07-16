package com.example.asm_mvvm.response

import com.example.asm_mvvm.models.Province

data class ProvinceResponse(
    val code: Int,
    val message: String,
    val data: List<Province>
)
