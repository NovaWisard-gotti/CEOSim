package com.educalab.ceosim.ui.screens.cashbox

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.educalab.ceosim.data.local.entity.TransactionType
import com.educalab.ceosim.ui.CeoSimViewModel
import com.educalab.ceosim.ui.illustrations.CoinIcon
import com.educalab.ceosim.ui.theme.ShopGreenDark
import com.educalab.ceosim.ui.theme.ShopRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CashboxScreen(viewModel: CeoSimViewModel, onBack: () -> Unit) {
    val header by viewModel.header.collectAsState()
    val transactions by viewModel.recentTransactions.collectAsState()

    val sales = transactions.count { it.type == TransactionType.VENTA }
    val purchases = transactions.count { it.type == TransactionType.COMPRA }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("La Caja") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, contentDescription = "Volver") } }
            )
        }
    ) { padding: PaddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Card(shape = RoundedCornerShape(20.dp), elevation = CardDefaults.cardElevation(2.dp), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CoinIcon(size = 36.dp)
                        Text(text = " ${header.balance}", style = MaterialTheme.typography.displayMedium)
                    }
                    Text(text = "Monedas actuales", style = MaterialTheme.typography.bodyMedium)
                }
            }

            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                DotStat(label = "Ventas", count = sales, color = ShopGreenDark)
                DotStat(label = "Compras", count = purchases, color = ShopRed)
            }

            Text(text = "Movimientos recientes", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(bottom = 8.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(transactions) { tx ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = tx.description, style = MaterialTheme.typography.bodyMedium)
                        val amountText = if (tx.amount >= 0) "+${tx.amount}" else "${tx.amount}"
                        Text(
                            text = amountText,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (tx.amount >= 0) ShopGreenDark else ShopRed
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DotStat(label: String, count: Int, color: androidx.compose.ui.graphics.Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "●".repeat(count.coerceAtMost(8)) + if (count > 8) " +${count - 8}" else "", color = color, style = MaterialTheme.typography.titleMedium)
        Text(text = "$label ($count)", style = MaterialTheme.typography.bodyMedium)
    }
}
