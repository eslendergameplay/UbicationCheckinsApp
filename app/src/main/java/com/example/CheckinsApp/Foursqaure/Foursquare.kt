package com.example.CheckinsApp.Foursqaure

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.CheckinsApp.Mensaje.Errores
import com.example.CheckinsApp.Mensaje.Mensaje
import com.example.CheckinsApp.Mensaje.Mensajes
import com.example.CheckinsApp.UtidadesNUB.Network
import com.example.CheckinsApp.interfaces.CategoriesIT
import com.example.CheckinsApp.interfaces.HttpPostResponse
import com.example.CheckinsApp.interfaces.HttpPostResponse2
import com.example.CheckinsApp.interfaces.HttpResponse
import com.example.CheckinsApp.interfaces.HttpResponse2
import com.example.CheckinsApp.interfaces.HttpResponse3
import com.example.CheckinsApp.interfaces.HttpResponse4
import com.example.CheckinsApp.interfaces.HttpResponse5
import com.example.CheckinsApp.interfaces.HttpResponse6
import com.example.CheckinsApp.interfaces.ObtenerUsuarioIT
import com.example.CheckinsApp.interfaces.ObtenerVenuesCategIt
import com.example.CheckinsApp.interfaces.ObtenerVenuesIT
import com.example.CheckinsApp.interfaces.Stats
import com.example.CheckinsApp.interfaces.StatsIT
import com.example.CheckinsApp.interfaces.obtenerVenuesLikesIT
import com.foursquare.android.nativeoauth.FoursquareOAuth
import com.google.gson.Gson

class Foursquare(var activity:AppCompatActivity, var activityDestino:AppCompatActivity) {
    private val codigo_codigo_Conexion:Int = 200

    private val codigo_intercambio_Token = 201

    private val Client_ID = "2444HCU0VONNWRTPA5N1WQKLGC2KESLEPENOKCBV0HYHT5UM"

    private val Client_Secret = "MVC3P01EOTLAVXNDS1PTE3I0KZFIKVKVELZ55FSDGZJ3XM3R"

    private val Settings = "settings"

    val acceso = "ACCESS_TK"

    private val url_base = "https://api.foursquare.com/v2/"

    private val url_base2 = "https://api.foursquare.com/v2/"


    init {

    }

    fun iniciarSesion(){
        val intent = FoursquareOAuth.getConnectIntent(this.activity.applicationContext,Client_ID)

        if(FoursquareOAuth.isPlayStoreIntent(intent)){
            Mensaje.mensajeError(this.activity.applicationContext, Errores.no_hay_App_fsqr)
            this.activity.startActivityIfNeeded(intent,codigo_codigo_Conexion)
        }else{
            this.activity.startActivityIfNeeded(intent,codigo_codigo_Conexion)
        }
    }

    fun validarActivityResult(requestCode:Int,resultCode: Int, data: Intent?){

        when(requestCode){
            codigo_codigo_Conexion ->{
                conexionCompleta(resultCode,data)
            }

            codigo_intercambio_Token ->{
                intercambioTokenCompleta(resultCode,data)
            }
        }
    }

    fun conexionCompleta(resultCode: Int,data: Intent?){
        val codigoRespuesta = FoursquareOAuth.getAuthCodeFromResult(resultCode,data)
        val excepcion = codigoRespuesta.exception
        Log.d("Code",codigoRespuesta.code)

        if(excepcion == null){
            val codigo = codigoRespuesta.code
            realizarIntercambioToken(codigo)
        }else{
            Mensaje.mensajeError(this.activity.applicationContext,Errores.Error_conexion_fsqr)
            Log.d("exception",excepcion.toString())
        }
    }

    fun intercambioTokenCompleta(resultCode: Int,data: Intent?){
        val respuestaTK = FoursquareOAuth.getTokenFromResult(resultCode,data)

        val exception = respuestaTK.exception

        if(exception == null){
            val accessToken = respuestaTK.accessToken
            if(!guardarToken(accessToken)){
                Mensaje.mensajeError(this.activity.applicationContext,Errores.Error_guar_tk)
                navegarSiguienteActividad(activityDestino)

            }else{
                navegarSiguienteActividad(activityDestino)
            }
        }else{
            Mensaje.mensajeError(this.activity.applicationContext,Errores.Error_ITK)
        }
    }

    private fun realizarIntercambioToken(codigo: String?) {
        val intent = FoursquareOAuth.getTokenExchangeIntent(this.activity.applicationContext,Client_ID,Client_Secret,codigo)

        Log.d("TAG",intent.toString())
        activity.startActivityIfNeeded(intent,codigo_intercambio_Token)

    }

    fun hayToken():Boolean{
        if(obtenerToken() == ""){
            return false
        }else{
            return true
        }
    }

    fun obtenerToken():String{
        val settings = this.activity.getSharedPreferences(Settings,0)
        val token = settings.getString(acceso,"")

        return token!!
    }

    fun guardarToken(token:String):Boolean{
        if(token.isEmpty()){
            return false
        }
        val settings = activity.getSharedPreferences(Settings,0)

        val editor = settings.edit()

        editor.putString(acceso,token)

        editor.apply()

        return true
    }

    fun cerrarSesion(){
        val settings = activity.getSharedPreferences(Settings,0)
        val editor = settings.edit()
        editor.putString(acceso,"")
        editor.apply()
    }

    fun navegarSiguienteActividad(activityDestino: AppCompatActivity){
        this.activity.startActivity(Intent(this.activity,activityDestino::class.java))
    }

    fun obtenervenues(lat:String,lon:String,obtenerVenuesIT:ObtenerVenuesIT){
        val network = Network(activity)
        val seccion = "venues/"
        val metodo = "search/?"
        val token = "oauth_token=" + "YXEF14HU3U1ZFLIBRKLYKZNAULVALFW4ROSNP2POYMMPWZSS"
        Log.d("Token",obtenerToken())
        val ll = "ll=$lat,$lon"
        val version = "v=20240426"
        var url:String = url_base + seccion + metodo + token + "&" + ll + "&" + version
        network.HttpResquest(activity.applicationContext,url,object:HttpResponse{
            override fun httpResponseSuccess(response: String) {
                Log.d("Json",response)
                var gson = Gson()
                var oR = gson.fromJson(response,FoursquareApiRV::class.java)

                var meta = oR.meta
                var venues = oR.response?.venues!!




                if(meta?.code == 200){
                    for(venue in venues) {
                        obtenerVenueStats(venue.id, object : StatsIT {
                            override fun statsgenerado(stats: ArrayList<ItemFoto>) {
                                if (stats.count() > 0) {
                                    val urlImagen = construirUrlImagen(stats)
                                    venue.imagePreview = urlImagen

                                }

                            }


                        }, object : Stats {
                            override fun statsgeneradas(a: ArrayList<VenueStat>) {
                                if(a.count() > 0){
                                    val estadist = a
                                    venue.estadisticas = estadist
                                }
                            }

                        })
                        if(venue.categories?.count()!! > 0){
                            val urlicono = construirUrlImagenICono(venue.categories?.get(0)!!)
                            venue.iconPreview = urlicono
                        }

                    }
                    obtenerVenuesIT.venuesGenerados(venues)
                }else if(meta?.code == 400){
                    Mensaje.mensajeError(activity.applicationContext,meta.errorDetail)
                }else{
                    Mensaje.mensajeError(activity.applicationContext,Errores.Error_Query)
                }
            }

        })
    }

    fun obtenervenuesPorCate(lat:String,lon:String,categoriaid:String,obtenerVenuesCategIT:ObtenerVenuesCategIt) {
        val network = Network(activity)
        val seccion = "venues/"
        val metodo = "search/?"
        val categoriaidgener = "categoryId=" + categoriaid
        val token = "oauth_token=" + "YXEF14HU3U1ZFLIBRKLYKZNAULVALFW4ROSNP2POYMMPWZSS"
        Log.d("Token", obtenerToken())
        val ll = "ll=$lat,$lon"
        val version = "v=20240427"
        var url: String = url_base + seccion + metodo + ll + "&" + categoriaidgener + "&" + token + "&" + version
        Log.d("Url-Cate",url)
        network.HttpRequestVPorCategories(activity.applicationContext, url, object : HttpResponse4 {
            override fun httpSuccessVPCate(response: String) {
                Log.d("Json", response)
                var gson = Gson()
                var oR = gson.fromJson(response, FoursquareApiVenuesPorCategoria::class.java)

                var meta = oR.meta
                var venues = oR.response?.venues!!




                if (meta?.code == 200) {
                    for (venue in venues) {
                        obtenerVenueStats(venue.id, object : StatsIT {
                            override fun statsgenerado(stats: ArrayList<ItemFoto>) {
                                if (stats.count() > 0) {
                                    val urlImagen = construirUrlImagen(stats)
                                    venue.imagePreview = urlImagen

                                }

                            }


                        }, object : Stats {
                            override fun statsgeneradas(a: ArrayList<VenueStat>) {
                                if (a.count() > 0) {
                                    val estadist = a
                                    venue.estadisticas = estadist
                                }
                            }

                        })
                        if (venue.categories?.count()!! > 0) {
                            val urlicono = construirUrlImagenICono(venue.categories?.get(0)!!)
                            venue.iconPreview = urlicono
                        }

                    }
                    obtenerVenuesCategIT.venuesporcate(venues)
                } else if (meta?.code == 400) {
                    Mensaje.mensajeError(activity.applicationContext, meta.errorDetail)
                } else {
                    Mensaje.mensajeError(activity.applicationContext, Errores.Error_Query)
                }
            }
        })
    }

    fun obtenerLikesPorLike(obtenerVenuesLikesIT:obtenerVenuesLikesIT){
        val network = Network(activity)
        val seccion = "users/"
        val usuario = "self/"
        val metodo = "venuelikes?"
        val token = "oauth_token=" + obtenerToken()
        var version = "v=20240428"
        var url = url_base + seccion + usuario + "/" + metodo + token + "&" + version
        network.HttpRequestVPorLikes(activity.applicationContext,url,object: HttpResponse5{
            override fun httpSuccessVPLikes(response: String) {
                var gson = Gson()
                var oR = gson.fromJson(response,FoursquareApiLikesPorCategoria::class.java)

                var meta = oR.meta
                var venuesLiked = oR.response?.venues?.items!!

                if(meta?.code == 200){
                    for (venue in venuesLiked) {
                        obtenerVenueStats(venue.id, object : StatsIT {
                            override fun statsgenerado(stats: ArrayList<ItemFoto>) {
                                if (stats.count() > 0) {
                                    val urlImagen = construirUrlImagen(stats)
                                    venue.imagePreview = urlImagen

                                }

                            }


                        }, object : Stats {
                            override fun statsgeneradas(a: ArrayList<VenueStat>) {
                                if (a.count() > 0) {
                                    val estadist = a
                                    venue.estadisticas = estadist
                                }
                            }

                        })
                        if (venue.categories?.count()!! > 0) {
                            val urlicono = construirUrlImagenICono(venue.categories?.get(0)!!)
                            venue.iconPreview = urlicono
                        }

                    }
                    obtenerVenuesLikesIT.venuesLKgenerados(venuesLiked)
                }else if(meta?.code == 400){
                    Mensaje.mensajeError(activity.applicationContext, meta.errorDetail)
                }else{
                    Mensaje.mensajeError(activity.applicationContext, Errores.Error_Query)
                }
            }

        })
    }


    /*
    fun obtenervenuesPlaces(lat:String,lon:String,obtenerPlacesIT:ObtenerPlacesIT) {
        val network = Network(activity)
        val seccion = "places/"
        val metodo = "search/?"
        val token = "oauth_token=" + "fsq3hOSaMCaiMv/YPP+58vhyEn+cbvl9bbr71fzWt5J44Ng="
        //+ obtenerToken()
        val ll = "ll=" + lat + "," + lon
        val version = "v=20240423"
        var url: String = url_base2 + seccion + metodo + token + "&" + ll + "&" + version
        network.HttpResquest(activity.applicationContext, url, object : HttpResponse {
            override fun httpResponseSuccess(response: String) {
                Log.d("Json2",response)
            }


        })

    }

     */

    fun construirUrlImagenICono(categoria: Category): String {
        if (categoria.icon?.prefix?.isNotEmpty()!! && categoria.icon?.suffix?.isNotEmpty()!!) {
            val prefix = categoria.icon?.prefix
            val suffix = categoria.icon?.suffix
            val size = "32"
            val token = "?oauth_token=" + "YXEF14HU3U1ZFLIBRKLYKZNAULVALFW4ROSNP2POYMMPWZSS"
            val version = "v=20240426"

            val url = prefix + size + suffix + token + "&" + version
            Log.d("Tag", url) // Log the constructed URL
            return url // Return the constructed URL
        } else {
            // Handle case with no photos (e.g., return empty string)
            return ""
        }
    }

    fun construirUrlImagen(statss: ArrayList<ItemFoto>): String {
        if (statss.isNotEmpty()) {
            val prefix = statss.get(0).items?.get(0)?.prefix ?: ""
            val suffix = statss.get(0).items?.get(0)?.suffix ?: ""
            val size = "400x200"
            val token = "?oauth_token=" + "YXEF14HU3U1ZFLIBRKLYKZNAULVALFW4ROSNP2POYMMPWZSS"
            val version = "v=20240426"

            val url = prefix + size + suffix + token + "&" + version
            Log.d("Tag", url) // Log the constructed URL
            return url // Return the constructed URL
        } else {
            // Handle case with no photos (e.g., return empty string)
            return ""
        }
    }

    fun construirUrlImagen(photo:Fotos): String {
        return if(photo.prefix != "" && photo.suffix != ""){
            val prefix = photo.prefix ?: ""
            val suffix = photo.suffix ?: ""
            val size = "64"
            val token = "?oauth_token=" + obtenerToken()
            val version = "v=20240428"

            val url = prefix + size + suffix + token + "&" + version
            Log.d("Tag-FotoPerfil", url) // Log the constructed URL
            url // Return the constructed URL
        }else{
            ""
        }
    }


    fun obtenerVenueStats(id:String,statsIt:StatsIT,statsinter:Stats){
        val network = Network(activity)
        val seccion = "venues/"
        val metodo = id +"/?"
        val token = "oauth_token=" + "YXEF14HU3U1ZFLIBRKLYKZNAULVALFW4ROSNP2POYMMPWZSS"
        //+ obtenerToken()
        val version = "v=20240426"
        var url:String = url_base + seccion + metodo + token + "&" + version
        network.HttpResquestStats(activity.applicationContext,url,object:HttpResponse2{
            override fun httpResponseSuccessStats(response: String) {
                Log.d("Json3", response)
                var gson = Gson()
                var oR = gson.fromJson(response, VenuesStats::class.java)

                var meta = oR.meta
                var estadisticas = oR.response?.venue
                var stats = oR.response?.venue?.photos?.groups

                var estasts:ArrayList<VenueStat> = ArrayList()

                if (meta?.code == 200) {

                    estasts.add(estadisticas!!)
                    statsinter.statsgeneradas(estasts)
                    statsIt.statsgenerado(stats!!)

                } else if (meta?.code == 400) {
                    Mensaje.mensajeError(activity.applicationContext, meta.errorDetail)
                } else {
                    Mensaje.mensajeError(activity.applicationContext, Errores.Error_Query)
                }
            }

        })
    }

    fun obtenerCategories(categoriasIt:CategoriesIT){
        val network = Network(activity)
        val seccion = "venues/"
        val metodo = "categories?"
        var version = "v=20240426"
        val token = "oauth_token=" + "YXEF14HU3U1ZFLIBRKLYKZNAULVALFW4ROSNP2POYMMPWZSS"

        var url:String = url_base + seccion + metodo + version + "&" + token

        network.HttpRequestCategories(activity.applicationContext,url,object:HttpResponse3{
            override fun httpSuccess3(response: String) {
                var gson = Gson()
                var oR = gson.fromJson(response,FourquareApiCategories::class.java)

                var meta = oR.meta

                var categg = oR.response?.categories

                if(meta?.code == 200){
                    categoriasIt.categoriasgeneradas(categg!!)
                    for(cate in categg){
                        val urlicono = construirUrlImagenICono(cate)
                        cate.iconPreview = urlicono

                    }
                }else if(meta?.code == 400){
                    Mensaje.mensajeError(activity.applicationContext, meta.errorDetail)
                }else{
                    Mensaje.mensajeError(activity.applicationContext, Errores.Error_Query)
                }
            }

        })

    }

    fun obtenerUsuario(obtenerUsuarioIT: ObtenerUsuarioIT){
        val network = Network(activity)
        val seccion = "users/"
        val usuario = "self?"
        val token = "oauth_token=" + obtenerToken()
        var version = "v=20240428"
        var url:String = url_base + seccion + usuario + "&" + token + "&" + version
        Log.d("Url-Usuario", url)
        network.HttpRequestUsuarioDetalles(activity.applicationContext,url,object : HttpResponse6{
            override fun httpSuccessUsuario(response: String) {
                var gson = Gson()
                var oR = gson.fromJson(response,FoursqaureApiResponseUser::class.java)

                var meta = oR.meta
                var usuario4 = oR.response?.user

                if(meta?.code!! == 200){
                    val urlperfil = construirUrlImagen(usuario4?.photo!!)
                    usuario4.fotopreview = urlperfil
                    obtenerUsuarioIT.usuarioGenerado(usuario4)
                }else if(meta.code == 400){
                    Mensaje.mensajeError(activity.applicationContext, meta.errorDetail)
                }else{
                    Mensaje.mensajeError(activity.applicationContext, Errores.Error_Query)
                }
            }

        })
    }

    fun Darleallike(id:String){
        val network = Network(activity)
        val seccion = "venues/"
        val venueid = id + "/"
        val metodo = "/rate?&value=3"
        var token = "oauth_token=" + obtenerToken()
        var version = "v=20240426"
        var url:String = url_base + seccion + id + metodo + "&" + token + "&" + version
        network.HttpPost(activity.applicationContext,url,object:HttpPostResponse{
            override fun httpPostSuccess(response: String) {
                var gson = Gson()
                var oR = gson.fromJson(response,Likes::class.java)
                var meta = oR.meta

                if(meta?.code == 200){
                    Toast.makeText(activity.applicationContext,"Has dado al Like con exito.",Toast.LENGTH_SHORT).show()
                }else if(meta?.code == 400){
                    Mensaje.mensajeError(activity.applicationContext, meta.errorDetail)
                }else{
                    Mensaje.mensajeError(activity.applicationContext, Errores.Error_Query)
                }
            }

        })
    }

    fun Darlealcheckin(id:String,mensaje2:String){
        val network = Network(activity)
        val seccion = "checkins/"
        val metodo = "add?&"
        val venueid = "venueId=" +id
        var token = "oauth_token=" + obtenerToken()
        var version = "v=20240426"
        var mensajecheck = "&shout=" + mensaje2
        var url:String = url_base + seccion + metodo + venueid + mensajecheck + "&" + token + "&" + version
        network.HttpPostCheckin(activity.applicationContext,url,object : HttpPostResponse2{
            override fun httpPostSuccess2(response: String) {
                var gson = Gson()
                var oR = gson.fromJson(response,CheckinsUser::class.java)
                var meta = oR.meta

                if(meta?.code == 200){
                    Mensaje.mensajeSuccess(activity.applicationContext,Mensajes.Checkin_Succes)
                }else if(meta?.code == 400){
                    Mensaje.mensajeError(activity.applicationContext, meta.errorDetail)
                }else{
                    Mensaje.mensajeError(activity.applicationContext, Errores.Error_Query)
                }
            }

        })
    }





}