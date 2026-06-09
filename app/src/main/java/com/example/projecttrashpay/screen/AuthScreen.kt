package com.example.projecttrashpay.screen

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.projecttrashpay.R
import com.example.projecttrashpay.api.AuthRepository
import kotlinx.coroutines.launch

@Composable
fun Login(nav: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repository = remember { AuthRepository() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F4F4))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.ic_recycle),
            contentDescription = "",
            modifier = Modifier.size(100.dp),
            colorFilter = ColorFilter.tint(Color(0xFF0B7D2B))
        )

        Spacer(Modifier.height(16.dp))

        Text(
            "TrashPay",
            style = MaterialTheme.typography.headlineMedium,
            color = Color(0xFF1B5E20),
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "Ubah Sampah Jadi Poin,\nRaih Manfaat!",
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Masukkan Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Masukkan Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "Lupa Password?",
            color = Color(0xFF2E7D32),
            modifier = Modifier
                .align(Alignment.Start)
                .clickable {
                    if (email.isBlank()) {
                        Toast
                            .makeText(context, "Masukkan email terlebih dahulu", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        scope.launch {
                            isLoading = true

                            val result = repository.resetPassword(email.trim())

                            isLoading = false

                            if (result.isSuccess) {
                                Toast
                                    .makeText(context, "Link reset password dikirim ke email", Toast.LENGTH_LONG)
                                    .show()
                            } else {
                                Toast
                                    .makeText(
                                        context,
                                        result.exceptionOrNull()?.message ?: "Gagal mengirim reset password",
                                        Toast.LENGTH_LONG
                                    )
                                    .show()
                            }
                        }
                    }
                }
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    Toast
                        .makeText(context, "Email dan password wajib diisi", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    scope.launch {
                        isLoading = true

                        val result = repository.login(
                            email = email.trim(),
                            password = password
                        )

                        isLoading = false

                        if (result.isSuccess) {
                            Toast
                                .makeText(context, "Login berhasil", Toast.LENGTH_SHORT)
                                .show()

                            nav.navigate("main") {
                                popUpTo("login") { inclusive = true }
                            }
                        } else {
                            Toast
                                .makeText(
                                    context,
                                    result.exceptionOrNull()?.message ?: "Login gagal",
                                    Toast.LENGTH_LONG
                                )
                                .show()
                        }
                    }
                }
            },
            enabled = !isLoading,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0B7D2B)
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                Text("Memproses...", color = Color.White)
            } else {
                Text("Masuk", color = Color.White)
            }
        }

        Spacer(Modifier.height(16.dp))

        Row {
            Text("Belum punya akun?")
            Spacer(Modifier.width(4.dp))
            Text(
                "Daftar di sini",
                color = Color(0xFF2E7D32),
                modifier = Modifier.clickable {
                    nav.navigate("register")
                }
            )
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
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repository = remember { AuthRepository() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F4F4))
            .padding(24.dp)
    ) {
        IconButton(onClick = { nav.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
        }

        Spacer(Modifier.height(8.dp))

        Text(
            "Daftar Akun",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold
            ),
            color = Color(0xFF1B5E20),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            "Buat akun baru untuk mulai",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            placeholder = { Text("Masukkan Nama Lengkap") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = { Text("Masukkan Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = telepon,
            onValueChange = { telepon = it },
            placeholder = { Text("Masukkan Nomor Telepon") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Masukkan Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = konfirmasi,
            onValueChange = { konfirmasi = it },
            placeholder = { Text("Konfirmasi Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        Button(
            onClick = {
                when {
                    nama.isBlank() || email.isBlank() || telepon.isBlank() || password.isBlank() || konfirmasi.isBlank() -> {
                        Toast
                            .makeText(context, "Semua data wajib diisi", Toast.LENGTH_SHORT)
                            .show()
                    }

                    password.length < 6 -> {
                        Toast
                            .makeText(context, "Password minimal 6 karakter", Toast.LENGTH_SHORT)
                            .show()
                    }

                    password != konfirmasi -> {
                        Toast
                            .makeText(context, "Konfirmasi password tidak sama", Toast.LENGTH_SHORT)
                            .show()
                    }

                    else -> {
                        scope.launch {
                            isLoading = true

                            val result = repository.register(
                                nama = nama.trim(),
                                email = email.trim(),
                                telepon = telepon.trim(),
                                password = password
                            )

                            isLoading = false

                            if (result.isSuccess) {
                                Toast
                                    .makeText(context, "Register berhasil, silakan login", Toast.LENGTH_LONG)
                                    .show()

                                nav.navigate("login") {
                                    popUpTo("register") { inclusive = true }
                                }
                            } else {
                                Toast
                                    .makeText(
                                        context,
                                        result.exceptionOrNull()?.message ?: "Register gagal",
                                        Toast.LENGTH_LONG
                                    )
                                    .show()
                            }
                        }
                    }
                }
            },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0B7D2B)
            )
        ) {
            if (isLoading) {
                Text("Memproses...", color = Color.White)
            } else {
                Text("Daftar", color = Color.White)
            }
        }

        Spacer(Modifier.height(16.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Sudah punya akun?")
            Spacer(Modifier.width(4.dp))
            Text(
                "Masuk di sini",
                color = Color(0xFF2E7D32),
                modifier = Modifier.clickable {
                    nav.navigate("login")
                }
            )
        }
    }
}