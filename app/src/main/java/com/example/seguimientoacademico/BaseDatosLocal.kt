package com.example.seguimientoacademico

import androidx.compose.runtime.mutableStateListOf

enum class RolUsuario {
    PADRE_DE_FAMILIA,
    MAESTRO,
    DIRECTOR
}

data class UsuarioLocal(
    val usuario: String,
    val contrasena: String,
    val rol: RolUsuario,
    val nombre: String,
    val puesto: String
)

data class AlumnoLocal(
    val matricula: String,
    val nombres: String,
    val apellidoMaterno: String,
    val apellidoPaterno: String,
    val fechaNacimiento: String,
    val correoTutor: String,
    val grado: String
)

data class ProfesorLocal(
    val nombres: String,
    val apellidoMaterno: String,
    val apellidoPaterno: String,
    val correo: String,
    val telefono: String,
    val numeroEmpleado: String,
    val materias: String
)

data class GrupoLocal(
    val grupo: String,
    val profesores: List<String>
)

object BaseDatosLocal {
    val usuarios: List<UsuarioLocal> = listOf(
        UsuarioLocal(
            usuario = "padre1",
            contrasena = "123",
            rol = RolUsuario.PADRE_DE_FAMILIA,
            nombre = "Carlos Mendoza",
            puesto = "Padre de familia"
        ),
        UsuarioLocal(
            usuario = "maestro1",
            contrasena = "123",
            rol = RolUsuario.MAESTRO,
            nombre = "Laura Ramírez",
            puesto = "Maestro"
        ),
        UsuarioLocal(
            usuario = "director1",
            contrasena = "123",
            rol = RolUsuario.DIRECTOR,
            nombre = "Roberto Guzmán",
            puesto = "Director"
        )
    )

    val alumnos = mutableStateListOf(
        AlumnoLocal(
            matricula = "2026001",
            nombres = "Sofía",
            apellidoMaterno = "López",
            apellidoPaterno = "Mendoza",
            fechaNacimiento = "14/05/2014",
            correoTutor = "carlos.mendoza@correo.com",
            grado = "3A"
        ),
        AlumnoLocal(
            matricula = "2026002",
            nombres = "Mateo",
            apellidoMaterno = "Ruiz",
            apellidoPaterno = "Hernández",
            fechaNacimiento = "22/09/2013",
            correoTutor = "ana.ruiz@correo.com",
            grado = "4B"
        )
    )

    val profesores = mutableStateListOf(
        ProfesorLocal(
            nombres = "Laura",
            apellidoMaterno = "Gómez",
            apellidoPaterno = "Ramírez",
            correo = "laura.ramirez@escuela.edu.mx",
            telefono = "5512345678",
            numeroEmpleado = "EMP-101",
            materias = "Matemáticas, Ciencias"
        ),
        ProfesorLocal(
            nombres = "Jorge",
            apellidoMaterno = "Sánchez",
            apellidoPaterno = "Torres",
            correo = "jorge.torres@escuela.edu.mx",
            telefono = "5587654321",
            numeroEmpleado = "EMP-102",
            materias = "Español, Historia"
        )
    )

    val grupos = mutableStateListOf(
        GrupoLocal(
            grupo = "3A",
            profesores = listOf("Laura Ramírez Gómez", "Jorge Torres Sánchez")
        ),
        GrupoLocal(
            grupo = "4B",
            profesores = listOf("Laura Ramírez Gómez")
        )
    )

    fun autenticar(usuarioIngresado: String, contrasenaIngresada: String): UsuarioLocal? {
        val u = usuarioIngresado.trim()
        val p = contrasenaIngresada.trim()
        return usuarios.find { it.usuario == u && it.contrasena == p }
    }
}