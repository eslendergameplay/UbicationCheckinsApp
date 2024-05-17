package com.example.CheckinsApp.Fragmentos

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.CheckinsApp.Foursqaure.Foursquare
import com.example.CheckinsApp.Foursqaure.Venue
import com.example.CheckinsApp.RvPrincipal.AdaptadorRvPr
import com.example.CheckinsApp.RvPrincipal.ClickListener
import com.example.CheckinsApp.UtidadesNUB.Ubicacion
import com.example.CheckinsApp.interfaces.ObtenerVenuesIT
import com.example.CheckinsApp.interfaces.UbicacionListener
import com.example.CheckinsApp.interfaces.VolverActv
import com.example.nuevokt3.R
import com.example.nuevokt3.databinding.FragmentSecondBinding
import com.google.android.gms.location.LocationResult
import com.google.android.material.button.MaterialButton


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(),MenuProvider {

    var lat: Double = 0.0
    var lon: Double = 0.0

    var inter:VolverActv? = null

    companion object {
        var lugaresstatico: ArrayList<Venue>? = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is VolverActv){
            inter = context

        }
    }

    var argumento: Bundle = Bundle()
    var lista: RecyclerView? = null
    var adaptador: AdaptadorRvPr? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var ubicacion: Ubicacion? = null
    var fsq: Foursquare? = null
    var navc: NavController? = null
    var tb: Toolbar? = null
    var vista: View? = null
    var btnlike: MaterialButton? = null
    var btncheckin: MaterialButton? = null

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        vista = inflater.inflate(R.layout.fragment_second, container, false)



        return vista!!

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()

        val activity: AppCompatActivity = activity as AppCompatActivity

        lista = vista?.findViewById(R.id.rvprincipal)
        lista?.hasFixedSize()

        fsq = Foursquare(activity, activity)

        layoutManager = LinearLayoutManager(context)
        lista?.layoutManager = layoutManager

        if (fsq?.hayToken()!!) {
            ubicacion = Ubicacion(activity, object : UbicacionListener {
                override fun ubicaionResponse(locationResult: LocationResult) {
                    lat = locationResult.lastLocation.latitude
                    lon = locationResult.lastLocation.longitude


                    fsq?.obtenervenues(lat.toString(), lon.toString(), object : ObtenerVenuesIT {
                        override fun venuesGenerados(venues: ArrayList<Venue>) {
                            implementacionRecyclerView(venues, lat, lon)
                            for (venue in venues) {
                                var id = venue.id
                                Log.d("Nombre", venues.size.toString())

                            }

                            Log.d("TAG", venues.size.toString())

                            lugaresstatico = venues
                        }

                    })


                }
            })


        }


        //Toast.makeText(context,lat.toString() + "-" + lon.toString(),Toast.LENGTH_SHORT).show()

        /*
        val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
        ft.setReorderingAllowed(false)
        ft.detach(this).attach(this).commit()

            /*no funciona https://api.foursquare.com/v3/places/search?oauth_token=fsq3F1Nlr3cbI+S6TDSNTiMsATv5yqRu05wyWuHTCRKBQPM=&ll=41.8781,-87.6298&v=20240423
            if(fsq?.hayToken()!!){
                fsq?.obtenervenuesPlaces(lat.toString(),lon.toString(),object:ObtenerPlacesIT{
                    override fun placesgenerados() {

                    }


                })
            }
            */


 */

    }

    override fun onStart() {
        super.onStart()
        ubicacion?.incializarubicacion()
    }

    override fun onPause() {
        super.onPause()
        ubicacion?.detenerActualizacionUbicacion()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun initToolbar() {
        tb = vista?.findViewById(R.id.tb)

        tb?.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.CREATED)
        navc = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(tb!!, navc!!)
        tb?.setTitle("Pantalla Principal")


    }

    fun implementacionRecyclerView(lugares: ArrayList<Venue>, lat: Double, lon: Double) {
        adaptador = AdaptadorRvPr(lugares, object : ClickListener {
            override fun onClick(vista: View, index: Int) {
                var indice: Int = index
                var mibunld: Bundle = Bundle()
                mibunld.putInt("lugar", indice)
                mibunld.putDouble("lat", lat)
                mibunld.putDouble("lon", lon)
                Navigation.findNavController(vista).navigate(R.id.detalleFragment, mibunld)
            }

        }, object : com.example.CheckinsApp.RvCategoria.LongClickListener {
            override fun longClick(vista: View, index: Int) {

            }

        })

        lista?.adapter = adaptador
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.categoria -> {
                var mibundl: Bundle = Bundle()
                mibundl.putDouble("lat-cate", lat)
                mibundl.putDouble("lon-cate", lon)
                Navigation.findNavController(vista!!).navigate(R.id.categoriasFragment, mibundl)
                return true
            }

            R.id.favorito -> {
                var mibundl: Bundle = Bundle()
                mibundl.putDouble("lat-Liked", lat)
                mibundl.putDouble("lon-Liked", lon)
                Navigation.findNavController(vista!!).navigate(R.id.favoritosFragment, mibundl)
                return true
            }

            R.id.perfil -> {
                Navigation.findNavController(vista!!).navigate(R.id.perfilFragment)
                return true
            }
            R.id.cerrarSesion ->{
                fsq?.cerrarSesion()
                inter?.volver()
                return true
            }

            else -> {
                return false
            }
        }
    }




}

