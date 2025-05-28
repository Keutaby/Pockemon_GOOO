package com.example.perfil_usuario.usuario_app_joshua

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

val blancoCafesoso = Color(0xFFF8F4EC)

data class PokemonItem(val nombre: String, val imagenUrl: String)

@Composable
fun PantallaEliminarPokemon(navController: NavController) {
    val listaPokemon = remember {
        mutableStateListOf(
            PokemonItem("Pikachu", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png"),
            PokemonItem("Charmander", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png"),
            PokemonItem("Bulbasaur", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png"),
            PokemonItem("Squirtle", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/7.png"),
            PokemonItem("Eevee", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/133.png"),
            PokemonItem("Snorlax", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/143.png"),
            PokemonItem("Gengar", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/94.png"),
            PokemonItem("Jigglypuff", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/39.png")
        )
    }
    val seleccionados = remember { mutableStateListOf<PokemonItem>() }

    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(blancoCafesoso)
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        listaPokemon.removeAll(seleccionados)
                        seleccionados.clear()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Eliminar Pokémon seleccionados",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        },
        containerColor = blancoCafesoso
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Eliminar Pokémon",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(listaPokemon) { pokemon ->
                    val estaSeleccionado = seleccionados.contains(pokemon)

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (estaSeleccionado) Color.Red.copy(alpha = 0.3f) else Color.White)
                            .clickable {
                                if (estaSeleccionado) {
                                    seleccionados.remove(pokemon)
                                } else {
                                    seleccionados.add(pokemon)
                                }
                            }
                            .padding(6.dp)
                    ) {
                        AsyncImage(
                            model = pokemon.imagenUrl,
                            contentDescription = pokemon.nombre,
                            modifier = Modifier.size(64.dp),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
        }
    }
}
