package com.educalab.ceosim.ui.screens.counter

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.educalab.ceosim.ui.CeoSimViewModel
import com.educalab.ceosim.ui.components.BalanceChip
import com.educalab.ceosim.ui.illustrations.CustomerIllustration
import com.educalab.ceosim.ui.illustrations.ProductIllustration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterScreen(viewModel: CeoSimViewModel, onBack: () -> Unit) {
    val header by viewModel.header.collectAsState()
    val shelves by viewModel.shelves.collectAsState()
    val customers by viewModel.customers.collectAsState()

    var seed by remember { mutableStateOf(0) }
    val currentCustomer = remember(customers, seed) { customers.randomOrNull() }
    val requestedProduct = remember(shelves, seed) {
        // Preferimos pedir algo que sí exista en el catálogo; a veces el
        // cliente pedirá algo agotado, lo cual es intencional (Módulo 7).
        shelves.randomOrNull()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mostrador") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, contentDescription = "Volver") } },
                actions = { BalanceChip(balance = header.balance, modifier = Modifier.padding(end = 12.dp)) }
            )
        }
    ) { padding: PaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (currentCustomer == null || requestedProduct == null) {
                Text("Todavía no tienes clientes ni productos configurados.", style = MaterialTheme.typography.bodyLarge)
                return@Column
            }

            AnimatedContent(targetState = seed, label = "customer") { _ ->
                Card(shape = RoundedCornerShape(20.dp), elevation = CardDefaults.cardElevation(2.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        CustomerIllustration(avatar = currentCustomer.avatar, size = 88.dp)
                        Text(text = currentCustomer.name, style = MaterialTheme.typography.titleLarge)
                        Text(text = currentCustomer.greeting, style = MaterialTheme.typography.bodyMedium)

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            ProductIllustration(category = requestedProduct.product.category, size = 40.dp)
                            Text(
                                text = "Quiero: ${requestedProduct.product.name}",
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                        Text(
                            text = "Stock disponible: ${requestedProduct.quantity}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        viewModel.sellProduct(requestedProduct.product.id, currentCustomer.id)
                        seed++
                    },
                    enabled = requestedProduct.quantity > 0,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Entregar producto")
                }
                OutlinedButton(onClick = { seed++ }, modifier = Modifier.weight(1f)) {
                    Text("Siguiente cliente")
                }
            }
        }
    }
}
