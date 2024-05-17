package com.example.CheckinsApp.interfaces

import com.google.android.gms.location.LocationResult

interface UbicacionListener {

    fun ubicaionResponse(locationResult:LocationResult)
}