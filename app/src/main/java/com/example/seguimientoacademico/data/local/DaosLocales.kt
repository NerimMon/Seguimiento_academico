package com.example.seguimientoacademico.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM usuarios WHERE usuario = :usuario AND contrasena = :contrasena LIMIT 1")
    suspend fun autenticar(usuario: String, contrasena: String): UsuarioEntidad?

    @Query("SELECT * FROM usuarios")
    fun observarUsuarios(): Flow<List<UsuarioEntidad>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(usuarios: List<UsuarioEntidad>)
}

@Dao
interface AlumnoDao {
    @Query("SELECT * FROM alumnos ORDER BY apellidoPaterno, nombres")
    fun observarAlumnos(): Flow<List<AlumnoEntidad>>

    @Query("SELECT * FROM alumnos WHERE grado = :grado ORDER BY apellidoPaterno, nombres")
    fun observarAlumnosPorGrado(grado: String): Flow<List<AlumnoEntidad>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(alumno: AlumnoEntidad)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(alumnos: List<AlumnoEntidad>)
}

@Dao
interface ProfesorDao {
    @Query("SELECT * FROM profesores ORDER BY apellidoPaterno, nombres")
    fun observarProfesores(): Flow<List<ProfesorEntidad>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(profesor: ProfesorEntidad)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(profesores: List<ProfesorEntidad>)

    @Query("SELECT * FROM profesores WHERE pendienteSync = 1")
    suspend fun obtenerPendientes(): List<ProfesorEntidad>

    @Query("UPDATE profesores SET pendienteSync = 0 WHERE numeroEmpleado = :numeroEmpleado")
    suspend fun marcarSincronizado(numeroEmpleado: String)
}

@Dao
interface GrupoDao {
    @Query("SELECT * FROM grupos ORDER BY grupo")
    fun observarGrupos(): Flow<List<GrupoEntidad>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(grupo: GrupoEntidad)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodos(grupos: List<GrupoEntidad>)
}

@Dao
interface OperacionProfesorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarAsistencias(registros: List<AsistenciaEntidad>)

    @Query("SELECT * FROM asistencias WHERE grupo = :grupo AND fecha = :fecha")
    suspend fun obtenerAsistencias(grupo: String, fecha: String): List<AsistenciaEntidad>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarCalificaciones(registros: List<CalificacionEntidad>)

    @Query("SELECT * FROM calificaciones WHERE grupo = :grupo AND parcial = :parcial")
    suspend fun obtenerCalificaciones(grupo: String, parcial: String): List<CalificacionEntidad>

    @Insert
    suspend fun insertarReporte(reporte: ReporteActividadEntidad)

    @Query("SELECT * FROM reportes_actividad ORDER BY fechaRegistro DESC")
    fun observarReportes(): Flow<List<ReporteActividadEntidad>>

    @Insert
    suspend fun insertarRecado(recado: RecadoGrupalEntidad)

    @Query("SELECT * FROM recados_grupales ORDER BY fechaEnvio DESC")
    fun observarRecados(): Flow<List<RecadoGrupalEntidad>>

    @Insert
    suspend fun insertarJunta(junta: JuntaTutorEntidad)

    @Query("SELECT * FROM juntas_tutores ORDER BY id DESC")
    fun observarJuntas(): Flow<List<JuntaTutorEntidad>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCita(cita: CitaTutorEntidad)

    @Query("SELECT * FROM citas_tutores ORDER BY id DESC")
    fun observarCitas(): Flow<List<CitaTutorEntidad>>
}