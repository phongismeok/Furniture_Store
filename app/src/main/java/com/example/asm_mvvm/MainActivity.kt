package com.example.asm_mvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.asm_mvvm.screens.fragment.FavoritesFragment
import com.example.asm_mvvm.screens.fragment.HomeFragment
import com.example.asm_mvvm.screens.fragment.NotificationFragment
import com.example.asm_mvvm.screens.fragment.ProfileFragment
import com.example.asm_mvvm.viewmodels.ProductViewModel
import com.example.asm_mvvm.viewmodels.TypeViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navigationController = rememberNavController()
            val productViewModel = ProductViewModel()


            val selected = remember {
                mutableStateOf(Icons.Default.Home)
            }

            Scaffold(
                bottomBar = {
                    BottomAppBar(
                        containerColor = Color.White
                    ) {
                        IconButton(
                            onClick = {
                                selected.value = Icons.Default.Home
                                navigationController.navigate(Fragments.HomeFragment.frm) {
                                    popUpTo(0)
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                Icons.Default.Home,
                                contentDescription = null,
                                modifier = Modifier.size(26.dp),
                                tint = if (selected.value == Icons.Default.Home) Color.Red else Color.DarkGray
                            )
                        }

                        IconButton(
                            onClick = {
                                selected.value = Icons.Default.Favorite
                                navigationController.navigate(Fragments.FavoritesFragment.frm) {
                                    popUpTo(0)
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                Icons.Default.Favorite,
                                contentDescription = null,
                                modifier = Modifier.size(26.dp),
                                tint = if (selected.value == Icons.Default.Favorite) Color.Red else Color.DarkGray
                            )
                        }

                        IconButton(
                            onClick = {
                                selected.value = Icons.Default.Notifications
                                navigationController.navigate(Fragments.NotificationFragment.frm) {
                                    popUpTo(0)
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                Icons.Default.Notifications,
                                contentDescription = null,
                                modifier = Modifier.size(26.dp),
                                tint = if (selected.value == Icons.Default.Notifications) Color.Red else Color.DarkGray
                            )
                        }

                        IconButton(
                            onClick = {
                                selected.value = Icons.Default.Person
                                navigationController.navigate(Fragments.ProfileFragment.frm) {
                                    popUpTo(0)
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(26.dp),
                                tint = if (selected.value == Icons.Default.Person) Color.Red else Color.DarkGray
                            )
                        }

                    }
                }
            ) {paddingValues ->
                NavHost(navController = navigationController, startDestination = Fragments.HomeFragment.frm, modifier = Modifier.padding(paddingValues)){
                    composable(Fragments.HomeFragment.frm){ HomeFragment() }
                    composable(Fragments.FavoritesFragment.frm){ FavoritesFragment() }
                    composable(Fragments.NotificationFragment.frm){ NotificationFragment() }
                    composable(Fragments.ProfileFragment.frm){ ProfileFragment() }
                }

            }
        }
    }
}

