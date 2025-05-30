package com.example.perfil_usuario.PantallasMenu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class SimpleBattlePokemon(
    val name: String,
    val maxHp: Int, // The maximum HP a Pokémon can have
    var currentHp: Int, // The current HP, which will decrease during the battle
    val attackPower: Int, // How much damage this Pokémon can do
    val imageUrl: String // The URL to the Pokémon's image
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
                attackPower = 20,
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png" // Pikachu's image URL
            )
        )
    }

    //Opponent pokemon
    var opponentPokemon by remember {
        mutableStateOf(
            SimpleBattlePokemon(
                name = "Charmander",
                maxHp = 90,
                currentHp = 90,
                attackPower = 15,
                imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/4.png" // Charmander's image URL
            )
        )
    }

    // Message displayed in the battle log area
    var battleMessage by remember { mutableStateOf("A wild Charmander appeared!") }

    // Boolean to check if the battle has ended
    var battleOver by remember { mutableStateOf(false) }

    // CoroutineScope is needed to run suspend functions (like 'delay')
    val coroutineScope = rememberCoroutineScope()

    //Battle Logic

    //opponent attack
    val performOpponentAttack: () -> Unit = performOpponentAttack@{
        if (battleOver) return@performOpponentAttack

        coroutineScope.launch { //attack sequence
            val damage = opponentPokemon.attackPower //opponent damage
            playerPokemon.currentHp -= damage //reduce player hp

            //player hp
            playerPokemon = playerPokemon.copy(currentHp = playerPokemon.currentHp.coerceAtLeast(0))

            battleMessage = "${opponentPokemon.name} attacked! ${playerPokemon.name} took $damage damage."

            //if player fainted
            if (playerPokemon.currentHp <= 0) {
                battleMessage = "${playerPokemon.name} fainted! You lose!"
                battleOver = true
            }
        }
    }

    //pokemon attack
    val performPlayerAttack: () -> Unit = performPlayerAttack@{
        //only attack if battle continues
        if (battleOver) return@performPlayerAttack

        coroutineScope.launch {
            val damage = playerPokemon.attackPower //Player damage
            opponentPokemon.currentHp -= damage //reduce HP

            //Update opponents HP state
            opponentPokemon = opponentPokemon.copy(currentHp = opponentPokemon.currentHp.coerceAtLeast(0))

            battleMessage = "${playerPokemon.name} attacked! ${opponentPokemon.name} took $damage damage." // Update message

            // Check if opponent fainted
            if (opponentPokemon.currentHp <= 0) {
                battleMessage = "${opponentPokemon.name} fainted! You win!"
                battleOver = true // End the battle
            } else {
                // If opponent is still standing, it's their turn after a short delay
                delay(1500) // Wait for 1.5 seconds
                performOpponentAttack() // Opponent attacks
            }
        }
    }

    //reset battle
    val resetBattle: () -> Unit = {
        playerPokemon = playerPokemon.copy(currentHp = playerPokemon.maxHp)
        opponentPokemon = opponentPokemon.copy(currentHp = opponentPokemon.maxHp)
        battleMessage = "A new battle begins!"
        battleOver = false
    }

    //UI Layout
    Column(
        modifier = Modifier
            .fillMaxSize() //fill entire screen
            .background(Color.DarkGray)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        //opponent pokemon
        PokemonDisplay(pokemon = opponentPokemon, isOpponent = true)

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = battleMessage,
            color = Color.Yellow,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                .padding(8.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        //battle actions
        if (!battleOver) {
            Button(
                onClick = performPlayerAttack,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text("Attack!", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        } else { //if battle is over
            Button(
                onClick = resetBattle,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                modifier = Modifier.fillMaxWidth(0.6f)
            ) {
                Text("Restart Battle", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        //player pokemon display
        PokemonDisplay(pokemon = playerPokemon, isOpponent = true)
    }
}

@Composable
fun PokemonDisplay(pokemon: SimpleBattlePokemon, isOpponent: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray.copy(alpha = 0.2f), shape = MaterialTheme.shapes.medium)
            .padding(16.dp),
        horizontalAlignment = if (isOpponent) Alignment.Start else Alignment.End //opponent left, player right
    ) {
        //Pokemon name
        Text(
            text = pokemon.name,
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        //Pokemon image
        AsyncImage(
            model = pokemon.imageUrl,
            contentDescription = "${pokemon.name} sprite",
            modifier = Modifier.size(160.dp),
            contentScale = androidx.compose.ui.layout.ContentScale.Fit //scale to fit
        )
        Spacer(modifier = Modifier.height(8.dp))

        //HP Display
        Text(
            text = "HP: ${pokemon.currentHp}",
            color = if (pokemon.currentHp > pokemon.maxHp / 4) Color.Green else Color.Red, //Green if healthy, Red if low HP
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}