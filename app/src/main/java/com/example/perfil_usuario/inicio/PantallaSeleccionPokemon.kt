package com.example.perfil_usuario.inicio

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.Perfil_Usuario.PantallasNavegacion.PantallaMenuPrincipal
import com.example.perfil_usuario.R

//import com.example.pockemon_goo.R
//import com.example.pockemon_goo.ui.navigation.Screen

@Composable
fun PantallaSeleccionPokemon(navController: NavHostController) {
    var seleccionado by remember { mutableStateOf<String?>(null) }
    var mostrarPokebola by remember { mutableStateOf(false) }

    // Animación de escala para la pokebola
    val scaleAnim = remember { Animatable(0f) }

    LaunchedEffect(mostrarPokebola) {
        if (mostrarPokebola) {
            scaleAnim.snapTo(0f)
            scaleAnim.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing)
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            "Escoge tu Pokémon inicial",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Bulbasaur
            PokemonOpcion(
                id = "Bulbasaur",
                imagenRes = R.drawable.p1,
                tipos = "Grass / Poison",
                seleccionado = seleccionado,
                onClick = {
                    seleccionado = "Bulbasaur"
                    mostrarPokebola = true
                }
            )
            // Charmander
            PokemonOpcion(
                id = "Charmander",
                imagenRes = R.drawable.p2,
                tipos = "Fire",
                seleccionado = seleccionado,
                onClick = {
                    seleccionado = "Charmander"
                    mostrarPokebola = true
                }
            )
            // Squirtle
            PokemonOpcion(
                id = "Squirtle",
                imagenRes = R.drawable.p3,
                tipos = "Water",
                seleccionado = seleccionado,
                onClick = {
                    seleccionado = "Squirtle"
                    mostrarPokebola = true
                }
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        AnimatedVisibility(visible = mostrarPokebola) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pokebola),
                    contentDescription = "Pokebola",
                    modifier = Modifier
                        .size(120.dp)
                        .scale(scaleAnim.value)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "¡Felicidades! Tienes a tu primer Pokémon: $seleccionado",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = {
                    // Navegar a la siguiente pantalla, por ejemplo Main o Dashboard
                    navController.navigate(PantallaMenuPrincipal.Home.ruta) {
                        popUpTo(PantallaMenuPrincipal.SeleccionPokemon.ruta) { inclusive = true }
                    }
                }) {
                    Text("Siguiente")
                }
            }
        }
    }
}

@Composable
fun PokemonOpcion(
    id: String,
    imagenRes: Int,
    tipos: String,
    seleccionado: String?,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = imagenRes),
            contentDescription = id,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(id)
        Text(tipos, style = MaterialTheme.typography.bodySmall)
    }
}