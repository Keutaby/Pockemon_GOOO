package com.example.perfil_usuario.PantallasNavegacion

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.Perfil_Usuario.ControladoresMapa.GPSControlador
import com.example.Perfil_Usuario.PantallasMenu.MapaPokemones
import com.example.Perfil_Usuario.PantallasNavegacion.PantallaMenuPrincipal

@Composable
fun PantallaNavegadora(modifier: Modifier, controlador_gps: GPSControlador){
    val control_navegacion = rememberNavController()

    NavHost(navController = control_navegacion, startDestination = PantallaMenuPrincipal.Home.ruta){
        composable(PantallaMenuPrincipal.Home.ruta) {
            MapaPokemones(controlador_gps = controlador_gps)
//            (modifier, mapa_inicial, navegar_siguiente = {
//                control_navegacion.navigate(PantallaNavegadora())
//            })
        }

        composable(PantallaMenuPrincipal.Perfil.ruta) {
            Text(".......")
        }
    }
}