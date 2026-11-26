package com.example.seguimientoacademico.data.local

import androidx.room.TypeConverter
import com.example.seguimientoacademico.RolUsuario

class ConvertidoresRoom {

    @TypeConverter
    fun desdeRol(rol: RolUsuario): String = rol.name

    @TypeConverter
    fun aRol(valor: String): RolUsuario =
        runCatching { RolUsuario.valueOf(valor) }.getOrElse { RolUsuario.MAESTRO }

    @TypeConverter
    fun desdeListaString(lista: List<String>): String =
        lista.joinToString(separator = "||")

    @TypeConverter
    fun aListaString(texto: String): List<String> =
        if (texto.isBlank()) emptyList() else texto.split("||")
}