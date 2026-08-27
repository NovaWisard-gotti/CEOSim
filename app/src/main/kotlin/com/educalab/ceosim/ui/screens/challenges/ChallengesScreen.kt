package com.educalab.ceosim.ui.screens.challenges

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
import androidx.compose.material.icons.filled.Star
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
import com.educalab.ceosim.ui.illustrations.BadgeMedallion
import com.educalab.ceosim.ui.theme.CoinGoldDark

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChallengesScreen(viewModel: CeoSimViewModel, onBack: () -> Unit) {
    val challenges by viewModel.challenges.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pequeños retos") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, contentDescription = "Volver") } }
            )
        }
    ) { padding: PaddingValues ->
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            items(challenges) { challenge ->
                Card(shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(1.dp), modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        BadgeMedallion(unlocked = challenge.completed, size = 48.dp)
                        Column(modifier = Modifier.padding(start = 12.dp).weight(1f)) {
                            Text(text = challenge.title, style = MaterialTheme.typography.titleMedium)
                            Text(text = challenge.narrative, style = MaterialTheme.typography.bodyMedium)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Filled.Star, contentDescription = null, tint = CoinGoldDark, modifier = Modifier.padding(end = 4.dp))
                                Text(text = "+${challenge.xpReward} XP", style = MaterialTheme.typography.labelMedium)
                            }
                        }
                        if (!challenge.completed) {
                            FilledTonalButton(onClick = { viewModel.completeChallenge(challenge.id, challenge.xpReward) }) {
                                Text("Listo")
                            }
                        }
                    }
                }
            }
        }
    }
}
