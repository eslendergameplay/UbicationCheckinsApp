package com.example.CheckinsApp.Actividades

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.CheckinsApp.Foursqaure.Foursquare
import com.example.CheckinsApp.UtidadesNUB.Ubicacion
import com.example.CheckinsApp.interfaces.VolverActv
import com.example.nuevokt3.R

class Principal : AppCompatActivity(),VolverActv {

    var fsq:Foursquare? = null
    var ubicacion:Ubicacion? = null
    var navh:NavHostFragment? = null

    var navc:NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        navh = NavHostFragment.create(R.navigation.nav_graph_principal)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, navh!!).addToBackStack(null).setPrimaryNavigationFragment(navh).commit()


        /*

        fsq = Foursquare(this,this)

        if(fsq?.hayToken()!!){
            ubicacion = Ubicacion(this,object :UbicacionListener{
                override fun ubicaionResponse(locationResult: LocationResult) {
                    var lat = locationResult.lastLocation.latitude
                    var lon = locationResult.lastLocation.longitude
                    var mibundl = Bundle()
                    mibundl.putDouble("lat", lat)
                    mibundl.putDouble("lon", lon)



                }
            })


         */

        }



    override fun onStart() {
        super.onStart()
        navc = navh?.navController

    }

    override fun onPause() {
        super.onPause()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>,grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        ubicacion?.onRequestPermissionsResult(requestCode,permissions,grantResults)
    }

    override fun volver() {
        var intento:Intent = Intent(this,MainActivity::class.java)
        startActivity(intento)
    }


}