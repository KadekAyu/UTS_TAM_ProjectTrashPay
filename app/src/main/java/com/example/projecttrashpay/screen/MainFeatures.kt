package com.example.projecttrashpay.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
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

@Composable
fun Dashboard(nav: NavHostController)
{
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF5F5F5)).padding(16.dp)) 
    {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) 
        {
            Image(painter = painterResource(R.drawable.profileicon), contentDescription = null, modifier = Modifier.size(50.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Halo, User!", fontWeight = FontWeight.Bold)
                Text("Selamat datang kembali", color = Color.Gray)
            }
            Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF0B7D2B)), shape = RoundedCornerShape(16.dp), modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text("Saldo Poin Anda", color = Color.White)
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("2.350 Poin", color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.headlineMedium)
                    }
                    Image(painter = painterResource(R.drawable.saldopoin), contentDescription = null, modifier = Modifier.size(60.dp))
                }
                Spacer(modifier = Modifier.height(14.dp))
                Box(modifier = Modifier.fillMaxWidth().background(Color(0xFFDCE5DC), RoundedCornerShape(8.dp)).clickable { nav.navigate("riwayat") }.padding(12.dp)) {
                    Text("Riwayat Poin >", color = Color(0xFF1B5E20), fontWeight = FontWeight.SemiBold)
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text("Menu Utama", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                MenuItem("Setor Sampah", R.drawable.trashbox, Modifier.weight(1f)) { nav.navigate("input") }
                MenuItem("Penjemputan", R.drawable.carbox, Modifier.weight(1f)) { nav.navigate("penjemputan") }
            }
            Spacer(modifier = Modifier.height(28.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                MenuItem("Riwayat", R.drawable.riwayat, Modifier.weight(1f)) { nav.navigate("riwayat") }
                MenuItem("Tukar Poin", R.drawable.tukarpoin, Modifier.weight(1f)) { nav.navigate("tukar_poin") }
            }
            Spacer(modifier = Modifier.height(28.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                MenuItem("Panduan", R.drawable.panduan, Modifier.weight(1f)) { }
                MenuItem("Profil", R.drawable.profileicon, Modifier.weight(1f)) { nav.navigate("profil") }
            }
        }
    }
}

@Composable
fun MenuItem(title: String, icon: Int, modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Card(modifier = modifier.height(120.dp), shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F3F3)), elevation = CardDefaults.cardElevation(3.dp)) {
        Column(modifier = Modifier.fillMaxSize().clickable { onClick() }.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Image(painter = painterResource(icon), contentDescription = null, modifier = Modifier.size(50.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, color = Color(0xFF1B5E20), fontWeight = FontWeight.Medium, textAlign = TextAlign.Center, fontSize = 12.sp)
        }
    }
}

@Composable
fun TukarPoin(nav: NavHostController) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFFF4F4F4)).padding(20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { nav.popBackStack() }) { Icon(Icons.Default.ArrowBack, null) }
            Text("Tukar Poin", modifier = Modifier.weight(1f).offset(x = (-20).dp), textAlign = TextAlign.Center, color = Color(0xFF1B5E20), fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
        Text("Tukarkan poin Anda dengan hadiah menarik", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, color = Color.Gray, fontSize = 14.sp)
        Spacer(Modifier.height(16.dp))
        Box(modifier = Modifier.background(Color(0xFFCFE6C9), RoundedCornerShape(16.dp)).padding(horizontal = 16.dp, vertical = 8.dp).align(Alignment.End)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("2.350 Poin", color = Color(0xFF1B5E20), fontWeight = FontWeight.Bold)
                Spacer(Modifier.width(8.dp))
                Image(painter = painterResource(R.drawable.saldopoin), contentDescription = null, modifier = Modifier.size(20.dp))
            }
        }
        Spacer(Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            TukarItem("Saldo E-Wallet\nRp10.000", "1.000 Poin", R.drawable.ewallet, Modifier.weight(1f))
            TukarItem("Voucher Belanja\nRp25.000", "2.000 Poin", R.drawable.voucherbelanja, Modifier.weight(1f))
        }
        Spacer(Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            TukarItem("Pulsa\nRp10.000", "1.000 Poin", R.drawable.pulsa, Modifier.weight(1f))
            TukarItem("Donasi Lingkungan\n500 Poin", "Tukar", R.drawable.donasilingkungan, Modifier.weight(1f))
        }
        Spacer(Modifier.weight(1f))
        Box(modifier = Modifier.fillMaxWidth().background(Color(0xFFE8F5E9), RoundedCornerShape(8.dp)).padding(16.dp)) {
            Text("Poin akan otomatis dikurangi setelah penukaran berhasil", color = Color(0xFF1B5E20), fontSize = 14.sp)
        }
    }
}

@Composable
fun TukarItem(title: String, cost: String, icon: Int, modifier: Modifier = Modifier) {
    Card(modifier = modifier.height(170.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFFEAEAEA)), shape = RoundedCornerShape(12.dp)) {
        Column(modifier = Modifier.fillMaxSize().padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Image(painter = painterResource(icon), contentDescription = null, modifier = Modifier.size(50.dp))
            Spacer(Modifier.height(8.dp))
            Text(title, textAlign = TextAlign.Center, fontSize = 12.sp, fontWeight = FontWeight.Medium)
            Text(cost, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1B5E20))
            Spacer(Modifier.height(8.dp))
            Button(onClick = { }, modifier = Modifier.height(36.dp).fillMaxWidth(), contentPadding = PaddingValues(0.dp), colors = ButtonDefaults.buttonColors(Color(0xFF0B7D2B)), shape = RoundedCornerShape(16.dp)) {
                Text("Tukar", fontSize = 12.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun Profil(nav: NavHostController) {
    Column(modifier = Modifier.padding(16.dp)) { Text("Halaman Profil") }
}
