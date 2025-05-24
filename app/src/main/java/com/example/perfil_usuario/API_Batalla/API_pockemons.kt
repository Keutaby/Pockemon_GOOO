package com.example.perfil_usuario.API_Batalla

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object InstanceRetrofitPoke{
    private const val url_base = "https://pokeapi.co/api/v2/"

    private val servicio: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(url_base)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val consumir_servicio: PokemonApi by lazy {
        servicio.create(PokemonApi::class.java)
    }
}