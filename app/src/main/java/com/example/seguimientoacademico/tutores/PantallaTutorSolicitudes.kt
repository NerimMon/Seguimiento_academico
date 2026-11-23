package com.example.seguimientoacademico

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PantallaTutorSolicitudAsesoria(
    profesor: ProfesorTutorInfo,
    onAbrirMenu: () -> Unit = {},
    onRegresar: () -> Unit = {}
) {
    val fondo = Color(0xFFF5F8FB)
    val azulOscuro = Color(0xFF15395B)

    val fechas = listOf("Lun 23", "Mar 24", "Mié 25", "Jue 26", "Vie 27")
    val horas = listOf("08:30 AM", "12:00 PM", "04:30 PM", "10:30 PM", "01:30 PM")

    var fechaSeleccionada by remember { mutableStateOf("Mié 25") }
    var horaSeleccionada by remember { mutableStateOf("08:30 AM") }
    var confirmacionTexto by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Solicitud de Asesoría",
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A202C),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "${profesor.materia} | ${profesor.nombre}",
            fontSize = 14.sp,
            color = Color(0xFF64748B),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        BarraSuperiorPantalla(
            tituloSeccion = "Solicitud de Asesoría",
            esCuestionario = true,
            onAbrirMenu = onAbrirMenu,
            onRegresarConfirmado = onRegresar
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Horarios disponibles para la asesoría",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = azulOscuro
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Seleccionar Fecha", fontSize = 13.sp, fontWeight = FontWeight.Bold)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            fechas.forEach { fecha ->
                FilterChip(
                    selected = fechaSeleccionada == fecha,
                    onClick = { fechaSeleccionada = fecha },
                    label = { Text(fecha) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text("Horas Disponibles", fontSize = 13.sp, fontWeight = FontWeight.Bold)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            horas.forEach { hora ->
                FilterChip(
                    selected = horaSeleccionada == hora,
                    onClick = { horaSeleccionada = hora },
                    label = { Text(hora) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                confirmacionTexto = "Confirmado: $fechaSeleccionada a las $horaSeleccionada"
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = azulOscuro),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Confirmar Horario", color = Color.White, fontWeight = FontWeight.Bold)
        }

        if (confirmacionTexto != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = confirmacionTexto!!, color = azulOscuro, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(18.dp))
        Text("Estado de asesorías", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = azulOscuro)
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Asesoría Actual (Vigente) | 25 Nov - 08:30 AM", fontWeight = FontWeight.Bold, color = Color(0xFF1A202C))
                Text("Observaciones: Reforzar operaciones básicas y resolución de problemas.", fontSize = 13.sp, color = Color(0xFF475569))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Asesoría Anterior (Terminada) | 18 Nov - 12:00 PM", fontWeight = FontWeight.Bold, color = Color(0xFF1A202C))
                Text("Observaciones: El alumno mostró gran avance, falta repasar en casa.", fontSize = 13.sp, color = Color(0xFF475569))
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PantallaTutorSolicitudReunion(
    profesor: ProfesorTutorInfo,
    onAbrirMenu: () -> Unit = {},
    onRegresar: () -> Unit = {}
) {
    val fondo = Color(0xFFF5F8FB)
    val azulOscuro = Color(0xFF15395B)

    val fechas = listOf("Lun 23", "Mar 24", "Mié 25", "Jue 26", "Vie 27")
    val horas = listOf("09:00 AM", "10:30 AM", "01:00 PM", "04:30 PM")

    var fechaSeleccionada by remember { mutableStateOf("Mié 25") }
    var horaSeleccionada by remember { mutableStateOf("09:00 AM") }
    var reunionCancelada by remember { mutableStateOf(false) }
    var avisoAgenda by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Solicitud de Reunión",
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A202C),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "${profesor.materia} | ${profesor.nombre}",
            fontSize = 14.sp,
            color = Color(0xFF64748B),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        BarraSuperiorPantalla(
            tituloSeccion = "Solicitud de Reunión",
            esCuestionario = true,
            onAbrirMenu = onAbrirMenu,
            onRegresarConfirmado = onRegresar
        )

        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Horarios disponibles para la reunión",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = azulOscuro
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Seleccionar Fecha", fontSize = 13.sp, fontWeight = FontWeight.Bold)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            fechas.forEach { fecha ->
                FilterChip(
                    selected = fechaSeleccionada == fecha,
                    onClick = { fechaSeleccionada = fecha },
                    label = { Text(fecha) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text("Horas Disponibles", fontSize = 13.sp, fontWeight = FontWeight.Bold)
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            horas.forEach { hora ->
                FilterChip(
                    selected = horaSeleccionada == hora,
                    onClick = { horaSeleccionada = hora },
                    label = { Text(hora) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                reunionCancelada = false
                avisoAgenda = "Reunión agendada: $fechaSeleccionada - $horaSeleccionada"
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = azulOscuro),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Agendar Reunión", color = Color.White, fontWeight = FontWeight.Bold)
        }

        if (avisoAgenda != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = avisoAgenda!!, color = azulOscuro, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(18.dp))
        Text("Reuniones Programadas y Anteriores", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = azulOscuro)
        Spacer(modifier = Modifier.height(8.dp))

        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = if (reunionCancelada) "Próxima Reunión (Cancelada)" else "Próxima Reunión | 27 Nov - 09:00 AM",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A202C)
                )
                Text("Lugar: Aula 4B", fontSize = 13.sp, color = Color(0xFF475569))
                if (!reunionCancelada) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Button(
                        onClick = { reunionCancelada = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDCE6F1))
                    ) {
                        Text("Cancelar Reunión", color = azulOscuro, fontSize = 13.sp)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text("Reunión Anterior (Realizada) | 12 Nov - 10:30 AM", fontWeight = FontWeight.Bold, color = Color(0xFF1A202C))
                Text("Minuta/Información: Se habló sobre el progreso de Mateo.", fontSize = 13.sp, color = Color(0xFF475569))
                Text("Acuerdos: Apoyo en casa con lecturas.", fontSize = 13.sp, color = Color(0xFF475569))
                Text("Asistió: Javier L. (Tutor)", fontSize = 13.sp, color = Color(0xFF64748B))
            }
        }
    }
}
