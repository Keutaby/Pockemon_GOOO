package com.example.perfil_usuario.usuario_app_joshua

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.perfil_usuario.R

@Composable
fun PantallaMuerte(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Fondo de pantalla
        Image(
            painter = painterResource(id = R.drawable.cementerio),
            contentDescription = "Fondo muerte",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Capa oscura encima del fondo
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0x99000000)) // Capa negra semitransparente
        )

        // Contenido principal
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.tumba),
                contentDescription = "Lápida",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(200.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Tu Pokémon ha muerto...",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    // Acción al continuar
                    navController.navigate("pantalla_principal") {
                        popUpTo("pantalla_muerte") { inclusive = true }
                    }
                },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Continuar")
            }
        }
    }
}