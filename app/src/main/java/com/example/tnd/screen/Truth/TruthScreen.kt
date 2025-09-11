package com.example.tnd.screen.Truth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun TruthScreen() {
    val truths = listOf(
        "What's your most embarrassing moment?",
        "Who is your secret crush?",
        "What's one thing you’ve never told anyone?"
    )
    val randomTruth = remember { truths.random() }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = randomTruth, style = MaterialTheme.typography.headlineSmall)
    }
}