package com.example.CheckinsApp.Fragmentos

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.CheckinsApp.Foursqaure.Foursquare
import com.example.nuevokt3.R
import com.example.nuevokt3.databinding.FragmentDetalleBinding
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso
import java.net.URLEncoder


class DetalleFragment : Fragment(),MenuProvider {

    var navc:NavController? = null
    var tb:Toolbar? = null
    var fsq:Foursquare? = null
    var nombre: TextView? = null

    var categorias: TextView? = null

    var localizacion: TextView? = null

    var tips: TextView? =  null

    var likes: TextView? = null

    var usuarios: TextView? = null

    var photoscount: TextView? = null

    var checkins: TextView? = null

    var imagen: ImageView? = null
    var index:Int = -1
    var vista:View? = null

    var btnlike:MaterialButton? = null

    var btncheckin:MaterialButton? = null

    private var _binding: FragmentDetalleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_detalle, container, false)

        return vista!!
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var activity:AppCompatActivity = activity as AppCompatActivity

        initToolbar()

        fsq = Foursquare(activity,activity)

        var mibundl: Bundle? = arguments
        Log.d("Bundle",mibundl.toString())

        if(mibundl != null){

            if(mibundl.containsKey("lugar")){
                index = mibundl.getInt("lugar")
                var lugar = SecondFragment.lugaresstatico?.get(index)
                var lat = mibundl.getString("lat")
                var lon = mibundl.getString("lon")
                var id = lugar?.id

                Log.d("Lugar",lugar?.id!!)
                Log.d("Imagen",lugar.imagePreview!!)

                nombre = vista?.findViewById(R.id.etinombre2)

                categorias = vista?.findViewById(R.id.eticategoria2)

                localizacion = vista?.findViewById(R.id.etilugares2)

                tips = vista?.findViewById(R.id.etitips2)

                likes = vista?.findViewById(R.id.etilikes2)

                usuarios = vista?.findViewById(R.id.etiusuario2)

                photoscount = vista?.findViewById(R.id.etifotos2)

                checkins = vista?.findViewById(R.id.eticheckins2)

                imagen = vista?.findViewById(R.id.fotorcvprincipal2)

                btnlike = vista?.findViewById(R.id.btnlike)

                btncheckin = vista?.findViewById(R.id.btnCkeck_In)


                nombre?.text = lugar.name

                categorias?.text = lugar.categories?.getOrNull(0)?.name

                localizacion?.text =
                    lugar?.location?.city + "," + lugar?.location?.state + "," +lugar?.location?.country + "."

                tips?.text = lugar?.estadisticas?.get(0)?.stats?.tipCount.toString() + " " + "Tips."

                likes?.text = lugar?.estadisticas?.get(0)?.likes?.count.toString() + " " + "Likes."

                usuarios?.text = lugar?.estadisticas?.get(0)?.beenHere?.count.toString() + " " + "Usuarios"

                photoscount?.text = lugar?.estadisticas?.get(0)?.photos?.count.toString() + " " + "Fotos."

                checkins?.text = lugar?.estadisticas?.get(0)?.listed?.count.toString() + " " + "Checkins"

                Picasso.get().load(lugar.imagePreview).placeholder(R.drawable.placeholder_venue).into(imagen)

                btnlike?.setOnClickListener {
                    if(fsq?.hayToken()!!){
                        fsq?.Darleallike(lugar.id)
                    }
                }

                btncheckin?.setOnClickListener {
                    if(fsq?.hayToken()!!){
                        val edittext = EditText(context)
                        edittext.hint = "Escriba un mesaje y debe tener menos de 140 caracteres."
                        AlertDialog.Builder(requireContext())
                            .setTitle("Nuevo Check-In.").setMessage("Ingrese un mensaje.")
                            .setView(edittext)
                            .setPositiveButton("Check-In",DialogInterface.OnClickListener { dialog, which ->
                                val mensaje = URLEncoder.encode(edittext.text.toString(),"UTF-8")

                                fsq?.Darlealcheckin(lugar.id,mensaje)

                            }).setNegativeButton("Cancelar.",DialogInterface.OnClickListener { dialog, which ->

                            }).show()
                    }
                }
            }else if(mibundl.containsKey("lugar-cate")){
                index = mibundl.getInt("lugar-cate")
                var lugar = VenuesPorCategoriaFragment.vporcateg?.get(index)
                var lat = mibundl.getString("lat")
                var lon = mibundl.getString("lon")
                var id = lugar?.id



                nombre = vista?.findViewById(R.id.etinombre2)

                categorias = vista?.findViewById(R.id.eticategoria2)

                localizacion = vista?.findViewById(R.id.etilugares2)

                tips = vista?.findViewById(R.id.etitips2)

                likes = vista?.findViewById(R.id.etilikes2)

                usuarios = vista?.findViewById(R.id.etiusuario2)

                photoscount = vista?.findViewById(R.id.etifotos2)

                checkins = vista?.findViewById(R.id.eticheckins2)

                imagen = vista?.findViewById(R.id.fotorcvprincipal2)

                btnlike = vista?.findViewById(R.id.btnlike)

                btncheckin = vista?.findViewById(R.id.btnCkeck_In)


                nombre?.text = lugar?.name

                categorias?.text = lugar?.categories?.getOrNull(0)?.name

                localizacion?.text =
                    lugar?.location?.city + "," + lugar?.location?.state + "," +lugar?.location?.country + "."

                tips?.text = lugar?.estadisticas?.get(0)?.stats?.tipCount.toString() + " " + "Tips."

                likes?.text = lugar?.estadisticas?.get(0)?.likes?.count.toString() + " " + "Likes."

                usuarios?.text = lugar?.estadisticas?.get(0)?.beenHere?.count.toString() + " " + "Usuarios"

                photoscount?.text = lugar?.estadisticas?.get(0)?.photos?.count.toString() + " " + "Fotos."

                checkins?.text = lugar?.estadisticas?.get(0)?.listed?.count.toString() + " " + "Checkins"

                Picasso.get().load(lugar?.imagePreview).placeholder(R.drawable.placeholder_venue).into(imagen)

                btnlike?.setOnClickListener {
                    if(fsq?.hayToken()!!){
                        fsq?.Darleallike(lugar?.id!!)
                    }
                }

                btncheckin?.setOnClickListener {
                    if (fsq?.hayToken()!!) {
                        val edittext = EditText(context)
                        edittext.hint = "Escriba un mesaje y debe tener menos de 140 caracteres."
                        AlertDialog.Builder(requireContext())
                            .setTitle("Nuevo Check-In.").setMessage("Ingrese un mensaje.")
                            .setView(edittext)
                            .setPositiveButton(
                                "Check-In",
                                DialogInterface.OnClickListener { dialog, which ->
                                    val mensaje =
                                        URLEncoder.encode(edittext.text.toString(), "UTF-8")

                                    fsq?.Darlealcheckin(lugar?.id!!, mensaje)

                                }).setNegativeButton(
                                "Cancelar.",
                                DialogInterface.OnClickListener { dialog, which ->

                                }).show()
                    }
                }
            }else if(mibundl.containsKey("lugar-Liked")){
                index = mibundl.getInt("lugar-Liked")
                var lugar = FavoritosFragment.venuesFavoritos?.get(index)
                var lat = mibundl.getString("lat")
                var lon = mibundl.getString("lon")
                var id = lugar?.id



                nombre = vista?.findViewById(R.id.etinombre2)

                categorias = vista?.findViewById(R.id.eticategoria2)

                localizacion = vista?.findViewById(R.id.etilugares2)

                tips = vista?.findViewById(R.id.etitips2)

                likes = vista?.findViewById(R.id.etilikes2)

                usuarios = vista?.findViewById(R.id.etiusuario2)

                photoscount = vista?.findViewById(R.id.etifotos2)

                checkins = vista?.findViewById(R.id.eticheckins2)

                imagen = vista?.findViewById(R.id.fotorcvprincipal2)

                btnlike = vista?.findViewById(R.id.btnlike)

                btncheckin = vista?.findViewById(R.id.btnCkeck_In)


                nombre?.text = lugar?.name

                categorias?.text = lugar?.categories?.getOrNull(0)?.name

                localizacion?.text =
                    lugar?.location?.city + "," + lugar?.location?.state + "," +lugar?.location?.country + "."

                tips?.text = lugar?.estadisticas?.get(0)?.stats?.tipCount.toString() + " " + "Tips."

                likes?.text = lugar?.estadisticas?.get(0)?.likes?.count.toString() + " " + "Likes."

                usuarios?.text = lugar?.estadisticas?.get(0)?.beenHere?.count.toString() + " " + "Usuarios"

                photoscount?.text = lugar?.estadisticas?.get(0)?.photos?.count.toString() + " " + "Fotos."

                checkins?.text = lugar?.estadisticas?.get(0)?.listed?.count.toString() + " " + "Checkins"

                Picasso.get().load(lugar?.imagePreview).placeholder(R.drawable.placeholder_venue).into(imagen)

                btnlike?.setOnClickListener {
                    if(fsq?.hayToken()!!){
                        fsq?.Darleallike(lugar?.id!!)
                    }
                }

                btncheckin?.setOnClickListener {
                    if (fsq?.hayToken()!!) {
                        val edittext = EditText(context)
                        edittext.hint = "Escriba un mesaje y debe tener menos de 140 caracteres."
                        AlertDialog.Builder(requireContext())
                            .setTitle("Nuevo Check-In.").setMessage("Ingrese un mensaje.")
                            .setView(edittext)
                            .setPositiveButton(
                                "Check-In",
                                DialogInterface.OnClickListener { dialog, which ->
                                    val mensaje =
                                        URLEncoder.encode(edittext.text.toString(), "UTF-8")

                                    fsq?.Darlealcheckin(lugar?.id!!, mensaje)

                                }).setNegativeButton(
                                "Cancelar.",
                                DialogInterface.OnClickListener { dialog, which ->

                                }).show()
                    }
                }
            }
        }



    }

    fun initToolbar(){

        var activity:AppCompatActivity = activity as AppCompatActivity

        tb = vista?.findViewById(R.id.tbdeta)

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