package com.example.perfil_usuario.inicio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.perfil_usuario.inicio.navigation.Screen
import com.example.Perfil_Usuario.ui.screens.*
import com.example.Perfil_Usuario.ui.navigation.Screen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                PokemonGoApp()
            }
        }
    }
}

@Composable
fun PokemonGoApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(navController)
        }
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(Screen.SeleccionPokemon.route) {
            PantallaSeleccionPokemon(navController)
        }
        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }
        composable(
            route = Screen.Permisos.route,
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Usuario"
            PantallaPermisos(
                nombreUsuario = username,
                onPermisosConcedidos = {
                    navController.navigate(Screen.SeleccionPokemon.route) {
                        popUpTo(Screen.Permisos.route) { inclusive = true }
                    }
                },
                onPermisosDenegados = {
                    // Aquí tu lógica cuando se deniegan permisos
                }
            )
        }
    }
}