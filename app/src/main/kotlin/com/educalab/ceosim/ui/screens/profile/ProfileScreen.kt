package com.educalab.ceosim.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.educalab.ceosim.domain.model.CustomerAvatar
import com.educalab.ceosim.ui.CeoSimViewModel
import com.educalab.ceosim.ui.illustrations.CustomerIllustration
import com.educalab.ceosim.ui.theme.ShopOrange

private val avatarOptions = CustomerAvatar.values().take(8)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: CeoSimViewModel, onBack: () -> Unit) {
    val header by viewModel.header.collectAsState()
    var alias by remember { mutableStateOf(header.alias) }
    var avatarId by remember { mutableIntStateOf(header.avatarId.coerceIn(1, avatarOptions.size)) }

    LaunchedEffect(header.alias, header.avatarId) {
        alias = header.alias
        avatarId = header.avatarId.coerceIn(1, avatarOptions.size)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi perfil") },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, contentDescription = "Volver") } }
            )
        }
    ) { padding: PaddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text(text = "Elige un alias (no uses tu nombre real)", style = MaterialTheme.typography.titleMedium)
            OutlinedTextField(
                value = alias,
                onValueChange = {
                    if (it.length <= 16) {
                        alias = it
                        viewModel.updateProfile(it, avatarId)
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 20.dp),
                singleLine = true
            )

            Text(text = "Elige tu avatar", style = MaterialTheme.typography.titleMedium)
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                items(avatarOptions.size) { index ->
                    val id = index + 1
                    val selected = id == avatarId
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(CircleShape)
                            .border(
                                width = if (selected) 3.dp else 0.dp,
                                color = ShopOrange,
                                shape = CircleShape
                            )
                            .clickable {
                                avatarId = id
                                viewModel.updateProfile(alias, id)
                            }
                            .padding(6.dp)
                    ) {
                        CustomerIllustration(avatar = avatarOptions[index], size = 56.dp)
                    }
                }
            }
        }
    }
}
