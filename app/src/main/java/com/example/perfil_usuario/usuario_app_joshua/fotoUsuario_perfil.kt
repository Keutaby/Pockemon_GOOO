package com.example.perfil_usuario


//abrir galeria
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*


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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.perfil_usuario.R
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.FontWeight
import coil.compose.rememberAsyncImagePainter

@Composable
fun FotoUsuarioPerfil() {
    var colorBorde by remember { mutableStateOf(Color(0xFFCC0000)) }
    val coloresDisponibles = listOf(
        Color(0xFFCC0000), Color(0xFF007BFF), Color(0xFF4CAF50), Color(0xFFFF9800), Color.White
    )

    var imagenSeleccionada by remember { mutableStateOf<Uri?>(null) }

    val galeriaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imagenSeleccionada = uri
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.fondo_bonito),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Centro de Foto de Perfil",
                color = Color.Black,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Foto de perfil
            Box(
                modifier = Modifier
                    .size(170.dp)
                    .clip(CircleShape)
                    .background(colorBorde),
                contentAlignment = Alignment.Center
            ) {
                if (imagenSeleccionada != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imagenSeleccionada),
                        contentDescription = "Foto de perfil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.fotoperfil_predeterminada),
                        contentDescription = "Foto de perfil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(150.dp)
                            .clip(CircleShape)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .background(Color(0xFFB0B0B0), shape = MaterialTheme.shapes.medium)
                    .padding(12.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    coloresDisponibles.forEach { color ->
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .padding(4.dp)
                                .clip(CircleShape)
                                .background(color)
                                .clickable {
                                    colorBorde = color
                                }
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    galeriaLauncher.launch("image/*") // Lanza la galería
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorBorde)
            ) {
                Text("Cambiar foto de perfil", color = Color.White)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun FotoUsuarioPerfilPreview() {
    FotoUsuarioPerfil()
}

