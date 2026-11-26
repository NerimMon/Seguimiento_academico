package com.example.seguimientoacademico.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.seguimientoacademico.RolUsuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        UsuarioEntidad::class,
        AlumnoEntidad::class,
        ProfesorEntidad::class,
        GrupoEntidad::class,
        AsistenciaEntidad::class,
        CalificacionEntidad::class,
        ReporteActividadEntidad::class,
        RecadoGrupalEntidad::class,
        JuntaTutorEntidad::class,
        CitaTutorEntidad::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(ConvertidoresRoom::class)
abstract class SeguimientoDatabase : RoomDatabase() {

    abstract fun usuarioDao(): UsuarioDao
    abstract fun alumnoDao(): AlumnoDao
    abstract fun profesorDao(): ProfesorDao
    abstract fun grupoDao(): GrupoDao
    abstract fun operacionProfesorDao(): OperacionProfesorDao

    companion object {
        @Volatile
        private var INSTANCIA: SeguimientoDatabase? = null

        fun obtenerInstancia(context: Context): SeguimientoDatabase {
            return INSTANCIA ?: synchronized(this) {
                val instancia = Room.databaseBuilder(
                    context.applicationContext,
                    SeguimientoDatabase::class.java,
                    "seguimiento_academico.db"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            INSTANCIA?.let { base ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    poblarDatosIniciales(base)
                                }
                            }
                        }
                    })
                    .build()
                INSTANCIA = instancia
                instancia
            }
        }

        private suspend fun poblarDatosIniciales(db: SeguimientoDatabase) {
            db.usuarioDao().insertarTodos(
                listOf(
                    UsuarioEntidad("padre1", "123", RolUsuario.PADRE_DE_FAMILIA, "Carlos Mendoza", "Padre de familia"),
                    UsuarioEntidad("maestro1", "123", RolUsuario.MAESTRO, "Laura Ramírez", "Maestro"),
                    UsuarioEntidad("director1", "123", RolUsuario.DIRECTOR, "Roberto Guzmán", "Director")
                )
            )
            db.alumnoDao().insertarTodos(
                listOf(
                    AlumnoEntidad("2026001", "Sofía", "López", "Mendoza", "14/05/2014", "carlos.mendoza@correo.com", "3A"),
                    AlumnoEntidad("2026002", "Mateo", "Ruiz", "Hernández", "22/09/2013", "ana.ruiz@correo.com", "4B")
                )
            )
            db.profesorDao().insertarTodos(
                listOf(
                    ProfesorEntidad("EMP-101", "Laura", "Gómez", "Ramírez", "laura.ramirez@escuela.edu.mx", "5512345678", "Matemáticas, Ciencias"),
                    ProfesorEntidad("EMP-102", "Jorge", "Sánchez", "Torres", "jorge.torres@escuela.edu.mx", "5587654321", "Español, Historia")
                )
            )
            db.grupoDao().insertarTodos(
                listOf(
                    GrupoEntidad("3A", listOf("Laura Ramírez Gómez", "Jorge Torres Sánchez")),
                    GrupoEntidad("4B", listOf("Laura Ramírez Gómez"))
                )
            )
            db.operacionProfesorDao().insertarJunta(
                JuntaTutorEntidad(
                    titulo = "Entrega de boletas y seguimiento del 1er parcial",
                    grupo = "Grupo 3A",
                    fechaHora = "28/11/2026 · 08:30 hrs",
                    aula = "Salón 12"
                )
            )
            db.operacionProfesorDao().upsertCita(
                CitaTutorEntidad(
                    tutor = "Carlos Mendoza",
                    alumno = "Sofía Mendoza (3A)",
                    motivo = "Revisión de avance en matemáticas",
                    horarioSolicitado = "Jueves 26/11 · 13:30 hrs"
                )
            )
        }
    }
}