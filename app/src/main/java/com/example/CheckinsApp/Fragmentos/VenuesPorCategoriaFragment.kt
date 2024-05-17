package com.example.CheckinsApp.Fragmentos

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.CheckinsApp.Foursqaure.Category
import com.example.CheckinsApp.Foursqaure.Foursquare
import com.example.CheckinsApp.Foursqaure.Venue
import com.example.CheckinsApp.RvCategoria.LongClickListener
import com.example.CheckinsApp.RvPrincipal.AdaptadorRvPr
import com.example.CheckinsApp.RvPrincipal.ClickListener
import com.example.CheckinsApp.UtidadesNUB.Ubicacion
import com.example.CheckinsApp.interfaces.ObtenerVenuesCategIt
import com.example.nuevokt3.R
import com.example.nuevokt3.databinding.FragmentVenuesPorCategoriaBinding
import com.squareup.picasso.Picasso


class VenuesPorCategoriaFragment : Fragment(),MenuProvider {

    companion object {
        var vporcateg: ArrayList<Venue>? = null
    }

    var navc: NavController? = null
    var tb: Toolbar? = null
    var cateid: String = ""
    var lat: Double = 0.0
    var lon: Double = 0.0
    var ubicacion: Ubicacion? = null
    var fsq: Foursquare? = null
    var lista: RecyclerView? = null
    var adaptador: AdaptadorRvPr? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var eticategeneral: TextView? = null
    var fotocategg: ImageView? = null
    var vista: View? = null

    private var _binding: FragmentVenuesPorCategoriaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_venues_por_categoria, container, false)

        return vista!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tb = vista?.findViewById(R.id.tb)

        initToolbar()


        var activity: AppCompatActivity = activity as AppCompatActivity

        fsq = Foursquare(activity, activity)

        lista = vista?.findViewById(R.id.rvderivacateg)
        lista?.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(context)
        lista?.layoutManager = layoutManager

        eticategeneral = vista?.findViewById(R.id.eticategoriagneral)

        fotocategg = vista?.findViewById(R.id.fotocateggneral)

        if (arguments != null) {
            var mibundl: Bundle = arguments as Bundle
            var indicegeneral: Int = mibundl.getInt("ncategoria")
            lat = mibundl.getDouble("lat")
            lon = mibundl.getDouble("lon")
            Log.d("cate-lat-lon",lat.toString() + "," + lon.toString())
            var categeneral: Category? = CategoriasFragment.categoriasObj?.get(indicegeneral)
            cateid = categeneral?.id!!
            Log.d("Cateid", cateid)

            eticategeneral?.text = categeneral.name
            Picasso.get().load(categeneral.iconPreview).placeholder(R.drawable.etiquetas)
                .into(fotocategg)



            if (fsq?.hayToken()!!) {
                fsq?.obtenervenuesPorCate(lat.toString(), lon.toString(), cateid, object : ObtenerVenuesCategIt {
                        override fun venuesporcate(vporcategoria: ArrayList<Venue>) {
                            for (venue in vporcategoria) {
                                var id = venue.id
                                Log.d("Nombre", vporcategoria.size.toString())

                            }

                            vporcateg = vporcategoria
                            implementacionRecyclerView(vporcategoria, lat, lon)
                        }


                    })
            }
        }

        tb?.setNavigationOnClickListener {
            activity.onBackPressedDispatcher.onBackPressed()
        }


    /*
            if(fsq?.hayToken()!!){
                ubicacion = Ubicacion(activity,object : UbicacionListener {
                    override fun ubicaionResponse(locationResult: LocationResult) {
                        lat = locationResult.lastLocation.latitude
                        lon = locationResult.lastLocation.longitude
                        Log.d("latitudylongitud",lat.toString() + "," + lon.toString())


                        fsq?.obtenervenuesPorCate(lat.toString(), lon.toString(),cateid, object : ObtenerVenuesCategIt {
                            override fun venuesporcate(vporcategoria: ArrayList<Venue>) {
                                for (venue in vporcategoria) {
                                    var id = venue.id
                                    Log.d("Nombre", vporcategoria.size.toString())

                                }

                                implementacionRe(vporcategoria,lat,lon)
                            }


                        })



                    }
                })
            }
        }



             */

    }

    fun initToolbar(){
        tb = vista?.findViewById(R.id.tbvpcate)

        var activity:AppCompatActivity = activity as AppCompatActivity

        tb?.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.CREATED)
        navc = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(tb!!, navc!!)
        tb?.setTitle("Pantalla Principal")

        tb?.setNavigationOnClickListener {
            activity.onBackPressedDispatcher.onBackPressed()
        }

    }

    fun implementacionRecyclerView(lugarcate:ArrayList<Venue>,lat:Double,lon:Double){
        adaptador = AdaptadorRvPr(lugarcate, object : ClickListener {
            override fun onClick(vista: View, index: Int) {
                var indice:Int = index
                var mibunld:Bundle = Bundle()
                mibunld.putInt("lugar-cate",indice)
                mibunld.putDouble("lat",lat)
                Log.d("Catelat",lat.toString())
                mibunld.putDouble("lon",lon)
                Log.d("Catelon",lon.toString())
                Navigation.findNavController(vista).navigate(R.id.detalleFragment,mibunld)
            }

        },object : LongClickListener{
            override fun longClick(vista: View, index: Int) {

            }

        })

        lista?.adapter = adaptador
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

        when(menuItem.itemId){
            else ->{return false}
        }
    }



}