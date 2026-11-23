package com.example.seguimientoacademico

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun ContenedorConMenuHamburguesa(
    usuario: UsuarioLocal?,
    onCerrarSesion: () -> Unit,
    contenidoPrincipal: @Composable (abrirMenu: () -> Unit) -> Unit
) {
    val drawerState = androidx.compose.material3.rememberDrawerState(
        initialValue = androidx.compose.material3.DrawerValue.Closed
    )
    val scope = rememberCoroutineScope()
    val azulOscuro = Color(0xFF15395B)

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(290.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        tint = azulOscuro,
                        modifier = Modifier.size(56.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = usuario?.nombre ?: "Usuario",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1A202C)
                    )
                    Text(
                        text = usuario?.puesto ?: "Sin puesto",
                        fontSize = 14.sp,
                        color = Color(0xFF64748B)
                    )
                    Text(
                        text = "Usuario: ${usuario?.usuario ?: ""}",
                        fontSize = 13.sp,
                        color = Color(0xFF64748B),
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Spacer(modifier = Modifier.height(18.dp))
                    HorizontalDivider(color = Color(0xFFE2E8F0))
                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            scope.launch { drawerState.close() }
                            onCerrarSesion()
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = azulOscuro),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Cerrar sesión",
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Cerrar sesión",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ) {
        contenidoPrincipal {
            scope.launch { drawerState.open() }
        }
    }
}

@Composable
internal fun BarraSuperiorPantalla(
    tituloSeccion: String,
    esCuestionario: Boolean = false,
    onAbrirMenu: () -> Unit,
    onRegresarConfirmado: () -> Unit
) {
    var mostrarDialogoCancelar by remember { mutableStateOf(false) }
    val azulOscuro = Color(0xFF15395B)

    if (mostrarDialogoCancelar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoCancelar = false },
            title = {
                Text(
                    text = "Cancelar operación",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A202C)
                )
            },
            text = {
                Text(
                    text = "¿Deseas cancelar la operación? Se perderán los datos ingresados.",
                    color = Color(0xFF475569)
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarDialogoCancelar = false
                        onRegresarConfirmado()
                    }
                ) {
                    Text(
                        text = "Sí, cancelar",
                        color = azulOscuro,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoCancelar = false }) {
                    Text(
                        text = "No, continuar",
                        color = Color(0xFF64748B)
                    )
                }
            }
        )
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(top = 8.dp)
    ) {
        IconButton(onClick = onAbrirMenu) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menú",
                tint = azulOscuro
            )
        }
        Text(
            text = tituloSeccion,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A202C),
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
        )
        TextButton(
            onClick = {
                if (esCuestionario) {
                    mostrarDialogoCancelar = true
                } else {
                    onRegresarConfirmado()
                }
            }
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Regresar",
                tint = azulOscuro,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Regresar",
                color = azulOscuro,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}