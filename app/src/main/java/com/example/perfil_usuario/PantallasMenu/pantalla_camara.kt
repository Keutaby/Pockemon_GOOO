package com.example.perfil_usuario.PantallasMenu

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.Perfil_Usuario.PantallasNavegacion.PantallaMenuPrincipal
import com.example.perfil_usuario.R
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@Composable
fun PantallaCamara(navController: NavController) {
    val context = LocalContext.current
    val activity = context as ComponentActivity
    val lifecycleOwner = LocalLifecycleOwner.current

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
    }

    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    if (!hasCameraPermission) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Button(onClick = {
                permissionLauncher.launch(Manifest.permission.CAMERA)
            }) {
                Text("Permitir acceso a la cámara")
            }
        }
    } else {
        var lensFacing by remember { mutableStateOf(CameraSelector.LENS_FACING_BACK) }
        val previewView = remember { PreviewView(context) }
        val imageCapture = remember { ImageCapture.Builder().build() }

        LaunchedEffect(lensFacing) {
            val cameraProvider = context.getCameraProvider()
            cameraProvider.unbindAll()

            val preview = Preview.Builder().build().apply {
                setSurfaceProvider(previewView.surfaceProvider)
            }

            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(lensFacing)
                .build()

            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageCapture
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())
            val imagenes = listOf(
                R.drawable.piko,
                R.drawable.dragonite,
                R.drawable.psyduck,
                R.drawable.squirtle,
                R.drawable.poker,
                R.drawable.poker2,
                R.drawable.pokwe3,
                R.drawable.poker4,
                R.drawable.poker5,
                R.drawable.poker6,

            )
            val imagenSeleccionada = remember { mutableStateOf(imagenes.random()) }

            // Marco sobre la cámara
            Image(
                painter = painterResource(id = imagenSeleccionada.value),
                contentDescription = "Marco sobre la cámara",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth(0.6f)
                    .align(Alignment.Center)
            )

            // Botones inferiores
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Button(
                    onClick = {
                        tomarFoto(imageCapture, context, imagenSeleccionada.value)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Icon(Icons.Default.AddCircle, contentDescription = "Tomar foto")
                }

                Button(
                    onClick = {
                        lensFacing =
                            if (lensFacing == CameraSelector.LENS_FACING_BACK)
                                CameraSelector.LENS_FACING_FRONT
                            else
                                CameraSelector.LENS_FACING_BACK
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Cambiar cámara")
                }

                Button(
                    onClick = {navController.navigate(PantallaMenuPrincipal.Galeria.ruta) },
                    modifier = Modifier


                ) {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = "Abrir galería"
                    )
                }
            }
        }
    }
}

// ------------------ FUNCIONES AUXILIARES ------------------

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCancellableCoroutine { cont ->
        val future = ProcessCameraProvider.getInstance(this)
        future.addListener({
            cont.resume(future.get())
        }, ContextCompat.getMainExecutor(this))
    }

fun tomarFoto(imageCapture: ImageCapture, context: Context, marcoId: Int) {
    imageCapture.takePicture(
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                val bitmap = imageProxyToBitmap(image)
                image.close()

                val finalBitmap = combinarFotoConMarco(context, bitmap, marcoId)
                guardarBitmap(finalBitmap, context)
                Log.d("Camara", "Foto guardada con marco")
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("Camara", "Error al tomar foto: ${exception.message}")
            }
        }
    )
}

fun imageProxyToBitmap(image: ImageProxy): Bitmap {
    val buffer = image.planes[0].buffer
    val bytes = ByteArray(buffer.remaining())
    buffer.get(bytes)
    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    return rotateBitmap(bitmap, image.imageInfo.rotationDegrees)
}

fun rotateBitmap(bitmap: Bitmap, rotationDegrees: Int): Bitmap {
    if (rotationDegrees == 0) return bitmap
    val matrix = Matrix().apply { postRotate(rotationDegrees.toFloat()) }
    return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
}

fun combinarFotoConMarco(context: Context, fotoOriginal: Bitmap, marcoId: Int): Bitmap {
    val marco = BitmapFactory.decodeResource(context.resources, marcoId)
    val marcoRedimensionado = Bitmap.createScaledBitmap(marco, fotoOriginal.width, fotoOriginal.height, true)

    val bitmapFinal = Bitmap.createBitmap(fotoOriginal.width, fotoOriginal.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmapFinal)
    canvas.drawBitmap(fotoOriginal, 0f, 0f, null)
    canvas.drawBitmap(marcoRedimensionado, 0f, 0f, null)
    return bitmapFinal
}

fun guardarBitmap(bitmap: Bitmap, context: Context) {
    val nombre = "FotoConMarco_${System.currentTimeMillis()}.jpg"
    val values = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, nombre)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Nuestra_app")
        }
    }

    val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    uri?.let {
        context.contentResolver.openOutputStream(it)?.use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }
    }
}
