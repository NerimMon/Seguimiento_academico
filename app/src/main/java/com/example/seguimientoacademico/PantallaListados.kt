package com.example.seguimientoacademico

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaListadoAlumnos(
    alumnos: List<AlumnoLocal> = emptyList(),
    onAbrirMenu: () -> Unit = {},
    onRegresar: () -> Unit = {}
) {
    val fondo = Color(0xFFF5F8FB)
    val azulOscuro = Color(0xFF15395B)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Listado de alumnos",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A202C),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Director",
            fontSize = 15.sp,
            color = Color(0xFF64748B),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        BarraSuperiorPantalla(
            tituloSeccion = "Alumnos",
            esCuestionario = false,
            onAbrirMenu = onAbrirMenu,
            onRegresarConfirmado = onRegresar
        )

        if (alumnos.isEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "No hay alumnos almacenados",
                color = Color(0xFF64748B),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            alumnos.forEach { alumno ->
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "${alumno.nombres} ${alumno.apellidoPaterno} ${alumno.apellidoMaterno}".trim(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = azulOscuro
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Matricula: ${alumno.matricula}",
                            fontSize = 14.sp,
                            color = Color(0xFF1A202C)
                        )
                        Text(
                            text = "Grado: ${alumno.grado}",
                            fontSize = 14.sp,
                            color = Color(0xFF475569)
                        )
                        Text(
                            text = "Fecha de Nacimiento: ${alumno.fechaNacimiento}",
                            fontSize = 13.sp,
                            color = Color(0xFF64748B)
                        )
                        Text(
                            text = "Correo del tutor: ${alumno.correoTutor}",
                            fontSize = 13.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PantallaListadoProfesores(
    profesores: List<ProfesorLocal> = emptyList(),
    onAbrirMenu: () -> Unit = {},
    onRegresar: () -> Unit = {}
) {
    val fondo = Color(0xFFF5F8FB)
    val azulOscuro = Color(0xFF15395B)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Listado de profesores",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A202C),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Director",
            fontSize = 15.sp,
            color = Color(0xFF64748B),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        BarraSuperiorPantalla(
            tituloSeccion = "Profesores",
            esCuestionario = false,
            onAbrirMenu = onAbrirMenu,
            onRegresarConfirmado = onRegresar
        )

        if (profesores.isEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "No hay profesores almacenados",
                color = Color(0xFF64748B),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            profesores.forEach { profesor ->
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "${profesor.nombres} ${profesor.apellidoPaterno} ${profesor.apellidoMaterno}".trim(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = azulOscuro
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Numero de empleado: ${profesor.numeroEmpleado}",
                            fontSize = 14.sp,
                            color = Color(0xFF1A202C)
                        )
                        Text(
                            text = "Materias: ${profesor.materias}",
                            fontSize = 14.sp,
                            color = Color(0xFF475569)
                        )
                        Text(
                            text = "Correo: ${profesor.correo}",
                            fontSize = 13.sp,
                            color = Color(0xFF64748B)
                        )
                        Text(
                            text = "Telefono: ${profesor.telefono}",
                            fontSize = 13.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PantallaListadoGrupos(
    grupos: List<GrupoLocal> = emptyList(),
    onAbrirMenu: () -> Unit = {},
    onRegresar: () -> Unit = {}
) {
    val fondo = Color(0xFFF5F8FB)
    val azulOscuro = Color(0xFF15395B)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Listado de grupos",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A202C),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Director",
            fontSize = 15.sp,
            color = Color(0xFF64748B),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        BarraSuperiorPantalla(
            tituloSeccion = "Grupos",
            esCuestionario = false,
            onAbrirMenu = onAbrirMenu,
            onRegresarConfirmado = onRegresar
        )

        if (grupos.isEmpty()) {
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "No hay grupos almacenados",
                color = Color(0xFF64748B),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            grupos.forEach { itemGrupo ->
                Spacer(modifier = Modifier.height(12.dp))
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Grupo: ${itemGrupo.grupo}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = azulOscuro
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        val textoProfesores = if (itemGrupo.profesores.isEmpty()) {
                            "Sin profesores asignados"
                        } else {
                            itemGrupo.profesores.joinToString(", ")
                        }
                        Text(
                            text = "Profesores: $textoProfesores",
                            fontSize = 14.sp,
                            color = Color(0xFF475569)
                        )
                    }
                }
            }
        }
    }
}