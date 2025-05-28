import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perfil_usuario.API_Batalla.POKE.PaginaContenedora // Importa PaginaContenedora
import com.example.perfil_usuario.API_Batalla.RepositorioPOKE
import kotlinx.coroutines.launch


// Define los diferentes estados en los que puede estar nuestro ViewModel
enum class Estados{
    cargando, // Indica que se están cargando datos
    mostrando_lista_de_pokemones, // Indica que la lista de Pokémon se ha cargado con éxito y se está mostrando
    error, // Indica que ocurrió un error
    registrar_frustracion // Un estado para registrar un problema específico
}


class PokeModelo: ViewModel(){
    private val repositorio = RepositorioPOKE()

    // LiveData que contendrá la página actual de datos de Pokémon, incluyendo la información de paginación
    private val _pagina_actual = MutableLiveData<PaginaContenedora>()
    val pagina_actual: LiveData<PaginaContenedora> = _pagina_actual

    // LiveData que contendrá el estado actual de la operación de descarga de datos
    private val _estado_actual = MutableLiveData<Estados>(Estados.cargando)
    val estado_actual: LiveData<Estados> = _estado_actual // Expone el estado para que la UI lo observe

    // LiveData para mensajes de error o información
    private val _mensaje = MutableLiveData("Por ahora nada")
    val mensaje: LiveData<String> = _mensaje

    // Offset actual para la paginación (indica desde qué elemento empezar)
    private var currentOffset: Int = 0
    // Límite de Pokémon por página
    private val POKEMON_LIMIT = 20

    // Por defecto, descarga la primera página (offset 0)
    fun descargar_pagina(offset: Int = 0){
        _estado_actual.postValue(Estados.cargando) // Establece el estado a "cargando"
        Log.v("PokeModelo", "Descargando página de pokemones con offset: $offset")
        viewModelScope.launch { // Lanza una corrutina en el ámbito del ViewModel
            try {
                // Llama al repositorio para obtener la página de Pokémon con el offset y límite especificados
                val pagina = repositorio.obtener_pagina_pokemones(offset, POKEMON_LIMIT)
                _pagina_actual.postValue(pagina) // Actualiza el LiveData con la página obtenida
                _estado_actual.postValue(Estados.mostrando_lista_de_pokemones) // Establece el estado a "mostrando lista"
                currentOffset = offset // Actualiza el offset actual
            }
            catch (error: Exception){
                // Si ocurre un error, lo registra, actualiza el mensaje y establece el estado a "error"
                Log.e("PokeModelo", "Error al descargar página de pokemones: ${error.message}")
                _mensaje.postValue(error.message)
                _estado_actual.postValue(Estados.error)
            }
        }
    }

    // Función para avanzar a la siguiente página de Pokémon
    fun pasar_a_siguiente_pagina() {
        // Calcula el siguiente offset. Si el offset calculado es menor que el total de Pokémon, avanza.
        val totalPokemon = _pagina_actual.value?.count ?: 0 // Obtiene el conteo total de Pokémon
        val nextOffset = currentOffset + POKEMON_LIMIT

        if (nextOffset < totalPokemon) {
            descargar_pagina(nextOffset) // Descarga la siguiente página
        } else {
            Log.v("PokeModelo", "Ya estás en la última página.")
            _mensaje.postValue("Ya estás en la última página.") // Informa al usuario
            _estado_actual.postValue(Estados.mostrando_lista_de_pokemones) // Permanece en el estado actual
        }
    }

    // Función para retroceder a la página anterior de Pokémon
    fun pasar_a_anterior_pagina() {
        val previousOffset = currentOffset - POKEMON_LIMIT
        if (previousOffset >= 0) { // Asegura que el offset no sea negativo
            descargar_pagina(previousOffset) // Descarga la página anterior
        } else {
            Log.v("PokeModelo", "Ya estás en la primera página.")
            _mensaje.postValue("Ya estás en la primera página.") // Informa al usuario
            _estado_actual.postValue(Estados.mostrando_lista_de_pokemones) // Permanece en el estado actual
        }
    }

    // Función para indicar un problema general o frustración
    fun indicar_un_problema(){
        _estado_actual.postValue(Estados.registrar_frustracion)
    }
}