package com.example.tnd.screen.Dare

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun DareScreen() {
    val dares = listOf(
        "Do 10 push-ups!",
        "Sing a song loudly.",
        "Dance for 30 seconds."
    )
    val randomDare = remember { dares.random() }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = randomDare, style = MaterialTheme.typography.headlineSmall)
    }
}