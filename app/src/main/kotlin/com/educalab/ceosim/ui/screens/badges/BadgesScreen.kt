package com.educalab.ceosim.ui.screens.badges

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.educalab.ceosim.ui.CeoSimViewModel
import com.educalab.ceosim.ui.illustrations.BadgeMedallion

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BadgesScreen(viewModel: CeoSimViewModel, onBack: () -> Unit) {
    val badges by viewModel.badges.collectAsState()
    val unlockedCount = badges.count { it.unlocked }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Insignias ($unlockedCount/${badges.size})") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, contentDescription = "Volver") } }
            )
        }
    ) { padding: PaddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            items(badges) { badge ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    BadgeMedallion(unlocked = badge.unlocked, size = 64.dp)
                    Text(
                        text = badge.title,
                        style = MaterialTheme.typography.labelLarge,
                        textAlign = TextAlign.Center
                    )
                    if (badge.unlocked) {
                        Text(text = badge.description, style = MaterialTheme.typography.labelMedium, textAlign = TextAlign.Center)
                    } else {
                        Text(text = "Bloqueada", style = MaterialTheme.typography.labelMedium, textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}
