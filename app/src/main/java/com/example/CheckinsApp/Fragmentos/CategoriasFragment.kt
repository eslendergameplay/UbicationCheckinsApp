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
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.CheckinsApp.Foursqaure.Category
import com.example.CheckinsApp.Foursqaure.Foursquare
import com.example.CheckinsApp.RvCategoria.AdaptadorRvCateg
import com.example.CheckinsApp.RvPrincipal.ClickListener
import com.example.CheckinsApp.interfaces.CategoriesIT
import com.example.nuevokt3.R
import com.example.nuevokt3.databinding.FragmentDetalleBinding


class CategoriasFragment : Fragment(),MenuProvider {

    var navc:NavController? = null
    var tb:Toolbar? = null
    var lat:Double = 0.0
    var lon:Double = 0.0
    companion object{
        var categoriasObj:ArrayList<Category>? = null
    }
    var vista:View? = null
    var fsq:Foursquare? = null
    var lista: RecyclerView? = null
    var adaptador: AdaptadorRvCateg? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    private var _binding: FragmentDetalleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_categorias, container, false)

        return vista!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var activity:AppCompatActivity = activity as AppCompatActivity

        initToolbar()

        lista = vista?.findViewById(R.id.rvcategorias)
        lista?.setHasFixedSize(true)

        layoutManager = GridLayoutManager(context,2)
        lista?.layoutManager = layoutManager

        var mibundl:Bundle? = arguments

        if(mibundl != null){

            lat = mibundl.getDouble("lat-cate")
            lon = mibundl.getDouble("lon-cate")
            Log.d("cate-lat-lon",lat.toString() + "," + lon.toString())

        }

        fsq = Foursquare(activity,activity)

        fsq?.obtenerCategories(object:CategoriesIT{
            override fun categoriasgeneradas(a: ArrayList<Category>) {
                Log.d("Numeros",a.count().toString())
                categoriasObj = a
                implementacionRecyclerView(categoriasObj!!,lat,lon)
            }

        })

    }

    fun implementacionRecyclerView(categorias:ArrayList<Category>,lat:Double,lon:Double){
        adaptador = AdaptadorRvCateg(categorias, object : ClickListener {
            override fun onClick(vista: View, index: Int) {
                var indice:Int = index
                var mibunld:Bundle = Bundle()
                mibunld.putInt("ncategoria",indice)
                mibunld.putDouble("lat",lat)
                mibunld.putDouble("lon",lon)
                Navigation.findNavController(vista).navigate(R.id.venuesPorCategoriaFragment,mibunld)

                Log.d("Indice", categoriasObj?.get(indice)?.id.toString())
            }

        },object : com.example.CheckinsApp.RvCategoria.LongClickListener {
            override fun longClick(vista: View, index: Int) {

            }

        })

        lista?.adapter = adaptador
    }

    fun initToolbar(){
        tb = vista?.findViewById(R.id.tbcate)

        var activity:AppCompatActivity = activity as AppCompatActivity

        tb?.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.CREATED)
        navc = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(tb!!, navc!!)
        tb?.setTitle("Categorias")

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