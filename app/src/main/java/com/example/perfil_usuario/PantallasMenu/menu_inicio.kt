package com.example.perfil_usuario.PantallasMenu

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.perfil_usuario.inicio.LoginScreen
import androidx.navigation.compose.composable
import com.example.Perfil_Usuario.PantallasNavegacion.PantallaMenuPrincipal
import com.example.perfil_usuario.inicio.SplashScreen
import com.example.perfil_usuario.inicio.PantallaSeleccionPokemon
import com.example.perfil_usuario.inicio.RegisterScreen
import com.example.perfil_usuario.inicio.PantallaPermisos

@Composable
fun PokemonGoApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = PantallaMenuPrincipal.SplashScreen.ruta
    ) {
        composable(PantallaMenuPrincipal.SplashScreen.ruta) {
            SplashScreen(navController)
        }
        composable(PantallaMenuPrincipal.Login.ruta) {
            LoginScreen(navController)
        }
        composable(PantallaMenuPrincipal.SeleccionPokemon.ruta) {
            PantallaSeleccionPokemon(navController)
        }
        composable(PantallaMenuPrincipal.Register.ruta) {
            RegisterScreen(navController)
        }
        composable(
            route = PantallaMenuPrincipal.Permisos.ruta,
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Usuario"
            PantallaPermisos(
                nombreUsuario = username,
                onPermisosConcedidos = {
                    navController.navigate(PantallaMenuPrincipal.SeleccionPokemon.ruta) {
                        popUpTo(PantallaMenuPrincipal.Permisos.ruta) { inclusive = true }
                    }
                },
                onPermisosDenegados = {
                    // Aquí tu lógica cuando se deniegan permisos
                }
            )
        }
    }
}


