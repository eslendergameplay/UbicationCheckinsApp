package com.example.CheckinsApp.GridView

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.nuevokt3.R

class AdaptadorGridViewPerfil(var context:Context, items:ArrayList<EstadisticasUser>) : BaseAdapter() {

    var items:ArrayList<EstadisticasUser>? = null

    init{
        this.items = items
    }

    override fun getCount(): Int {
        return this.items?.count()!!
    }

    override fun getItem(position: Int): Any {
        return this.items?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var vista = convertView

        var holder:ViewHolder?



        if(vista == null){
            vista = LayoutInflater.from(context).inflate(R.layout.plano_gridview,null)
            holder = ViewHolder(vista)
            vista.tag = holder
        }else{
            holder = vista.tag as? ViewHolder
        }

        val item = items?.get(position) as EstadisticasUser

        holder?.etinombre?.text = String.format("%s %s",item.numero.toString(),item.campo)
        holder?.imagen?.setImageResource(item.imagen)
        holder?.container?.setBackgroundColor(item.color)

        return vista!!
    }

    private class ViewHolder(vista:View){
        var etinombre:TextView? = null
        var imagen:ImageView? = null
        var container:LinearLayout? = null

        init{
            container = vista.findViewById(R.id.container)
            etinombre = vista.findViewById(R.id.eticate)
            imagen = vista.findViewById(R.id.fotogeneralusuario)
        }
    }
}