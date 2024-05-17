package com.example.CheckinsApp.UtidadesNUB

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.CheckinsApp.Mensaje.Errores
import com.example.CheckinsApp.Mensaje.Mensaje
import com.example.CheckinsApp.interfaces.HttpPostResponse
import com.example.CheckinsApp.interfaces.HttpPostResponse2
import com.example.CheckinsApp.interfaces.HttpResponse
import com.example.CheckinsApp.interfaces.HttpResponse2
import com.example.CheckinsApp.interfaces.HttpResponse3
import com.example.CheckinsApp.interfaces.HttpResponse4
import com.example.CheckinsApp.interfaces.HttpResponse5
import com.example.CheckinsApp.interfaces.HttpResponse6

class Network(var activity:AppCompatActivity) {

    fun hayRed():Boolean{
        val  CM = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val NTInfo = CM.activeNetwork?: return false
        val actm = CM.getNetworkCapabilities(NTInfo)?: return false

        return when{
            actm.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actm.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            //for other device how are able to connect with Ethernet
            actm.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            //for check internet over Bluetooth
            actm.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
        }
    }

    fun HttpResquest(context: Context, url:String, httpResponse: HttpResponse){
        if(hayRed()){
            val queue = Volley.newRequestQueue(context)


            val solicitud = StringRequest(Request.Method.GET,url, Response.Listener {
                response ->

                   httpResponse.httpResponseSuccess(response)

            },Response.ErrorListener {
                error ->

                Log.d("Error",error.message.toString())
                Mensaje.mensajeError(context, Errores.no_hay_Red)

            })

            queue.add(solicitud)
        }else{
            Mensaje.mensajeError(context,Errores.Http_Error)
        }


    }

    fun HttpResquestStats(context: Context, url:String, httpResponse2: HttpResponse2){
        if(hayRed()){
            val queue = Volley.newRequestQueue(context)


            val solicitud = StringRequest(Request.Method.GET,url, {
                    response ->

                httpResponse2.httpResponseSuccessStats(response)

            }, {
                    error ->

                Log.d("Error",error.message.toString())
                Mensaje.mensajeError(context, Errores.no_hay_Red)

            })

            queue.add(solicitud)
        }else{
            Mensaje.mensajeError(context,Errores.Http_Error)
        }


    }

    fun HttpRequestCategories(context: Context, url:String, httpResponse3: HttpResponse3){
        if(hayRed()){
            val queue = Volley.newRequestQueue(context)


            val solicitud = StringRequest(Request.Method.GET,url, {
                    response ->

                httpResponse3.httpSuccess3(response)

            }, {
                    error ->

                Log.d("Error",error.message.toString())
                Mensaje.mensajeError(context, Errores.no_hay_Red)

            })

            queue.add(solicitud)
        }else{
            Mensaje.mensajeError(context,Errores.Http_Error)
        }


    }

    fun HttpRequestVPorCategories(context: Context, url:String, httpResponse4: HttpResponse4){
        if(hayRed()){
            val queue = Volley.newRequestQueue(context)


            val solicitud = StringRequest(Request.Method.GET,url, {
                    response ->

                httpResponse4.httpSuccessVPCate(response)

            }, {
                    error ->

                Log.d("Error",error.message.toString())
                Mensaje.mensajeError(context, Errores.no_hay_Red)

            })

            queue.add(solicitud)
        }else{
            Mensaje.mensajeError(context,Errores.Http_Error)
        }


    }

    fun HttpRequestVPorLikes(context: Context, url:String, httpResponse5: HttpResponse5){
        if(hayRed()){
            val queue = Volley.newRequestQueue(context)


            val solicitud = StringRequest(Request.Method.GET,url, {
                    response ->

                httpResponse5.httpSuccessVPLikes(response)

            }, {
                    error ->

                Log.d("Error",error.message.toString())
                Mensaje.mensajeError(context, Errores.no_hay_Red)

            })

            queue.add(solicitud)
        }else{
            Mensaje.mensajeError(context,Errores.Http_Error)
        }


    }

    fun HttpRequestUsuarioDetalles(context: Context, url:String, httpResponse6: HttpResponse6){
        if(hayRed()){
            val queue = Volley.newRequestQueue(context)


            val solicitud = StringRequest(Request.Method.GET,url, {
                    response ->

                httpResponse6.httpSuccessUsuario(response)

            }, {
                    error ->

                Log.d("Error",error.message.toString())
                Mensaje.mensajeError(context, Errores.no_hay_Red)

            })

            queue.add(solicitud)
        }else{
            Mensaje.mensajeError(context,Errores.Http_Error)
        }


    }

    fun HttpPost(context: Context,url:String,httpPost:HttpPostResponse){
        if(hayRed()){
            val queue = Volley.newRequestQueue(context)

            val solicitud = StringRequest(Request.Method.POST,url, {
                response->
                                                                   httpPost.httpPostSuccess(response)


            }, {
                error ->
                Log.d("Error",error.message.toString())
                Mensaje.mensajeError(context, Errores.no_hay_Red)
            })

            queue.add(solicitud)
        }else{
            Mensaje.mensajeError(context,Errores.Http_Error)
        }
    }

    fun HttpPostCheckin(context: Context,url:String,httpPost:HttpPostResponse2){
        if(hayRed()){
            val queue = Volley.newRequestQueue(context)

            val solicitud = StringRequest(Request.Method.POST,url, {
                    response->
                httpPost.httpPostSuccess2(response)


            }, {
                    error ->
                Log.d("Error",error.message.toString())
                Mensaje.mensajeError(context, Errores.no_hay_Red)
            })

            queue.add(solicitud)
        }else{
            Mensaje.mensajeError(context,Errores.Http_Error)
        }
    }

}