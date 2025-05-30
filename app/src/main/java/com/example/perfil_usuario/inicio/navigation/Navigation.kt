package com.example.perfil_usuario.inicio.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Login : Screen("login")
    object Register : Screen("register")
    object Permisos : Screen("permisos/{username}") {
        fun createRoute(username: String) = "permisos/$username"
    }
    object SeleccionPokemon : Screen("seleccion_pokemon")

    object Mapa : Screen("map_screen")
}