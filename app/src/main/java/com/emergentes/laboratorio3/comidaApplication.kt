package com.emergentes.laboratorio3

import android.app.Application
import androidx.room.Room

class comidaApplication: Application() {
    companion object{
        lateinit var db :AppDataBase
    }
    override fun onCreate() {
        super.onCreate()
        comidaApplication.db =
            Room.databaseBuilder(applicationContext,AppDataBase::class.java,
                "comida.db"
            )
                .fallbackToDestructiveMigration() // para destruir todos los datos al migrar de una version a otra
                .allowMainThreadQueries() // para habilitar consultas a la db en el thread principal
                .build()
    }
}