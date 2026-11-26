package com.example.seguimientoacademico.profesores

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.seguimientoacademico.GrupoLocal
import com.example.seguimientoacademico.ProfesorLocal
import com.example.seguimientoacademico.UsuarioLocal

private val FondoPantalla = Color(0xFFF0F4F8)
private val AzulMarino = Color(0xFF284361)
private val AzulGrupoBg = Color(0xFFE3EEF8)
private val AmarilloCremaBg = Color(0xFFFEF7DC)
private val AmarilloIcono = Color(0xFFE5B526)

private val GrisChip = Color(0xFFEBF0F5)
private val TextoOscuro = Color(0xFF1E293B)
private val TextoSecundario = Color(0xFF64748B)

data class GrupoProfesor(
    val id: String,
    val nombre: String,
    val materia: String
)

enum class RutaProfesor {
    INICIO,
    ASISTENCIA,
    CALIFICACIONES,
    RECADO_GRUPAL,
    REPORTE_ACTIVIDAD,
    ANUNCIOS,
    MENSAJES_PADRES,
    JUNTAS_TUTORES,
    CITAS_PENDIENTES
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaProfesorInicio(
    usuario: UsuarioLocal? = null,
    profesores: List<ProfesorLocal> = emptyList(),
    grupos: List<GrupoLocal> = emptyList(),
    onNavegar: (RutaProfesor) -> Unit = {},
    onGrupoClick: (GrupoProfesor) -> Unit = {},
    onAbrirMenu: () -> Unit = {},
    onCerrarSesion: () -> Unit = {}
) {
    val nombreMostrado = usuario?.nombre?.takeIf { it.isNotBlank() } ?: "Laura Ramírez"

    val profesorEncontrado = remember(nombreMostrado, profesores) {
        profesores.find { prof ->
            val nombreCompleto = "${prof.nombres} ${prof.apellidoPaterno} ${prof.apellidoMaterno}".trim()
            val nombreCorto = "${prof.nombres} ${prof.apellidoPaterno}".trim()
            nombreMostrado.equals(nombreCompleto, ignoreCase = true) ||
                    nombreMostrado.equals(nombreCorto, ignoreCase = true) ||
                    nombreMostrado.equals(prof.nombres, ignoreCase = true)
        }
    }

    val especialidad = profesorEncontrado?.materias?.substringBefore(",")?.trim()
        ?: "Matemáticas"
    val idUsuario = usuario?.usuario?.uppercase() ?: "MAESTRO1"
    val numEmpleado = profesorEncontrado?.numeroEmpleado ?: "EMP-101"
    val institucion = "INST001"

    val gruposAsignados = remember(nombreMostrado, especialidad, grupos) {
        val filtrados = grupos.filter { g ->
            g.profesores.any { p ->
                p.contains(nombreMostrado, ignoreCase = true) ||
                        nombreMostrado.contains(p.substringBefore(" "), ignoreCase = true)
            }
        }
        val base = filtrados.ifEmpty { grupos }
        base.mapIndexed { index, grupoLocal ->
            GrupoProfesor(
                id = (index + 1).toString(),
                nombre = "Grupo ${grupoLocal.grupo}",
                materia = especialidad
            )
        }
    }

    var menuPerfilExpandido by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = FondoPantalla,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                navigationIcon = {
                    IconButton(onClick = onAbrirMenu) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Abrir menú",
                            tint = TextoOscuro
                        )
                    }
                },
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Tablero del Profesor",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextoOscuro
                        )
                    }
                },
                actions = {
                    Box {
                        Box(
                            modifier = Modifier
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(AzulGrupoBg)
                                .clickable { menuPerfilExpandido = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Perfil",
                                tint = AzulMarino,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        DropdownMenu(
                            expanded = menuPerfilExpandido,
                            onDismissRequest = { menuPerfilExpandido = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Cerrar sesión") },
                                onClick = {
                                    menuPerfilExpandido = false
                                    onCerrarSesion()
                                }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clickable { onNavegar(RutaProfesor.ANUNCIOS) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notificaciones",
                            tint = AzulMarino,
                            modifier = Modifier.size(24.dp)
                        )
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .align(Alignment.TopEnd)
                                .offset(x = (-6).dp, y = 6.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE11D48))
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TarjetaDetallesProfesor(
                nombreProfesor = nombreMostrado,
                especialidad = especialidad,
                idUsuario = idUsuario,
                numEmpleado = numEmpleado,
                institucion = institucion,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TarjetaGrupos(
                    modifier = Modifier.weight(1f),
                    grupos = gruposAsignados,
                    onGrupoClick = onGrupoClick
                )
                TarjetaAccionesRapidas(
                    modifier = Modifier.weight(1f),
                    onAsistenciaClick = { onNavegar(RutaProfesor.ASISTENCIA) },
                    onCalificacionesClick = { onNavegar(RutaProfesor.CALIFICACIONES) },
                    onRecadoGrupalClick = { onNavegar(RutaProfesor.RECADO_GRUPAL) },
                    onReporteActividadClick = { onNavegar(RutaProfesor.REPORTE_ACTIVIDAD) }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TarjetaNotificaciones(
                    modifier = Modifier.weight(1f),
                    onAnunciosClick = { onNavegar(RutaProfesor.ANUNCIOS) },
                    onMensajesClick = { onNavegar(RutaProfesor.MENSAJES_PADRES) }
                )
                TarjetaReuniones(
                    modifier = Modifier.weight(1f),
                    onJuntasClick = { onNavegar(RutaProfesor.JUNTAS_TUTORES) },
                    onCitasClick = { onNavegar(RutaProfesor.CITAS_PENDIENTES) }
                )
            }
        }
    }
}

@Composable
private fun TarjetaDetallesProfesor(
    nombreProfesor: String,
    especialidad: String,
    idUsuario: String,
    numEmpleado: String,
    institucion: String,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF8FAFC))
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Detalles de Profesor",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextoOscuro
                )
            }
            HorizontalDivider(color = Color(0xFFE2E8F0), thickness = 0.8.dp)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Profesor: $nombreProfesor",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextoOscuro
                    )
                    Text(
                        text = "Especialidad: $especialidad",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextoOscuro
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        ChipDato(etiqueta = "ID Usuario:", valor = idUsuario)
                        ChipDato(etiqueta = "N° Empleado:", valor = numEmpleado)
                    }
                }

                Box(
                    modifier = Modifier
                        .height(74.dp)
                        .width(1.dp)
                        .background(Color(0xFFE2E8F0))
                )

                Column(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .width(96.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Institución: $institucion",
                        fontSize = 10.sp,
                        color = TextoSecundario,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
private fun ChipDato(etiqueta: String, valor: String) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(GrisChip)
            .padding(horizontal = 6.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "$etiqueta ", fontSize = 10.sp, color = TextoSecundario)
        Text(text = valor, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextoOscuro)
    }
}

@Composable
private fun TarjetaGrupos(
    modifier: Modifier = Modifier,
    grupos: List<GrupoProfesor>,
    onGrupoClick: (GrupoProfesor) -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.AccountBalance,
                    contentDescription = null,
                    tint = AzulMarino,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Grupos",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoOscuro
                )
            }

            grupos.forEach { grupo ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(AzulGrupoBg)
                        .clickable { onGrupoClick(grupo) }
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = grupo.nombre,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextoOscuro
                        )
                        Text(
                            text = "(${grupo.materia})",
                            fontSize = 11.sp,
                            color = TextoSecundario
                        )
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Ver grupo",
                        tint = AzulMarino,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun TarjetaAccionesRapidas(
    modifier: Modifier = Modifier,
    onAsistenciaClick: () -> Unit,
    onCalificacionesClick: () -> Unit,
    onRecadoGrupalClick: () -> Unit,
    onReporteActividadClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .clip(CircleShape)
                        .background(AzulMarino),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Acciones Rápidas",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoOscuro
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BotonAccionCircular(
                    icono = Icons.Default.CheckCircle,
                    texto = "Asistencia",
                    onClick = onAsistenciaClick
                )
                BotonAccionCircular(
                    icono = Icons.Default.EditNote,
                    texto = "Registrar\nCalificaciones",
                    onClick = onCalificacionesClick
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BotonAccionCircular(
                    icono = Icons.Default.Email,
                    texto = "Recado\nGrupal",
                    onClick = onRecadoGrupalClick
                )
                BotonAccionCircular(
                    icono = Icons.Default.Assessment,
                    texto = "Reporte\nActividad",
                    onClick = onReporteActividadClick
                )
            }
        }
    }
}

@Composable
private fun BotonAccionCircular(
    icono: ImageVector,
    texto: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(72.dp)
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(AzulMarino),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icono,
                contentDescription = texto,
                tint = Color.White,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = texto,
            fontSize = 11.sp,
            lineHeight = 13.sp,
            textAlign = TextAlign.Center,
            color = TextoOscuro,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun TarjetaNotificaciones(
    modifier: Modifier = Modifier,
    onAnunciosClick: () -> Unit,
    onMensajesClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Campaign,
                    contentDescription = null,
                    tint = AzulMarino,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Notificaciones y\nComunicación",
                    fontSize = 13.sp,
                    lineHeight = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoOscuro
                )
            }

            ItemCrema(
                icono = Icons.Default.Notifications,
                texto = "Anuncios Generales",
                onClick = onAnunciosClick
            )
            ItemCrema(
                icono = Icons.Default.ChatBubble,
                texto = "Nuevos Mensajes (Padres)",
                onClick = onMensajesClick
            )
        }
    }
}

@Composable
private fun TarjetaReuniones(
    modifier: Modifier = Modifier,
    onJuntasClick: () -> Unit,
    onCitasClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Event,
                    contentDescription = null,
                    tint = AzulMarino,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Reuniones",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoOscuro
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            ItemCrema(
                icono = Icons.Default.CalendarMonth,
                texto = "Juntas de Tutores",
                onClick = onJuntasClick
            )
            ItemCrema(
                icono = Icons.Default.DateRange,
                texto = "Citas Pendientes",
                onClick = onCitasClick
            )
        }
    }
}

@Composable
private fun ItemCrema(
    icono: ImageVector,
    texto: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(AmarilloCremaBg)
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = AmarilloIcono,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = texto,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = TextoOscuro
        )
    }
}