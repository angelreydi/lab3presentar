package com.emergentes.laboratorio3

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import layout.comida

@Dao
interface comidaDAO {
    @Query("select * from comida")
    fun selectComidas(): List<comida>
    @Insert
    fun insertComida(vararg per: comida)
    @Update
    fun updateComida(p: comida)
    @Query("select * from comida where id=:id")
    fun selecComidaBydId(id:Long):comida
    @Query("update comida set nombre=:nombre where id=:id")
    fun updateNombre(id:Long,nombre:String)

    @Query("DELETE FROM comida WHERE id = :id")
    fun deleteComidaById(id: Long)

}