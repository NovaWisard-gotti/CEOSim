package com.educalab.ceosim.ui.screens.store

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.educalab.ceosim.domain.model.UpgradeCategory
import com.educalab.ceosim.ui.CeoSimViewModel
import com.educalab.ceosim.ui.components.BalanceChip
import com.educalab.ceosim.ui.components.LevelProgressBar
import com.educalab.ceosim.ui.components.NicoBubble
import com.educalab.ceosim.ui.illustrations.BadgeMedallion
import com.educalab.ceosim.ui.illustrations.CoinIcon
import com.educalab.ceosim.ui.illustrations.CustomerIllustration
import com.educalab.ceosim.ui.illustrations.ProductIllustration
import com.educalab.ceosim.ui.illustrations.StoreFrontIllustration
import com.educalab.ceosim.ui.illustrations.UpgradeIllustration
import com.educalab.ceosim.ui.navigation.Routes
import com.educalab.ceosim.ui.theme.ShopBlue
import com.educalab.ceosim.ui.theme.ShopGreen
import com.educalab.ceosim.ui.theme.ShopOrange
import com.educalab.ceosim.ui.theme.ShopPurple
import com.educalab.ceosim.ui.theme.ShopRed
import com.educalab.ceosim.ui.theme.ShopYellow
import com.educalab.ceosim.domain.model.ProductCategory
import com.educalab.ceosim.domain.model.CustomerAvatar

private data class ModuleTile(val title: String, val subtitle: String, val route: String, val accent: Color)

@Composable
fun StoreHomeScreen(viewModel: CeoSimViewModel, navController: NavHostController) {
    val header by viewModel.header.collectAsState()
    val nico by viewModel.nicoMessage.collectAsState()
    val badges by viewModel.badges.collectAsState()

    val modules = listOf(
        ModuleTile("Almacén", "Compra productos", Routes.WAREHOUSE, ShopOrange),
        ModuleTile("Estantes", "Organiza tu tienda", Routes.INVENTORY, ShopGreen),
        ModuleTile("Mostrador", "Atiende clientes", Routes.COUNTER, ShopBlue),
        ModuleTile("La Caja", "Revisa tus monedas", Routes.CASHBOX, ShopYellow),
        ModuleTile("Mejoras", "Decora tu tienda", Routes.UPGRADES, ShopPurple),
        ModuleTile("Retos", "Pequeños desafíos", Routes.CHALLENGES, ShopRed)
    )

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = "Hola, ${header.alias}", style = MaterialTheme.typography.titleLarge)
                    Text(text = "Mi Pequeña Tienda", style = MaterialTheme.typography.bodyMedium)
                }
                BalanceChip(balance = header.balance)
            }
        }
    ) { padding: PaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            StoreFrontIllustration(modifier = Modifier.fillMaxWidth())

            LevelProgressBar(
                level = header.level,
                fraction = header.progressFraction,
                modifier = Modifier.padding(top = 12.dp)
            )

            NicoBubble(message = nico, modifier = Modifier.padding(vertical = 14.dp))

            Text(text = "¿Qué quieres hacer hoy?", style = MaterialTheme.typography.titleMedium)

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(modules) { module ->
                    ModuleCard(module = module, onClick = { navController.navigate(module.route) })
                }
            }

            AnimatedVisibility(visible = badges.any { it.unlocked }) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {
                    badges.filter { it.unlocked }.take(4).forEach {
                        BadgeMedallion(unlocked = true, size = 40.dp)
                    }
                }
            }
        }
    }
}

@Composable
private fun ModuleCard(module: ModuleTile, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 148.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = module.accent.copy(alpha = 0.14f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ModuleIcon(module.route)
            Column {
                Text(
                    text = module.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = module.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun ModuleIcon(route: String) {
    Box(modifier = Modifier.padding(bottom = 4.dp)) {
        when (route) {
            Routes.WAREHOUSE -> ProductIllustration(category = ProductCategory.JUGUETE, size = 48.dp)
            Routes.INVENTORY -> UpgradeIllustration(category = UpgradeCategory.ESTANTE, size = 48.dp)
            Routes.COUNTER -> CustomerIllustration(avatar = CustomerAvatar.NINA_TRENZAS, size = 48.dp)
            Routes.CASHBOX -> CoinIcon(size = 48.dp)
            Routes.UPGRADES -> UpgradeIllustration(category = UpgradeCategory.DECORACION, size = 48.dp)
            Routes.CHALLENGES -> BadgeMedallion(unlocked = true, size = 48.dp)
            else -> BadgeMedallion(unlocked = false, size = 48.dp)
        }
    }
}
