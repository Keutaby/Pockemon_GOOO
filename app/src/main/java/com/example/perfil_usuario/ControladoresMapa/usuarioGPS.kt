package com.example.Perfil_Usuario.ControladoresMapa

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationProvider
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class usuario_GPS(
    private val contexto: Context,
    private val cliente: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(contexto) //conexion
){
    @SuppressLint("MissingPermission")
    fun configurar_actualizar_ubicacion(gps_controlador: GPSControlador){
        cliente.requestLocationUpdates(
            LocationRequest.Builder(calcular_milesimas(1)).build(),
            { ubicacion ->
                gps_controlador.actualizar_ubicacion(ubicacion)
                Log.v("Ubicacion", "Lat: ${ubicacion.latitude} long: ${ubicacion.longitude}")
            },
            Looper.getMainLooper()
        )
    }


    fun calcular_milesimas(segundos: Long): Long{
        return segundos * 1000
    }
}