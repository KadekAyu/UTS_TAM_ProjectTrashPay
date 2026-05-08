package com.example.projecttrashpay.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.projecttrashpay.R

@Composable
fun Login(nav: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFFF4F4F4)).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(R.drawable.recycle), contentDescription = "", modifier = Modifier.size(100.dp))
        Spacer(Modifier.height(16.dp))
        Text("TrashPay", style = MaterialTheme.typography.headlineMedium, color = Color(0xFF1B5E20), fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("Ubah Sampah Jadi Poin,\nRaih Manfaat!", textAlign = TextAlign.Center)
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(value = email, onValueChange = { email = it }, placeholder = { Text("Masukkan Email") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(value = password, onValueChange = { password = it }, placeholder = { Text("Masukkan Password") }, modifier = Modifier.fillMaxWidth(), visualTransformation = PasswordVisualTransformation())
        Spacer(Modifier.height(8.dp))
        Text("Lupa Password?", color = Color(0xFF2E7D32), modifier = Modifier.align(Alignment.Start))
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { nav.navigate("main") { popUpTo("login") { inclusive = true } } },
            shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B7D2B)), modifier = Modifier.fillMaxWidth()
        ) { Text("Masuk", color = Color.White) }
        Spacer(Modifier.height(16.dp))
        Row {
            Text("Belum punya akun?")
            Spacer(Modifier.width(4.dp))
            Text("Daftar di sini", color = Color(0xFF2E7D32), modifier = Modifier.clickable { nav.navigate("register") })
        }
    }
}

@Composable
fun Register(nav: NavHostController) {
    var nama by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telepon by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var konfirmasi by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF4F4F4)).padding(24.dp)) {
        IconButton(onClick = { nav.popBackStack() }) { Icon(Icons.Default.ArrowBack, null) }
        Spacer(Modifier.height(8.dp))
        Text("Daftar Akun", style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold), color = Color(0xFF1B5E20), modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        Text("Buat akun baru untuk mulai", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = Color.Gray)
        Spacer(Modifier.height(24.dp))
        OutlinedTextField(nama, { nama = it }, placeholder = { Text("Masukkan Nama Lengkap") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(email, { email = it }, placeholder = { Text("Masukkan Email") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(telepon, { telepon = it }, placeholder = { Text("Masukkan Nomor Telepon") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(password, { password = it }, placeholder = { Text("Masukkan Password") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(12.dp))
        OutlinedTextField(konfirmasi, { konfirmasi = it }, placeholder = { Text("Konfirmasi Password") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(20.dp))
        Button(
            onClick = { nav.navigate("login") }, modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0B7D2B))
        ) { Text("Daftar", color = Color.White) }
        Spacer(Modifier.height(16.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text("Sudah punya akun?")
            Spacer(Modifier.width(4.dp))
            Text("Masuk di sini", color = Color(0xFF2E7D32), modifier = Modifier.clickable { nav.navigate("login") })
        }
    }
}