package com.example.asm_mvvm

open class Fragments (val frm: String) {
    data object HomeFragment: Fragments("home")
    data object FavoritesFragment: Fragments("favorites")
    data object NotificationFragment: Fragments("notification")
    data object ProfileFragment: Fragments("profile")
}