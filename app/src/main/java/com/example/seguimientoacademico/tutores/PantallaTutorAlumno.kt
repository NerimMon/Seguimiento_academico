package com.example.seguimientoacademico

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaTutorAlumno(
    onAbrirMenu: () -> Unit = {},
    onRegresar: () -> Unit = {}
) {
    val fondo = Color(0xFFF5F8FB)
    val azulOscuro = Color(0xFF15395B)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Información del Alumno",
            fontSize = 21.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A202C),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Mateo Luna Pérez",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = azulOscuro,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Edad: 9 años | Grupo: 4° A | Turno: Matutino",
            fontSize = 13.sp,
            color = Color(0xFF64748B),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        BarraSuperiorPantalla(
            tituloSeccion = "Alumno",
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
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Tarjeta de Datos del Alumno",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = azulOscuro
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text("Nombre completo: Mateo Luna Pérez", color = Color(0xFF1A202C))
                Text("Edad: 9 años", color = Color(0xFF475569))
                Text("Grupo: 4° A", color = Color(0xFF475569))
                Text("Turno: Matutino", color = Color(0xFF475569))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Tarjeta de Información de Grado, Horario y Profesores",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = azulOscuro
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text("4to de Primaria", color = Color(0xFF1A202C))
                Text("Horario: Lunes a Viernes 8:00 AM - 1:00 PM", color = Color(0xFF475569))
                Text("Maestra de Grupo: Laura Gómez", color = Color(0xFF475569))
                Spacer(modifier = Modifier.height(8.dp))
                Text("8:00 - 9:00 | Lun/Mié/Vie: Física / Mate (Lic. Gómez) | Mar/Jue: Ing. Ruiz", fontSize = 13.sp, color = Color(0xFF64748B))
                Text("9:00 - 10:00 | Lun/Mié/Vie: Historia (Dra. Díaz) | Mar/Jue: Español (Mtra. Lee)", fontSize = 13.sp, color = Color(0xFF64748B))
                Text("10:30 - 11:30 | Lun/Mié/Vie: Inglés (Prof. Smith) | Mar/Jue: Artes (Mtra. Cruz)", fontSize = 13.sp, color = Color(0xFF64748B))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Tarjeta de Información de Salud",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = azulOscuro
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text("Alergias: Polen y Nueces", color = Color(0xFF1A202C))
                Text("Estatura: 1.34 m", color = Color(0xFF475569))
            }
        }
    }
}
