package com.example.projecttrashpay.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
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

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF4F4F4)).padding(20.dp)) {
        IconButton(onClick = { nav.popBackStack() }) { Icon(Icons.Default.ArrowBack, null) }
        Text("Setor Sampah", modifier = Modifier.fillMaxWidth().padding(top = 8.dp), textAlign = TextAlign.Center, color = Color(0xFF1B5E20), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium)
        Text("Masukkan data sampah yang ingin disetor", modifier = Modifier.fillMaxWidth().padding(top = 6.dp), textAlign = TextAlign.Center, color = Color.Black)
        Spacer(Modifier.height(24.dp))
        Text("Pilih Jenis Sampah")
        Box {
            OutlinedTextField(value = jenis, onValueChange = {}, readOnly = true, modifier = Modifier.fillMaxWidth(), trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null, modifier = Modifier.clickable { expanded = true }) })
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                listOf("Plastik", "Botol", "Kertas").forEach { DropdownMenuItem(text = { Text(it) }, onClick = { jenis = it; expanded = false }) }
            }
        }
        Spacer(Modifier.height(16.dp))
        Text("Berat (kg)")
        OutlinedTextField(value = berat, onValueChange = { berat = it }, placeholder = { Text("Contoh: 2.5") }, modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(16.dp))
        Card(colors = CardDefaults.cardColors(Color(0xFFCFE6C9)), modifier = Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column { Text("Estimasi Poin"); Text("250 Poin", fontWeight = FontWeight.Bold) }
                Image(painter = painterResource(R.drawable.saldopoin), contentDescription = null, modifier = Modifier.size(40.dp))
            }
        }
        Spacer(Modifier.height(20.dp))
        Text("Metode")
        MetodeItem("Setor Langsung", metode == "langsung") { metode = "langsung" }
        MetodeItem("Minta Penjemputan", metode == "jemput") { metode = "jemput" }
        Spacer(Modifier.weight(1f))
        Button(onClick = { if(metode == "jemput") nav.navigate("penjemputan") else nav.navigate("dashboard") }, colors = ButtonDefaults.buttonColors(Color(0xFF0B7D2B)), modifier = Modifier.fillMaxWidth()) { Text("Lanjutkan", color = Color.White) }
    }
}

@Composable
fun MetodeItem(text: String, selected: Boolean, onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { onClick() }.padding(vertical = 8.dp)) {
        Box(modifier = Modifier.size(20.dp).background(if (selected) Color(0xFF0B7D2B) else Color.Transparent, CircleShape).border(1.dp, Color.Gray, CircleShape), contentAlignment = Alignment.Center) {
            if (selected) Text("✓", color = Color.White, fontSize = 12.sp)
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
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF4F4F4)).padding(20.dp)) {
        IconButton(onClick = { nav.popBackStack() }) { Icon(Icons.Default.ArrowBack, null) }
        Text("Penjemputan Sampah", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = Color(0xFF1B5E20), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineSmall)
        Text("Isi alamat untuk penjemputan", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = Color.Gray, fontSize = 14.sp)
        Spacer(Modifier.height(24.dp))
        Text("Nama Penerima", fontSize = 14.sp); OutlinedTextField(value = nama, onValueChange = { nama = it }, placeholder = { Text("Masukkan nama") }, modifier = Modifier.fillMaxWidth().height(55.dp))
        Spacer(Modifier.height(12.dp))
        Text("Nomor Telepon", fontSize = 14.sp); OutlinedTextField(value = telepon, onValueChange = { telepon = it }, placeholder = { Text("Masukkan no telepon") }, modifier = Modifier.fillMaxWidth().height(55.dp))
        Spacer(Modifier.height(12.dp))
        Text("Alamat Lengkap", fontSize = 14.sp); OutlinedTextField(value = alamat, onValueChange = { alamat = it }, placeholder = { Text("Masukkan alamat") }, modifier = Modifier.fillMaxWidth().height(55.dp))
        Spacer(Modifier.height(12.dp))
        Text("Catatan (Opsional)", fontSize = 14.sp); OutlinedTextField(value = catatan, onValueChange = { catatan = it }, placeholder = { Text("Tambahkan catatan") }, modifier = Modifier.fillMaxWidth().height(55.dp))
        Spacer(Modifier.height(24.dp))
        Box(modifier = Modifier.fillMaxWidth().background(Color(0xFFE8F5E9), RoundedCornerShape(8.dp)).padding(16.dp)) { Text("Penjemputan dilakukan dalam 1x24 jam setelah konfirmasi.", color = Color(0xFF1B5E20), fontSize = 14.sp) }
        Spacer(Modifier.weight(1f))
        Button(onClick = { nav.navigate("dashboard") }, colors = ButtonDefaults.buttonColors(Color(0xFF0B7D2B)), modifier = Modifier.fillMaxWidth()) { Text("Lanjutkan", color = Color.White) }
    }
}

@Composable
fun Riwayat(nav: NavHostController) {
    var selectedTab by remember { mutableStateOf("Semua") }

    val filteredList = if (selectedTab == "Semua") {
        riwayatList
    } else {
        riwayatList.filter { it.metode.contains(selectedTab, ignoreCase = true) }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF4F4F4)).padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { nav.popBackStack() }) { Icon(Icons.Default.ArrowBack, null) }
            Text("Riwayat Transaksi", modifier = Modifier.weight(1f).offset(x = (-20).dp), textAlign = TextAlign.Center, color = Color(0xFF1B5E20), fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
        Spacer(Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            listOf("Semua", "Setor", "Penjemputan").forEach { tab ->
                val isSelected = selectedTab == tab
                Box(modifier = Modifier.padding(horizontal = 4.dp).background(if(isSelected) Color(0xFF0B7D2B) else Color(0xFFE0E0E0), RoundedCornerShape(16.dp)).clickable { selectedTab = tab }.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Text(tab, color = if(isSelected) Color.White else Color.Gray, fontSize = 14.sp)
                }
            }
        }
        Spacer(Modifier.height(16.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(filteredList) { data ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(2.dp),
                    modifier = Modifier.fillMaxWidth().clickable {
                        nav.navigate("detail_transaksi/${data.id}")
                    }
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        Image(painter = painterResource(data.image), contentDescription = null, modifier = Modifier.size(40.dp))
                        Spacer(Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            // Menggunakan data langsung dari riwayatList
                            Text(data.jenisSampah, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Spacer(Modifier.height(4.dp))
                            Text("${data.berat} . ${data.metode}", fontSize = 12.sp, color = Color.DarkGray)
                            Text("${data.tanggal} . 10:30", fontSize = 12.sp, color = Color.Gray)
                        }
                        Text(data.poinDidapat, color = Color(0xFF0B7D2B), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun DetailTransaksi(nav: NavHostController, data: Riwayat) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF4F4F4)).padding(20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { nav.popBackStack() }) { Icon(Icons.Default.ArrowBack, null) }
            Text("Detail Transaksi", modifier = Modifier.weight(1f).offset(x = (-20).dp), textAlign = TextAlign.Center, color = Color(0xFF1B5E20), fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
        Spacer(Modifier.height(24.dp))

        Image(
            painter = painterResource(id = data.image),
            contentDescription = null,
            modifier = Modifier.size(120.dp).align(Alignment.CenterHorizontally)
        )

        Spacer(Modifier.height(16.dp))

        Box(modifier = Modifier.background(Color(0xFFCFE6C9), RoundedCornerShape(16.dp)).padding(horizontal = 16.dp, vertical = 6.dp).align(Alignment.CenterHorizontally)) {
            Text(data.metode, color = Color(0xFF1B5E20), fontWeight = FontWeight.Medium)
        }

        Spacer(Modifier.height(32.dp))

        DetailRow("Lokasi", data.lokasi)
        DetailRow("Jenis Sampah", data.jenisSampah)
        DetailRow("Berat", data.berat)
        DetailRow("Poin Didapat", data.poinDidapat)
        DetailRow("Tanggal", data.tanggal)

        Spacer(Modifier.weight(1f))

        Button(onClick = { nav.popBackStack() }, colors = ButtonDefaults.buttonColors(Color(0xFF0B7D2B)), modifier = Modifier.fillMaxWidth()) {
            Text("Kembali", color = Color.White)
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, color = Color.Gray, fontSize = 14.sp)
            Text(value, fontWeight = FontWeight.Medium, fontSize = 14.sp)
        }
        Divider(color = Color.LightGray, thickness = 1.dp, modifier = Modifier.padding(top = 8.dp))
    }
}