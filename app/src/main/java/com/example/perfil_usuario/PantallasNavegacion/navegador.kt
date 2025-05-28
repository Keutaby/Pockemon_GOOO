package com.example.perfil_usuario.PantallasNavegacion

import android.util.Log
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.Perfil_Usuario.ControladoresMapa.GPSControlador
import com.example.Perfil_Usuario.PantallasMenu.MapaPokemones
import com.example.Perfil_Usuario.PantallasNavegacion.PantallaMenuPrincipal
import com.example.perfil_usuario.API_Batalla.InstanceRetrofitPoke

import com.example.perfil_usuario.PantallasMenu.PantallaCamara
import com.example.perfil_usuario.PantallasMenu.PantallaPerfil
import com.example.perfil_usuario.PantallasMenu.PokedexScreen
import com.example.perfil_usuario.PantallasMenu.PantallaConfiguracion
import com.example.perfil_usuario.ui.theme.Perfil_UsuarioTheme

@Composable
fun PantallaNavegadora(modifier: Modifier, controlador_gps: GPSControlador){
    val control_navegacion = rememberNavController()

    val apiClientInstance = InstanceRetrofitPoke.consumir_servicio

    NavHost(navController = control_navegacion, startDestination = PantallaMenuPrincipal.Home.ruta){
        composable(PantallaMenuPrincipal.Home.ruta) {
            MapaPokemones(controlador_gps = controlador_gps)
        }

        composable(PantallaMenuPrincipal.Perfil.ruta) {
            Text("Mostrando Perfil")
            PantallaPerfil(
            name = "",
            level = 5,
            team = "",
            pokedexCount = 10,
                navController = control_navegacion)
        }

        composable(PantallaMenuPrincipal.Pokedex.ruta){
            Log.d("PokedexNav", "Navigated to Pokedex screen!")
            Text("Mostrando Pokedex")
            PokedexScreen(apiClient = apiClientInstance)
        }

        composable(PantallaMenuPrincipal.Camara.ruta){
            Text("Mostrando Camara")

                Surface {
                    PantallaCamara()
                }

        }

        composable(PantallaMenuPrincipal.Batalla.ruta){
            Text("Mostrando Batalla")
        }

        composable(PantallaMenuPrincipal.Configuracion.ruta){
            Text("CONFIGURACION")
            PantallaConfiguracion(navController = control_navegacion)
        }


    }
}