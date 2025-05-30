package com.example.perfil_usuario.inicio

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.perfil_usuario.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

//import com.example.pockemon_goo.R
//import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PantallaPermisos(
    nombreUsuario: String,
    onPermisosConcedidos: () -> Unit,
    onPermisosDenegados: () -> Unit
) {
    val context = LocalContext.current

    val permissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA
        )
    )

    LaunchedEffect(permissions.allPermissionsGranted) {
        if (permissions.allPermissionsGranted) {
            onPermisosConcedidos()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Image(
                painter = painterResource(id = R.drawable.permisos_icon),
                contentDescription = "Logo",
                modifier = Modifier
                    .height(160.dp)
                    .padding(top = 32.dp)
            )

            Text(
                text = "Hola, $nombreUsuario 👋",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Antes de continuar, necesitamos los siguientes permisos:",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
            )

            Button(
                onClick = {
                    permissions.launchMultiplePermissionRequest()
                },
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Conceder permisos")
            }

            if (permissions.allPermissionsGranted) {
                Text(
                    text = "✅ ¡Gracias! Todos los permisos están concedidos.",
                    color = MaterialTheme.colorScheme.primary
                )
            } else if (permissions.shouldShowRationale) {
                Text(
                    text = "Necesitamos los permisos para darte la mejor experiencia.",
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                Text(
                    text = "Haz clic en el botón para conceder los permisos.",
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}