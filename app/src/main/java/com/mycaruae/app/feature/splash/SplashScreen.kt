package com.mycaruae.app.feature.splash

import androidx.compose.animation.core.Animatable
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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mycaruae.app.R
import com.mycaruae.app.ui.theme.Amber60
import com.mycaruae.app.ui.theme.Amber80
import com.mycaruae.app.ui.theme.Navy10
import com.mycaruae.app.ui.theme.Navy20
import com.mycaruae.app.ui.theme.Navy30
import com.mycaruae.app.ui.theme.Navy40
import com.mycaruae.app.ui.theme.Navy80
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    onNavigateToOnboarding: () -> Unit,
    onNavigateToDashboard: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    // ── Animations ──
    val logoScale = remember { Animatable(0f) }
    val logoAlpha = remember { Animatable(0f) }
    val textAlpha = remember { Animatable(0f) }
    val taglineAlpha = remember { Animatable(0f) }
    val shimmerAlpha = remember { Animatable(0f) }

    val infiniteTransition = rememberInfiniteTransition(label = "splash")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.08f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "pulse",
    )

    LaunchedEffect(Unit) {
        launch {
            logoScale.animateTo(1f, tween(600, easing = FastOutSlowInEasing))
        }
        launch {
            logoAlpha.animateTo(1f, tween(500))
        }
        launch {
            delay(400)
            textAlpha.animateTo(1f, tween(500))
        }
        launch {
            delay(700)
            taglineAlpha.animateTo(1f, tween(500))
        }
        launch {
            delay(900)
            shimmerAlpha.animateTo(1f, tween(400))
        }
    }

    LaunchedEffect(Unit) {
        delay(2500)
        if (viewModel.isUserLoggedIn()) {
            onNavigateToDashboard()
        } else {
            onNavigateToOnboarding()
        }
    }

    // ── Background ──
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Navy10, Navy20, Navy10),
                ),
            ),
        contentAlignment = Alignment.Center,
    ) {
        // Subtle radial glow behind logo
        Canvas(
            modifier = Modifier
                .size(320.dp)
                .alpha(logoAlpha.value * 0.3f),
        ) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Navy40, Color.Transparent),
                    radius = size.minDimension / 2,
                ),
            )
        }

        // ── Content ──
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // Logo icon with glow ring
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .scale(logoScale.value * pulseScale)
                    .alpha(logoAlpha.value),
            ) {
                // Outer glow ring
                Canvas(modifier = Modifier.size(140.dp)) {
                    drawCircle(
                        brush = Brush.sweepGradient(
                            colors = listOf(
                                Amber60.copy(alpha = 0.6f),
                                Navy80.copy(alpha = 0.1f),
                                Amber60.copy(alpha = 0.6f),
                                Navy80.copy(alpha = 0.1f),
                            ),
                        ),
                        radius = size.minDimension / 2 - 2f,
                        style = Stroke(width = 2f),
                    )
                }

                // Inner circle with car icon
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Navy30, Navy20),
                            ),
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_car_splash),
                        contentDescription = "Car",
                        modifier = Modifier.size(75.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // App name
            Text(
                text = "My Car Tracker",
                modifier = Modifier.alpha(textAlpha.value),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp,
                ),
                color = Color.White,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Tagline
            Text(
                text = "Smart Vehicle Management",
                modifier = Modifier.alpha(taglineAlpha.value),
                style = MaterialTheme.typography.bodyLarge.copy(
                    letterSpacing = 2.sp,
                ),
                color = Navy80.copy(alpha = 0.7f),
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Loading indicator dots
            Row(
                modifier = Modifier.alpha(shimmerAlpha.value),
                horizontalArrangement = Arrangement.Center,
            ) {
                repeat(3) { index ->
                    val dotAlpha by infiniteTransition.animateFloat(
                        initialValue = 0.3f,
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(600, delayMillis = index * 200),
                            repeatMode = RepeatMode.Reverse,
                        ),
                        label = "dot$index",
                    )
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .alpha(dotAlpha)
                            .clip(CircleShape)
                            .background(Amber80),
                    )
                    if (index < 2) Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }

        // Bottom branding
        Text(
            text = "v1.0.0",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .alpha(taglineAlpha.value),
            style = MaterialTheme.typography.labelSmall,
            color = Navy80.copy(alpha = 0.3f),
        )
    }
}