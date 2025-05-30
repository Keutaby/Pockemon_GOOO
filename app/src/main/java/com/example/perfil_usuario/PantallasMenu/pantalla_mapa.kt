package com.example.Perfil_Usuario.PantallasMenu

import android.graphics.drawable.Drawable
import android.location.Location
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.media3.common.util.Log
import com.example.Perfil_Usuario.ControladoresMapa.GPSControlador
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun MapaPokemones(controlador_gps: GPSControlador){
    val contexto = LocalContext.current
    val mapa_view = MapView(contexto)

    val ubicacion_actual: Location? by controlador_gps.ubicacion.observeAsState(initial = null)

    val parque_cowan = remember { GeoPoint(31.711242676792303, -106.36436336981875) }
    val parque_veterans = remember { GeoPoint(31.92019790477828, -106.42046109544629) }
    val parque_westside = remember { GeoPoint(31.816170773485606, -106.53212165580622) }

    mapa_view.setTileSource(TileSourceFactory.MAPNIK)
    mapa_view.setBuiltInZoomControls(true)
    mapa_view.setMultiTouchControls(true)
    mapa_view.controller.setZoom(15.0)

    mapa_view.controller.setCenter(parque_cowan)


    AndroidView(modifier = Modifier.fillMaxSize(),
        factory = { contexto ->
            val marcador_ubicacion_actual_instance = Marker(mapa_view)
            marcador_ubicacion_actual_instance.position = parque_cowan
            marcador_ubicacion_actual_instance.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marcador_ubicacion_actual_instance.icon = ContextCompat.getDrawable(contexto, com.example.perfil_usuario.R.drawable.avatar_profile)

            val marcador_cowan_instance = Marker(mapa_view)
            marcador_cowan_instance.position = parque_cowan
            marcador_cowan_instance.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

            val marcador_veterans_instance = Marker(mapa_view)
            marcador_veterans_instance.position = parque_veterans
            marcador_veterans_instance.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marcador_veterans_instance.icon = ContextCompat.getDrawable(contexto, com.example.perfil_usuario.R.drawable.threed_gym)

            val marcador_westside_instance = Marker(mapa_view)
            marcador_westside_instance.position = parque_westside
            marcador_westside_instance.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marcador_westside_instance.icon = ContextCompat.getDrawable(contexto, com.example.perfil_usuario.R.drawable.threed_pokestop)

            //Overlays
            mapa_view.overlays.add(marcador_ubicacion_actual_instance)
            mapa_view.overlays.add(marcador_cowan_instance)
            mapa_view.overlays.add(marcador_veterans_instance)
            mapa_view.overlays.add(marcador_westside_instance)

            mapa_view
        }){ mapa_view_updated ->

        val current_location_marker = mapa_view_updated.overlays.firstOrNull {
            it is Marker && it.icon == ContextCompat.getDrawable(contexto, com.example.perfil_usuario.R.drawable.avatar_mapa)
        } as? Marker

        if(ubicacion_actual != null && ubicacion_actual!!.latitude != 0.0 && ubicacion_actual!!.longitude != 0.0){
            current_location_marker?.position = GeoPoint(ubicacion_actual!!.latitude, ubicacion_actual!!.longitude)
            mapa_view_updated.controller.animateTo(current_location_marker?.position)
        } else {
            mapa_view_updated.controller.animateTo(parque_cowan)
            current_location_marker?.position = parque_cowan
        }
<<<<<<< HEAD

        val marcador = Marker(mapa_view)
        val parque_cowan = GeoPoint(31.711242676792303, -106.36436336981875)
        marcador.position = parque_cowan
        marcador.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        val poke_avatar: Drawable? = ContextCompat.getDrawable(contexto, com.example.perfil_usuario.R.drawable.avatar_mapa)
        marcador_ubicacion_actual.icon = poke_avatar

        val marcador_2 = Marker(mapa_view)
        val parque_veterans = GeoPoint(31.92019790477828, -106.42046109544629) //get from google maps, veterans pool
        marcador_2.position = parque_veterans
        marcador_2.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        //marcador_2.icon
        val poke_gym_marker: Drawable? = ContextCompat.getDrawable(contexto, com.example.perfil_usuario.R.drawable.threed_gym,)
        marcador_2.icon = poke_gym_marker

        val marcador_3 = Marker(mapa_view)
        val parque_westside = GeoPoint(31.816170773485606, -106.53212165580622) //get from google maps, Westside Natatorium pool
        marcador_3.position = parque_westside
        marcador_3.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        val pokestop_marker: Drawable? = ContextCompat.getDrawable(contexto, com.example.perfil_usuario.R.drawable.threed_pokestop)
        marcador_3.icon = pokestop_marker


        //val distancia = parque_veterans.distanceToAsDouble(marcador.position) //calculate distance from one geoPoint to the other Geo point
        //Log.v("Distancia", "La distancia es ${distancia}")
        //mapa_view.controller.animateTo(parque_veterans)

        mapa_view.overlays.add(marcador_ubicacion_actual)
        mapa_view.overlays.add(marcador)
        mapa_view.overlays.add(marcador_2)
        mapa_view.overlays.add(marcador_3)
=======
        mapa_view_updated.invalidate()
>>>>>>> bd1a56b5797c35d53a2f6f1f26a06167010e6441
    }
}