package com.example.seguimientoacademico

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
fun Pantalla5RegistrarTutor(
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
    val matriculasAlumnos = remember { mutableStateListOf("") }

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
            text = "Registrar Tutor",
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

        matriculasAlumnos.forEachIndexed { indice, valorMatricula ->
            val etiqueta = if (indice == 0) "Matricula alumno:" else "Matricula alumno ${indice + 1}:"
            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = etiqueta,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A202C)
            )
            Spacer(modifier = Modifier.height(6.dp))
            OutlinedTextField(
                value = valorMatricula,
                onValueChange = { nuevoValor -> matriculasAlumnos[indice] = nuevoValor },
                placeholder = { Text("Input", color = Color(0xFF8A94A6)) },
                trailingIcon = {
                    if (valorMatricula.isNotEmpty()) {
                        IconButton(onClick = { matriculasAlumnos[indice] = "" }) {
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

        Spacer(modifier = Modifier.height(14.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            IconButton(
                onClick = { matriculasAlumnos.add("") }
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircleOutline,
                    contentDescription = "+",
                    tint = azulOscuro,
                    modifier = Modifier.size(36.dp)
                )
            }

            if (matriculasAlumnos.size > 1) {
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = { matriculasAlumnos.removeAt(matriculasAlumnos.lastIndex) }
                ) {
                    Icon(
                        imageVector = Icons.Default.RemoveCircleOutline,
                        contentDescription = "Quitar",
                        tint = Color(0xFF64748B),
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
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