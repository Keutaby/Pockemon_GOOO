package com.example.perfil_usuario.API_Batalla

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.io.IOException
import org.json.JSONObject
import org.json.JSONArray

class ApiClient : PokemonApi {

    //URL
    private val BASE_URL = "https://pokeapi.co/api/v2/"

    //API call
    private suspend fun getApiResponse(url: String): String {
        return withContext(Dispatchers.IO) { // Use IO dispatcher for network operations
            val connection = URL(url).openConnection() as HttpURLConnection // Open connection
            try {
                connection.requestMethod = "GET" // Set request method
                connection.connect() // Connect to the server
                val responseCode = connection.responseCode // Get response code
                if (responseCode >= 200 && responseCode < 300) { // Check for success
                    val reader = BufferedReader(InputStreamReader(connection.inputStream)) // Read the response
                    return@withContext reader.readText() // Return the response text
                } else {
                    throw IOException("Failed to fetch data from $url: $responseCode - ${connection.responseMessage}") // Handle errors
                }
            } finally {
                connection.disconnect() // Disconnect
            }
        }
    }

    //JSON
    private fun PokemonListResponse(json: String): PokemonListResponse {
        val jsonObject = JSONObject(json) // Create JSONObject
        val resultsJsonArray = jsonObject.getJSONArray("results") // Get results array
        val results = mutableListOf<PokemonEntry>() // Create list to store results
        for (i in 0 until resultsJsonArray.length()) { // Loop through results array
            val resultJson = resultsJsonArray.getJSONObject(i) // Get each result object
            val name = resultJson.getString("name") // Extract name
            val url = resultJson.getString("url") // Extract URL
            results.add(PokemonEntry(name, url)) // Add to the list
        }
        return PokemonListResponse(results) // Return the parsed response
    }

    //JSON
    private fun PokemonDetailResponse(json: String): PokemonDetailResponse {
        val jsonObject = JSONObject(json) //JSONObject

        val id = jsonObject.getInt("id")
        val name = jsonObject.getString("name")

        // Parse Sprites
        val spritesJson = jsonObject.getJSONObject("sprites")
        val frontDefault = if (spritesJson.has("front_default") && !spritesJson.isNull("front_default")) {
            spritesJson.getString("front_default")
        } else {
            null
        }
        val sprites = PokemonSprites(frontDefault)

        //Types
        val typesJsonArray = jsonObject.getJSONArray("types")
        val types = mutableListOf<PokemonTypeEntry>()
        for (i in 0 until typesJsonArray.length()) {
            val typeJson = typesJsonArray.getJSONObject(i).getJSONObject("type")
            val typeName = typeJson.getString("name")
            types.add(PokemonTypeEntry(PokemonType(typeName)))
        }

        //Stats
        val statsJsonArray = jsonObject.getJSONArray("stats")
        val stats = mutableListOf<PokemonStatEntry>()
        for (i in 0 until statsJsonArray.length()) {
            val statJson = statsJsonArray.getJSONObject(i)
            val baseStat = statJson.getInt("base_stat")
            val stat = statJson.getJSONObject("stat")
            val statName = stat.getString("name")
            val statUrl = stat.getString("url")
            stats.add(PokemonStatEntry(baseStat, PokemonStat(statName, statUrl)))
        }

        //Moves
        val movesJsonArray = jsonObject.getJSONArray("moves")
        val moves = mutableListOf<PokemonMoveEntry>()
        for (i in 0 until movesJsonArray.length()) {
            val moveJson = movesJsonArray.getJSONObject(i).getJSONObject("move")
            val moveName = moveJson.getString("name")
            val moveUrl = moveJson.getString("url")
            moves.add(PokemonMoveEntry(PokemonMove(moveName, moveUrl)))
        }
        val hp = jsonObject.getInt("hp")

        return PokemonDetailResponse(id, name, sprites, types, stats, moves, hp)
    }

    //JSON
    private fun parseMoveDetailResponse(json: String): MoveDetailResponse {
        val jsonObject = JSONObject(json)
        val power = if (jsonObject.has("power") && !jsonObject.isNull("power")) {
            jsonObject.getInt("power")
        } else {
            null
        }
        return MoveDetailResponse(power)
    }

    //List
    override suspend fun getPokemonList(): PokemonListResponse {
        val response = getApiResponse("${BASE_URL}pokemon")
        return PokemonListResponse(response)
    }
    //Details
    override suspend fun getPokemonDetails(id: Int): PokemonDetailResponse {
        val response = getApiResponse("${BASE_URL}pokemon/$id")
        return PokemonDetailResponse(response)
    }
    //Move
    override suspend fun getMoveDetails(id: Int): MoveDetailResponse {
        val response = getApiResponse("${BASE_URL}move/$id")
        return parseMoveDetailResponse(response)
    }
}

//Instance
object ApiClientInstance {
    val instance: PokemonApi = ApiClient()
}