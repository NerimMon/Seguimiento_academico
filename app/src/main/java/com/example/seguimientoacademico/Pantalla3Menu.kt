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

enum class RutaPantalla {
    LOGIN,
    PROXIMAMENTE,
    AVISOS,
    MENU,
    REGISTRAR_PROFESOR,
    REGISTRAR_TUTOR,
    REGISTRAR_ALUMNO,
    REGISTRAR_GRUPO,
    NUEVO_ANUNCIO,
    LISTADO_ALUMNOS,
    LISTADO_PROFESORES,
    LISTADO_GRUPOS
}

@Composable
fun Pantalla3Menu(
    onNavegar: (RutaPantalla) -> Unit = {},
    onAbrirMenu: () -> Unit = {},
    onRegresar: () -> Unit = {}
) {
    val fondo = Color(0xFFF5F8FB)
    val azulOscuro = Color(0xFF15395B)
    val azulClaro = Color(0xFFDCE6F1)

    val opciones = listOf(
        "Registrar profesor" to RutaPantalla.REGISTRAR_PROFESOR,
        "Registrar Alumno" to RutaPantalla.REGISTRAR_ALUMNO,
        "Registrar Tutores" to RutaPantalla.REGISTRAR_TUTOR,
        "Registrar grupo" to RutaPantalla.REGISTRAR_GRUPO,
        "Publicar anuncio general" to RutaPantalla.NUEVO_ANUNCIO,
        "Visualizar actividades" to RutaPantalla.AVISOS,
        "Listado de alumnos" to RutaPantalla.LISTADO_ALUMNOS,
        "Listado de profesores" to RutaPantalla.LISTADO_PROFESORES,
        "Listado de grupos" to RutaPantalla.LISTADO_GRUPOS
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Menu",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A202C),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Director",
            fontSize = 15.sp,
            color = Color(0xFF64748B),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        BarraSuperiorPantalla(
            tituloSeccion = "Menu",
            esCuestionario = false,
            onAbrirMenu = onAbrirMenu,
            onRegresarConfirmado = onRegresar
        )

        opciones.forEach { (titulo, destino) ->
            Spacer(modifier = Modifier.height(10.dp))
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
                        .padding(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.AccountCircle,
                        contentDescription = titulo,
                        tint = azulOscuro,
                        modifier = Modifier
                            .size(52.dp)
                            .background(azulClaro, RoundedCornerShape(12.dp))
                            .padding(10.dp)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Text(
                        text = titulo,
                        fontSize = 15.sp,
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