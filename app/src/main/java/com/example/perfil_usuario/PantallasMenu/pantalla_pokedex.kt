package com.example.perfil_usuario.PantallasMenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.perfil_usuario.API_Batalla.InstanceRetrofitPoke
import com.example.perfil_usuario.API_Batalla.POKE.PokemonDetailResponse
import com.example.perfil_usuario.API_Batalla.POKE.PokemonEntry
import com.example.perfil_usuario.API_Batalla.PokemonApi
import java.io.IOException


class PokedexActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiClient = InstanceRetrofitPoke.consumir_servicio

        setContent {
            Surface(color = Color.White) {
                PokedexScreen(apiClient = apiClient)
            }
        }
    }
}

@Composable
fun PokedexScreen(apiClient: PokemonApi) {
    //list of Pokemon
    var pokemonList by remember { mutableStateOf<List<PokemonEntry>?>(null) }
    //any error message
    var errorMessage by remember { mutableStateOf<String?>(null) }
    //indicate loading
    var loading by remember { mutableStateOf(true) }

    //launch asynchronous operations
    val coroutineScope = rememberCoroutineScope()

    //Pokemon data
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val response = apiClient.getPokemonList(offset = 0, limit = 100)
                pokemonList = response.results
                loading = false // Set loading to false after successful fetch
            } catch (e: IOException) {
                errorMessage = "Failed to load Pokemon: ${e.message}"
                loading = false // Set loading to false even on error
            } catch (e: Exception) {
                errorMessage = "An unexpected error occurred: ${e.message}"
                loading = false
            }
        }
    }
    val contexto = LocalContext.current
    // UI for the Pokedex screen
    Column(modifier = Modifier.fillMaxSize()) {
        if (loading) {
            // Show a loading indicator while fetching data
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Blue)
            }
        } else if (errorMessage != null) {
            // Show an error message if fetching data failed
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error Message", color = Color.Red)
            }
        } else if (pokemonList != null) {
            // Display the list of Pokemon
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(pokemonList!!) { pokemon ->  // Use non-null assertion here, since pokemonList is checked for null
<<<<<<< HEAD

                    Box(modifier = Modifier.background(color = Color.Yellow)
                       .fillMaxSize()
                       .padding(15.dp)
                       .size(50.dp)
                       .border( width = 2.dp, color = Color.Black),

                       contentAlignment = Alignment.Center,

                   ){
                       AsyncImage(
                           model = ImageRequest.Builder(contexto)
                               .data("https://pokeapi.co/api/v2/pokemon/13/")
                               .crossfade(true)
                               .build(),
                           contentDescription = "Pokémon",
                           modifier = Modifier.size(150.dp)
                       )
=======
                    //Pokemon Name
>>>>>>> 0f85904d42ded53debe3efe8290415e876b0252a
                    Text(
                        text = "Name: ${pokemon.name}",
                        modifier = Modifier.padding(10.dp),
                        style = TextStyle(fontSize = 20.sp)
                    )

                   }
                }
            }
        } else {
            // Show a message if the list is empty
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No Pokemon found.")
            }
        }
    }
}