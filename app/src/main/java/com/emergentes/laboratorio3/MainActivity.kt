package com.emergentes.laboratorio3

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import layout.comida
import layout.comidaAdapter
import java.util.Date

class MainActivity : AppCompatActivity() {
    private var n:Int =1
    private var b:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var botonAgregar: Button = findViewById<Button>(R.id.btnAdd)
        val r = findViewById<RecyclerView>(R.id.recycler1)
        /*val personasList = mutableListOf<comida>(
            comida(1,"saice", "MORALES", 2313526, "https://images.ctfassets.net/h6goo9gw1hh6/2sNZtFAWOdP1lmQ33VwRN3/24e953b920a9cd0ff2e1d587742a2472/1-intro-photo-final.jpg?w=1200&h=992&fl=progressive&q=70&fm=jpg"),
            comida(2,"lentejas", "FERNANDEZ", 2343551, "https://img.freepik.com/foto-gratis/retrato-persona-negra-americana-mirando-arriba_23-2148749586.jpg"),
            comida(3,"mani", "PEÑA", 2343559),
            comida(4,"torta", "CHOQUE", 2343557),
            comida(5,"sopa", "PEREZ", 2343554),
            comida(6,"arroz", "JIMENEZ", 2343552),
            comida(7,"papas fritas", "COLQUE", 2341556)
        )*/
        //val personasList:MutableList<comida> = comidaApplication.db.selectComidas()
        //val comidasAdapter = comidaAdapter(applicationContext,personasList)
        //r.adapter = comidasAdapter

        val layout = LinearLayoutManager(applicationContext)
        layout.orientation = LinearLayoutManager.VERTICAL
        r.layoutManager = layout

        //haciendo consultas
        var comidaList : List<comida> =
            comidaApplication.db.comidaDao().selectComidas()//conectando a la bd
        //mostrar por el adapter
        val comidasAdapter = comidaAdapter(applicationContext,(comidaList as MutableList<comida>))
        r.adapter = comidasAdapter

        for (p in comidaList){
            println(p.nombre)
        }

        botonAgregar.setOnClickListener(){
          showInputDialog(this)
          println("cuadro de dialogo")
        }
    //insertando datos comentado pq se inserta muchas veces
    //   val p = comida(8,"panchitos","pan un salchi",12,"no tiene")
      //  comidaApplication.db.comidaDao().insertComida(p)

    }
    fun showInputDialog(context: Context) {
        // Crear un EditText para capturar el texto del usuario
        val editText = EditText(context)
        val editTextDesc = EditText(context)
        val editTextPrecio = EditText(context)

        // Configurar un diálogo con un EditText
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle("Ingrese datos de la comida")


        // Configurar el EditText en un LinearLayout para que se muestre correctamente en el diálogo
        //para el nombre
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(30, 5, 30, 30) // Agregar algo de espacio alrededor del EditText
        //agregar los edit text al layout
        layout.addView(editText) // Añadir el EditText del nombre
        layout.addView(editTextDesc) // Añadir el EditText de la descripcion
        layout.addView(editTextPrecio) // Añadir el EditText de la descripcion

        editText.hint= "nombre"
        editTextDesc.hint = "descripcion"
        editTextPrecio.hint = "precio"
        dialogBuilder.setView(layout) // Establecer el layout en el diálogo



        // Botón para aceptar el texto ingresado
        dialogBuilder.setPositiveButton("Aceptar") { dialog, _ ->
            val nombre = editText.text.toString() // Obtener el texto ingresado
            val descripcion = editTextDesc.text.toString()
            val precio = editTextPrecio.text.toString()
            //Toast.makeText(context, "Texto ingresado: $userInput", Toast.LENGTH_SHORT).show() // Mostrar un mensaje emergente
            val nuevaComida=comida(Date().time,nombre, descripcion, precio.toInt())

            notifyItemChanged(adapterPosition) // Notificar cambio en la vista
            comidaApplication.db.comidaDao().insertComida(nuevaComida)
            dialog.dismiss() // Cerrar el diálogo
        }

        // Botón para cancelar el diálogo
        dialogBuilder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss() // Cerrar el diálogo sin hacer nada
        }
        // Mostrar el diálogo
        val dialog = dialogBuilder.create()
        dialog.show()
    }
    fun edit(id:Long){
        println("XD $id")
      // showUpdateDialog(this,id)
    }

}