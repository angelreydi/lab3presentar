package layout

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.emergentes.laboratorio3.MainActivity
import com.emergentes.laboratorio3.R
import com.emergentes.laboratorio3.comidaApplication
import java.util.Date

class comidaAdapter (ctx: Context, private val comidaModel: MutableList<comida> ): RecyclerView.Adapter<comidaAdapter.comidasViewHolder>(){
    inner class comidasViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var imageUrl = itemView.findViewById<ImageView>(R.id.imageLogo)
        var titulo = itemView.findViewById<TextView>(R.id.txtTitulo)
        var descripcion = itemView.findViewById<TextView>(R.id.txtDescripcion)
        var btnEliminar= itemView.findViewById<Button>(R.id.btnEliminar)
        var btnModificar= itemView.findViewById<Button>(R.id.btnModificar)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): comidasViewHolder {
        val v = LayoutInflater.from (parent.context).inflate(R.layout.item_view,parent,false)
        return comidasViewHolder(v)
    }
    override fun getItemCount(): Int {
        return comidaModel.size
    }
    override fun onBindViewHolder(holder: comidasViewHolder, position: Int) {
        val i = comidaModel[position]
        holder.titulo.text = i.nombre
        holder.descripcion.text = i.descripcion
//control de imagen vacia o inexistente
        val imageUrl = i.imagen
     /*   if (imageUrl.isNullOrEmpty()) {
            // Si no hay imagen, carga un recurso predeterminado o muestra un mensaje de error
            Glide.with(holder.itemView.context)
                .load(R.drawable.torta) // Reemplaza por tu recurso predeterminado
                .into(holder.imageUrl)
        } else {
            // Cargar la imagen si la URL es válida
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .centerCrop()
                .into(holder.imageUrl)
        }*/
        //ejemplo del ing
        //Glide.with(holder.itemView.context)
            //.load(i.imagen,)
          //  .centerCrop()
//.placeholder(R.drawable.loading_spinner)
        //    .into(holder.imageUrl)

//eliminar la comida
        holder.btnEliminar.setOnClickListener {
            // Captura los datos de la comida correspondiente y muestra un log
            Log.i("eliminar", "Comida seleccionada:id:${i.id} con posicion $position")
            var idDelete=i.id

            //(holder.itemView.context as MainActivity).removeComida(position)
            val comidaDao = comidaApplication.db.comidaDao()

            notifyItemRemoved(idDelete.toInt())
            notifyItemRangeChanged(position, comidaModel.size) // Ajustar las posiciones
            comidaDao.deleteComidaById(idDelete)
            println(comidaModel.toString())
        }
        holder.btnModificar.setOnClickListener(){
            val adapterPosition = holder.adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                val comida = comidaModel[adapterPosition]

                val dialog = AlertDialog.Builder(holder.itemView.context)
                val inflater = LayoutInflater.from(holder.itemView.context)
                val dialogView = inflater.inflate(R.layout.item_modificar, null)

                // **Prellenar campos con datos existentes**
                val editTextNuevoNombre = dialogView.findViewById<EditText>(R.id.editTextNuevoNombre)
                val editTextNuevaDescripcion = dialogView.findViewById<EditText>(R.id.editTextNuevaDescripcion)
                val editTextNuevoPrecio = dialogView.findViewById<EditText>(R.id.editTextNuevoPrecio)

                editTextNuevoNombre.setText(comida.nombre)
                editTextNuevaDescripcion.setText(comida.descripcion)
                editTextNuevoPrecio.setText(comida.precio.toString())

                dialog.setView(dialogView)

                dialog.setPositiveButton("Guardar") { _, _ ->
                    // **Obtener los nuevos datos ingresados**
                    val nuevoNombre = editTextNuevoNombre.text.toString()
                    val nuevaDescripcion = editTextNuevaDescripcion.text.toString()
                    val nuevoPrecio = editTextNuevoPrecio.text.toString()

                    // **Modificar datos en la lista y en la base de datos**
                    val nuevaComida = comida(comida.id, nuevoNombre, nuevaDescripcion, nuevoPrecio.toInt(), "")
                    comidaModel[adapterPosition] = nuevaComida
                    notifyItemChanged(adapterPosition) // Notificar cambio en la vista

                    val personasDao = comidaApplication.db.comidaDao()
                    personasDao.updateComida(nuevaComida) // Actualizar en la base de datos
                }

                dialog.setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.dismiss() // Cerrar sin cambios
                }

                dialog.show()
            }

        }//cierra onclick
    }

}
