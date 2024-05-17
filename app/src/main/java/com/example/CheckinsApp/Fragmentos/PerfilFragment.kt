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
import android.widget.GridView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.CheckinsApp.Foursqaure.Foursquare
import com.example.CheckinsApp.Foursqaure.User
import com.example.CheckinsApp.GridView.AdaptadorGridViewPerfil
import com.example.CheckinsApp.GridView.EstadisticasUser
import com.example.CheckinsApp.interfaces.ObtenerUsuarioIT
import com.example.nuevokt3.R
import com.example.nuevokt3.databinding.FragmentPerfilBinding
import com.squareup.picasso.Picasso

class PerfilFragment : Fragment(),MenuProvider {

    var imagencircular:de.hdodenhof.circleimageview.CircleImageView? = null
    var etinombre:TextView? = null
    var navc:NavController? = null
    var tb:Toolbar? = null
    var vista:View? = null
    var fsq:Foursquare? = null
    var usuario:User? = null
    var grid:GridView? = null
    var adaptadorGrid:AdaptadorGridViewPerfil? = null
    var listaEstats:ArrayList<EstadisticasUser>? = null

    private var _binding: FragmentPerfilBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_perfil, container, false)

        return vista!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        imagencircular = vista?.findViewById(R.id.profile_image)

        etinombre = vista?.findViewById(R.id.etiNombreperfil)

        var activity:AppCompatActivity = activity as AppCompatActivity

        fsq = Foursquare(activity,activity)

        if(fsq?.hayToken()!!){
            fsq?.obtenerUsuario(object:ObtenerUsuarioIT{
                override fun usuarioGenerado(usuariogenerado: User) {
                    usuario = usuariogenerado

                    listaEstats = ArrayList()

                    Picasso.get().load(usuario?.fotopreview).placeholder(R.drawable.usuario).into(imagencircular)

                    etinombre?.text = usuario?.firstName + " " + usuario?.lastName

                    grid = vista?.findViewById(R.id.gridestadisticas)

                    Log.d("amigos",usuario?.friends?.count.toString())
                    Log.d("tips",usuario?.tips?.count.toString())
                    Log.d("photos",usuario?.photos?.count.toString())
                    Log.d("checkins",usuario?.checkins?.count.toString())


                    listaEstats?.add(EstadisticasUser(usuario?.friends?.count!!,R.drawable.usuario,"Amigos.",ContextCompat.getColor(requireContext(),R.color.purple)))
                    listaEstats?.add(EstadisticasUser(usuario?.tips?.count!!,R.drawable.tips,"Tips.",ContextCompat.getColor(requireContext(),R.color.entr)))
                    listaEstats?.add(EstadisticasUser(usuario?.photos?.count!!,
                        R.drawable.fotos,"Fotos.",ContextCompat.getColor(requireContext(),R.color.etc)))
                    listaEstats?.add(EstadisticasUser(usuario?.checkins?.count!!,R.drawable.checkins,"Checkins.",ContextCompat.getColor(requireContext(),R.color.Checkin)))


                    adaptadorGrid = AdaptadorGridViewPerfil(activity.applicationContext,listaEstats!!)
                    grid?.adapter = adaptadorGrid


                    initToolbar()

                }

            })
        }



    }

    fun initToolbar(){
        var activity:AppCompatActivity = activity as AppCompatActivity

        tb = vista?.findViewById(R.id.tbperfil)

        tb?.addMenuProvider(this,viewLifecycleOwner, Lifecycle.State.CREATED)
        navc = NavHostFragment.findNavController(this)
        NavigationUI.setupWithNavController(tb!!, navc!!)
        tb?.setTitle(usuario?.firstName + " " + usuario?.lastName)

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