package com.example.seguimientoacademico

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaTutorProfesores(
    onSeleccionarProfesor: (ProfesorTutorInfo) -> Unit,
    onAbrirMenu: () -> Unit = {},
    onRegresar: () -> Unit = {}
) {
    val fondo = Color(0xFFF5F8FB)
    val azulOscuro = Color(0xFF15395B)
    val azulClaro = Color(0xFFDCE6F1)
    var busqueda by remember { mutableStateOf("") }

    val filtrados = profesoresTutorEjemplo.filter {
        busqueda.isBlank() ||
                it.nombre.contains(busqueda, ignoreCase = true) ||
                it.materia.contains(busqueda, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Profesores",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A202C),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        BarraSuperiorPantalla(
            tituloSeccion = "Profesores",
            esCuestionario = false,
            onAbrirMenu = onAbrirMenu,
            onRegresarConfirmado = onRegresar
        )

        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = busqueda,
            onValueChange = { busqueda = it },
            placeholder = { Text("Buscar profesor o materia", color = Color(0xFF8A94A6)) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null, tint = Color(0xFF64748B))
            },
            trailingIcon = {
                if (busqueda.isNotEmpty()) {
                    IconButton(onClick = { busqueda = "" }) {
                        Icon(Icons.Default.Close, contentDescription = null, tint = Color(0xFF64748B))
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )

        filtrados.forEach { prof ->
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = azulClaro),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSeleccionarProfesor(prof) }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .background(Color.White, RoundedCornerShape(27.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = prof.nombre,
                            tint = azulOscuro,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = prof.materia,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1A202C)
                        )
                        Text(
                            text = prof.nombre,
                            fontSize = 14.sp,
                            color = Color(0xFF334155)
                        )
                        Text(
                            text = "${prof.grupo} | ${prof.turno}",
                            fontSize = 13.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Ver más",
                            tint = azulOscuro
                        )
                        Text(
                            text = "Ver más",
                            fontSize = 12.sp,
                            color = azulOscuro
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PantallaTutorDetalleProfesor(
    profesor: ProfesorTutorInfo,
    onSolicitarAsesoria: () -> Unit,
    onSolicitarReunion: () -> Unit,
    onAbrirMenu: () -> Unit = {},
    onRegresar: () -> Unit = {}
) {
    val fondo = Color(0xFFF5F8FB)
    val azulOscuro = Color(0xFF15395B)
    val azulClaro = Color(0xFFDCE6F1)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Detalle de Profesor",
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A202C)
        )

        BarraSuperiorPantalla(
            tituloSeccion = "Detalle de Profesor",
            esCuestionario = false,
            onAbrirMenu = onAbrirMenu,
            onRegresarConfirmado = onRegresar
        )

        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .size(104.dp)
                .background(azulClaro, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = profesor.nombre,
                tint = azulOscuro,
                modifier = Modifier.size(64.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                Text(
                    text = "Datos del profesor:",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = azulOscuro
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = profesor.nombre,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A202C)
                )
                Text(text = "Grupo: ${profesor.grupo}", color = Color(0xFF334155))
                Text(text = "Turno: ${profesor.turno}", color = Color(0xFF334155))
                Text(text = "Materia: ${profesor.materia}", color = Color(0xFF334155))
            }
        }

        Spacer(modifier = Modifier.height(18.dp))
        Button(
            onClick = onSolicitarAsesoria,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = azulOscuro),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Solicitar Asesorías", color = Color.White, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = onSolicitarReunion,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = azulClaro),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Solicitar Reunión", color = azulOscuro, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Selecciona una opción para agendar",
            fontSize = 13.sp,
            color = Color(0xFF64748B)
        )
    }
}