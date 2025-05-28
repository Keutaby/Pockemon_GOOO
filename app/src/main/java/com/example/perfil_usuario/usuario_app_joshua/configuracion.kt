package com.example.perfil_usuario.usuario_app_joshua



import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun PantallaConfiguracion(navController: NavController) {
    val contexto = LocalContext.current
    var musicaActiva by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7)) // Gris claro
    ) {
        // BARRA SUPERIOR
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF4CAF50)) // Verde hoja
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Icono engranaje",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "CONFIGURACIÓN",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // OPCIONES
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            // SWITCH MÚSICA
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Música", color = Color(0xFF3A5F81), fontSize = 18.sp)
                Spacer(modifier = Modifier.weight(1f))
                Switch(
                    checked = musicaActiva,
                    onCheckedChange = { musicaActiva = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.Yellow,
                        uncheckedThumbColor = Color(0xFF3A5F81)
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // PERMISOS DE LA APP
            Text(
                text = "Permisos de la app",
                color = Color(0xFF3A5F81),
                fontSize = 18.sp,
                modifier = Modifier
                    .clickable {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.data = Uri.parse("package:" + contexto.packageName)
                        contexto.startActivity(intent)
                    }
                    .padding(vertical = 8.dp)
            )

            // CAMBIAR FOTO DE PERFIL
            Text(
                text = "Cambiar foto de perfil",
                color = Color(0xFF3A5F81),
                fontSize = 18.sp,
                modifier = Modifier
                    .clickable {
                        navController.navigate("fotoUsuario_perfil")
                    }
                    .padding(vertical = 8.dp)
            )

            // CERRAR SESIÓN
            Text(
                text = "Cerrar sesión",
                color = Color(0xFF3A5F81),
                fontSize = 18.sp,
                modifier = Modifier
                    .clickable {
                        (contexto as? Activity)?.finish()
                    }
                    .padding(vertical = 8.dp)
            )

            // BOTÓN DE SOPORTE
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=xvFZjo5PgG0"))
                    contexto.startActivity(intent)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                Text("Soporte", color = Color.White)
            }

            // VERSIÓN
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = "Versión 1.0.0",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}


