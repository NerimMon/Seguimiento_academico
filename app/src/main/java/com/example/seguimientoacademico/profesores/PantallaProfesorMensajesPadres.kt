package com.example.seguimientoacademico.profesores

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
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
private val TextoOscuro = Color(0xFF1E293B)
private val TextoSecundario = Color(0xFF64748B)

data class MensajeTutorItem(
    val id: String,
    val tutor: String,
    val alumno: String,
    val hora: String,
    val ultimoMensaje: String,
    val noLeido: Boolean = true
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaProfesorMensajesPadres(
    onAbrirMenu: () -> Unit = {},
    onRegresar: () -> Unit = {}
) {
    val conversaciones = remember {
        mutableStateListOf(
            MensajeTutorItem(
                id = "1",
                tutor = "Carlos Mendoza",
                alumno = "Sofía Mendoza (3A)",
                hora = "10:15",
                ultimoMensaje = "Buenos días maestra, ¿podemos revisar el tema de fracciones?",
                noLeido = true
            ),
            MensajeTutorItem(
                id = "2",
                tutor = "Ana Ruiz",
                alumno = "Mateo Hernández (4B)",
                hora = "Ayer",
                ultimoMensaje = "Gracias por el aviso de la tarea de esta semana.",
                noLeido = false
            )
        )
    }

    var seleccionada by remember { mutableStateOf(conversaciones.firstOrNull()) }
    var respuestaTexto by remember { mutableStateOf("") }

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
                        text = "Mensajes de Padres",
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
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(conversaciones, key = { it.id }) { item ->
                    val estaSeleccionada = seleccionada?.id == item.id
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { seleccionada = item },
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (estaSeleccionada) Color(0xFFE3EEF8) else Color.White
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = item.tutor,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextoOscuro
                                )
                                Text(
                                    text = item.hora,
                                    fontSize = 11.sp,
                                    color = TextoSecundario
                                )
                            }
                            Text(
                                text = item.alumno,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = AzulMarino
                            )
                            Text(
                                text = item.ultimoMensaje,
                                fontSize = 12.sp,
                                color = TextoSecundario
                            )
                            if (item.noLeido) {
                                Box(
                                    modifier = Modifier
                                        .padding(top = 4.dp)
                                        .background(AmarilloCremaBg, RoundedCornerShape(6.dp))
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "Sin responder",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = TextoOscuro
                                    )
                                }
                            }
                        }
                    }
                }
            }

            seleccionada?.let { actual ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Responder a \${actual.tutor}",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoOscuro
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = respuestaTexto,
                                onValueChange = { respuestaTexto = it },
                                placeholder = { Text("Escribe tu respuesta...") },
                                modifier = Modifier.weight(1f),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp)
                            )
                            IconButton(
                                onClick = {
                                    if (respuestaTexto.isNotBlank()) {
                                        val idx = conversaciones.indexOfFirst { it.id == actual.id }
                                        if (idx >= 0) {
                                            val actualizada = actual.copy(
                                                ultimoMensaje = "Tú: \${respuestaTexto.trim()}",
                                                hora = "Ahora",
                                                noLeido = false
                                            )
                                            conversaciones[idx] = actualizada
                                            seleccionada = actualizada
                                        }
                                        respuestaTexto = ""
                                    }
                                },
                                modifier = Modifier
                                    .size(48.dp)
                                    .background(AzulMarino, RoundedCornerShape(12.dp))
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Send,
                                    contentDescription = "Enviar",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}