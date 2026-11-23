package com.example.seguimientoacademico

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PantallaProximamente(
    usuario: UsuarioLocal?,
    onAbrirMenu: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val fondo = Color(0xFFF5F8FB)
    val azulOscuro = Color(0xFF15395B)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .padding(20.dp)
    ) {
        BarraSuperiorPantalla(
            tituloSeccion = usuario?.puesto ?: "Inicio",
            esCuestionario = false,
            onAbrirMenu = onAbrirMenu,
            onRegresarConfirmado = onCerrarSesion
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "proximamente",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = azulOscuro
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = usuario?.nombre ?: "",
                fontSize = 15.sp,
                color = Color(0xFF64748B)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onCerrarSesion,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = azulOscuro),
                modifier = Modifier
                    .width(200.dp)
                    .height(48.dp)
            ) {
                Text(
                    text = "Cerrar sesión",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}