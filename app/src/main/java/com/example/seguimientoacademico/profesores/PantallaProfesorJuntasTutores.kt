package com.example.seguimientoacademico.profesores

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val FondoPantalla = Color(0xFFF0F4F8)
private val AzulMarino = Color(0xFF284361)
private val AmarilloCremaBg = Color(0xFFFEF7DC)
private val AmarilloIcono = Color(0xFFE5B526)
private val TextoOscuro = Color(0xFF1E293B)
private val TextoSecundario = Color(0xFF64748B)

data class JuntaTutorItem(
    val id: String,
    val titulo: String,
    val grupo: String,
    val fechaHora: String,
    val aula: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaProfesorJuntasTutores(
    onAbrirMenu: () -> Unit = {},
    onRegresar: () -> Unit = {}
) {
    val juntas = remember {
        mutableStateListOf(
            JuntaTutorItem(
                id = "1",
                titulo = "Entrega de boletas y seguimiento del 1er parcial",
                grupo = "Grupo 3A",
                fechaHora = "28/11/2026 · 08:30 hrs",
                aula = "Salón 12"
            ),
            JuntaTutorItem(
                id = "2",
                titulo = "Acuerdos de tareas y evaluación continua",
                grupo = "Grupo 4B",
                fechaHora = "02/12/2026 · 09:00 hrs",
                aula = "Aula de Usos Múltiples"
            )
        )
    }

    var mostrarFormulario by remember { mutableStateOf(false) }
    var nuevoTitulo by remember { mutableStateOf("") }
    var nuevoGrupo by remember { mutableStateOf("3A") }
    var nuevaFecha by remember { mutableStateOf("") }

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
                        text = "Juntas de Tutores",
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
        floatingActionButton = {
            FloatingActionButton(
                onClick = { mostrarFormulario = !mostrarFormulario },
                containerColor = AzulMarino,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.CalendarMonth, contentDescription = "Programar junta")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 72.dp)
        ) {
            if (mostrarFormulario) {
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Text(
                                text = "Programar nueva junta",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextoOscuro
                            )
                            OutlinedTextField(
                                value = nuevoTitulo,
                                onValueChange = { nuevoTitulo = it },
                                label = { Text("Tema de la junta") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp)
                            )
                            OutlinedTextField(
                                value = nuevoGrupo,
                                onValueChange = { nuevoGrupo = it },
                                label = { Text("Grupo (ej. 3A)") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp)
                            )
                            OutlinedTextField(
                                value = nuevaFecha,
                                onValueChange = { nuevaFecha = it },
                                label = { Text("Fecha y hora (ej. 05/12/2026 · 08:00)") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp)
                            )
                            Button(
                                onClick = {
                                    if (nuevoTitulo.isNotBlank() && nuevaFecha.isNotBlank()) {
                                        juntas.add(
                                            0,
                                            JuntaTutorItem(
                                                id = (juntas.size + 1).toString(),
                                                titulo = nuevoTitulo.trim(),
                                                grupo = "Grupo ${nuevoGrupo.trim()}",
                                                fechaHora = nuevaFecha.trim(),
                                                aula = "Por asignar"
                                            )
                                        )
                                        nuevoTitulo = ""
                                        nuevaFecha = ""
                                        mostrarFormulario = false
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = AzulMarino)
                            ) {
                                Text("Guardar Junta")
                            }
                        }
                    }
                }
            }

            items(juntas, key = { it.id }) { junta ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(AmarilloCremaBg, RoundedCornerShape(8.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = junta.grupo,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextoOscuro
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.CalendarMonth,
                                contentDescription = null,
                                tint = AmarilloIcono,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = junta.fechaHora,
                                fontSize = 12.sp,
                                color = TextoSecundario
                            )
                        }
                        Text(
                            text = junta.titulo,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoOscuro
                        )
                        Text(
                            text = "Lugar: ${junta.aula}",
                            fontSize = 12.sp,
                            color = TextoSecundario
                        )
                    }
                }
            }
        }
    }
}