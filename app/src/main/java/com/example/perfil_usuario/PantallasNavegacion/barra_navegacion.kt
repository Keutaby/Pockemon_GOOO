package com.example.Perfil_Usuario.PantallasNavegacion

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

sealed class PantallaMenuPrincipal(val ruta: String){
    object Home: PantallaMenuPrincipal("pantalla_inicial")
    object StarWars: PantallaMenuPrincipal("pantalla_star_wars")
    object Perfil: PantallaMenuPrincipal("pantalla_perfil")
}

data class BotonesInferioresNavegacion(
    val etiqueta: String = "",
    val icono: ImageVector = Icons.Filled.Call,
    val ruta: String = ""
){
    fun botones_de_navegacion(): List<BotonesInferioresNavegacion>{
        return listOf(
            BotonesInferioresNavegacion(
                etiqueta = "Mapa",
                icono = Icons.Filled.Home,
                ruta = PantallaMenuPrincipal.Home.ruta
            ),

            BotonesInferioresNavegacion(
                etiqueta = "Pokedex",
                icono = Icons.Filled.Info,
                ruta = PantallaMenuPrincipal.StarWars.ruta
            ),

            BotonesInferioresNavegacion(
                etiqueta = "Batallas",
                icono = Icons.Filled.LocationOn,
                ruta = PantallaMenuPrincipal.StarWars.ruta
            ),

            BotonesInferioresNavegacion(
                etiqueta = "Perfil",
                icono = Icons.Filled.AccountCircle,
                ruta = PantallaMenuPrincipal.Perfil.ruta)
        )
    }
}