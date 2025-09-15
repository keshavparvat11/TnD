package com.example.tnd.screen.Dare

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tnd.model.DareData
import kotlinx.coroutines.delay

@Composable
fun ShowDare(navController: NavController, item: String) {
    val dd = DareData()
    val dareData = dd.getDare(item)
    val dareList = remember { dareData ?: emptyList() }
    var allDare by remember { mutableStateOf(false) }
    var currentDare by remember { mutableStateOf(dareData?.random() ?: "No Dare Available") }
    var isRefreshing by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (isRefreshing) 360f else 0f,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 1000)
    )




    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(1000)
            currentDare = dareData?.random() ?: "No Dare Available"
            isRefreshing = false
        }

    }

    Box(
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
    ) {
        // Background pattern
        Image(
            painter = painterResource(id = android.R.drawable.ic_menu_help), // Placeholder, replace with your own pattern
            contentDescription = "Background pattern",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.1f)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header
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
                    text = "DARE",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                )

                IconButton(
                    onClick = { showMenu = !showMenu },
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color(0x30FFFFFF), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options",
                        tint = Color.White
                    )
                }
            }

            // Dare Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp)
                    .shadow(16.dp, RoundedCornerShape(24.dp)),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF2D2D44)
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF2D2D44),
                                    Color(0xFF252536)
                                )
                            )
                        )
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if (allDare){
                        LazyColumn( modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            var i = 1
                            items(dareList) { dare ->
                                Text(
                                    text = "$i. " + dare,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.White,
                                        textAlign = TextAlign.Center,
                                        lineHeight = 20.sp
                                    ),
                                    modifier = Modifier.padding(4.dp)
                                )

                               Spacer(modifier = Modifier.height(15.dp))
                                i++
                            }
                        }


                    }
                    else {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {


                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                text = currentDare,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 28.sp
                                ),
                                modifier = Modifier.padding(16.dp)
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                text = "Category: $item",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color(0xFFA0A0BD),
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                    }
                }
            }

            // Action Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { isRefreshing = true },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    ),
                    shape = RoundedCornerShape(16.dp),
                    enabled = !isRefreshing
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "New Dare",
                        tint = Color.White,
                        modifier = Modifier
                            .graphicsLayer { rotationZ = rotation }
                            .size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("New Dare", color = Color.White)
                }

                Button(
                    onClick = { navController.navigate("Home"){
                        popUpTo(0) { inclusive = true }    // clears the whole stack
                        launchSingleTop = true
                    } },
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE63946)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Completed",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Completed", color = Color.White)
                }
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
                        Text(
                            text = "Show All Dare",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                lineHeight = 20.sp
                            ),
                            modifier = Modifier.padding(4.dp).clickable( onClick = { allDare = !allDare })
                        )
                        IconButton(
                            onClick = { allDare = !allDare },

                            )
                        {
                            Icon(
                                imageVector = if (allDare) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = "More options",
                                tint = Color.White
                            )
                        }
                    }
                    Text(
                        text = "Feedback & Details",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF0034FC),
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
@Preview
@Composable
fun View(){
    val nv = rememberNavController()
    ShowDare(nv,"")
}