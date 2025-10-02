package com.example.tnd.screen.Dare

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
import com.example.tnd.component.DareTopicCard
import com.example.tnd.model.DareTopic
import kotlinx.coroutines.delay



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DareScreen(navController: NavController) {
    val dareTopics = listOf(
        DareTopic("Random", Icons.Default.Quiz, Color(0xFF9A8C98), "Random"),
        DareTopic("Compliments & Flattering Others", Icons.Default.ThumbUp, Color(0xFF4CAF50), "Compliments"),
        DareTopic("Creative / Drawing / Singing / Dancing", Icons.Default.Brush, Color(0xFF9C27B0), "Creative"),
        DareTopic("Food / Weird Taste Challenges", Icons.Default.Restaurant, Color(0xFF795548), "Food"),
        DareTopic("Group / Party Games", Icons.Default.Groups, Color(0xFF2196F3), "Group"),
        DareTopic("Mystery / Random / Surprise Dares", Icons.Default.Casino, Color(0xFF673AB7), "Mystery"),
        DareTopic("Pranks (Light & Harmless)", Icons.Default.Masks, Color(0xFFFF9800), "Pranks"),
        DareTopic("Silly & Goofy", Icons.Default.SentimentVerySatisfied, Color(0xFFFFEB3B), "Silly"),
        DareTopic("Social Media Challenges", Icons.Default.Share, Color(0xFF3F51B5), "SocialMedia"),
        DareTopic("Phone Dares", Icons.Default.Smartphone, Color(0xFF607D8B), "Tech"),
        DareTopic("Time-based Mini Games", Icons.Default.Timer, Color(0xFF009688), "TimeGames"),
        DareTopic("Acting / Roleplay ", Icons.Default.TheaterComedy, Color(0xFFE91E63), "Acting"),
        DareTopic("Confessions / Funny Lines", Icons.Default.Mic, Color(0xFF8BC34A), "Confessions"),
        DareTopic("Adult (18+)", Icons.Default.Whatshot, Color(0xFFF44336), "AdultD")
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

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0x30FFFFFF), shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Text(
                text = "Dare Challenges",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                ),
            )

            IconButton(
                onClick = { showMenu = !showMenu },
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0x30FFFFFF), shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Settings",
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Choose a category to get exciting dare challenges",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color(0xFFA0A0BD)
            ),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            verticalItemSpacing = 12.dp,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(dareTopics) { topic ->
                DareTopicCard(
                    topic = topic,
                    isSelected = topic.title == selectedTopic,
                    onSelect = {
                        navController.navigate("ShowDare/${topic.nav}")
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
