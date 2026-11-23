package com.example.seguimientoacademico

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
fun PantallaTutorReportes(
    onAbrirMenu: () -> Unit = {},
    onRegresar: () -> Unit = {}
) {
    val fondo = Color(0xFFF5F8FB)
    val azulOscuro = Color(0xFF15395B)

    val reportes = listOf(
        Triple("Falta de Material | Matemáticas", "15 Nov - Prof. Gómez", "No trajo el libro ni la libreta para la actividad en clase."),
        Triple("No Trabajó | Historia", "12 Nov - Dra. Díaz", "Se distrajo toda la sesión y no completó los ejercicios de historia."),
        Triple("Problema de Conducta | Educación Física", "10 Nov - Prof. Ruíz", "Se peleó/discutió con un compañero durante el juego."),
        Triple("Inasistencia/Retardo | Ciencias", "08 Nov - Mtra. Lee", "Llegó 20 minutos tarde y se perdió la explicación de la clase."),
        Triple("Olvidó Tarea | Español", "05 Nov - Mtra. Lee", "No entregó la tarea correspondiente a la lectura de comprensión."),
        Triple("Falta de Atención | Artes", "02 Nov - Mtra. Cruz", "Se mostró disperso durante la explicación de la clase.")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Reportes",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A202C),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Mateo Luna Pérez | 4° A",
            fontSize = 14.sp,
            color = Color(0xFF64748B),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        BarraSuperiorPantalla(
            tituloSeccion = "Reportes",
            esCuestionario = false,
            onAbrirMenu = onAbrirMenu,
            onRegresarConfirmado = onRegresar
        )

        reportes.forEach { (titulo, subtitulo, detalle) ->
            Spacer(modifier = Modifier.height(10.dp))
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(titulo, fontWeight = FontWeight.Bold, color = azulOscuro, fontSize = 15.sp)
                    Text(subtitulo, fontSize = 12.sp, color = Color(0xFF64748B))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(detalle, fontSize = 14.sp, color = Color(0xFF1A202C))
                }
            }
        }
    }
}

@Composable
fun PantallaTutorAnuncios(
    onAbrirMenu: () -> Unit = {},
    onRegresar: () -> Unit = {}
) {
    val fondo = Color(0xFFF5F8FB)
    val azulOscuro = Color(0xFF15395B)
    var filtro by remember { mutableStateOf("") }

    val anuncios = listOf(
        Triple("27 Nov | Junta Escolar", "Reunión de Padres de Familia", "Fecha: Viernes 27 de Nov | Hora: 8:00 AM. Lugar: Auditorio Principal. Se tratarán temas de evaluación del segundo trimestre y entrega de calificaciones."),
        Triple("10 Dic | Convivio/Evento", "Festejo de Fin de Año", "Fecha: Jueves 10 de Dic | Hora: 10:00 AM. Lugar: Patios de la Escuela. Compartiremos un desayuno navideño con los alumnos."),
        Triple("18 Dic | Festival/Cultural", "Festival de Invierno y Bailables", "Fecha: Viernes 18 de Dic | Hora: 4:00 PM. Lugar: Explanada de la Escuela. Presentación de los grupos de primaria con bailes tradicionales.")
    ).filter {
        filtro.isBlank() ||
                it.first.contains(filtro, ignoreCase = true) ||
                it.second.contains(filtro, ignoreCase = true) ||
                it.third.contains(filtro, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Anuncios",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A202C),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        BarraSuperiorPantalla(
            tituloSeccion = "Anuncios",
            esCuestionario = false,
            onAbrirMenu = onAbrirMenu,
            onRegresarConfirmado = onRegresar
        )

        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = filtro,
            onValueChange = { filtro = it },
            placeholder = { Text("Buscar or filtra por tipo de anuncio", color = Color(0xFF8A94A6)) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )

        anuncios.forEach { (encabezado, titulo, descripcion) ->
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(encabezado, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = azulOscuro)
                    Text(titulo, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1A202C))
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(descripcion, fontSize = 14.sp, color = Color(0xFF475569))
                }
            }
        }
    }
}

@Composable
fun PantallaTutorCalificaciones(
    onAbrirMenu: () -> Unit = {},
    onRegresar: () -> Unit = {}
) {
    val fondo = Color(0xFFF5F8FB)
    val azulOscuro = Color(0xFF15395B)

    val calificaciones = listOf(
        listOf("Matemáticas", "8.5", "9.0", "8.8"),
        listOf("Español", "9.2", "8.5", "9.0"),
        listOf("Historia", "7.5", "8.0", "8.2"),
        listOf("Ciencias", "6.8", "7.5", "-"),
        listOf("Geografía", "8.0", "8.5", "8.7"),
        listOf("Inglés", "9.5", "9.2", "9.8")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Calificaciones",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A202C),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Mateo Luna Pérez | 4° A",
            fontSize = 14.sp,
            color = Color(0xFF64748B),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        BarraSuperiorPantalla(
            tituloSeccion = "Calificaciones",
            esCuestionario = false,
            onAbrirMenu = onAbrirMenu,
            onRegresarConfirmado = onRegresar
        )

        Spacer(modifier = Modifier.height(12.dp))
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp)
            ) {
                Text("Promedio General:", fontSize = 14.sp, color = Color(0xFF64748B))
                Text("8.6", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = azulOscuro)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text("Materia", fontWeight = FontWeight.Bold, color = azulOscuro, modifier = Modifier.weight(1.4f))
                    Text("Periodo 1", fontWeight = FontWeight.Bold, color = azulOscuro, modifier = Modifier.weight(1f))
                    Text("Periodo 2", fontWeight = FontWeight.Bold, color = azulOscuro, modifier = Modifier.weight(1f))
                    Text("Periodo 3", fontWeight = FontWeight.Bold, color = azulOscuro, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider()

                calificaciones.forEach { fila ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(fila[0], fontWeight = FontWeight.Bold, color = Color(0xFF1A202C), modifier = Modifier.weight(1.4f))
                        Text(fila[1], color = Color(0xFF334155), modifier = Modifier.weight(1f))
                        Text(fila[2], color = Color(0xFF334155), modifier = Modifier.weight(1f))
                        Text(fila[3], color = Color(0xFF334155), modifier = Modifier.weight(1f))
                    }
                    HorizontalDivider(color = Color(0xFFF1F5F9))
                }
            }
        }
    }
}