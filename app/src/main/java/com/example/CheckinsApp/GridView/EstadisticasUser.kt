package com.example.CheckinsApp.GridView

class EstadisticasUser (numero:Int,imagen:Int,campo:String,color:Int){
    var numero:Int = -1
    var imagen:Int = -1
    var campo:String = ""
    var color:Int = -1

    init{
        this.color = color
        this.numero = numero
        this.imagen = imagen
        this.campo = campo
    }
}