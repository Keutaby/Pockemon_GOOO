package com.example.perfil_usuario.API_Batalla

//API Response
data class PokemonListResponse(
    val results: List<PokemonEntry>
)

data class PokemonEntry(
    val name: String,
    val url: String
)

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val sprites: PokemonSprites,
    val types: List<PokemonTypeEntry>,
    val stats: List<PokemonStatEntry>,
    val moves: List<PokemonMoveEntry>,
    var hp: Int
)

data class PokemonSprites(
    val frontDefault: String?
)

data class PokemonTypeEntry(
    val type: PokemonType
)

data class PokemonType(
    val name: String
)

data class PokemonStatEntry(
    val baseStat: Int,
    val stat: PokemonStat
)

data class PokemonStat(
    val name: String,
    val url: String
)

data class PokemonMoveEntry(
    val move: PokemonMove
)

data class PokemonMove(
    val name: String,
    val url: String
)

data class MoveDetailResponse(
    val power: Int?
)