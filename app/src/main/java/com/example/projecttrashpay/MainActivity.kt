package com.example.projecttrashpay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.projecttrashpay.model.riwayatList
import com.example.projecttrashpay.screen.*

class MainActivity : ComponentActivity() 
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContent
        {
            TrashPayApp()
        }
    }
}

@Composable
fun TrashPayApp() {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = "login"
    ) {
        composable("login") { Login(nav) }
        composable("register") { Register(nav) }
        composable("main") { MainScreen() }
    }
}

@Composable
fun MainScreen() {
    val nav = rememberNavController()

    Scaffold(
        bottomBar = { BottomBar(nav) }
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = "dashboard",
            modifier = Modifier.padding(padding)
        ) {
            composable("dashboard") { Dashboard(nav) }
            composable("input") { Input(nav) }
            composable("riwayat") { Riwayat(nav) }
            composable("profil") { Profil(nav) }
            composable("penjemputan") { Penjemputan(nav) }
            composable("tukar_poin") { TukarPoin(nav) }

            composable(
                route = "detail_transaksi/{id}",
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val riwayatId = backStackEntry.arguments?.getInt("id")
                val data = riwayatList.find { it.id == riwayatId }

                if (data != null) {
                    DetailTransaksi(nav = nav, data = data)
                }
            }
        }
    }
}

@Composable
fun BottomBar(nav: NavHostController) {
    val navBackStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .background(Color(0xFFF4F4F4)),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomItem(
                title = "Beranda",
                icon = Icons.Default.Home,
                selected = currentRoute == "dashboard"
            ) {
                nav.navigate("dashboard") {
                    popUpTo("dashboard") { inclusive = true }
                    launchSingleTop = true
                }
            }

            BottomItem(
                title = "Riwayat",
                icon = Icons.Default.AccessTime,
                selected = currentRoute == "riwayat"
            ) {
                nav.navigate("riwayat") {
                    launchSingleTop = true
                }
            }

            Spacer(modifier = Modifier.width(60.dp))

            BottomItem(
                title = "Tukar",
                icon = Icons.Default.Sync,
                selected = currentRoute == "tukar_poin"
            ) {
                nav.navigate("tukar_poin") {
                    launchSingleTop = true
                }
            }

            BottomItem(
                title = "Profil",
                icon = Icons.Default.Person,
                selected = currentRoute == "profil"
            ) {
                nav.navigate("profil") {
                    launchSingleTop = true
                }
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-25).dp)
                .size(60.dp)
                .background(Color.White, RoundedCornerShape(50)),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = {
                    nav.navigate("input") { launchSingleTop = true }
                },
                modifier = Modifier
                    .size(50.dp)
                    .background(Color(0xFF0B7D2B), RoundedCornerShape(50))
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun BottomItem(
    title: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    if (selected) Color(0xFF0B7D2B)
                    else Color.Transparent,
                    RoundedCornerShape(50)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = if (selected) Color.White else Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title,
            color = if (selected) Color(0xFF0B7D2B) else Color.Gray,
            style = MaterialTheme.typography.labelSmall
        )
    }
}
