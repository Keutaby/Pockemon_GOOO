package com.example.perfil_usuario.API_Batalla

import com.example.perfil_usuario.API_Batalla.POKE.MoveDetailResponse
import com.example.perfil_usuario.API_Batalla.POKE.PaginaContenedora
import com.example.perfil_usuario.API_Batalla.POKE.PokemonDetailResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PaginaContenedora

    @GET("pokemon/{id}")
    suspend fun getPokemonDetails(@Path("id") id: Int): PokemonDetailResponse

    @GET("move/{id}")
    suspend fun getMoveDetails(@Path("id") id: Int): MoveDetailResponse
}