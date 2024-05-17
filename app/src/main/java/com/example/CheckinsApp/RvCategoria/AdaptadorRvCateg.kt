package com.example.CheckinsApp.RvCategoria

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.CheckinsApp.Foursqaure.Category
import com.example.CheckinsApp.RvPrincipal.ClickListener
import com.example.nuevokt3.R
import com.squareup.picasso.Picasso

class AdaptadorRvCateg(items:ArrayList<Category>, var listener: ClickListener, var longClickListener: LongClickListener)
    :RecyclerView.Adapter<AdaptadorRvCateg.ViewHolder>(){
        var items:ArrayList<Category>? = null
    var multiSeleccion = false
    var itemsSeleccionados:ArrayList<Int>? = null
    var viewHolder: AdaptadorRvCateg.ViewHolder? = null

    init{
        this.items = items
        itemsSeleccionados = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val vista = LayoutInflater.from(parent.context).inflate(R.layout.planorvcateg,parent,false)
        var viewHolder = AdaptadorRvCateg.ViewHolder(vista, listener, longClickListener)

        return viewHolder
    }

    override fun getItemCount(): Int {
        return items?.count()!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item = items?.get(position)

        holder.eticateg?.text = item?.name

        Picasso.get().load(item?.iconPreview).placeholder(R.drawable.etiquetas).into(holder.fotocateg)

        if (itemsSeleccionados?.contains(position)!!) {
            holder.vista.setBackgroundColor(Color.LTGRAY)
        } else {
            holder.vista.setBackgroundColor(Color.WHITE)
        }

    }

    class ViewHolder (vista: View,listener: ClickListener,longClickListener: com.example.CheckinsApp.RvCategoria.LongClickListener):RecyclerView.ViewHolder(vista),View.OnClickListener,View.OnLongClickListener{

        var vista = vista

        var eticateg:TextView? = null

        var fotocateg:ImageView? = null

        var listener:ClickListener? = null

        var Longlistener:LongClickListener? = null

        init{
            eticateg = vista.findViewById(R.id.etirvcategoria)

            fotocateg = vista.findViewById(R.id.fotorvcategoria)

            this.listener = listener
            this.Longlistener = longClickListener

            vista.setOnClickListener(this)
            vista.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!,adapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            this.Longlistener?.longClick(v!!,adapterPosition)
            return true
        }

    }
}
