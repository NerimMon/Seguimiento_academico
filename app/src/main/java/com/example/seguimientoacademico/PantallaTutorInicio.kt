package com.example.seguimientoacademico

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class RutaTutor {
    INICIO,
    ALUMNO,
    PROFESORES,
    DETALLE_PROFESOR,
    SOLICITUD_ASESORIA,
    SOLICITUD_REUNION,
    REPORTES,
    ANUNCIOS,
    CALIFICACIONES
}

data class ProfesorTutorInfo(
    val nombre: String,
    val materia: String,
    val grupo: String,
    val turno: String
)

val profesoresTutorEjemplo = listOf(
    ProfesorTutorInfo("Lic. Laura Gómez", "Matemáticas", "4° A", "Matutino"),
    ProfesorTutorInfo("Mtra. Sofía Lee", "Español", "4° A", "Matutino"),
    ProfesorTutorInfo("Dr. Carlos Díaz", "Historia", "4° A", "Matutino"),
    ProfesorTutorInfo("Mtra. Ana Ruiz", "Ciencias", "4° A", "Matutino")
)

@Composable
fun PantallaTutorInicio(
    usuario: UsuarioLocal?,
    onNavegar: (RutaTutor) -> Unit,
    onAbrirMenu: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val fondo = Color(0xFFF5F8FB)
    val azulOscuro = Color(0xFF15395B)
    val azulClaro = Color(0xFFDCE6F1)

    val secciones = listOf(
        "Información del Alumno" to RutaTutor.ALUMNO,
        "Profesores" to RutaTutor.PROFESORES,
        "Reportes" to RutaTutor.REPORTES,
        "Anuncios" to RutaTutor.ANUNCIOS,
        "Calificaciones" to RutaTutor.CALIFICACIONES
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Tutor",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A202C),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = usuario?.nombre ?: "Padre de familia",
            fontSize = 15.sp,
            color = Color(0xFF64748B),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        BarraSuperiorPantalla(
            tituloSeccion = "Mateo Luna Pérez | 4° A",
            esCuestionario = false,
            onAbrirMenu = onAbrirMenu,
            onRegresarConfirmado = onCerrarSesion
        )

        secciones.forEach { (titulo, destino) ->
            Spacer(modifier = Modifier.height(12.dp))
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavegar(destino) }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = titulo,
                        tint = azulOscuro,
                        modifier = Modifier
                            .size(48.dp)
                            .background(azulClaro, RoundedCornerShape(12.dp))
                            .padding(10.dp)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                        text = titulo,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A202C),
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = titulo,
                        tint = Color(0xFF64748B)
                    )
                }
            }
        }
    }
}