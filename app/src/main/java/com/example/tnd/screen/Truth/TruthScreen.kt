package com.example.tnd.screen.Truth

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tnd.component.TruthTopicCard
import com.example.tnd.model.TruthTopic
import kotlinx.coroutines.delay

// Data class for topic with icon


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TruthScreen(navController: NavController) {
    val truthTopics = listOf(
        TruthTopic("Random", Icons.Default.Quiz, Color(0xFF9A8C98), "Random"),
        TruthTopic("Deep & Thought-provoking", Icons.Default.Psychology, Color(0xFF6A5ACD), "Deep"),
        TruthTopic("Embarrassing Moments", Icons.Default.SentimentDissatisfied, Color(0xFFFF6B6B), "Embarrassing"),
        TruthTopic("Favorites", Icons.Default.Star, Color(0xFFFFD166), "Favorites"),
        TruthTopic("Friendship & Relationships", Icons.Default.Favorite, Color(0xFFE84855), "Friendship"),
        TruthTopic("Never Have I Ever", Icons.Default.VisibilityOff, Color(0xFF118AB2), "NeverHaveIEver"),
        TruthTopic("Personal Secrets", Icons.Default.Lock, Color(0xFF7B506F), "Secrets"),
        TruthTopic("Romantic Life", Icons.Default.FavoriteBorder, Color(0xFFE56B70), "Romantic"),
        TruthTopic("Spicy / Adult (18+)", Icons.Default.Whatshot, Color(0xFFFF6B6B), "Spicy"),
        TruthTopic("Travel & Adventure", Icons.Default.Flight, Color(0xFF1A936F), "Travel"),
        TruthTopic("Weird & Wacky", Icons.Default.AutoAwesome, Color(0xFF9B5DE5), "Weird"),
        TruthTopic("Funny / Silly", Icons.Default.SentimentVerySatisfied, Color(0xFFFEE440), "Funny"),
        TruthTopic("Dreams & Ambitions", Icons.Default.Nightlight, Color(0xFF9A8C98), "Dreams")
    )

    var selectedTopic by remember { mutableStateOf<String?>(null) }
    var showMenu by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A1A2E),
                        Color(0xFF16213E),
                        Color(0xFF0F3460)
                    )
                )
            )
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Truth Topics",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                ),
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = { showMenu=!showMenu },
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0x30FFFFFF), shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Choose a category to explore truth questions",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color(0xFFA0A0BD)
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Topic selection grid
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            verticalItemSpacing = 12.dp,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(truthTopics) { topic ->
                TruthTopicCard(
                    topic = topic,
                    isSelected = topic.title == selectedTopic,
                    onSelect = {
                        navController.navigate("ShowTruth/${topic.nav}")
                    }
                )
            }
        }

    }
    if (showMenu) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = { showMenu = false })
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp, start = 200.dp, end = 25.dp)
                    .shadow(16.dp, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(topStart = 1.dp, bottomEnd = 1.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF7A8090)
                )
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                    }
                    Text(
                        text = "Feedback & Details",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF2A6BEE),
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        ),
                        modifier = Modifier.padding(5.dp)
                            .clickable(onClick = {navController.navigate("Feedback")})
                    )
                }
            }
        }
    }
}

