package com.example.seguimientoacademico

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Pantalla1Login(
    onLoginExitoso: (UsuarioLocal) -> Unit = {}
) {
    var valor1 by remember { mutableStateOf("") }
    var valor2 by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf<String?>(null) }

    val fondo = Color(0xFFF5F8FB)
    val azulOscuro = Color(0xFF15395B)
    val azulClaro = Color(0xFFDCE6F1)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Inicio de sesión",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A202C)
        )

        Spacer(modifier = Modifier.height(28.dp))
        Box(
            modifier = Modifier
                .size(96.dp)
                .background(azulClaro, RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Inicio de sesión",
                tint = azulOscuro,
                modifier = Modifier.size(52.dp)
            )
        }

        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "Inicio de sesión",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = azulOscuro
        )

        Spacer(modifier = Modifier.height(28.dp))
        OutlinedTextField(
            value = valor1,
            onValueChange = {
                valor1 = it
                mensajeError = null
            },
            placeholder = { Text("Value", color = Color(0xFF8A94A6)) },
            trailingIcon = {
                if (valor1.isNotEmpty()) {
                    IconButton(onClick = { valor1 = "" }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = Color(0xFF8A94A6)
                        )
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

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = valor2,
            onValueChange = {
                valor2 = it
                mensajeError = null
            },
            placeholder = { Text("Value", color = Color(0xFF8A94A6)) },
            trailingIcon = {
                if (valor2.isNotEmpty()) {
                    IconButton(onClick = { valor2 = "" }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = Color(0xFF8A94A6)
                        )
                    }
                }
            },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        )

        if (mensajeError != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = mensajeError!!,
                color = Color(0xFFB91C1C),
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(28.dp))
        Button(
            onClick = {
                val encontrado = BaseDatosLocal.autenticar(valor1, valor2)
                if (encontrado != null) {
                    mensajeError = null
                    onLoginExitoso(encontrado)
                } else {
                    mensajeError = "Credenciales no válidas"
                }
            },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = azulOscuro),
            modifier = Modifier
                .width(220.dp)
                .height(52.dp)
        ) {
            Text(
                text = "Ingresar",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}