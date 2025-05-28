package com.example.perfil_usuario.PantallasMenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class SimpleBattlePokemon(
    val name: String,
    val maxHp: Int,
    var currentHp: Int,
    val attackPower: Int
)

@Composable
fun BattleScreen() {
    //Player pokemon
    var playerPokemon by remember {
        mutableStateOf(
            SimpleBattlePokemon(
                name = "Pikachu",
                maxHp = 100,
                currentHp = 100,
                attackPower = 20
            )
        )
    }

    //Opponents pokemon
    var opponentPokemon by remember {
        mutableStateOf(
            SimpleBattlePokemon(
                name = "Charmander",
                maxHp = 90,
                currentHp = 90,
                attackPower = 15
            )
        )
    }

    @Composable
    fun PokemonDisplay(pokemon: SimpleBattlePokemon, isOpponent: Boolean) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray) // Semi-transparent light grey background
                .padding(16.dp),
            horizontalAlignment = if (isOpponent) Alignment.Start else Alignment.End // Align opponent left, player right
        ) {
            //Pokemon Name
            Text(
                text = pokemon.name,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))

            //HP Display
            Text(
                text = "HP: ${pokemon.currentHp}",
                color = if (pokemon.currentHp > pokemon.maxHp / 4) Color.Green else Color.Red,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
