package com.example.seguimientoacademico

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