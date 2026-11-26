package com.example.seguimientoacademico.profesores

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seguimientoacademico.GrupoLocal
import kotlinx.coroutines.launch

private val FondoPantalla = Color(0xFFF0F4F8)
private val AzulMarino = Color(0xFF284361)
private val TextoOscuro = Color(0xFF1E293B)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaProfesorRecadoGrupal(
    grupos: List<GrupoLocal> = emptyList(),
    onEnviarRecado: suspend (String, String, String) -> Unit = { _, _, _ -> },
    onAbrirMenu: () -> Unit = {},
    onRegresar: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val gruposDisponibles = remember(grupos) {
        grupos.map { it.grupo }.distinct().ifEmpty { listOf("3A", "4B") }
    }

    var grupoSeleccionado by remember(gruposDisponibles) {
        mutableStateOf(gruposDisponibles.first())
    }
    var asunto by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var enviado by remember { mutableStateOf(false) }

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
                        text = "Recado Grupal",
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
                    Text(
                        text = "Grupo destinatario",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoOscuro
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        gruposDisponibles.forEach { g ->
                            FilterChip(
                                selected = g == grupoSeleccionado,
                                onClick = {
                                    grupoSeleccionado = g
                                    enviado = false
                                },
                                label = { Text("Grupo $g") }
                            )
                        }
                    }

                    OutlinedTextField(
                        value = asunto,
                        onValueChange = {
                            asunto = it
                            enviado = false
                        },
                        label = { Text("Asunto del recado") },
                        placeholder = { Text("Ej. Material para clase de mañana") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = mensaje,
                        onValueChange = {
                            mensaje = it
                            enviado = false
                        },
                        label = { Text("Mensaje para padres y alumnos") },
                        placeholder = { Text("Escribe el comunicado aquí...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        shape = RoundedCornerShape(12.dp)
                    )

                    if (enviado) {
                        Text(
                            text = "Recado enviado a los tutores del Grupo $grupoSeleccionado.",
                            color = Color(0xFF16A34A),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Button(
                        onClick = {
                            val asuntoLimpio = asunto.trim()
                            val mensajeLimpio = mensaje.trim()
                            if (asuntoLimpio.isNotBlank() && mensajeLimpio.isNotBlank()) {
                                scope.launch {
                                    onEnviarRecado(grupoSeleccionado, asuntoLimpio, mensajeLimpio)
                                    enviado = true
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AzulMarino)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Enviar Recado", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}