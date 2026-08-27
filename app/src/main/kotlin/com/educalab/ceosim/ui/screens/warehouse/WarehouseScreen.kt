package com.educalab.ceosim.ui.screens.warehouse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.educalab.ceosim.ui.CeoSimViewModel
import com.educalab.ceosim.ui.components.BalanceChip
import com.educalab.ceosim.ui.illustrations.CoinIcon
import com.educalab.ceosim.ui.illustrations.ProductIllustration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarehouseScreen(viewModel: CeoSimViewModel, onBack: () -> Unit) {
    val header by viewModel.header.collectAsState()
    val shelves by viewModel.shelves.collectAsState()
    val quantities = remember { mutableStateMapOf<String, Int>() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Almacén") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, contentDescription = "Volver") }
                },
                actions = { BalanceChip(balance = header.balance, modifier = Modifier.padding(end = 12.dp)) }
            )
        }
    ) { padding: PaddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(shelves) { item ->
                val qty = quantities[item.product.id] ?: 1
                Card(shape = RoundedCornerShape(18.dp), elevation = CardDefaults.cardElevation(1.dp)) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        ProductIllustration(category = item.product.category, modifier = Modifier.fillMaxWidth())
                        Text(text = item.product.name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 6.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CoinIcon(size = 14.dp)
                            Text(text = " ${item.product.buyCost} c/u", style = MaterialTheme.typography.bodyMedium)
                        }
                        Text(text = "En tienda: ${item.quantity}", style = MaterialTheme.typography.bodyMedium)

                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                            IconButton(onClick = { quantities[item.product.id] = (qty - 1).coerceAtLeast(1) }) {
                                Icon(Icons.Filled.Remove, contentDescription = "Menos")
                            }
                            Text(text = "$qty", style = MaterialTheme.typography.titleMedium)
                            IconButton(onClick = { quantities[item.product.id] = qty + 1 }) {
                                Icon(Icons.Filled.Add, contentDescription = "Más")
                            }
                        }

                        FilledTonalButton(
                            onClick = { viewModel.buyProduct(item.product.id, qty) },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = header.balance >= item.product.buyCost * qty
                        ) {
                            Text("Comprar (${item.product.buyCost * qty})")
                        }
                    }
                }
            }
        }
    }
}
