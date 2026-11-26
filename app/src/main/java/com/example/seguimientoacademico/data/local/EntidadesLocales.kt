package com.example.seguimientoacademico.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.seguimientoacademico.RolUsuario

@Entity(tableName = "usuarios")
data class UsuarioEntidad(
    @PrimaryKey val usuario: String,
    val contrasena: String,
    val rol: RolUsuario,
    val nombre: String,
    val puesto: String,
    val ultimaActualizacion: Long = System.currentTimeMillis(),
    val pendienteSync: Boolean = false
)

@Entity(tableName = "alumnos")
data class AlumnoEntidad(
    @PrimaryKey val matricula: String,
    val nombres: String,
    val apellidoMaterno: String,
    val apellidoPaterno: String,
    val fechaNacimiento: String,
    val correoTutor: String,
    val grado: String,
    val ultimaActualizacion: Long = System.currentTimeMillis(),
    val pendienteSync: Boolean = false
)

@Entity(tableName = "profesores")
data class ProfesorEntidad(
    @PrimaryKey val numeroEmpleado: String,
    val nombres: String,
    val apellidoMaterno: String,
    val apellidoPaterno: String,
    val correo: String,
    val telefono: String,
    val materias: String,
    val activo: Boolean = true,
    val ultimaActualizacion: Long = System.currentTimeMillis(),
    val pendienteSync: Boolean = false
)

@Entity(tableName = "grupos")
data class GrupoEntidad(
    @PrimaryKey val grupo: String,
    val profesores: List<String>,
    val ultimaActualizacion: Long = System.currentTimeMillis(),
    val pendienteSync: Boolean = false
)

@Entity(tableName = "asistencias")
data class AsistenciaEntidad(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val grupo: String,
    val fecha: String,
    val matriculaAlumno: String,
    val estado: String, // PRESENTE, RETARDO, FALTA, JUSTIFICADO
    val ultimaActualizacion: Long = System.currentTimeMillis(),
    val pendienteSync: Boolean = true
)

@Entity(tableName = "calificaciones")
data class CalificacionEntidad(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val grupo: String,
    val parcial: String,
    val matriculaAlumno: String,
    val nota: String,
    val ultimaActualizacion: Long = System.currentTimeMillis(),
    val pendienteSync: Boolean = true
)

@Entity(tableName = "reportes_actividad")
data class ReporteActividadEntidad(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tipo: String,
    val alumnoNombre: String,
    val titulo: String,
    val observaciones: String,
    val fechaRegistro: Long = System.currentTimeMillis(),
    val pendienteSync: Boolean = true
)

@Entity(tableName = "recados_grupales")
data class RecadoGrupalEntidad(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val grupo: String,
    val asunto: String,
    val mensaje: String,
    val fechaEnvio: Long = System.currentTimeMillis(),
    val pendienteSync: Boolean = true
)

@Entity(tableName = "juntas_tutores")
data class JuntaTutorEntidad(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val titulo: String,
    val grupo: String,
    val fechaHora: String,
    val aula: String,
    val pendienteSync: Boolean = true
)

@Entity(tableName = "citas_tutores")
data class CitaTutorEntidad(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tutor: String,
    val alumno: String,
    val motivo: String,
    val horarioSolicitado: String,
    val estado: String = "Pendiente",
    val pendienteSync: Boolean = true
)