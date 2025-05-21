package com.example.perfil_usuario

import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.example.Perfil_Usuario.ControladoresMapa.GPSControlador
import com.example.Perfil_Usuario.ControladoresMapa.usuario_GPS
import com.example.Perfil_Usuario.PantallasMenu.MapaPokemones
import org.osmdroid.config.Configuration
import android.Manifest
import androidx.compose.runtime.getValue
import com.example.perfil_usuario.PantallasNavegacion.MenuHome
import com.example.perfil_usuario.ui.theme.Perfil_UsuarioTheme

class MainActivity : ComponentActivity() {
    private lateinit var gps_sensor: usuario_GPS
    private val controlador_gps: GPSControlador by viewModels()

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            gps_sensor = usuario_GPS(this)

            if(!consultar_permisos_gps()){
                requestPermissions(arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), permisos_totales_gps)
            }
            else{
                controlador_gps.actualizar_permisos(true)
            }

            enableEdgeToEdge()
            setContent {
                val tenemos_permitido_usar_gps: Boolean by controlador_gps.autorizacion.observeAsState(initial = false)

                LaunchedEffect(tenemos_permitido_usar_gps){
                    if(tenemos_permitido_usar_gps){
                        gps_sensor.configurar_actualizar_ubicacion(controlador_gps)
                    }
                }


        enableEdgeToEdge()
        setContent {
            Perfil_UsuarioTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    //MapaPokemones(controlador_gps)
                    MenuHome(modifier = Modifier.fillMaxSize(), controlador_gps = controlador_gps)
                }
            }
        }
    }

        val contexto = applicationContext
        Configuration.getInstance().load(contexto, PreferenceManager.getDefaultSharedPreferences(contexto)) //download/import the osmDroid so it works, not android
        Configuration.getInstance().userAgentValue = "Mapa"
    }
    private fun consultar_permisos_gps(): Boolean{
        var permiso_fine_location = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        var permiso_coarse_location = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        return permiso_fine_location || permiso_coarse_location
    }

    companion object{
        const val permisos_totales_gps = 123 //request code #
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    Perfil_UsuarioTheme {
//        Surface(
//            modifier = Modifier.fillMaxSize(),
//            color = MaterialTheme.colorScheme.background
//        ) {
//            //MapaPokemones(controlador_gps)
//            MenuHome(modifier = Modifier.fillMaxSize(), gps_controlador)
//        }
//    }
//}