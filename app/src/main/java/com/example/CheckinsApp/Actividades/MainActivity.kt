package com.example.CheckinsApp.Actividades

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.CheckinsApp.Foursqaure.Foursquare
import com.example.CheckinsApp.interfaces.PasaraActv
import com.example.nuevokt3.R
import com.example.nuevokt3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() , PasaraActv {

    var foursquare: Foursquare? = null
    var navh: NavHostFragment? = null
    var navc: NavController? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    /*
    var startForResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        foursquare?.validarActivityResult(result.resultCode,result.data)
        Log.d("RESULT",result.resultCode.toString())
        Log.d("data",result.data.toString())
    }*/

    var launcher:ActivityResultLauncher<ActivityResult>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        navh = NavHostFragment.create(R.navigation.nav_graph)
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment_content_main, navh!!).addToBackStack(null).setPrimaryNavigationFragment(navh).commit()


        /*
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                activityResult ->
            foursquare?.validarActivityResult(activityResult.resultCode,activityResult.data)
        }
         */

        foursquare = Foursquare(this,Principal())

        if(foursquare?.hayToken()!!){
            foursquare?.navegarSiguienteActividad(Principal())
        }

    }

    override fun onStart() {
        super.onStart()

        navc = navh?.navController


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun pasar() {
        foursquare = Foursquare(this, Principal())
        foursquare?.iniciarSesion()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        foursquare?.validarActivityResult(requestCode,resultCode,data)
    }




}

