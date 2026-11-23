package com.example.seguimientoacademico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var usuarioActual by remember { mutableStateOf<UsuarioLocal?>(null) }
            var rutaActual by remember { mutableStateOf(RutaPantalla.LOGIN) }
            var rutaTutor by remember { mutableStateOf(RutaTutor.INICIO) }
            var profesorSeleccionado by remember { mutableStateOf(profesoresTutorEjemplo.first()) }

            val cerrarSesion = {
                usuarioActual = null
                rutaTutor = RutaTutor.INICIO
                rutaActual = RutaPantalla.LOGIN
            }

            if (rutaActual == RutaPantalla.LOGIN) {
                Pantalla1Login(
                    onLoginExitoso = { usuario ->
                        usuarioActual = usuario
                        rutaTutor = RutaTutor.INICIO
                        rutaActual = when (usuario.rol) {
                            RolUsuario.DIRECTOR -> RutaPantalla.MENU
                            RolUsuario.PADRE_DE_FAMILIA -> RutaPantalla.PROXIMAMENTE
                            RolUsuario.MAESTRO -> RutaPantalla.PROXIMAMENTE
                        }
                    }
                )
            } else {
                ContenedorConMenuHamburguesa(
                    usuario = usuarioActual,
                    onCerrarSesion = cerrarSesion
                ) { abrirMenu ->
                    if (usuarioActual?.rol == RolUsuario.PADRE_DE_FAMILIA) {
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
                    } else {
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
                                onAbrirMenu = abrirMenu,
                                onRegresar = { rutaActual = RutaPantalla.MENU },
                                onRegistrarClick = { rutaActual = RutaPantalla.MENU }
                            )
                            RutaPantalla.REGISTRAR_GRUPO -> Pantalla6RegistrarGrupo(
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
                                onAbrirMenu = abrirMenu,
                                onRegresar = { rutaActual = RutaPantalla.MENU }
                            )
                            RutaPantalla.LISTADO_PROFESORES -> PantallaListadoProfesores(
                                onAbrirMenu = abrirMenu,
                                onRegresar = { rutaActual = RutaPantalla.MENU }
                            )
                            RutaPantalla.LISTADO_GRUPOS -> PantallaListadoGrupos(
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
}
