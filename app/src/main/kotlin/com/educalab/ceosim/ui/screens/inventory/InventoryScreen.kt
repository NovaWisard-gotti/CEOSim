package com.educalab.ceosim.ui.screens.inventory

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
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
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.educalab.ceosim.domain.engine.MarginResult
import com.educalab.ceosim.domain.engine.PriceEngine
import com.educalab.ceosim.ui.CeoSimViewModel
import com.educalab.ceosim.ui.components.StatusTag
import com.educalab.ceosim.ui.illustrations.ProductIllustration
import com.educalab.ceosim.ui.illustrations.ShelfBackdrop
import com.educalab.ceosim.ui.theme.ShopGreenDark
import com.educalab.ceosim.ui.theme.ShopRed
import com.educalab.ceosim.ui.theme.InkMedium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(viewModel: CeoSimViewModel, onBack: () -> Unit) {
    val shelves by viewModel.shelves.collectAsState()
    val stocked = shelves.filter { it.quantity > 0 }

    // Orden visual de los estantes: el niño puede arrastrar productos para
    // reorganizarlos. Este orden es una preferencia visual de la sesión
    // (ver docs/MANUAL_TECNICO.md, sección de simplificaciones).
    val order = remember { mutableStateListOf<String>() }
    stocked.forEach { if (it.product.id !in order) order.add(it.product.id) }
    order.retainAll(stocked.map { it.product.id }.toSet())

    val orderedItems = order.mapNotNull { id -> stocked.find { it.product.id == id } }

    var draggingId by remember { mutableStateOf<String?>(null) }
    var dragOffsetY by remember { mutableStateOf(0f) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Estantes de mi tienda") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, contentDescription = "Volver") } }
            )
        }
    ) { padding: PaddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            ShelfBackdrop()
            if (orderedItems.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Tus estantes están vacíos.", style = MaterialTheme.typography.titleMedium)
                    Text("Ve al Almacén para comprar tus primeros productos.", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(orderedItems, key = { it.product.id }) { item ->
                        val margin = PriceEngine.classifyMargin(item.product.buyCost, item.sellPrice)
                        val isDragging = draggingId == item.product.id

                        Card(
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(if (isDragging) 8.dp else 1.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .graphicsLayer {
                                    translationY = if (isDragging) dragOffsetY else 0f
                                }
                                .pointerInput(item.product.id) {
                                    detectDragGesturesAfterLongPress(
                                        onDragStart = { draggingId = item.product.id; dragOffsetY = 0f },
                                        onDrag = { change, dragAmount ->
                                            change.consume()
                                            dragOffsetY += dragAmount.y
                                            val currentIndex = order.indexOf(item.product.id)
                                            val approxRowHeight = 96f
                                            if (dragOffsetY > approxRowHeight && currentIndex < order.lastIndex) {
                                                order.removeAt(currentIndex)
                                                order.add(currentIndex + 1, item.product.id)
                                                dragOffsetY = 0f
                                            } else if (dragOffsetY < -approxRowHeight && currentIndex > 0) {
                                                order.removeAt(currentIndex)
                                                order.add(currentIndex - 1, item.product.id)
                                                dragOffsetY = 0f
                                            }
                                        },
                                        onDragEnd = { draggingId = null; dragOffsetY = 0f },
                                        onDragCancel = { draggingId = null; dragOffsetY = 0f }
                                    )
                                }
                        ) {
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                ProductIllustration(category = item.product.category, size = 48.dp)
                                Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
                                    Text(text = item.product.name, style = MaterialTheme.typography.titleMedium)
                                    Text(text = "Cantidad: ${item.quantity}", style = MaterialTheme.typography.bodyMedium)
                                    Text(text = "Costo: ${item.product.buyCost}  ·  Venta: ${item.sellPrice}", style = MaterialTheme.typography.bodyMedium)
                                    Slider(
                                        value = item.sellPrice.toFloat(),
                                        onValueChange = { viewModel.setSellPrice(item.product.id, it.toInt().coerceAtLeast(1)) },
                                        valueRange = 1f..(item.product.buyCost * 3).coerceAtLeast(2).toFloat(),
                                        steps = 0
                                    )
                                    val (label, color) = when (margin) {
                                        MarginResult.GANANCIA -> "Con ganancia" to ShopGreenDark
                                        MarginResult.SIN_GANANCIA -> "Sin ganancia" to InkMedium
                                        MarginResult.PERDIDA -> "Con pérdida" to ShopRed
                                    }
                                    StatusTag(text = label, color = color, modifier = Modifier.padding(top = 4.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
