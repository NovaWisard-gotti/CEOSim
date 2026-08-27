package com.educalab.ceosim.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.educalab.ceosim.ui.illustrations.NicoCharacter
import com.educalab.ceosim.ui.illustrations.NicoExpression
import com.educalab.ceosim.ui.illustrations.StoreFrontIllustration

private data class OnboardingPage(val title: String, val body: String, val expression: NicoExpression)

private val pages = listOf(
    OnboardingPage(
        title = "¡Bienvenido a tu pequeña tienda!",
        body = "Aquí vas a comprar, organizar y vender productos para aprender a administrar tu propio negocio.",
        expression = NicoExpression.FELIZ
    ),
    OnboardingPage(
        title = "Soy Nico",
        body = "Te voy a acompañar en la tienda. Te doy consejos cortos cuando los necesites, ¡pero tú tomas las decisiones!",
        expression = NicoExpression.NEUTRAL
    ),
    OnboardingPage(
        title = "Compra, organiza y vende",
        body = "Ve al almacén a comprar productos, ordénalos en tus estantes y atiende a los clientes en el mostrador.",
        expression = NicoExpression.EMOCIONADO
    ),
    OnboardingPage(
        title = "Tus datos son solo tuyos",
        body = "CEOSim funciona sin internet. No pedimos tu nombre real ni ningún dato personal: solo un alias y un avatar.",
        expression = NicoExpression.PENSANDO
    )
)

@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    var pageIndex by remember { mutableIntStateOf(0) }
    val page = pages[pageIndex]

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                if (pageIndex == 0) {
                    StoreFrontIllustration(modifier = Modifier.fillMaxWidth())
                } else {
                    NicoCharacter(expression = page.expression, size = 140.dp)
                }
                Text(
                    text = page.title,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 20.dp)
                )
                Text(
                    text = page.body,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                PageIndicator(currentIndex = pageIndex, total = pages.size)
                Button(
                    onClick = { if (pageIndex < pages.lastIndex) pageIndex++ else onFinish() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (pageIndex < pages.lastIndex) "Siguiente" else "¡Entrar a mi tienda!")
                }
                if (pageIndex < pages.lastIndex) {
                    TextButton(onClick = onFinish) { Text("Saltar") }
                }
            }
        }
    }
}

@Composable
private fun PageIndicator(currentIndex: Int, total: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(bottom = 16.dp)) {
        repeat(total) { i ->
            val color = if (i == currentIndex) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .size(if (i == currentIndex) 10.dp else 8.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}
