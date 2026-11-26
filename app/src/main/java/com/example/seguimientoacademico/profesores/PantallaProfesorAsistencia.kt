package com.example.seguimientoacademico.profesores

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seguimientoacademico.AlumnoLocal
import com.example.seguimientoacademico.GrupoLocal
import kotlinx.coroutines.launch

private val FondoPantalla = Color(0xFFF0F4F8)
private val AzulMarino = Color(0xFF284361)
private val TextoOscuro = Color(0xFF1E293B)
private val TextoSecundario = Color(0xFF64748B)

enum class EstadoAsistencia(val etiqueta: String, val sigla: String, val color: Color) {
    PRESENTE("Presente", "P", Color(0xFF16A34A)),
    RETARDO("Retardo", "R", Color(0xFFD97706)),
    FALTA("Falta", "F", Color(0xFFDC2626)),
    JUSTIFICADO("Justificado", "J", Color(0xFF2563EB))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaProfesorAsistencia(
    grupos: List<GrupoLocal> = emptyList(),
    alumnos: List<AlumnoLocal> = emptyList(),
    grupoInicial: GrupoProfesor? = null,
    onGuardarAsistencia: suspend (String, Map<String, EstadoAsistencia>) -> Unit = { _, _ -> },
    onAbrirMenu: () -> Unit = {},
    onRegresar: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()

    val gruposDisponibles = remember(grupos) {
        grupos.map { it.grupo }.distinct().ifEmpty { listOf("3A", "4B") }
    }

    var grupoSeleccionado by remember(gruposDisponibles) {
        mutableStateOf(
            grupoInicial?.nombre?.removePrefix("Grupo ")?.trim()
                ?.takeIf { it.isNotEmpty() }
                ?: gruposDisponibles.first()
        )
    }

    val alumnosGrupo = remember(grupoSeleccionado, alumnos) {
        val filtrados = alumnos.filter {
            it.grado.equals(grupoSeleccionado, ignoreCase = true)
        }
        filtrados.ifEmpty { alumnos }
    }

    val asistencias = remember(grupoSeleccionado, alumnosGrupo) {
        mutableStateMapOf<String, EstadoAsistencia>().apply {
            alumnosGrupo.forEach { put(it.matricula, EstadoAsistencia.PRESENTE) }
        }
    }
    var guardadoExitoso by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = FondoPantalla,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
                navigationIcon = {
                    IconButton(onClick = onRegresar) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar",
                            tint = TextoOscuro
                        )
                    }
                },
                title = {
                    Text(
                        text = "Pase de Asistencia",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoOscuro
                    )
                },
                actions = {
                    IconButton(onClick = onAbrirMenu) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menú",
                            tint = TextoOscuro
                        )
                    }
                }
            )
        },
        bottomBar = {
            Surface(color = Color.White, shadowElevation = 6.dp) {
                Column(modifier = Modifier.padding(14.dp)) {
                    if (guardadoExitoso) {
                        Text(
                            text = "Asistencia del grupo $grupoSeleccionado guardada correctamente.",
                            color = Color(0xFF16A34A),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                    Button(
                        onClick = {
                            scope.launch {
                                onGuardarAsistencia(grupoSeleccionado, asistencias.toMap())
                                guardadoExitoso = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AzulMarino)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Guardar Asistencia", fontWeight = FontWeight.Bold)
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
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        text = "Selecciona el Grupo",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextoSecundario
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        gruposDisponibles.forEach { grupo ->
                            val seleccionado = grupo == grupoSeleccionado
                            FilterChip(
                                selected = seleccionado,
                                onClick = {
                                    grupoSeleccionado = grupo
                                    guardadoExitoso = false
                                },
                                label = { Text("Grupo $grupo") },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = AzulMarino,
                                    selectedLabelColor = Color.White
                                )
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
                    val estadoActual = asistencias[alumno.matricula] ?: EstadoAsistencia.PRESENTE
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
                                    text = "${alumno.nombres} ${alumno.apellidoPaterno} ${alumno.apellidoMaterno}",
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

                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                EstadoAsistencia.entries.forEach { estado ->
                                    val activo = estadoActual == estado
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .clip(CircleShape)
                                            .background(
                                                if (activo) estado.color else Color(0xFFE2E8F0)
                                            )
                                            .clickable {
                                                asistencias[alumno.matricula] = estado
                                                guardadoExitoso = false
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = estado.sigla,
                                            color = if (activo) Color.White else TextoOscuro,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 13.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}