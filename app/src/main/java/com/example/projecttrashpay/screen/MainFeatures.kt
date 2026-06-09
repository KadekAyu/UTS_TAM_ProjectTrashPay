package com.example.projecttrashpay.screen

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.example.projecttrashpay.R
import com.example.projecttrashpay.api.AuthRepository
import com.example.projecttrashpay.api.NotificationItem
import com.example.projecttrashpay.api.NotificationRepository
import kotlinx.coroutines.launch

@Composable
fun Dashboard(nav: NavHostController) {
    var namaUser by remember { mutableStateOf("User") }
    var poinUser by remember { mutableStateOf(0) }

    val repository = remember { AuthRepository() }

    LaunchedEffect(Unit) {
        namaUser = repository.getCurrentUserName()
        poinUser = repository.getCurrentUserPoint()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(R.drawable.ic_profile_circle),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        nav.navigate("profil")
                    },
                colorFilter = ColorFilter.tint(Color(0xFF0B7D2B))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Halo, $namaUser!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )

                Text(
                    text = "Selamat datang kembali",
                    color = Color.Gray,
                    fontSize = 13.sp
                )
            }

            IconButton(
                onClick = {
                    nav.navigate("notifikasi")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = null,
                    tint = Color(0xFF1B5E20)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF0B7D2B)
            ),
            shape = RoundedCornerShape(18.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Saldo Poin Anda",
                            color = Color.White,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "$poinUser Poin",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }

                    Image(
                        painter = painterResource(R.drawable.ic_coins),
                        contentDescription = null,
                        modifier = Modifier.size(60.dp),
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color(0xFFDCE5DC),
                            RoundedCornerShape(10.dp)
                        )
                        .clickable {
                            nav.navigate("riwayat")
                        }
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Riwayat Poin >",
                        color = Color(0xFF1B5E20),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Menu Utama",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color(0xFF1B5E20)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Pilih setor sampah untuk setor langsung atau minta penjemputan",
            color = Color.Gray,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                MenuItem(
                    title = "Setor Sampah",
                    icon = R.drawable.ic_trash,
                    modifier = Modifier.weight(1f)
                ) {
                    nav.navigate("input")
                }

                MenuItem(
                    title = "Riwayat",
                    icon = R.drawable.ic_receipt,
                    modifier = Modifier.weight(1f)
                ) {
                    nav.navigate("riwayat")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                MenuItem(
                    title = "Tukar Poin",
                    icon = R.drawable.ic_gift,
                    modifier = Modifier.weight(1f)
                ) {
                    nav.navigate("tukar_poin")
                }

                MenuItem(
                    title = "Panduan",
                    icon = R.drawable.ic_book,
                    modifier = Modifier.weight(1f)
                ) {
                    nav.navigate("panduan")
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            MenuItem(
                title = "Profil",
                icon = R.drawable.ic_profile_circle,
                modifier = Modifier.fillMaxWidth()
            ) {
                nav.navigate("profil")
            }
        }
    }
}

@Composable
fun MenuItem(
    title: String,
    icon: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.height(120.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    onClick()
                }
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                colorFilter = ColorFilter.tint(Color(0xFF0B7D2B))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                color = Color(0xFF1B5E20),
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun TukarPoin(nav: NavHostController) {
    var poinUser by remember { mutableStateOf(0) }
    var loadingHadiah by remember { mutableStateOf("") }

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repository = remember { AuthRepository() }

    LaunchedEffect(Unit) {
        poinUser = repository.getCurrentUserPoint()
    }

    fun prosesTukar(namaHadiah: String, jumlahPoin: Int) {

        scope.launch {
            loadingHadiah = namaHadiah

            val result = repository.tukarPoin(
                namaHadiah = namaHadiah,
                jumlahPoin = jumlahPoin
            )

            loadingHadiah = ""

            if (result.isSuccess) {
                poinUser = result.getOrNull() ?: poinUser

                Toast
                    .makeText(
                        context,
                        "Penukaran berhasil",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            } else {
                Toast
                    .makeText(
                        context,
                        result.exceptionOrNull()?.message ?: "Poin tidak cukup",
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        }
    }

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
                text = "Tukar Poin",
                modifier = Modifier
                    .weight(1f)
                    .offset(x = (-20).dp),
                textAlign = TextAlign.Center,
                color = Color(0xFF1B5E20),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        Text(
            text = "Tukarkan poin Anda dengan hadiah menarik",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .background(
                    Color(0xFFCFE6C9),
                    RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .align(Alignment.End)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "$poinUser Poin",
                    color = Color(0xFF1B5E20),
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.width(8.dp))

                Image(
                    painter = painterResource(R.drawable.ic_coins),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    colorFilter = ColorFilter.tint(Color(0xFF0B7D2B))
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TukarItem(
                title = "Saldo E-Wallet\nRp10.000",
                cost = "1.000 Poin",
                icon = R.drawable.ic_wallet,
                modifier = Modifier.weight(1f),
                isLoading = loadingHadiah == "Saldo E-Wallet Rp10.000",
                enabled =  loadingHadiah != "Saldo E-Wallet Rp10.000",
                onTukar = {
                    prosesTukar(
                        namaHadiah = "Saldo E-Wallet Rp10.000",
                        jumlahPoin = 1000
                    )
                }
            )

            TukarItem(
                title = "Voucher Belanja\nRp25.000",
                cost = "2.000 Poin",
                icon = R.drawable.ic_shopping,
                modifier = Modifier.weight(1f),
                isLoading = loadingHadiah == "Voucher Belanja Rp25.000",
                enabled = loadingHadiah != "Voucher Belanja Rp25.000",
                onTukar = {
                    prosesTukar(
                        namaHadiah = "Voucher Belanja Rp25.000",
                        jumlahPoin = 2000
                    )
                }
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TukarItem(
                title = "Pulsa\nRp10.000",
                cost = "1.000 Poin",
                icon = R.drawable.ic_phone,
                modifier = Modifier.weight(1f),
                isLoading = loadingHadiah == "Pulsa Rp10.000",
                enabled = loadingHadiah != "Pulsa Rp10.000",
                onTukar = {
                    prosesTukar(
                        namaHadiah = "Pulsa Rp10.000",
                        jumlahPoin = 1000
                    )
                }
            )

            TukarItem(
                title = "Donasi Lingkungan",
                cost = "500 Poin",
                icon = R.drawable.ic_donation,
                modifier = Modifier.weight(1f),
                isLoading = loadingHadiah == "Donasi Lingkungan",
                enabled = loadingHadiah != "Donasi Lingkungan",
                onTukar = {
                    prosesTukar(
                        namaHadiah = "Donasi Lingkungan",
                        jumlahPoin = 500
                    )
                }
            )
        }

        Spacer(Modifier.weight(1f))

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
                text = "Poin akan otomatis dikurangi setelah penukaran berhasil",
                color = Color(0xFF1B5E20),
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun TukarItem(
    title: String,
    cost: String,
    icon: Int,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    enabled: Boolean = true,
    onTukar: () -> Unit
) {
    Card(
        modifier = modifier.height(170.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                colorFilter = ColorFilter.tint(Color(0xFF0B7D2B))
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = title,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = cost,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20)
            )

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = onTukar,
                enabled = enabled,
                modifier = Modifier
                    .height(36.dp)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF0B7D2B),
                    disabledContainerColor = Color.Gray
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = if (isLoading) "Proses..." else "Tukar",
                    fontSize = 12.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun Profil(nav: NavHostController) {
    var namaUser by remember { mutableStateOf("User") }
    var emailUser by remember { mutableStateOf("-") }
    var teleponUser by remember { mutableStateOf("-") }
    var poinUser by remember { mutableStateOf(0) }

    val repository = remember { AuthRepository() }

    LaunchedEffect(Unit) {
        namaUser = repository.getCurrentUserName()
        emailUser = repository.getCurrentUserEmail() ?: "-"
        teleponUser = repository.getCurrentUserPhone()
        poinUser = repository.getCurrentUserPoint()
    }

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
                text = "Profil",
                modifier = Modifier
                    .weight(1f)
                    .offset(x = (-20).dp),
                textAlign = TextAlign.Center,
                color = Color(0xFF1B5E20),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        Spacer(Modifier.height(24.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.ic_profile_circle),
                contentDescription = null,
                modifier = Modifier.size(90.dp),
                colorFilter = ColorFilter.tint(Color(0xFF0B7D2B))
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = namaUser,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color(0xFF1B5E20)
            )

            Text(
                text = emailUser,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        Spacer(Modifier.height(28.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                ProfileRow("Nama", namaUser)
                ProfileRow("Email", emailUser)
                ProfileRow("Telepon", teleponUser)
                ProfileRow("Total Poin", "$poinUser Poin")
            }
        }
    }
}

@Composable
fun ProfileRow(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Text(
            text = label,
            color = Color.Gray,
            fontSize = 13.sp
        )

        Text(
            text = value,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp
        )

        Divider(
            color = Color(0xFFE0E0E0),
            thickness = 1.dp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun Panduan(nav: NavHostController) {
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
                text = "Panduan",
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
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Cara Menggunakan TrashPay",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20),
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text("1. Pilih menu Setor Sampah.")
                Text("2. Pilih jenis sampah dan masukkan berat sampah.")
                Text("3. Pilih metode Setor Langsung atau Minta Penjemputan.")
                Text("4. Jika memilih Setor Langsung, tekan Simpan.")
                Text("5. Jika memilih Minta Penjemputan, tekan Lanjutkan lalu isi alamat.")
                Text("6. Poin akan masuk ke akun setelah transaksi berhasil.")
                Text("7. Poin dapat ditukar melalui menu Tukar Poin.")
            }
        }
    }
}

@Composable
fun Notifikasi(nav: NavHostController) {
    var notificationList by remember {
        mutableStateOf<List<NotificationItem>>(emptyList())
    }

    var isLoading by remember {
        mutableStateOf(true)
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    val repository = remember { NotificationRepository() }

    LaunchedEffect(Unit) {
        isLoading = true

        val result = repository.getNotifikasiUser()

        if (result.isSuccess) {
            notificationList = result.getOrNull() ?: emptyList()
            errorMessage = ""
        } else {
            errorMessage = result.exceptionOrNull()?.message ?: "Gagal mengambil notifikasi"
        }

        isLoading = false
    }

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
                text = "Notifikasi",
                modifier = Modifier
                    .weight(1f)
                    .offset(x = (-20).dp),
                textAlign = TextAlign.Center,
                color = Color(0xFF1B5E20),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        Spacer(Modifier.height(24.dp))

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

            notificationList.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Belum ada notifikasi",
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }

            else -> {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    notificationList.forEach { item ->
                        NotificationCard(
                            title = item.title,
                            desc = item.desc
                        )

                        Spacer(Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationCard(title: String, desc: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_receipt),
                contentDescription = null,
                modifier = Modifier.size(38.dp),
                colorFilter = ColorFilter.tint(Color(0xFF0B7D2B))
            )

            Spacer(Modifier.width(12.dp))

            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20),
                    fontSize = 14.sp
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = desc,
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}