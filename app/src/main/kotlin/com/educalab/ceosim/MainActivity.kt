package com.educalab.ceosim

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.educalab.ceosim.ui.CeoSimViewModel
import com.educalab.ceosim.ui.CeoSimViewModelFactory
import com.educalab.ceosim.ui.navigation.CeoSimNavGraph
import com.educalab.ceosim.ui.theme.CeoSimTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as CeoSimApplication

        setContent {
            CeoSimTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val viewModel: CeoSimViewModel = viewModel(factory = CeoSimViewModelFactory(app.repository))
                    val isReady by viewModel.isReady.collectAsState()
                    if (!isReady) {
                        // Evita decidir la pantalla de inicio antes de que el
                        // perfil termine de cargar/sembrarse (sección 16).
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    } else {
                        val header by viewModel.header.collectAsState()
                        CeoSimNavGraph(viewModel = viewModel, startAtOnboarding = !header.onboardingCompleted)
                    }
                }
            }
        }
    }
}
