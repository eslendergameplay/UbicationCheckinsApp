package com.example.CheckinsApp.UtidadesNUB

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.CheckinsApp.Mensaje.Errores
import com.example.CheckinsApp.Mensaje.Mensaje
import com.example.CheckinsApp.Mensaje.Mensajes
import com.example.CheckinsApp.interfaces.UbicacionListener
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult

class Ubicacion(var activity:AppCompatActivity,UbicacionListen: UbicacionListener) {

    private val permisoFineLocation = android.Manifest.permission.ACCESS_FINE_LOCATION
    private val permisoCoarseLocation = android.Manifest.permission.ACCESS_COARSE_LOCATION

    private val codigo_solicitud_ubicacion = 100

    private var fusedLC:FusedLocationProviderClient? = null

    private var lR:LocationRequest? = null

    private var callback:LocationCallback? = null

    init{
        fusedLC = FusedLocationProviderClient(activity.applicationContext)

        incializarLC()

        callback = object:LocationCallback(){
            override fun onLocationResult(LocationRs: LocationResult?) {
                super.onLocationResult(LocationRs)
                UbicacionListen.ubicaionResponse(LocationRs!!)
            }
        }
    }

    fun incializarLC(){
        lR = LocationRequest()
        lR?.interval = 600000
        lR?.fastestInterval = 45000
        lR?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun validarPermisosubicacion():Boolean{
        val hasUp = ActivityCompat.checkSelfPermission(activity.applicationContext,permisoFineLocation) == PackageManager.PERMISSION_GRANTED
        val hasCSP = ActivityCompat.checkSelfPermission(activity.applicationContext,permisoCoarseLocation) == PackageManager.PERMISSION_GRANTED

        return hasUp && hasCSP
    }

    private fun pedirPermisos(){
        val dbPC = ActivityCompat.shouldShowRequestPermissionRationale(activity,permisoFineLocation)

        if(dbPC){
            solicitudPermiso()
            Mensaje.mensajeSuccess(activity.applicationContext,Mensajes.Rationale)
        }else{
            solicitudPermiso()
        }
    }

    private fun solicitudPermiso(){
        ActivityCompat.requestPermissions(activity, arrayOf(permisoFineLocation,permisoCoarseLocation),codigo_solicitud_ubicacion)
    }

    fun onRequestPermissionsResult(requestCode:Int,permisssions:Array<out String>,grantResults:IntArray){
        when(requestCode){
            codigo_solicitud_ubicacion ->{
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    obtenerUbicacion()
                }else{
                    Mensaje.mensajeError(activity.applicationContext,Errores.Error_Permiso_Negado)
                }
            }
        }
    }

    fun detenerActualizacionUbicacion(){
        this.fusedLC?.removeLocationUpdates(callback)
    }

    fun incializarubicacion(){
        if(validarPermisosubicacion()){
            obtenerUbicacion()
        }else{
            pedirPermisos()
        }
    }

    @SuppressLint("MissingPermission")
    fun obtenerUbicacion(){
        validarPermisosubicacion()

        fusedLC?.requestLocationUpdates(lR,callback,null)
    }
}