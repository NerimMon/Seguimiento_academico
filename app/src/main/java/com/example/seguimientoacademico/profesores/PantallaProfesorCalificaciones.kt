package com.example.seguimientoacademico.profesores

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seguimientoacademico.AlumnoLocal
import com.example.seguimientoacademico.GrupoLocal
import kotlinx.coroutines.launch

private val FondoPantalla = Color(0xFFF0F4F8)
private val AzulMarino = Color(0xFF284361)
private val TextoOscuro = Color(0xFF1E293B)
private val TextoSecundario = Color(0xFF64748B)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaProfesorCalificaciones(
    grupos: List<GrupoLocal> = emptyList(),
    alumnos: List<AlumnoLocal> = emptyList(),
    onGuardarCalificaciones: suspend (String, String, Map<String, String>) -> Unit = { _, _, _ -> },
    onAbrirMenu: () -> Unit = {},
    onRegresar: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()

    val gruposDisponibles = remember(grupos) {
        grupos.map { it.grupo }.distinct().ifEmpty { listOf("3A", "4B") }
    }
    val parciales = listOf("Parcial 1", "Parcial 2", "Parcial 3")

    var grupoSeleccionado by remember(gruposDisponibles) {
        mutableStateOf(gruposDisponibles.first())
    }
    var parcialSeleccionado by remember { mutableStateOf(parciales.first()) }
    var mensajeGuardado by remember { mutableStateOf(false) }

    val alumnosGrupo = remember(grupoSeleccionado, alumnos) {
        val filtrados = alumnos.filter {
            it.grado.equals(grupoSeleccionado, ignoreCase = true)
        }
        filtrados.ifEmpty { alumnos }
    }

    val notas = remember(grupoSeleccionado, parcialSeleccionado, alumnosGrupo) {
        mutableStateMapOf<String, String>().apply {
            alumnosGrupo.forEach { put(it.matricula, "9.0") }
        }
    }

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
                        text = "Registrar Calificaciones",
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
        },
        bottomBar = {
            Surface(color = Color.White, shadowElevation = 6.dp) {
                Column(modifier = Modifier.padding(14.dp)) {
                    if (mensajeGuardado) {
                        Text(
                            text = "Calificaciones de $parcialSeleccionado ($grupoSeleccionado) guardadas.",
                            color = Color(0xFF16A34A),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    Button(
                        onClick = {
                            scope.launch {
                                onGuardarCalificaciones(
                                    grupoSeleccionado,
                                    parcialSeleccionado,
                                    notas.toMap()
                                )
                                mensajeGuardado = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AzulMarino)
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Guardar Calificaciones", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Grupo y Periodo", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        gruposDisponibles.forEach { g ->
                            FilterChip(
                                selected = g == grupoSeleccionado,
                                onClick = {
                                    grupoSeleccionado = g
                                    mensajeGuardado = false
                                },
                                label = { Text("Grupo $g") }
                            )
                        }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        parciales.forEach { p ->
                            FilterChip(
                                selected = p == parcialSeleccionado,
                                onClick = {
                                    parcialSeleccionado = p
                                    mensajeGuardado = false
                                },
                                label = { Text(p) }
                            )
                        }
                    }
                }
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(alumnosGrupo, key = { it.matricula }) { alumno ->
                    val valorNota = notas[alumno.matricula] ?: ""
                    Card(
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "${alumno.nombres} ${alumno.apellidoPaterno}",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextoOscuro
                                )
                                Text(
                                    text = "Matrícula: ${alumno.matricula}",
                                    fontSize = 12.sp,
                                    color = TextoSecundario
                                )
                            }

                            OutlinedTextField(
                                value = valorNota,
                                onValueChange = { nuevo ->
                                    if (nuevo.length <= 4) {
                                        notas[alumno.matricula] = nuevo
                                        mensajeGuardado = false
                                    }
                                },
                                singleLine = true,
                                label = { Text("Nota") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                modifier = Modifier.width(96.dp),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}