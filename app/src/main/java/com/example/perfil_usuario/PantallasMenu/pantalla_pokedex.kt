package com.example.perfil_usuario.PantallasMenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.perfil_usuario.API_Batalla.InstanceRetrofitPoke
import com.example.perfil_usuario.API_Batalla.POKE.PokemonEntry
import com.example.perfil_usuario.API_Batalla.PokemonApi
import java.io.IOException

class PokedexActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create an instance of ApiClient
        val apiClient = InstanceRetrofitPoke

        setContent {
            // Use your theme if available, or a basic Surface
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
                //list of Pokemon using the apiClient
                val response = apiClient.getPokemonList()
                pokemonList = response.results
                loading = false //loading to false after successful fetch
            } catch (e: IOException) {
                errorMessage = "Failed to load Pokemon: ${e.message}"
                loading = false
            }
        }
    }

    // UI for the Pokedex screen
    Column(modifier = Modifier.fillMaxSize()) {
        //TopAppBar(Text("Pokedex"), Color.Red)

        if (loading) {
            // Show a loading indicator while fetching data
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Red)
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
                    Text(
                        text = "Name: ${pokemon.name}",
                        modifier = Modifier.padding(8.dp),
                        style = TextStyle(fontSize = 18.sp)
                    )
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