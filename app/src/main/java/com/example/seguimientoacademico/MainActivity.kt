package com.example.seguimientoacademico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import com.example.seguimientoacademico.data.local.AlumnoEntidad
import com.example.seguimientoacademico.data.local.GrupoEntidad
import com.example.seguimientoacademico.data.local.ProfesorEntidad
import com.example.seguimientoacademico.data.local.SeguimientoDatabase
import com.example.seguimientoacademico.profesores.GrupoProfesor
import com.example.seguimientoacademico.profesores.PantallaProfesorAsistencia
import com.example.seguimientoacademico.profesores.PantallaProfesorCalificaciones
import com.example.seguimientoacademico.profesores.PantallaProfesorCitasPendientes
import com.example.seguimientoacademico.profesores.PantallaProfesorInicio
import com.example.seguimientoacademico.profesores.PantallaProfesorJuntasTutores
import com.example.seguimientoacademico.profesores.PantallaProfesorMensajesPadres
import com.example.seguimientoacademico.profesores.PantallaProfesorRecadoGrupal
import com.example.seguimientoacademico.profesores.PantallaProfesorReporteActividad
import com.example.seguimientoacademico.profesores.RutaProfesor
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = SeguimientoDatabase.obtenerInstancia(applicationContext)

        setContent {
            AppSeguimientoAcademico(db = db)
        }
    }
}

@Composable
private fun AppSeguimientoAcademico(db: SeguimientoDatabase) {
    val scope = rememberCoroutineScope()

    var usuarioActual by remember { mutableStateOf<UsuarioLocal?>(null) }
    var rutaActual by remember { mutableStateOf(RutaPantalla.LOGIN) }
    var rutaTutor by remember { mutableStateOf(RutaTutor.INICIO) }
    var rutaProfesor by remember { mutableStateOf(RutaProfesor.INICIO) }
    var grupoSeleccionado by remember { mutableStateOf<GrupoProfesor?>(null) }
    var profesorSeleccionado by remember { mutableStateOf(profesoresTutorEjemplo.first()) }

    val alumnosRoom by db.alumnoDao().observarAlumnos().collectAsState(initial = emptyList())
    val profesoresRoom by db.profesorDao().observarProfesores().collectAsState(initial = emptyList())
    val gruposRoom by db.grupoDao().observarGrupos().collectAsState(initial = emptyList())

    val listaAlumnos = remember(alumnosRoom) {
        alumnosRoom.map {
            AlumnoLocal(
                matricula = it.matricula,
                nombres = it.nombres,
                apellidoMaterno = it.apellidoMaterno,
                apellidoPaterno = it.apellidoPaterno,
                fechaNacimiento = it.fechaNacimiento,
                correoTutor = it.correoTutor,
                grado = it.grado
            )
        }
    }

    val listaProfesores = remember(profesoresRoom) {
        profesoresRoom.map {
            ProfesorLocal(
                nombres = it.nombres,
                apellidoMaterno = it.apellidoMaterno,
                apellidoPaterno = it.apellidoPaterno,
                correo = it.correo,
                telefono = it.telefono,
                numeroEmpleado = it.numeroEmpleado,
                materias = it.materias
            )
        }
    }

    val listaGrupos = remember(gruposRoom) {
        gruposRoom.map {
            GrupoLocal(
                grupo = it.grupo,
                profesores = it.profesores
            )
        }
    }

    val cerrarSesion = {
        usuarioActual = null
        rutaTutor = RutaTutor.INICIO
        rutaProfesor = RutaProfesor.INICIO
        grupoSeleccionado = null
        rutaActual = RutaPantalla.LOGIN
    }

    if (rutaActual == RutaPantalla.LOGIN) {
        Pantalla1Login(
            onAutenticar = { usuarioIngresado, contrasenaIngresada ->
                val entidad = db.usuarioDao().autenticar(usuarioIngresado, contrasenaIngresada)
                entidad?.let {
                    UsuarioLocal(
                        usuario = it.usuario,
                        contrasena = it.contrasena,
                        rol = it.rol,
                        nombre = it.nombre,
                        puesto = it.puesto
                    )
                }
            },
            onLoginExitoso = { usuario ->
                usuarioActual = usuario
                rutaTutor = RutaTutor.INICIO
                rutaProfesor = RutaProfesor.INICIO
                rutaActual = when (usuario.rol) {
                    RolUsuario.DIRECTOR -> RutaPantalla.MENU
                    RolUsuario.PADRE_DE_FAMILIA -> RutaPantalla.MENU
                    RolUsuario.MAESTRO -> RutaPantalla.MENU
                }
            }
        )
    } else {
        ContenedorConMenuHamburguesa(
            usuario = usuarioActual,
            onCerrarSesion = cerrarSesion
        ) { abrirMenu ->
            when (usuarioActual?.rol) {
                RolUsuario.PADRE_DE_FAMILIA -> {
                    when (rutaTutor) {
                        RutaTutor.INICIO -> PantallaTutorInicio(
                            usuario = usuarioActual,
                            onNavegar = { rutaTutor = it },
                            onAbrirMenu = abrirMenu,
                            onCerrarSesion = cerrarSesion
                        )
                        RutaTutor.ALUMNO -> PantallaTutorAlumno(
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaTutor = RutaTutor.INICIO }
                        )
                        RutaTutor.PROFESORES -> PantallaTutorProfesores(
                            onSeleccionarProfesor = {
                                profesorSeleccionado = it
                                rutaTutor = RutaTutor.DETALLE_PROFESOR
                            },
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaTutor = RutaTutor.INICIO }
                        )
                        RutaTutor.DETALLE_PROFESOR -> PantallaTutorDetalleProfesor(
                            profesor = profesorSeleccionado,
                            onSolicitarAsesoria = { rutaTutor = RutaTutor.SOLICITUD_ASESORIA },
                            onSolicitarReunion = { rutaTutor = RutaTutor.SOLICITUD_REUNION },
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaTutor = RutaTutor.PROFESORES }
                        )
                        RutaTutor.SOLICITUD_ASESORIA -> PantallaTutorSolicitudAsesoria(
                            profesor = profesorSeleccionado,
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaTutor = RutaTutor.DETALLE_PROFESOR }
                        )
                        RutaTutor.SOLICITUD_REUNION -> PantallaTutorSolicitudReunion(
                            profesor = profesorSeleccionado,
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaTutor = RutaTutor.DETALLE_PROFESOR }
                        )
                        RutaTutor.REPORTES -> PantallaTutorReportes(
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaTutor = RutaTutor.INICIO }
                        )
                        RutaTutor.ANUNCIOS -> PantallaTutorAnuncios(
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaTutor = RutaTutor.INICIO }
                        )
                        RutaTutor.CALIFICACIONES -> PantallaTutorCalificaciones(
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaTutor = RutaTutor.INICIO }
                        )
                    }
                }

                RolUsuario.MAESTRO -> {
                    when (rutaProfesor) {
                        RutaProfesor.INICIO -> PantallaProfesorInicio(
                            usuario = usuarioActual,
                            profesores = listaProfesores,
                            grupos = listaGrupos,
                            onNavegar = { rutaProfesor = it },
                            onGrupoClick = { grupo ->
                                grupoSeleccionado = grupo
                                rutaProfesor = RutaProfesor.ASISTENCIA
                            },
                            onAbrirMenu = abrirMenu,
                            onCerrarSesion = cerrarSesion
                        )
                        RutaProfesor.ASISTENCIA -> PantallaProfesorAsistencia(
                            grupos = listaGrupos,
                            alumnos = listaAlumnos,
                            grupoInicial = grupoSeleccionado,
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaProfesor = RutaProfesor.INICIO }
                        )
                        RutaProfesor.CALIFICACIONES -> PantallaProfesorCalificaciones(
                            grupos = listaGrupos,
                            alumnos = listaAlumnos,
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaProfesor = RutaProfesor.INICIO }
                        )
                        RutaProfesor.RECADO_GRUPAL -> PantallaProfesorRecadoGrupal(
                            grupos = listaGrupos,
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaProfesor = RutaProfesor.INICIO }
                        )
                        RutaProfesor.REPORTE_ACTIVIDAD -> PantallaProfesorReporteActividad(
                            alumnos = listaAlumnos,
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaProfesor = RutaProfesor.INICIO }
                        )
                        RutaProfesor.ANUNCIOS -> Pantalla2Avisos(
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaProfesor = RutaProfesor.INICIO }
                        )
                        RutaProfesor.MENSAJES_PADRES -> PantallaProfesorMensajesPadres(
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaProfesor = RutaProfesor.INICIO }
                        )
                        RutaProfesor.JUNTAS_TUTORES -> PantallaProfesorJuntasTutores(
                            onAbrirMenu = abrirMenu,
                            onRegresar = {
                                scope.launch {
                                    val dao = db.operacionProfesorDao()
                                }
                                rutaProfesor = RutaProfesor.INICIO
                            }
                        )
                        RutaProfesor.CITAS_PENDIENTES -> PantallaProfesorCitasPendientes(
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaProfesor = RutaProfesor.INICIO }
                        )
                    }
                }

                else -> {
                    when (rutaActual) {
                        RutaPantalla.PROXIMAMENTE -> PantallaProximamente(
                            usuario = usuarioActual,
                            onAbrirMenu = abrirMenu,
                            onCerrarSesion = cerrarSesion
                        )
                        RutaPantalla.MENU -> Pantalla3Menu(
                            onNavegar = { destino -> rutaActual = destino },
                            onAbrirMenu = abrirMenu,
                            onRegresar = cerrarSesion
                        )
                        RutaPantalla.AVISOS -> Pantalla2Avisos(
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaActual = RutaPantalla.MENU }
                        )
                        RutaPantalla.REGISTRAR_PROFESOR -> Pantalla4RegistrarProfesor(
                            onGuardarProfesor = { prof ->
                                db.profesorDao().insertar(
                                    ProfesorEntidad(
                                        numeroEmpleado = prof.numeroEmpleado,
                                        nombres = prof.nombres,
                                        apellidoMaterno = prof.apellidoMaterno,
                                        apellidoPaterno = prof.apellidoPaterno,
                                        correo = prof.correo,
                                        telefono = prof.telefono,
                                        materias = prof.materias,
                                        activo = true,
                                        pendienteSync = true
                                    )
                                )
                            },
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaActual = RutaPantalla.MENU },
                            onRegistrarClick = { rutaActual = RutaPantalla.MENU }
                        )
                        RutaPantalla.REGISTRAR_TUTOR -> Pantalla5RegistrarTutor(
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaActual = RutaPantalla.MENU },
                            onRegistrarClick = { rutaActual = RutaPantalla.MENU }
                        )
                        RutaPantalla.REGISTRAR_ALUMNO -> Pantalla6RegistrarAlumno(
                            onGuardarAlumno = { alumno ->
                                db.alumnoDao().insertar(
                                    AlumnoEntidad(
                                        matricula = alumno.matricula,
                                        nombres = alumno.nombres,
                                        apellidoMaterno = alumno.apellidoMaterno,
                                        apellidoPaterno = alumno.apellidoPaterno,
                                        fechaNacimiento = alumno.fechaNacimiento,
                                        correoTutor = alumno.correoTutor,
                                        grado = alumno.grado,
                                        pendienteSync = true
                                    )
                                )
                            },
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaActual = RutaPantalla.MENU },
                            onRegistrarClick = { rutaActual = RutaPantalla.MENU }
                        )
                        RutaPantalla.REGISTRAR_GRUPO -> Pantalla6RegistrarGrupo(
                            onGuardarGrupo = { gpo ->
                                db.grupoDao().insertar(
                                    GrupoEntidad(
                                        grupo = gpo.grupo,
                                        profesores = gpo.profesores,
                                        pendienteSync = true
                                    )
                                )
                            },
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaActual = RutaPantalla.MENU },
                            onRegistrarClick = { rutaActual = RutaPantalla.MENU }
                        )
                        RutaPantalla.NUEVO_ANUNCIO -> Pantalla6NuevoAnuncio(
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaActual = RutaPantalla.MENU },
                            onRegistrarClick = { rutaActual = RutaPantalla.MENU }
                        )
                        RutaPantalla.LISTADO_ALUMNOS -> PantallaListadoAlumnos(
                            alumnos = listaAlumnos,
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaActual = RutaPantalla.MENU }
                        )
                        RutaPantalla.LISTADO_PROFESORES -> PantallaListadoProfesores(
                            profesores = listaProfesores,
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaActual = RutaPantalla.MENU }
                        )
                        RutaPantalla.LISTADO_GRUPOS -> PantallaListadoGrupos(
                            grupos = listaGrupos,
                            onAbrirMenu = abrirMenu,
                            onRegresar = { rutaActual = RutaPantalla.MENU }
                        )
                        RutaPantalla.LOGIN -> Unit
                    }
                }
            }
        }
    }
}