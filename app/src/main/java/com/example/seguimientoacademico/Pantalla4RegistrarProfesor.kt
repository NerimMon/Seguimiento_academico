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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Pantalla4RegistrarProfesor(
    onAbrirMenu: () -> Unit = {},
    onRegresar: () -> Unit = {},
    onRegistrarClick: () -> Unit = {}
) {
    val fondo = Color(0xFFF5F8FB)
    val azulOscuro = Color(0xFF15395B)

    var nombres by remember { mutableStateOf("") }
    var apellidoMaterno by remember { mutableStateOf("") }
    var apellidoPaterno by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var numeroEmpleado by remember { mutableStateOf("") }
    var materias by remember { mutableStateOf("") }

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
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Director",
            fontSize = 15.sp,
            color = Color(0xFF64748B),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        BarraSuperiorPantalla(
            tituloSeccion = "Perfil",
            esCuestionario = true,
            onAbrirMenu = onAbrirMenu,
            onRegresarConfirmado = onRegresar
        )

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Registrar profesor",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = azulOscuro,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        CampoEtiquetadoLocal("Nombre(s):", nombres) { nombres = it }
        CampoEtiquetadoLocal("Apellido Materno:", apellidoMaterno) { apellidoMaterno = it }
        CampoEtiquetadoLocal("Apellido Paterno:", apellidoPaterno) { apellidoPaterno = it }
        CampoEtiquetadoLocal("Correo:", correo) { correo = it }
        CampoEtiquetadoLocal("Telefono:", telefono) { telefono = it }
        CampoEtiquetadoLocal("Numero de empleado:", numeroEmpleado) { numeroEmpleado = it }
        CampoEtiquetadoLocal("Materias:", materias) { materias = it }

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                if (nombres.isNotBlank() || numeroEmpleado.isNotBlank()) {
                    BaseDatosLocal.profesores.add(
                        ProfesorLocal(
                            nombres = nombres.trim(),
                            apellidoMaterno = apellidoMaterno.trim(),
                            apellidoPaterno = apellidoPaterno.trim(),
                            correo = correo.trim(),
                            telefono = telefono.trim(),
                            numeroEmpleado = numeroEmpleado.trim(),
                            materias = materias.trim()
                        )
                    )
                }
                onRegistrarClick()
            },
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

@Composable
internal fun CampoEtiquetadoLocal(
    etiqueta: String,
    valor: String,
    placeholderTexto: String = "Input",
    onValueChange: (String) -> Unit
) {
    Spacer(modifier = Modifier.height(14.dp))
    Text(
        text = etiqueta,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF1A202C)
    )
    Spacer(modifier = Modifier.height(6.dp))
    OutlinedTextField(
        value = valor,
        onValueChange = onValueChange,
        placeholder = { Text(placeholderTexto, color = Color(0xFF8A94A6)) },
        trailingIcon = {
            if (valor.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {
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
}