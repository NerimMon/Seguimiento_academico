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
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Pantalla6RegistrarGrupo(
    onAbrirMenu: () -> Unit = {},
    onRegresar: () -> Unit = {},
    onRegistrarClick: () -> Unit = {}
) {
    val fondo = Color(0xFFF5F8FB)
    val azulOscuro = Color(0xFF15395B)

    var grupo by remember { mutableStateOf("") }
    val listaProfesores = remember { mutableStateListOf("") }

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
            text = "Registrar grupo",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = azulOscuro,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        CampoEtiquetadoLocal("grupo (ej. 3A)", grupo) { grupo = it }

        listaProfesores.forEachIndexed { indice, valorProfesor ->
            val etiqueta = if (indice == 0) "Profesores" else "Profesores ${indice + 1}"
            CampoEtiquetadoLocal(etiqueta, valorProfesor) { nuevoTexto ->
                listaProfesores[indice] = nuevoTexto
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            IconButton(
                onClick = { listaProfesores.add("") }
            ) {
                Icon(
                    imageVector = Icons.Default.AddCircleOutline,
                    contentDescription = "Agregar campo",
                    tint = azulOscuro,
                    modifier = Modifier.size(36.dp)
                )
            }

            if (listaProfesores.size > 1) {
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = { listaProfesores.removeAt(listaProfesores.lastIndex) }
                ) {
                    Icon(
                        imageVector = Icons.Default.RemoveCircleOutline,
                        contentDescription = "Quitar campo",
                        tint = Color(0xFF64748B),
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Button(
            onClick = {
                val profesLimpios = listaProfesores.map { it.trim() }.filter { it.isNotEmpty() }
                if (grupo.isNotBlank()) {
                    BaseDatosLocal.grupos.add(
                        GrupoLocal(
                            grupo = grupo.trim(),
                            profesores = profesLimpios
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