package com.example.seguimientoacademico

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun Pantalla1Login(
    onAutenticar: suspend (String, String) -> UsuarioLocal?,
    onLoginExitoso: (UsuarioLocal) -> Unit
) {
    val scope = rememberCoroutineScope()
    var usuarioTexto by remember { mutableStateOf("") }
    var contrasenaTexto by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf<String?>(null) }
    var cargando by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Seguimiento Académico",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF284361)
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = usuarioTexto,
            onValueChange = {
                usuarioTexto = it
                mensajeError = null
            },
            label = { Text("Usuario") },
            singleLine = true,
            enabled = !cargando,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = contrasenaTexto,
            onValueChange = {
                contrasenaTexto = it
                mensajeError = null
            },
            label = { Text("Contraseña") },
            singleLine = true,
            enabled = !cargando,
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        mensajeError?.let { error ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                fontSize = 13.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                val u = usuarioTexto.trim()
                val p = contrasenaTexto.trim()
                if (u.isEmpty() || p.isEmpty()) {
                    mensajeError = "Ingresa tu usuario y contraseña."
                    return@Button
                }
                cargando = true
                scope.launch {
                    val encontrado = onAutenticar(u, p)
                    cargando = false
                    if (encontrado != null) {
                        onLoginExitoso(encontrado)
                    } else {
                        mensajeError = "Usuario o contraseña incorrectos."
                    }
                }
            },
            enabled = !cargando,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF284361))
        ) {
            if (cargando) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = Color.White
                )
            } else {
                Text("Iniciar sesión", fontWeight = FontWeight.Bold)
            }
        }
    }
}