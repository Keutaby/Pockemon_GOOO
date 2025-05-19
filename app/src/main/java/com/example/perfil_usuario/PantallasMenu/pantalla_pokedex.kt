package com.example.perfil_usuario.PantallasMenu
/*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import com.example.perfil_usuario.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import com.google.gson.annotations.SerializedName
import coil.compose.AsyncImage
import kotlin.random.Random

// 1. Data Classes for API Response
data class PokemonListResponse(
    @SerializedName("results") val results: List<PokemonEntry>
)

data class PokemonEntry(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)

data class PokemonDetailResponse(
    @SerializedName("sprites") val sprites: PokemonSprites,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("types") val types: List<PokemonTypeEntry>,
    @SerializedName("attack") val attack: List<PokemonAttackEntry>, // Changed to a List
    @SerializedName("defense") val defense: Int
)

data class PokemonSprites(
    @SerializedName("front_default") val frontDefault: String?
)

data class PokemonTypeEntry(
    @SerializedName("type") val type: PokemonType
)

data class PokemonType(
    @SerializedName("name") val name: String
)

data class PokemonAttackEntry( // Added data class for attack
    @SerializedName("base_stat") val baseStat: Int,
    @SerializedName("effort") val effort: Int,
    @SerializedName("stat") val stat: PokemonStat
)

data class PokemonStat(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)

// 2. Define the API Interface using Retrofit
interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemonList(): PokemonListResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(@Path("id") id: Int): PokemonDetailResponse
}

// 3. Create a Retrofit Instance
object ApiClient {
    private const val BASE_URL = "https://pokeapi.co/api/v2/"

    val instance: PokemonApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(PokemonApi::class.java)
    }
}

// 4. Composable for the Battle Screen
@Composable
fun BattleScreen() {
    // 5. State Management
    // Use rememberSaveable to survive configuration changes
    val playerPokemon = rememberSaveable { mutableStateOf<PokemonDetailResponse?>(null) }
    val opponentPokemon = rememberSaveable { mutableStateOf<PokemonDetailResponse?>(null) }
    val battleLog = rememberSaveable { mutableStateOf(mutableListOf<String>()) }
    val isPlayerTurn = rememberSaveable { mutableStateOf(true) }
    val isBattleOver = rememberSaveable { mutableStateOf(false) }
    val winner = rememberSaveable { mutableStateOf("") }
    val showDialog = rememberSaveable { mutableStateOf(false) }
    val moveOptions = remember { mutableStateOf(listOf<Pair<String, Int>>()) } //hold move options
    val coroutineScope = rememberCoroutineScope()
    val playerPokemonId = 1 // Example: Bulbasaur
    val opponentPokemonId = 25 // Example: Pikachu

    // 6. Fetch Pokemon Data on Launch
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                // Fetch player and opponent Pokemon details
                playerPokemon.value = ApiClient.instance.getPokemonDetails(playerPokemonId)
                opponentPokemon.value = ApiClient.instance.getPokemonDetails(opponentPokemonId)
                // Initialize move options.  For simplicity, use first 4 moves.
                if (playerPokemon.value != null) {
                    moveOptions.value = listOf(
                        "Tackle" to 40,
                        "Quick Attack" to 30,
                        "Vine Whip" to 45,
                        "Razor Leaf" to 55
                    )
                }

            } catch (e: Exception) {
                // Handle error (e.g., show a message to the user)
                println("Error fetching Pokemon details: $e")
                battleLog.value.add("Error: Failed to load Pokemon data.") //add error to battle log
            }
        }
    }

    // 7. Function to handle attacks
    val performAttack = remember {
        { moveName: String ->
            if (isBattleOver) return@remember // Prevent attacks after battle over

            val moveDamage = moveOptions.value.find { it.first == moveName }?.second ?: 0
            if (isPlayerTurn && playerPokemon.value != null && opponentPokemon.value != null) {
                // Player attacks opponent
                val damage =
                    calculateDamage(playerPokemon.value!!.attack[0].baseStat, opponentPokemon.value!!.defense, moveDamage) // Access attack
                opponentPokemon.value =
                    opponentPokemon.value!!.copy(hp = (opponentPokemon.value!!.hp - damage).coerceAtLeast(0))
                battleLog.value.add("Player's ${playerPokemon.value!!.name} used $moveName and dealt $damage damage!")

                if (opponentPokemon.value!!.hp <= 0) {
                    winner = "Player"
                    isBattleOver = true
                    showDialog = true
                    battleLog.value.add("Opponent's ${opponentPokemon.value!!.name} fainted!")
                    return@{ }
                }
            } else if (!isPlayerTurn && playerPokemon.value != null && opponentPokemon.value != null) {
                // Opponent attacks player (Simple AI)
                val opponentMove = moveOptions.value[Random.nextInt(moveOptions.value.size)].first
                val damage =
                    calculateDamage(opponentPokemon.value!!.attack[0].baseStat, playerPokemon.value!!.defense, moveDamage) //access attack
                playerPokemon.value =
                    playerPokemon.value!!.copy(hp = (playerPokemon.value!!.hp - damage).coerceAtLeast(0))
                battleLog.value.add("Opponent's ${opponentPokemon.value!!.name} used $opponentMove and dealt $damage damage!")
                if (playerPokemon.value!!.hp <= 0) {
                    winner = "Opponent"
                    isBattleOver = true;
                    showDialog = true
                    battleLog.value.add("Player's ${playerPokemon.value!!.name} fainted!")
                    return@{}
                }
            }
            isPlayerTurn = !isPlayerTurn
        }
    }

    // 8. Function to calculate damage
    fun calculateDamage(attack: Int, defense: Int, moveDamage: Int): Int {
        // Very basic damage calculation
        val baseDamage = (attack * moveDamage) / (defense + 10)
        return (baseDamage.coerceAtLeast(10)).toInt() // Ensure a minimum of 10 damage
    }

    // 9. Function to reset the battle
    val resetBattle = remember {
        {
            coroutineScope.launch { //reset battle.
                playerPokemon.value = ApiClient.instance.getPokemonDetails(playerPokemonId)
                opponentPokemon.value = ApiClient.instance.getPokemonDetails(opponentPokemonId)
                battleLog.value = mutableListOf()
                isPlayerTurn = true
                isBattleOver = false
                winner = ""
                showDialog = false
            }
        }
    }

    // 10. UI Structure
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Battle Fight", color = Color.White) },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Red),
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            if (playerPokemon?.value == null || opponentPokemon?.value == null) {
                // Show loading or error message
                if (battleLog.value.isEmpty()) {
                    Text(text = "Loading Pokemon...", color = Color.Black)
                } else {
                    for (log in battleLog.value) {
                        Text(text = log, color = Color.Red)
                    }
                }
            } else {
                // Pokemon Display
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    // Player Pokemon
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        AsyncImage(
                            model = playerPokemon.value?.sprites?.frontDefault,
                            contentDescription = playerPokemon.value?.name,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Fit,

                            )
                        Text(
                            text = playerPokemon.value?.name ?: "N/A",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "HP: ${playerPokemon.value?.hp ?: "N/A"}",
                            ///style = MaterialTheme.typography.body1,
                            color = Color.DarkGray
                        )
                        Text(
                            text = "Type: ${playerPokemon.value?.types?.joinToString(", ") ?: "N/A"}",
                            //style = MaterialTheme.typography.body2,
                            color = Color.DarkGray
                        )
                    }
                    // Opponent Pokemon
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        AsynImage(
                            model = opponentPokemon.value?.sprites?.frontDefault,
                            contentDescription = opponentPokemon.value?.name,
                            modifier = Modifier
                                .size(150.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Fit
                        )
                        Text(
                            text = opponentPokemon.value?.name ?: "N/A",
                            style = MaterialTheme.typography.h6,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "HP: ${opponentPokemon.value?.hp ?: "N/A"}",
                            style = MaterialTheme.typography.body1,
                            color = Color.DarkGray
                        )
                        Text(
                            text = "Type: ${opponentPokemon.value?.types?.joinToString(", ") ?: "N/A"}",
                            style = MaterialTheme.typography.body2,
                            color = Color.DarkGray
                        )
                    }
                }

                // Battle Log
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(8.dp),
                    horizontalAlignment = Alignment.Start,

                    ) {
                    Text(
                        text = "Battle Log:",
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        textAlign = TextAlign.Start
                    )
                    for (log in battleLog.value) {
                        Text(
                            text = log,
                            style = MaterialTheme.typography.body2,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Start
                        )
                    }
                }

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    moveOptions.value.forEach { (moveName, _) ->
                        Button(
                            onClick = { performAttack(moveName) },
                            enabled = isPlayerTurn && !isBattleOver,
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text(moveName, color = Color.White)
                        }
                    }
                }
                if (showDialog) {
                    AlertDialog(
                        onDismissRequest = { /* */ },
                        title = { Text(text = "Battle Over", color = Color.Black) },
                        text = {
                            Text(
                                text = "The winner is $winner!",
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        },
                        confirmButton = {
                            Button(onClick = { resetBattle() }) {
                                Text("OK", color = Color.White)
                            }
                        },

                        )
                }
            }
        }
    }
}
 */
