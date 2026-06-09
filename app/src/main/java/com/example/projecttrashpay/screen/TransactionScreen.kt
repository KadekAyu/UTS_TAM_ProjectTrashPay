package com.example.projecttrashpay.screen

import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.projecttrashpay.R
import com.example.projecttrashpay.api.TransactionRepository
import com.example.projecttrashpay.api.TransaksiFirestoreItem
import com.example.projecttrashpay.model.Riwayat
import kotlinx.coroutines.launch

@Composable
fun Input(nav: NavHostController) {
    var jenis by remember { mutableStateOf("Plastik") }
    var expanded by remember { mutableStateOf(false) }
    var berat by remember { mutableStateOf("") }
    var metode by remember { mutableStateOf("langsung") }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repository = remember { TransactionRepository() }

    val beratDouble = berat.toDoubleOrNull() ?: 0.0
    val estimasiPoin = repository.hitungPoin(jenis, beratDouble)
    val buttonText = if (metode == "langsung") "Simpan" else "Lanjutkan"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F4F4))
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }

            Text(
                text = "Setor Sampah",
                modifier = Modifier
                    .weight(1f)
                    .offset(x = (-20).dp),
                textAlign = TextAlign.Center,
                color = Color(0xFF1B5E20),
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Isi data sampah, lalu pilih metode setor",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(18.dp),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Data Sampah",
                    color = Color(0xFF1B5E20),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(Modifier.height(14.dp))

                Text(
                    text = "Jenis Sampah",
                    color = Color.DarkGray,
                    fontSize = 13.sp
                )

                Spacer(Modifier.height(6.dp))

                Box {
                    OutlinedTextField(
                        value = jenis,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            IconButton(onClick = { expanded = true }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null
                                )
                            }
                        },
                        shape = RoundedCornerShape(12.dp)
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        }
                    ) {
                        listOf("Plastik", "Botol", "Kertas").forEach { item ->
                            DropdownMenuItem(
                                text = {
                                    Text(item)
                                },
                                onClick = {
                                    jenis = item
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(Modifier.height(14.dp))

                Text(
                    text = "Berat Sampah (kg)",
                    color = Color.DarkGray,
                    fontSize = 13.sp
                )

                Spacer(Modifier.height(6.dp))

                OutlinedTextField(
                    value = berat,
                    onValueChange = {
                        berat = it
                    },
                    placeholder = {
                        Text("Contoh: 2.5")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFCFE6C9)),
            shape = RoundedCornerShape(18.dp),
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
                    Text(
                        text = "Estimasi Poin",
                        color = Color(0xFF1B5E20),
                        fontSize = 13.sp
                    )

                    Text(
                        text = "$estimasiPoin Poin",
                        color = Color(0xFF1B5E20),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }

                Image(
                    painter = painterResource(R.drawable.ic_coins),
                    contentDescription = null,
                    modifier = Modifier.size(46.dp),
                    colorFilter = ColorFilter.tint(Color(0xFF0B7D2B))
                )
            }
        }

        Spacer(Modifier.height(18.dp))

        Text(
            text = "Pilih Metode",
            color = Color(0xFF1B5E20),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Spacer(Modifier.height(10.dp))

        MetodeItem(
            text = "Setor Langsung",
            desc = "Data langsung disimpan sebagai transaksi setor sampah",
            selected = metode == "langsung"
        ) {
            metode = "langsung"
        }

        Spacer(Modifier.height(10.dp))

        MetodeItem(
            text = "Minta Penjemputan",
            desc = "Lanjut isi alamat agar petugas dapat menjemput sampah",
            selected = metode == "jemput"
        ) {
            metode = "jemput"
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = {
                val beratValue = berat.toDoubleOrNull()

                if (berat.isBlank() || beratValue == null || beratValue <= 0.0) {
                    Toast
                        .makeText(context, "Masukkan berat sampah yang valid", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    if (metode == "jemput") {
                        nav.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("jenisSampah", jenis)

                        nav.currentBackStackEntry
                            ?.savedStateHandle
                            ?.set("beratSampah", berat)

                        nav.navigate("penjemputan")
                    } else {
                        scope.launch {
                            isLoading = true

                            val result = repository.simpanSetorSampah(
                                jenisSampah = jenis,
                                berat = berat
                            )

                            isLoading = false

                            if (result.isSuccess) {
                                Toast
                                    .makeText(
                                        context,
                                        "Setor sampah berhasil disimpan",
                                        Toast.LENGTH_LONG
                                    )
                                    .show()

                                nav.navigate("dashboard") {
                                    popUpTo("input") {
                                        inclusive = true
                                    }
                                }
                            } else {
                                Toast
                                    .makeText(
                                        context,
                                        result.exceptionOrNull()?.message ?: "Gagal menyimpan data",
                                        Toast.LENGTH_LONG
                                    )
                                    .show()
                            }
                        }
                    }
                }
            },
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0B7D2B),
                disabledContainerColor = Color.Gray
            ),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            if (isLoading) {
                Text(
                    text = "Menyimpan...",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = buttonText,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun MetodeItem(
    text: String,
    desc: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        colors = CardDefaults.cardColors(
            containerColor = if (selected) Color(0xFFE8F5E9) else Color.White
        ),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (selected) 3.dp else 1.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = if (selected) Color(0xFF0B7D2B) else Color(0xFFE0E0E0),
                    shape = RoundedCornerShape(14.dp)
                )
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .background(
                        if (selected) Color(0xFF0B7D2B) else Color.Transparent,
                        CircleShape
                    )
                    .border(
                        width = 1.dp,
                        color = if (selected) Color(0xFF0B7D2B) else Color.Gray,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (selected) {
                    Text(
                        text = "✓",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.width(12.dp))

            Column {
                Text(
                    text = text,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20),
                    fontSize = 14.sp
                )

                Spacer(Modifier.height(3.dp))

                Text(
                    text = desc,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun Penjemputan(nav: NavHostController) {
    var nama by remember { mutableStateOf("") }
    var telepon by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var catatan by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repository = remember { TransactionRepository() }

    val jenisSampah = nav.previousBackStackEntry
        ?.savedStateHandle
        ?.get<String>("jenisSampah") ?: "Belum ditentukan"

    val beratSampah = nav.previousBackStackEntry
        ?.savedStateHandle
        ?.get<String>("beratSampah") ?: "0"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F4F4))
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }

            Text(
                text = "Form Penjemputan",
                modifier = Modifier
                    .weight(1f)
                    .offset(x = (-20).dp),
                textAlign = TextAlign.Center,
                color = Color(0xFF1B5E20),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Lengkapi alamat agar sampah bisa dijemput",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(20.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFCFE6C9)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Data Sampah",
                    color = Color(0xFF1B5E20),
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = "$jenisSampah - $beratSampah kg",
                    color = Color.DarkGray,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(Modifier.height(18.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(18.dp),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Data Penjemputan",
                    color = Color(0xFF1B5E20),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )

                Spacer(Modifier.height(14.dp))

                Text("Nama Penerima", color = Color.DarkGray, fontSize = 13.sp)

                Spacer(Modifier.height(6.dp))

                OutlinedTextField(
                    value = nama,
                    onValueChange = {
                        nama = it
                    },
                    placeholder = {
                        Text("Masukkan nama")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(Modifier.height(12.dp))

                Text("Nomor Telepon", color = Color.DarkGray, fontSize = 13.sp)

                Spacer(Modifier.height(6.dp))

                OutlinedTextField(
                    value = telepon,
                    onValueChange = {
                        telepon = it
                    },
                    placeholder = {
                        Text("Masukkan nomor telepon")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                Spacer(Modifier.height(12.dp))

                Text("Alamat Lengkap", color = Color.DarkGray, fontSize = 13.sp)

                Spacer(Modifier.height(6.dp))

                OutlinedTextField(
                    value = alamat,
                    onValueChange = {
                        alamat = it
                    },
                    placeholder = {
                        Text("Masukkan alamat lengkap")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    minLines = 2
                )

                Spacer(Modifier.height(12.dp))

                Text("Catatan", color = Color.DarkGray, fontSize = 13.sp)

                Spacer(Modifier.height(6.dp))

                OutlinedTextField(
                    value = catatan,
                    onValueChange = {
                        catatan = it
                    },
                    placeholder = {
                        Text("Opsional")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    minLines = 2
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFFE8F5E9),
                    RoundedCornerShape(10.dp)
                )
                .padding(14.dp)
        ) {
            Text(
                text = "Penjemputan dilakukan dalam 1x24 jam setelah data dikonfirmasi.",
                color = Color(0xFF1B5E20),
                fontSize = 13.sp
            )
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = {
                if (nama.isBlank() || telepon.isBlank() || alamat.isBlank()) {
                    Toast
                        .makeText(context, "Nama, telepon, dan alamat wajib diisi", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    scope.launch {
                        isLoading = true

                        val result = repository.simpanPenjemputan(
                            jenisSampah = jenisSampah,
                            berat = beratSampah,
                            nama = nama.trim(),
                            telepon = telepon.trim(),
                            alamat = alamat.trim(),
                            catatan = catatan.trim()
                        )

                        isLoading = false

                        if (result.isSuccess) {
                            Toast
                                .makeText(
                                    context,
                                    "Data penjemputan berhasil disimpan",
                                    Toast.LENGTH_LONG
                                )
                                .show()

                            nav.navigate("dashboard") {
                                popUpTo("penjemputan") {
                                    inclusive = true
                                }
                            }
                        } else {
                            Toast
                                .makeText(
                                    context,
                                    result.exceptionOrNull()?.message ?: "Gagal menyimpan penjemputan",
                                    Toast.LENGTH_LONG
                                )
                                .show()
                        }
                    }
                }
            },
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF0B7D2B),
                disabledContainerColor = Color.Gray
            ),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            if (isLoading) {
                Text(
                    text = "Menyimpan...",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(
                    text = "Ajukan Penjemputan",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun Riwayat(nav: NavHostController) {
    var transaksiList by remember {
        mutableStateOf<List<TransaksiFirestoreItem>>(emptyList())
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    val repository = remember { TransactionRepository() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        isLoading = true

        val result = repository.getRiwayatUser()

        if (result.isSuccess) {
            transaksiList = result.getOrNull() ?: emptyList()
            errorMessage = ""
        } else {
            errorMessage = result.exceptionOrNull()?.message ?: "Gagal mengambil data"
        }

        isLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF4F4F4))
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }

            Text(
                text = "Riwayat Transaksi",
                modifier = Modifier
                    .weight(1f)
                    .offset(x = (-20).dp),
                textAlign = TextAlign.Center,
                color = Color(0xFF1B5E20),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFF0B7D2B)
                    )
                }
            }

            errorMessage.isNotEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                }
            }

            transaksiList.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Belum ada riwayat transaksi",
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }

            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(transaksiList) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    Toast
                                        .makeText(
                                            context,
                                            "Detail: ${item.jenisSampah} - ${item.berat}",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
                                },
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            ),
                            shape = RoundedCornerShape(14.dp),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(item.image),
                                    contentDescription = null,
                                    modifier = Modifier.size(42.dp),
                                    colorFilter = ColorFilter.tint(Color(0xFF0B7D2B))
                                )

                                Spacer(modifier = Modifier.width(16.dp))

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = item.jenisSampah,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color.Black
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Text(
                                        text = "${item.berat} • ${item.metode}",
                                        fontSize = 12.sp,
                                        color = Color.DarkGray
                                    )

                                    Text(
                                        text = item.tanggal,
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )

                                    if (item.lokasi.isNotBlank()) {
                                        Text(
                                            text = item.lokasi,
                                            fontSize = 11.sp,
                                            color = Color.Gray,
                                            maxLines = 1
                                        )
                                    }
                                }

                                Text(
                                    text = item.poinDidapat,
                                    color = Color(0xFF0B7D2B),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
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
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { nav.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }

            Text(
                text = "Detail Transaksi",
                modifier = Modifier
                    .weight(1f)
                    .offset(x = (-20).dp),
                textAlign = TextAlign.Center,
                color = Color(0xFF1B5E20),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(2.dp)
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