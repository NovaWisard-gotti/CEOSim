package com.educalab.ceosim.ui.screens.upgrades

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
import androidx.compose.material.icons.filled.CheckCircle
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.educalab.ceosim.ui.CeoSimViewModel
import com.educalab.ceosim.ui.components.BalanceChip
import com.educalab.ceosim.ui.illustrations.UpgradeIllustration
import com.educalab.ceosim.ui.theme.ShopGreenDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpgradesScreen(viewModel: CeoSimViewModel, onBack: () -> Unit) {
    val header by viewModel.header.collectAsState()
    val upgrades by viewModel.upgrades.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mejoras de la tienda") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, contentDescription = "Volver") } },
                actions = { BalanceChip(balance = header.balance, modifier = Modifier.padding(end = 12.dp)) }
            )
        }
    ) { padding: PaddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            items(upgrades) { upgrade ->
                val unlocked = header.level >= upgrade.unlockLevel
                Card(shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(1.dp), modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        UpgradeIllustration(category = upgrade.category, size = 52.dp)
                        Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
                            Text(text = upgrade.name, style = MaterialTheme.typography.titleMedium)
                            Text(text = upgrade.description, style = MaterialTheme.typography.bodyMedium)
                            if (!unlocked) {
                                Text(text = "Se desbloquea en el nivel ${upgrade.unlockLevel}", style = MaterialTheme.typography.labelMedium)
                            }
                        }
                        when {
                            upgrade.owned -> Icon(Icons.Filled.CheckCircle, contentDescription = "Ya la tienes", tint = ShopGreenDark)
                            else -> FilledTonalButton(
                                onClick = { viewModel.buyUpgrade(upgrade.id) },
                                enabled = unlocked && header.balance >= upgrade.cost
                            ) {
                                Text("${upgrade.cost}")
                            }
                        }
                    }
                }
            }
        }
    }
}
