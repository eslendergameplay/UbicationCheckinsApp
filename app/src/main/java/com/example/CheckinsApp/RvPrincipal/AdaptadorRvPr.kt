package com.example.CheckinsApp.RvPrincipal

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.CheckinsApp.Foursqaure.Venue
import com.example.nuevokt3.R
import com.squareup.picasso.Picasso

class AdaptadorRvPr(
    items:ArrayList<Venue>,
    var listener:ClickListener,
    var longClickListener: com.example.CheckinsApp.RvCategoria.LongClickListener
):RecyclerView.Adapter<AdaptadorRvPr.ViewHolder>() {

    var items:ArrayList<Venue>? = null
    var multiSeleccion = false

    var itemsSeleccionados:ArrayList<Int>? = null
    var viewHolder:ViewHolder? = null

    init{
        this.items = items
        itemsSeleccionados = ArrayList()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.plano_rv_principal,parent,false)
        viewHolder = ViewHolder(vista,listener,longClickListener)

        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return items?.count()!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items?.get(position)


        holder.nombre?.text = item?.name

        holder.categorias?.text = item?.categories?.getOrNull(0)?.name

        holder.localizacion?.text =
            item?.location?.city + "," + item?.location?.state + "," +item?.location?.country + "."

        holder.tips?.text = item?.estadisticas?.get(0)?.stats?.tipCount.toString() + " " + "Tips."

        holder.likes?.text = item?.estadisticas?.get(0)?.likes?.count.toString() + " " + "Likes."

        holder.usuarios?.text = item?.estadisticas?.get(0)?.beenHere?.count.toString() + " " + "Usuarios"

        holder.photoscount?.text = item?.estadisticas?.get(0)?.photos?.count.toString() + " " + "Fotos."

        holder.checkins?.text = item?.estadisticas?.get(0)?.listed?.count.toString() + " " + "Checkins"

        Picasso.get().load(item?.imagePreview).placeholder(R.drawable.placeholder_venue).into(holder.imagen)

        Picasso.get().load(item?.iconPreview).placeholder(R.drawable.etiquetas).into(holder.imagenicono)

        if (itemsSeleccionados?.contains(position)!!) {
            holder.vista.setBackgroundColor(Color.LTGRAY)
        } else {
            holder.vista.setBackgroundColor(Color.WHITE)
        }
    }

    class ViewHolder(
        vista: View,
        listener: ClickListener,
        longClickListener: com.example.CheckinsApp.RvCategoria.LongClickListener
    ):RecyclerView.ViewHolder(vista),View.OnClickListener,View.OnLongClickListener{

        var vista = vista

        var nombre:TextView? = null

        var categorias:TextView? = null

        var localizacion:TextView? = null

        var tips:TextView? =  null

        var likes:TextView? = null

        var usuarios:TextView? = null

        var photoscount:TextView? = null

        var checkins:TextView? = null

        var imagen:ImageView? = null

        var imagenicono:ImageView? = null

        var listener:ClickListener? = null

        var longlistener:com.example.CheckinsApp.RvCategoria.LongClickListener? = null

        init {

            nombre = vista.findViewById(R.id.etinombre)

            categorias = vista.findViewById(R.id.eticategoria)

            localizacion = vista.findViewById(R.id.etilugares)

            tips = vista.findViewById(R.id.etitips)

            likes = vista.findViewById(R.id.etilikes)

            usuarios = vista.findViewById(R.id.etiusuario)

            photoscount = vista.findViewById(R.id.etifotos)

            checkins = vista.findViewById(R.id.eticheckins)

            imagen = vista.findViewById(R.id.fotorcvprincipal)

            imagenicono = vista.findViewById(R.id.fotocateg)

            this.listener = listener
            this.longlistener = longClickListener
            vista.setOnClickListener(this)
            vista.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!,adapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            this.longlistener?.longClick(v!!,adapterPosition)
            return true
        }

    }


}