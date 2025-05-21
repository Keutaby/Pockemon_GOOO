package com.example.perfil_usuario.PantallasNavegacion

import android.net.http.SslCertificate.saveState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.Perfil_Usuario.ControladoresMapa.GPSControlador
import com.example.Perfil_Usuario.PantallasMenu.MapaPokemones
import com.example.Perfil_Usuario.PantallasNavegacion.BotonesInferioresNavegacion
import com.example.Perfil_Usuario.PantallasNavegacion.PantallaMenuPrincipal
import com.example.perfil_usuario.PantallasMenu.PantallaPerfil
import com.example.perfil_usuario.PantallasMenu.PokedexScreen


@Composable
fun MenuHome(modifier: Modifier, controlador_gps: GPSControlador){

    var pantalla_actual: Int by remember {
        mutableStateOf(0)
    }
    val control_nav = rememberNavController()

    Scaffold(modifier = Modifier, bottomBar = {
        NavigationBar{
            BotonesInferioresNavegacion().botones_de_navegacion().forEachIndexed{indice, boton_de_navegacion ->
                NavigationBarItem(
                    selected = indice == pantalla_actual,
                    label = {
                        Text("${boton_de_navegacion.etiqueta}")
                    },
                    icon = {
                        Icon(boton_de_navegacion.icono, contentDescription = "${boton_de_navegacion.etiqueta}")
                    },
                    onClick = {
                        pantalla_actual = indice

                        control_nav.navigate(boton_de_navegacion.ruta){
                            popUpTo(control_nav.graph.startDestinationId){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }) { innerPadding ->
        NavHost(navController = control_nav,
            startDestination = PantallaMenuPrincipal.Home.ruta,
            modifier = Modifier.padding(innerPadding)) {

            composable(PantallaMenuPrincipal.Home.ruta) {
                PantallaNavegadora(modifier = Modifier.fillMaxSize(), controlador_gps = controlador_gps)
            }

            composable(PantallaMenuPrincipal.Perfil.ruta){
                PantallaPerfil(name = "",
                    level = 5,
                    team = "",
                    pokedexCount = 10)
            }

            composable(PantallaMenuPrincipal.Pokedex.ruta){
                PokedexScreen()
            }
        }
    }
}