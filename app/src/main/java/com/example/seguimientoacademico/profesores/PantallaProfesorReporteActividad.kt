package com.example.seguimientoacademico.profesores

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PostAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seguimientoacademico.AlumnoLocal
import kotlinx.coroutines.launch

private val FondoPantalla = Color(0xFFF0F4F8)
private val AzulMarino = Color(0xFF284361)
private val TextoOscuro = Color(0xFF1E293B)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaProfesorReporteActividad(
    alumnos: List<AlumnoLocal> = emptyList(),
    onGuardarReporte: suspend (String, String, String, String) -> Unit = { _, _, _, _ -> },
    onAbrirMenu: () -> Unit = {},
    onRegresar: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val tiposReporte = listOf("Tarea / Actividad", "Participación", "Conducta / Incidencia")

    val nombresAlumnos = remember(alumnos) {
        alumnos.map { "${it.nombres} ${it.apellidoPaterno}".trim() }
            .filter { it.isNotEmpty() }
            .distinct()
    }

    var tipoSeleccionado by remember { mutableStateOf(tiposReporte.first()) }
    var alumnoSeleccionado by remember(nombresAlumnos) {
        mutableStateOf(nombresAlumnos.firstOrNull() ?: "Sofía Mendoza")
    }
    var tituloActividad by remember { mutableStateOf("") }
    var observaciones by remember { mutableStateOf("") }
    var registrado by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = FondoPantalla,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                navigationIcon = {
                    IconButton(onClick = onRegresar) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Regresar", tint = TextoOscuro)
                    }
                },
                title = {
                    Text(
                        text = "Reporte de Actividad",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoOscuro
                    )
                },
                actions = {
                    IconButton(onClick = onAbrirMenu) {
                        Icon(Icons.Default.Menu, "Menú", tint = TextoOscuro)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Tipo de reporte", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        tiposReporte.forEach { tipo ->
                            FilterChip(
                                selected = tipo == tipoSeleccionado,
                                onClick = {
                                    tipoSeleccionado = tipo
                                    registrado = false
                                },
                                label = { Text(tipo) }
                            )
                        }
                    }

                    Text("Alumno", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        nombresAlumnos.forEach { nombre ->
                            FilterChip(
                                selected = nombre == alumnoSeleccionado,
                                onClick = {
                                    alumnoSeleccionado = nombre
                                    registrado = false
                                },
                                label = { Text(nombre) }
                            )
                        }
                    }

                    OutlinedTextField(
                        value = tituloActividad,
                        onValueChange = {
                            tituloActividad = it
                            registrado = false
                        },
                        label = { Text("Título de actividad o motivo") },
                        placeholder = { Text("Ej. Entrega de guía de fracciones") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = observaciones,
                        onValueChange = {
                            observaciones = it
                            registrado = false
                        },
                        label = { Text("Observaciones para el tutor") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp),
                        shape = RoundedCornerShape(12.dp)
                    )

                    if (registrado) {
                        Text(
                            text = "Reporte guardado para $alumnoSeleccionado.",
                            color = Color(0xFF16A34A),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Button(
                        onClick = {
                            val tituloLimpio = tituloActividad.trim()
                            if (tituloLimpio.isNotBlank()) {
                                scope.launch {
                                    onGuardarReporte(
                                        tipoSeleccionado,
                                        alumnoSeleccionado,
                                        tituloLimpio,
                                        observaciones.trim()
                                    )
                                    registrado = true
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AzulMarino)
                    ) {
                        Icon(Icons.Default.PostAdd, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Registrar Reporte", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}