package com.example.perfil_usuario.PantallasMenu

import android.provider.MediaStore
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.io.File

@Composable
fun PantallaGaleria(navController: NavController) {
    val context = LocalContext.current

    // Leer imágenes desde MediaStore en la carpeta Pictures/Nuestra_app
    val listaFotos = remember {
        val fotos = mutableListOf<File>()
        val collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val selection = "${MediaStore.Images.Media.RELATIVE_PATH} LIKE ?"
        val selectionArgs = arrayOf("%Pictures/Nuestra_app%")
        val cursor = context.contentResolver.query(collection, projection, selection, selectionArgs, null)

        cursor?.use {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            while (cursor.moveToNext()) {
                val path = cursor.getString(columnIndex)
                fotos.add(File(path))
            }
        }
        fotos.sortedByDescending { it.lastModified() }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        // Barra superior con botón de retroceso
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopStart) {
            Button(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Volver")
                Text("Volver")
            }
        }

        // Título centrado
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(
                text = "Mis Pokemones",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        // Cuadrícula de imágenes
        if (listaFotos.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listaFotos) { archivo ->
                    val bitmap = remember(archivo.absolutePath) {
                        BitmapFactory.decodeFile(archivo.absolutePath)?.asImageBitmap()
                    }
                    bitmap?.let {
                        Image(
                            bitmap = it,
                            contentDescription = "Foto",
                            modifier = Modifier
                                .aspectRatio(1f)
                                .fillMaxWidth()
                        )
                    }
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No se encontraron imágenes")
            }
        }
    }
}
