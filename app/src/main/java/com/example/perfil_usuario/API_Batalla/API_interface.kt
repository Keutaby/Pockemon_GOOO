package com.example.perfil_usuario.API_Batalla

import com.example.perfil_usuario.API_Batalla.POKE.MoveDetailResponse
import com.example.perfil_usuario.API_Batalla.POKE.PokemonDetailResponse
import com.example.perfil_usuario.API_Batalla.POKE.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path

//API Interface
interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemonList(): PokemonListResponse

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(@Path("id") id: Int): PokemonDetailResponse

    @GET("move/{id}")
    suspend fun getMoveDetails(@Path("id") id: Int): MoveDetailResponse
}