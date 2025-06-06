package com.example.perfil_usuario.PantallasNavegacion

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.example.Perfil_Usuario.ControladoresMapa.GPSControlador
import com.example.Perfil_Usuario.PantallasMenu.MapaPokemones
import com.example.Perfil_Usuario.PantallasNavegacion.PantallaMenuPrincipal
import com.example.perfil_usuario.API_Batalla.InstanceRetrofitPoke

import com.example.perfil_usuario.FotoUsuarioPerfil
import com.example.perfil_usuario.PantallasMenu.BattleScreen

import com.example.perfil_usuario.PantallasMenu.PantallaCamara
import com.example.perfil_usuario.PantallasMenu.PantallaPerfil
import com.example.perfil_usuario.PantallasMenu.PokedexScreen
import com.example.perfil_usuario.PantallasMenu.PantallaConfiguracion
import com.example.perfil_usuario.PantallasMenu.PantallaGaleria
import com.example.perfil_usuario.inicio.LoginScreen
import com.example.perfil_usuario.inicio.PantallaSeleccionPokemon
import com.example.perfil_usuario.inicio.RegisterScreen
import com.example.perfil_usuario.inicio.SplashScreen
import com.example.perfil_usuario.ui.theme.Perfil_UsuarioTheme

@Composable
fun PantallaNavegadora(modifier: Modifier, controlador_gps: GPSControlador,

){
    val control_navegacion = rememberNavController()

    val apiClientInstance = InstanceRetrofitPoke.consumir_servicio

    NavHost(navController = control_navegacion, startDestination = PantallaMenuPrincipal.SplashScreen.ruta){
        composable(PantallaMenuPrincipal.SplashScreen.ruta) {
            SplashScreen(navController = control_navegacion)
        }

        composable(PantallaMenuPrincipal.Login.ruta) {
            LoginScreen(navController = control_navegacion)
        }

        composable(PantallaMenuPrincipal.SeleccionPokemon.ruta) {
            PantallaSeleccionPokemon(navController = control_navegacion)
        }

        composable(PantallaMenuPrincipal.Register.ruta) {
            RegisterScreen(navController = control_navegacion)
        }

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

                PantallaCamara(navController = control_navegacion,)

        }
        composable(PantallaMenuPrincipal.Galeria.ruta){
            Text("Mostrando Galeria")
            PantallaGaleria(navController = control_navegacion)
        }

        composable(PantallaMenuPrincipal.Batalla.ruta){
            Text("Mostrando Batalla")
            BattleScreen()
        }

        /*composable(PantallaMenuPrincipal.Configuracion.ruta){
            Text("CONFIGURACION")
            PantallaConfiguracion(navController = control_navegacion)
        }*/

        composable(PantallaMenuPrincipal.Fotou.ruta) {
            FotoUsuarioPerfil(navController = control_navegacion)
        }


    }
}