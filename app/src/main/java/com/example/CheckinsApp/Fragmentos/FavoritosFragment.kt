package com.example.CheckinsApp.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
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
import com.example.CheckinsApp.Foursqaure.Foursquare
import com.example.CheckinsApp.Foursqaure.Venue
import com.example.CheckinsApp.RvPrincipal.AdaptadorRvPr
import com.example.CheckinsApp.RvPrincipal.ClickListener
import com.example.CheckinsApp.interfaces.obtenerVenuesLikesIT
import com.example.nuevokt3.R
import com.example.nuevokt3.databinding.FragmentFavoritosBinding


class FavoritosFragment : Fragment(),MenuProvider {

    companion object{
        var venuesFavoritos:ArrayList<Venue>? = null
    }
    var fsq:Foursquare? = null
    var tb:Toolbar? = null
    var lista:RecyclerView? = null
    var adaptador: AdaptadorRvPr? = null
    var layoutManager:RecyclerView.LayoutManager? = null
    var navc:NavController? = null
    var lat:Double = 0.0
    var lon:Double = 0.0
    var vista:View? = null

    private var _binding: FragmentFavoritosBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_favoritos, container, false)

        return vista!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var activity:AppCompatActivity = activity as AppCompatActivity

        fsq = Foursquare(activity,activity)

        initToolbar()

        lista = vista?.findViewById(R.id.rvderivalikes)

        lista?.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(context)

        lista?.layoutManager = layoutManager

        var mibundl:Bundle = arguments as Bundle

        lat = mibundl.getDouble("lat-Liked")
        lon = mibundl.getDouble("lon-Liked")

        if(fsq?.hayToken()!!){
            fsq?.obtenerLikesPorLike(object:obtenerVenuesLikesIT{
                override fun venuesLKgenerados(venuesLikes: ArrayList<Venue>) {
                    venuesFavoritos = venuesLikes
                    implementacionRecyclerView(venuesFavoritos!!,lat,lon)
                }


            })
        }

    }

    fun implementacionRecyclerView(lugares:ArrayList<Venue>,lat:Double,lon:Double){
        adaptador = AdaptadorRvPr(lugares, object : ClickListener {
            override fun onClick(vista: View, index: Int) {
                var indice:Int = index
                var mibunld:Bundle = Bundle()
                mibunld.putInt("lugar-Liked",indice)
                mibunld.putDouble("lat",lat)
                mibunld.putDouble("lon",lon)
                Navigation.findNavController(vista).navigate(R.id.detalleFragment,mibunld)
            }

        },object : com.example.CheckinsApp.RvCategoria.LongClickListener{
            override fun longClick(vista: View, index: Int) {

            }

        })

        lista?.adapter = adaptador
    }

    fun initToolbar(){

        var activity:AppCompatActivity = activity as AppCompatActivity

        tb = vista?.findViewById(R.id.tbfav)

        tb?.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.CREATED)
        navc = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(tb!!, navc!!)
        tb?.setTitle("Favoritos.")

        tb?.setNavigationOnClickListener {
            activity.onBackPressedDispatcher.onBackPressed()
        }

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