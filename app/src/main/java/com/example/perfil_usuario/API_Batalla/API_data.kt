package com.example.perfil_usuario.API_Batalla

import com.example.perfil_usuario.API_Batalla.POKE.MoveDetailResponse
import com.example.perfil_usuario.API_Batalla.POKE.PaginaContenedora
import com.example.perfil_usuario.API_Batalla.POKE.PokemonDetailResponse

class RepositorioPOKE{
    private val API_pockemons = InstanceRetrofitPoke.consumir_servicio

    suspend fun obtener_pagina_pokemones(offset: Int, limit: Int): PaginaContenedora {
        return API_pockemons.getPokemonList(offset, limit)
    }

    suspend fun obtener_detalles_pokemon(id: Int): PokemonDetailResponse {
        return API_pockemons.getPokemonDetails(id)
    }

    suspend fun obtener_detalles_movimiento(id: Int): MoveDetailResponse {
        return API_pockemons.getMoveDetails(id)
    }
}