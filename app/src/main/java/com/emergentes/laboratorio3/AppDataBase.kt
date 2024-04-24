package com.emergentes.laboratorio3

import androidx.room.Database
import androidx.room.RoomDatabase
import layout.comida

@Database(entities = [comida::class],version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun comidaDao(): comidaDAO

}
