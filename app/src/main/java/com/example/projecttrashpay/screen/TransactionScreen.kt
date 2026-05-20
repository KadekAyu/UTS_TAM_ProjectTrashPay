package com.example.projecttrashpay.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.projecttrashpay.R
import com.example.projecttrashpay.model.Riwayat
import com.example.projecttrashpay.model.riwayatList

@Composable
fun Input(nav: NavHostController) {
    var jenis by remember { mutableStateOf("Plastik") }
    var expanded by remember { mutableStateOf(false) }
    var berat by remember { mutableStateOf("") }
    var metode by remember { mutableStateOf("langsung") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F4F4))
            .padding(20.dp)
    ) {
        IconButton(onClick = { nav.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, null)
        }

        Text(
            "Setor Sampah",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            textAlign = TextAlign.Center,
            color = Color(0xFF1B5E20),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            "Masukkan data sampah yang ingin disetor",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            textAlign = TextAlign.Center,
            color = Color.Black
        )

        Spacer(Modifier.height(24.dp))

        Text("Pilih Jenis Sampah")

        Box {
            OutlinedTextField(
                value = jenis,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable { expanded = true }
                    )
                }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listOf("Plastik", "Botol", "Kertas").forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            jenis = it
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Text("Berat (kg)")

        OutlinedTextField(
            value = berat,
            onValueChange = { berat = it },
            placeholder = { Text("Contoh: 2.5") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Card(
            colors = CardDefaults.cardColors(Color(0xFFCFE6C9)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Estimasi Poin")
                    Text("250 Poin", fontWeight = FontWeight.Bold)
                }

                Image(
                    painter = painterResource(R.drawable.ic_coins),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp),
                    colorFilter = ColorFilter.tint(Color(0xFF0B7D2B))
                )
            }
        }

        Spacer(Modifier.height(20.dp))

        Text("Metode")

        MetodeItem("Setor Langsung", metode == "langsung") {
            metode = "langsung"
        }

        MetodeItem("Minta Penjemputan", metode == "jemput") {
            metode = "jemput"
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = {
                if (metode == "jemput") {
                    nav.navigate("penjemputan")
                } else {
                    nav.navigate("dashboard")
                }
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF0B7D2B)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Lanjutkan", color = Color.White)
        }
    }
}

@Composable
fun MetodeItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(20.dp)
                .background(
                    if (selected) Color(0xFF0B7D2B) else Color.Transparent,
                    CircleShape
                )
                .border(1.dp, Color.Gray, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (selected) {
                Text("✓", color = Color.White, fontSize = 12.sp)
            }
        }

        Spacer(Modifier.width(10.dp))
        Text(text)
    }
}

@Composable
fun Penjemputan(nav: NavHostController) {
    var nama by remember { mutableStateOf("") }
    var telepon by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var catatan by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F4F4))
            .padding(20.dp)
    ) {
        IconButton(onClick = { nav.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, null)
        }

        Text(
            "Penjemputan Sampah",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color(0xFF1B5E20),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineSmall
        )

        Text(
            "Isi alamat untuk penjemputan",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(24.dp))

        Text("Nama Penerima")
        OutlinedTextField(
            value = nama,
            onValueChange = { nama = it },
            placeholder = { Text("Masukkan nama") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Text("Nomor Telepon")
        OutlinedTextField(
            value = telepon,
            onValueChange = { telepon = it },
            placeholder = { Text("Masukkan no telepon") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Text("Alamat Lengkap")
        OutlinedTextField(
            value = alamat,
            onValueChange = { alamat = it },
            placeholder = { Text("Masukkan alamat") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Text("Catatan (Opsional)")
        OutlinedTextField(
            value = catatan,
            onValueChange = { catatan = it },
            placeholder = { Text("Tambahkan catatan") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFFE8F5E9),
                    RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                "Penjemputan dilakukan dalam 1x24 jam setelah konfirmasi.",
                color = Color(0xFF1B5E20),
                fontSize = 14.sp
            )
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = { nav.navigate("dashboard") },
            colors = ButtonDefaults.buttonColors(Color(0xFF0B7D2B)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Lanjutkan", color = Color.White)
        }
    }
}

@Composable
fun Riwayat(nav: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F4F4))
            .padding(20.dp)
    ) {
        IconButton(onClick = { nav.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
        }

        Text(
            text = "Riwayat Transaksi",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color(0xFF1B5E20),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn {
            items(riwayatList) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable {
                            nav.navigate("detail_transaksi/${item.id}")
                        },
                    colors = CardDefaults.cardColors(Color.White)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(item.image),
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = item.jenisSampah,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = item.tanggal,
                                color = Color.Gray,
                                fontSize = 12.sp
                            )
                        }

                        Text(
                            text = item.poinDidapat,
                            color = Color(0xFF0B7D2B),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DetailTransaksi(
    nav: NavHostController,
    data: Riwayat
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F4F4))
            .padding(20.dp)
    ) {
        IconButton(onClick = { nav.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = null)
        }

        Text(
            text = "Detail Transaksi",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color(0xFF1B5E20),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(Color.White)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Image(
                    painter = painterResource(data.image),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Jenis Sampah: ${data.jenisSampah}")
                Text("Berat: ${data.berat}")
                Text("Poin Didapat: ${data.poinDidapat}")
                Text("Metode: ${data.metode}")
                Text("Lokasi: ${data.lokasi}")
                Text("Tanggal: ${data.tanggal}")
            }
        }
    }
}