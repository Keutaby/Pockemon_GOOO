package com.example.perfil_usuario.inicio

// Ubicación: `com.example.pockemon_goo/MainScreen.kt`
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import android.media.MediaPlayer
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import com.example.Perfil_Usuario.R
import com.example.Perfil_Usuario.ui.navigation.Screen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen(navController: NavHostController) {
    var showPokebola by remember { mutableStateOf(true) }
    val context = LocalContext.current

    val mediaPlayer = remember {
        MediaPlayer.create(context, R.raw.sonido)
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFFADD8E6))
                )
            )
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = showPokebola,
            transitionSpec = {
                fadeIn(animationSpec = tween(300)) togetherWith
                        fadeOut(animationSpec = tween(300))
            }
        ) { state ->
            val image = if (state) R.drawable.pokebola else R.drawable.logo
            val imageSize = if (state) 200.dp else 800.dp

            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier
                    .size(imageSize)
                    .clickable {
                        if (showPokebola) {
                            mediaPlayer.start()
                            showPokebola = false
                        } else {
                            navController.navigate(Screen.Login.route)
                        }
                    }
            )
        }
    }
}