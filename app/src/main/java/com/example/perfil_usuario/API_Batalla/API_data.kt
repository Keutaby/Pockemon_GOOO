package com.example.perfil_usuario.API_Batalla

import com.example.perfil_usuario.API_Batalla.POKE.MoveDetailResponse
import com.example.perfil_usuario.API_Batalla.POKE.PaginaContenedora
import com.example.perfil_usuario.API_Batalla.POKE.PokemonDetailResponse
import com.example.perfil_usuario.API_Batalla.POKE.PokemonListResponse

class RepositorioPOKE{
    private val API_pockemons = InstanceRetrofitPoke.consumir_servicio

    suspend fun obtener_pokemones(pagina: Int): PokemonDetailResponse{
        return API_pockemons.getPokemonDetails(pagina)
    }

    suspend fun obtener_poke(id: Int): MoveDetailResponse{
        return API_pockemons.getMoveDetails(id)
    }
}