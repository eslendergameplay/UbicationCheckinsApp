package com.example.CheckinsApp.Mensaje

import android.content.Context
import android.widget.Toast

class Mensaje {
    companion object{
        fun mensajeSuccess(context:Context,mensajes: Mensajes){
            var str = ""
            when(mensajes){
                Mensajes.Rationale ->{
                    str = "Requiere permisos para obtener ubicaion."
                }
                Mensajes.Checkin_Succes ->{
                    str = "Has dado Visitado el lugar con exito."
                }
            }
            Toast.makeText(context,str,Toast.LENGTH_SHORT).show()
        }

        fun mensajeError(context: Context,error: Errores){
            var mensaje = ""
            when(error){
                Errores.no_hay_Red ->{
                    mensaje = "No se detecto una conexion a intenet Disponible."
                }
                Errores.Http_Error ->{
                    mensaje = "Hubo un problema en la solicitud http.Intentalo mas tarde o despues."
                }
                Errores.no_hay_App_fsqr ->{
                    mensaje = "No tienes instalada la app de Foursqaure."
                }

                Errores.Error_conexion_fsqr ->{
                    mensaje = "No se pudo completar la conexion a Foursquare"
                }

                Errores.Error_ITK ->{
                    mensaje = "No se pudo completar el intercambio de token en Foursquare"
                }

                Errores.Error_guar_tk -> {
                    mensaje = "No se pudo guardar el token"
                }

                Errores.Error_Permiso_Negado ->{
                    mensaje =" No diste el permiso para obtener la ubicacion"
                }

                Errores.Error_Oauth -> {

                }

                Errores.Error_Query ->{
                    mensaje = "Hubo un problema en la solicitud a la Api."
                }
            }
            Toast.makeText(context,mensaje,Toast.LENGTH_SHORT).show()
        }

        fun mensajeError(context: Context,error: String){
            Toast.makeText(context,error,Toast.LENGTH_SHORT).show()
        }
    }
}