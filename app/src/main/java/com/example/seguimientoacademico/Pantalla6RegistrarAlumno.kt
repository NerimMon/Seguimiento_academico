package com.example.seguimientoacademico

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Pantalla6RegistrarAlumno(
    onAbrirMenu: () -> Unit = {},
    onRegresar: () -> Unit = {},
    onRegistrarClick: () -> Unit = {}
) {
    val fondo = Color(0xFFF5F8FB)
    val azulOscuro = Color(0xFF15395B)

    var matricula by remember { mutableStateOf("") }
    var nombres by remember { mutableStateOf("") }
    var apellidoMaterno by remember { mutableStateOf("") }
    var apellidoPaterno by remember { mutableStateOf("") }
    var dd by remember { mutableStateOf("") }
    var mm by remember { mutableStateOf("") }
    var yyyy by remember { mutableStateOf("") }
    var correoTutor by remember { mutableStateOf("") }
    var grado by remember { mutableStateOf("") }

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
            text = "Registrar alumno",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = azulOscuro,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        CampoEtiquetadoLocal("Matricula:", matricula) { matricula = it }
        CampoEtiquetadoLocal("Nombre(s):", nombres) { nombres = it }
        CampoEtiquetadoLocal("Apellido Materno:", apellidoMaterno) { apellidoMaterno = it }
        CampoEtiquetadoLocal("Apellido Paterno:", apellidoPaterno) { apellidoPaterno = it }

        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = "Fecha de Nacimiento:",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A202C)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            CampoFechaCorto("DD", dd, Modifier.weight(1f)) { dd = it }
            CampoFechaCorto("MM", mm, Modifier.weight(1f)) { mm = it }
            CampoFechaCorto("YYYY", yyyy, Modifier.weight(1.3f)) { yyyy = it }
        }

        CampoEtiquetadoLocal("Correo del tutor:", correoTutor) { correoTutor = it }
        CampoEtiquetadoLocal("Grado: (Ej. 3A)", grado) { grado = it }

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                if (nombres.isNotBlank() || matricula.isNotBlank()) {
                    BaseDatosLocal.alumnos.add(
                        AlumnoLocal(
                            matricula = matricula.trim(),
                            nombres = nombres.trim(),
                            apellidoMaterno = apellidoMaterno.trim(),
                            apellidoPaterno = apellidoPaterno.trim(),
                            fechaNacimiento = "${dd.trim()}/${mm.trim()}/${yyyy.trim()}",
                            correoTutor = correoTutor.trim(),
                            grado = grado.trim()
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
private fun CampoFechaCorto(
    hint: String,
    valor: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = hint,
                color = Color(0xFF8A94A6),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        modifier = modifier
    )
}