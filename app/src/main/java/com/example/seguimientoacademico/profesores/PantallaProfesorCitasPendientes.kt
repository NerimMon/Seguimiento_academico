package com.example.seguimientoacademico.profesores

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val FondoPantalla = Color(0xFFF0F4F8)
private val AzulMarino = Color(0xFF284361)
private val TextoOscuro = Color(0xFF1E293B)
private val TextoSecundario = Color(0xFF64748B)

data class CitaTutorItem(
    val id: String,
    val tutor: String,
    val alumno: String,
    val motivo: String,
    val horarioSolicitado: String,
    val estado: String = "Pendiente"
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaProfesorCitasPendientes(
    onAbrirMenu: () -> Unit = {},
    onRegresar: () -> Unit = {}
) {
    val citas = remember {
        mutableStateListOf(
            CitaTutorItem(
                id = "1",
                tutor = "Carlos Mendoza",
                alumno = "Sofía Mendoza (3A)",
                motivo = "Revisión de avance en matemáticas",
                horarioSolicitado = "Jueves 26/11 · 13:30 hrs"
            ),
            CitaTutorItem(
                id = "2",
                tutor = "Ana Ruiz",
                alumno = "Mateo Hernández (4B)",
                motivo = "Estrategias de estudio en casa",
                horarioSolicitado = "Viernes 27/11 · 12:45 hrs"
            )
        )
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
                        text = "Citas Pendientes",
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(citas, key = { it.id }) { cita ->
                val colorEstado = when (cita.estado) {
                    "Confirmada" -> Color(0xFF16A34A)
                    "Reprogramar" -> Color(0xFFD97706)
                    else -> AzulMarino
                }
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = cita.tutor,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextoOscuro
                            )
                            Text(
                                text = cita.estado,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorEstado
                            )
                        }
                        Text(
                            text = "Alumno: ${cita.alumno}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextoSecundario
                        )
                        Text(
                            text = "Motivo: ${cita.motivo}",
                            fontSize = 13.sp,
                            color = TextoOscuro
                        )
                        Text(
                            text = "Horario: ${cita.horarioSolicitado}",
                            fontSize = 12.sp,
                            color = TextoSecundario
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = {
                                    val idx = citas.indexOfFirst { it.id == cita.id }
                                    if (idx >= 0) citas[idx] = cita.copy(estado = "Confirmada")
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = AzulMarino)
                            ) {
                                Text("Confirmar", fontSize = 12.sp)
                            }
                            OutlinedButton(
                                onClick = {
                                    val idx = citas.indexOfFirst { it.id == cita.id }
                                    if (idx >= 0) citas[idx] = cita.copy(estado = "Reprogramar")
                                },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("Reprogramar", fontSize = 12.sp, color = TextoOscuro)
                            }
                        }
                    }
                }
            }
        }
    }
}