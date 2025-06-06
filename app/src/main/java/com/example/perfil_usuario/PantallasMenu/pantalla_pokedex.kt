package com.example.perfil_usuario.PantallasMenu

import android.os.Bundle
import android.util.Log
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
    var pokemonDetailsList by remember { mutableStateOf<List<PokemonDetailResponse>?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(true) }

    val coroutineScope = rememberCoroutineScope()
    val contexto = LocalContext.current

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val listResponse = apiClient.getPokemonList(offset = 0, limit = 100)
                val detailedPokemon = mutableListOf<PokemonDetailResponse>()

                for (entry in listResponse.results) {
                    //Extract from https://pokeapi.co/api/v2/pokemon/1/
                    val id = entry.url.split("/").dropLast(1).last().toInt()
                    val detail = apiClient.getPokemonDetails(id)

                    Log.d("PokedexDebug", "Pokemon: ${detail.name}, Sprite URL: ${detail.sprites.front_default}")

                    detailedPokemon.add(detail)
                }

                pokemonDetailsList = detailedPokemon
                loading = false

            } catch (e: IOException) {
                errorMessage = "Failed to load Pokemon: ${e.message}"
                loading = false
            } catch (e: Exception) {
                errorMessage = "An unexpected error occurred: ${e.message}"
                loading = false
            }
        }
    }

    //UI for the Pokedex screen
    Column(modifier = Modifier.fillMaxSize()) {
        if (loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color.Blue)
            }
        } else if (errorMessage != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(errorMessage!!, color = Color.Red)
            }
        } else if (pokemonDetailsList != null) {
            LazyColumn(modifier = Modifier.padding(16.dp)) {

                items(pokemonDetailsList!!) { pokemonDetail ->
                    Box(
                        modifier = Modifier
                            .background(color = Color.Red, shape = RoundedCornerShape(8.dp))
                            .fillMaxWidth()
                            .padding(15.dp)
                            .height(200.dp)
                            .width(100.dp)
                            .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Log.v("URL_IMAGEN", "${pokemonDetail.sprites.front_default}")
                            AsyncImage(
                                model = ImageRequest.Builder(contexto)
                                    .data(pokemonDetail.sprites.front_default) //image URL
                                    .crossfade(true)
                                    .build(),
                                contentDescription = "${pokemonDetail.name} sprite",
                                modifier = Modifier
                                    .size(150.dp)
                                    .padding(10.dp)
                                    .border(2.dp, Color.Black),
                                contentScale = androidx.compose.ui.layout.ContentScale.Fit
                            )
                            Spacer(modifier = Modifier.height(8.dp))

                            //Pokemon name
                            Text(
                                text = "Name: ${pokemonDetail.name.replaceFirstChar { 
                                    if (it.isLowerCase()) it.titlecase() 
                                    else it.toString() }}",
                                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            //ID
                            Text(
                                text = "ID: ${pokemonDetail.id}",
                                style = TextStyle(fontSize = 16.sp, color = Color.Gray),
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No Pokemon found.")
            }
        }
    }
}