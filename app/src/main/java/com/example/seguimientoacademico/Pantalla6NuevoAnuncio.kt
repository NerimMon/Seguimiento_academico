package com.example.seguimientoacademico

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Pantalla6NuevoAnuncio(
    onAbrirMenu: () -> Unit = {},
    onRegresar: () -> Unit = {},
    onRegistrarClick: () -> Unit = {}
) {
    val fondo = Color(0xFFF5F8FB)
    val azulOscuro = Color(0xFF15395B)

    var banner by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Text(
            text = "Perfil",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A202C),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = "Director",
            fontSize = 15.sp,
            color = Color(0xFF64748B),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        BarraSuperiorPantalla(
            tituloSeccion = "Perfil",
            esCuestionario = true,
            onAbrirMenu = onAbrirMenu,
            onRegresarConfirmado = onRegresar
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Nuevo anuncio",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = azulOscuro,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        CampoEtiquetadoLocal("Ingrese banner (.jpg):", banner) { banner = it }
        CampoEtiquetadoLocal("Descripcion", descripcion) { descripcion = it }

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = onRegistrarClick,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = azulOscuro),
            modifier = Modifier
                .width(220.dp)
                .height(52.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = "Registrar",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}