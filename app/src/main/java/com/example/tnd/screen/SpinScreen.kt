package com.example.tnd.screen.SpinBottle

import android.graphics.Paint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tnd.R
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinBottleScreen(navController: NavHostController) {
    var numberOfPlayers by remember { mutableStateOf("") }
    var gameStarted by remember { mutableStateOf(false) }
    var selectedPlayer by remember { mutableStateOf(-1) }
    var bottleSet by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Spin the Bottle",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6A5AE0),
                    titleContentColor = Color.White
                ),

            )
        },
        containerColor = Color(0xFFF8F9FF)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFF8F9FF),
                            Color(0xFFEFF1FF)
                        )
                    )
                )
        ) {
            if (!gameStarted) {
                PlayerInputScreen(
                    numberOfPlayers = numberOfPlayers,
                    onNumberOfPlayersChange = { numberOfPlayers = it },
                    onStartGame = {
                        if (numberOfPlayers.toIntOrNull() != null && numberOfPlayers.toInt() > 1) {
                            gameStarted = true
                        }
                    }
                )
            } else {
                GameScreen(
                    navController = navController,
                    AngleOfBottleSet = bottleSet,
                    playerCount = numberOfPlayers.toInt(),
                    selectedPlayer = selectedPlayer,
                    onSelectedPlayerChange = { selectedPlayer = it },
                    onResetGame = {
                        gameStarted = false
                        numberOfPlayers = ""
                        selectedPlayer = -1
                    }
                )
            }
        }
    }
}

@Composable
fun PlayerInputScreen(
    numberOfPlayers: String,
    onNumberOfPlayersChange: (String) -> Unit,
    onStartGame: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 32.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF6A5AE0),
                                Color(0xFF8B7BEB)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Game Icon",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Spin the Bottle",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                ),
                color = Color(0xFF333333),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Enter the number of players",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF666666),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .shadow(8.dp, RoundedCornerShape(20.dp)),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Number of Players",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color(0xFF444444),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                var isFocused by remember { mutableStateOf(false) }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (isFocused) Color(0xFFF0F0F0) else Color(0xFFF8F8F8)
                        )
                        .padding(16.dp)
                ) {
                    BasicTextField(
                        value = numberOfPlayers,
                        onValueChange = {
                            if (it.isEmpty() || it.toIntOrNull() != null) {
                                if (it.isEmpty() || (it.toInt() in 2..12)) {
                                    onNumberOfPlayersChange(it)
                                }
                            }
                        },
                        textStyle = TextStyle(
                            color = Color(0xFF333333),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center
                        ),
                        cursorBrush = SolidColor(Color(0xFF6A5AE0)),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .onFocusChanged { isFocused = it.isFocused },
                        decorationBox = { innerTextField ->
                            if (numberOfPlayers.isEmpty()) {
                                Text(
                                    text = "2-12 players",
                                    color = Color(0xFF999999),
                                    fontSize = 20.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            innerTextField()
                        }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onStartGame,
                    enabled = numberOfPlayers.toIntOrNull() != null && numberOfPlayers.toInt() > 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF6A5AE0),
                        disabledContainerColor = Color(0xFFCCCCCC)
                    )
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Start",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Start Game",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }

        Text(
            text = "The bottle will spin and randomly select a player",
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF888888),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
                .padding(top = 24.dp)
        )
    }
}

@Composable
fun GameScreen(
    navController: NavController,
    AngleOfBottleSet: Boolean,
    playerCount: Int,
    selectedPlayer: Int,
    onSelectedPlayerChange: (Int) -> Unit,
    onResetGame: () -> Unit
) {
    val rotation = remember { Animatable(0f) }
    var isSpinning by remember { mutableStateOf(false) }
    var targetRotation by remember { mutableStateOf(0f) }
    var bottleSet by remember { mutableStateOf(AngleOfBottleSet) }
    // This offset corrects the bottle image orientation.
    // Default 0f (no extra correction). Use slider or calibrate to set.
    var bottleAngleOffset by remember { mutableStateOf(0f) }

    // Helper for calibration where user types the observed player
    var observedPlayerText by remember { mutableStateOf("") }

    // Utility to compute index given rotation value and current offset
    fun computePlayerIndex(rotationValue: Float, offsetDegrees: Float): Int {
        val anglePerPlayer = 360f / playerCount
        val normalizedRotation = (rotationValue % 360 + 360) % 360
        val adjustedRotation = (normalizedRotation + offsetDegrees + 360f) % 360f
        return (adjustedRotation / anglePerPlayer).toInt() % playerCount
    }

    // Animation & selection
    LaunchedEffect(isSpinning) {
        if (isSpinning) {
            rotation.animateTo(
                targetValue = targetRotation,
                animationSpec = tween(
                    durationMillis = 3000 + ((targetRotation - rotation.value).toInt() / 36).coerceAtLeast(0),
                    easing = EaseOut
                )
            )

            val playerIndex = computePlayerIndex(rotation.value, bottleAngleOffset)
            onSelectedPlayerChange(playerIndex)
            isSpinning = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Spin the Bottle",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            ),
            color = Color(0xFF333333),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "$playerCount players • Tap the bottle to spin",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF666666),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Box(
            modifier = Modifier
                .size(300.dp)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            PlayerWheel(
                playerCount = playerCount,
                selectedPlayer = selectedPlayer,
                modifier = Modifier.size(280.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.bottle),
                contentDescription = "Bottle",
                modifier = Modifier
                    .size(80.dp)
                    .rotate(rotation.value) // animation rotation in degrees
                    .clickable(
                        enabled = !isSpinning,
                        onClick = {
                            if (!isSpinning) {
                                isSpinning = true
                                val randomRotation =
                                    Random.nextInt(5, 15) * 360 + Random.nextInt(0, 360)
                                targetRotation = rotation.value + randomRotation
                            }
                        }
                    )
                    .shadow(8.dp, RoundedCornerShape(50))
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (selectedPlayer >= 0) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .shadow(6.dp, RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFD700).copy(alpha = 0.2f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "SELECTED PLAYER",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        color = Color(0xFF666666),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                            text = "Player ${selectedPlayer + 1}",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = Color(0xFF6A5AE0)
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { navController.navigate("truth") },
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp)
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF6A5AE0)
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = "Truth",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Truth", color = Color.White, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = { navController.navigate("dare") },
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp)
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFE63946)
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Whatshot,
                                contentDescription = "Dare",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Dare", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }




                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = {
                    if (!isSpinning) {
                        isSpinning = true
                        val randomRotation =
                            Random.nextInt(5, 15) * 360 + Random.nextInt(0, 360)
                        targetRotation = rotation.value + randomRotation
                    }
                },
                enabled = !isSpinning,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6A5AE0))
            ) {
                Text(
                    text = if (isSpinning) "Spinning..." else "Spin Again",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

        }

        Spacer(modifier = Modifier.height(18.dp))

        // Calibration card: slider + quick auto-calibrate by typing observed player
        if(bottleSet){
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .shadow(4.dp, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Calibration",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = Color(0xFF444444)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Fine-tune bottle tip (degrees): ${bottleAngleOffset.toInt()}°",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF666666)
                    )

                    Slider(
                        value = bottleAngleOffset,
                        onValueChange = { bottleAngleOffset = it },
                        valueRange = -180f..180f,
                        steps = 359, // integer steps
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Auto-calibrate: after a spin, enter the player number the bottle visually points to and press Calibrate.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF888888)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = observedPlayerText,
                            onValueChange = { txt ->
                                // only allow numbers
                                if (txt.isEmpty() || txt.toIntOrNull() != null) observedPlayerText =
                                    txt
                            },
                            modifier = Modifier.weight(1f),
                            label = { Text("Observed player #") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )

                        Button(
                            onClick = {
                                val observed = observedPlayerText.toIntOrNull()
                                if (observed != null && observed in 1..playerCount) {
                                    // Calculate the offset so the bottle points to the center of the observed player's segment
                                    val anglePerPlayer = 360f / playerCount
                                    val normalizedRotation = (rotation.value % 360 + 360) % 360
                                    val targetCenterAngle =
                                        ((observed - 1) * anglePerPlayer + anglePerPlayer / 2f) % 360f
                                    var computedOffset =
                                        (targetCenterAngle - normalizedRotation + 360f) % 360f
                                    // convert to -180..180 for nicer slider control
                                    if (computedOffset > 180f) computedOffset -= 360f
                                    bottleAngleOffset = computedOffset
                                    // update selected player using new offset
                                    val newSelected =
                                        computePlayerIndex(rotation.value, bottleAngleOffset)
                                    onSelectedPlayerChange(newSelected)
                                }
                            },
                            modifier = Modifier.height(56.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = "Calibrate")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Tip: Use the slider to make small adjustments. Use Calibrate to auto-calc offset after a spin.",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF888888),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            )
        }
    }
}

@Composable
fun PlayerWheel(
    playerCount: Int,
    selectedPlayer: Int,
    modifier: Modifier = Modifier,
    circleColor: Color = Color.White,
    strokeColor: Color = Color(0xFF6A5AE0),
    strokeWidth: Dp = 4.dp,
    selectedColor: Color = Color(0xFFFFD700),
    playerNames: List<String> = emptyList()
) {
    val transition = rememberInfiniteTransition()
    val pulseAnimation by transition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val glowAnimation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val radius = size.minDimension / 2
            val center = Offset(size.width / 2, size.height / 2)
            val anglePerPlayer = 360f / playerCount

            // Draw background with radial gradient
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF1A1A2E).copy(alpha = 0.9f),
                        Color(0xFF0F0F1B)
                    ),
                    center = center,
                    radius = radius * 1.5f
                ),
                radius = radius,
                center = center,
                style = Fill
            )

            // Draw outer glow effect for selected player
            if (playerCount > 0) {
                val selectedStartAngle = selectedPlayer * anglePerPlayer - 90
                drawArc(
                    color = selectedColor.copy(alpha = 0.2f + glowAnimation * 0.1f),
                    startAngle = selectedStartAngle,
                    sweepAngle = anglePerPlayer,
                    useCenter = true,
                    topLeft = Offset(center.x - radius * 1.05f, center.y - radius * 1.05f),
                    size = Size(radius * 2.1f, radius * 2.1f)
                )
            }

            // Draw outer ring with vibrant gradient
            drawCircle(
                brush = Brush.sweepGradient(
                    colors = listOf(
                        Color(0xFF6A5AE0),
                        Color(0xFF00B4D8),
                        Color(0xFF90E0EF),
                        Color(0xFF6A5AE0)
                    )
                ),
                radius = radius,
                center = center,
                style = Stroke(width = strokeWidth.toPx() * 2f)
            )

            // Draw inner decorative circle with subtle gradient
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF2D2D44).copy(alpha = 0.8f),
                        Color(0xFF1A1A2E).copy(alpha = 0.9f)
                    ),
                    center = center,
                    radius = radius * 0.8f
                ),
                radius = radius * 0.8f,
                center = center,
                style = Fill
            )

            for (i in 0 until playerCount) {
                val startAngle = i * anglePerPlayer - 90
                val sweepAngle = anglePerPlayer

                // Create a unique color for each segment using HSL rotation
                val hue = (i * 360f / playerCount) % 360f
                val segmentBaseColor = Color.hsl(hue, 0.7f, 0.5f)

                // Draw player segments with gradient
                val segmentColor = if (i == selectedPlayer) {
                    Brush.radialGradient(
                        colors = listOf(
                            selectedColor.copy(alpha = 0.6f),
                            selectedColor.copy(alpha = 0.2f)
                        ),
                        center = center,
                        radius = radius
                    )
                } else {
                    Brush.radialGradient(
                        colors = listOf(
                            segmentBaseColor.copy(alpha = 0.3f),
                            segmentBaseColor.copy(alpha = 0.1f)
                        ),
                        center = center,
                        radius = radius
                    )
                }

                drawArc(
                    brush = segmentColor,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    topLeft = Offset(center.x - radius, center.y - radius),
                    size = Size(radius * 2, radius * 2)
                )

                // Draw separator lines with gradient
                val endX = center.x + radius * cos(Math.toRadians(startAngle.toDouble())).toFloat()
                val endY = center.y + radius * sin(Math.toRadians(startAngle.toDouble())).toFloat()

                drawLine(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            strokeColor.copy(alpha = 0.8f),
                            strokeColor.copy(alpha = 0.3f)
                        ),
                        start = center,
                        end = Offset(endX, endY)
                    ),
                    start = center,
                    end = Offset(endX, endY),
                    strokeWidth = strokeWidth.toPx() / 1.5f
                )

                // Highlight selected player with animated effect
                if (i == selectedPlayer) {
                    // Inner highlight
                    drawArc(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                selectedColor.copy(alpha = 0.5f),
                                selectedColor.copy(alpha = 0.1f)
                            ),
                            center = center,
                            radius = radius
                        ),
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = true,
                        topLeft = Offset(center.x - radius, center.y - radius),
                        size = Size(radius * 2, radius * 2)
                    )

                    // Outer animated highlight
                    drawArc(
                        color = selectedColor,
                        startAngle = startAngle,
                        sweepAngle = sweepAngle,
                        useCenter = false,
                        topLeft = Offset(center.x - radius, center.y - radius),
                        size = Size(radius * 2, radius * 2),
                        style = Stroke(
                            width = strokeWidth.toPx() * pulseAnimation,
                            cap = StrokeCap.Round
                        )
                    )
                }

                // Calculate text position
                val textAngle = startAngle + anglePerPlayer / 2
                val textRadius = radius * 0.7f
                val textX = center.x + textRadius * cos(Math.toRadians(textAngle.toDouble())).toFloat()
                val textY = center.y + textRadius * sin(Math.toRadians(textAngle.toDouble())).toFloat()

                // Draw player number with better styling
                drawIntoCanvas {
                    val playerText = if (playerNames.getOrNull(i) != null) {
                        "${i + 1}\n${playerNames[i].take(8)}"
                    } else {
                        "${i + 1}"
                    }

                    val paint = android.graphics.Paint().apply {
                        color = if (i == selectedPlayer) {
                            android.graphics.Color.WHITE
                        } else {
                            android.graphics.Color.parseColor("#E0E0E0")
                        }
                        textSize = if (playerNames.getOrNull(i) != null) 20f else 26f
                        textAlign = android.graphics.Paint.Align.CENTER
                        isFakeBoldText = true

                        if (i == selectedPlayer) {
                            setShadowLayer(8f, 0f, 0f, android.graphics.Color.parseColor("#FFD700"))
                        } else {
                            setShadowLayer(4f, 0f, 0f, android.graphics.Color.parseColor("#6A5AE0"))
                        }
                    }

                    // For multiline text
                    if (playerText.contains("\n")) {
                        val lines = playerText.split("\n")
                        var yOffset = textY - (paint.textSize * (lines.size - 1) / 2)
                        for (line in lines) {
                            it.nativeCanvas.drawText(line, textX, yOffset, paint)
                            yOffset += paint.textSize * 1.2f
                        }
                    } else {
                        it.nativeCanvas.drawText(playerText, textX, textY + paint.textSize / 3, paint)
                    }
                }
            }

            // Draw center circle with gradient and shadow effect
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF6A5AE0),
                        Color(0xFF4A3FB0)
                    ),
                    center = center,
                    radius = radius * 0.15f
                ),
                radius = radius * 0.15f,
                center = center,
                style = Fill
            )

            // Draw center ring
            drawCircle(
                color = Color.White,
                radius = radius * 0.12f,
                center = center,
                style = Stroke(width = strokeWidth.toPx() / 2)
            )

            // Draw center text with better styling
            drawIntoCanvas {
                val paint = android.graphics.Paint().apply {
                    color = android.graphics.Color.WHITE
                    textSize = 20f
                    textAlign = android.graphics.Paint.Align.CENTER
                    isFakeBoldText = true
                    setShadowLayer(4f, 0f, 0f, android.graphics.Color.parseColor("#6A5AE0"))
                }
                it.nativeCanvas.drawText("${selectedPlayer + 1}", center.x, center.y + paint.textSize / 3, paint)
            }
        }

        // Add floating indicator above selected player
        if (playerCount > 0) {
            val anglePerPlayer = 360f / playerCount
            val selectedAngle = selectedPlayer * anglePerPlayer - 90 + anglePerPlayer / 2
            val indicatorRadius = 10 / 2 * 0.85f
            val indicatorX = indicatorRadius * cos(Math.toRadians(selectedAngle.toDouble())).toFloat()
            val indicatorY = indicatorRadius * sin(Math.toRadians(selectedAngle.toDouble())).toFloat()

            Box(
                modifier = Modifier
                    .offset(x = indicatorX.dp, y = indicatorY.dp)
                    .size(24.dp)
                    .graphicsLayer {
                        rotationZ = selectedAngle + 90f
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Selected Player",
                    tint = selectedColor,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}