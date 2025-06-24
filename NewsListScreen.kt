package com.example.newsapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.background
import androidx.compose.foundation.border

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp



import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons

import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*

import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import kotlinx.coroutines.launch

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun NewsListScreen(viewModel: NewsViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var isEditing by remember { mutableStateOf(false) }
    var idEdit by remember { mutableStateOf(0) }
    var tituloEdit by remember { mutableStateOf("") }
    var contenidoEdit by remember { mutableStateOf("") }

    val newsList by viewModel.newsList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    rememberCoroutineScope()

    val ColorSaver = Saver<Color, Long>(
        save = { it.value.toLong() },
        restore = { Color(it.toULong()) }
    )

    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = remember { AppColorStore(context) }

    var topBarColor by remember { mutableStateOf(Color(0xFF2196F3)) }
    var backgroundColor by remember { mutableStateOf(Color.White) }
    var cardColor by remember { mutableStateOf(Color(0xFFF1F1F1)) }
    var textColor by remember { mutableStateOf(Color.Black) }

    LaunchedEffect(Unit) {
        dataStore.topBarColor.collect { topBarColor = it }
        dataStore.backgroundColor.collect { backgroundColor = it }
        dataStore.cardColor.collect { cardColor = it }
        dataStore.textColor.collect { textColor = it }
    }





    var showColorDialog by remember { mutableStateOf(false) }
    var showHelpDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var showSearch by remember { mutableStateOf(false) }

    val filteredNews = if (searchQuery.isBlank()) {
        newsList
    } else {
        newsList.filter {
            it.titulo.contains(searchQuery, ignoreCase = true) ||
                    it.contenido.contains(searchQuery, ignoreCase = true)
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(240.dp)
            ) {
                Text("Opciones", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.titleMedium)

                NavigationDrawerItem(
                    label = { Text("Buscar") },
                    selected = false,
                    onClick = {
                        showSearch = !showSearch
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Cambiar colores") },
                    selected = false,
                    onClick = {
                        showColorDialog = true
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Ayuda") },
                    selected = false,
                    onClick = {
                        showHelpDialog = true
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                NewsTopBar(
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onAddClick = {
                        isEditing = false
                        tituloEdit = ""
                        contenidoEdit = ""
                        showDialog = true
                    },
                    onSyncClick = { viewModel.fetchNews() },
                    backgroundColor = topBarColor,
                    textColor = textColor
                )
            },
            containerColor = backgroundColor
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                if (showSearch) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = {
                            Text("Buscar noticias...", color = textColor.copy(alpha = 0.5f))
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = textColor,
                            unfocusedBorderColor = textColor.copy(alpha = 0.5f),
                            cursorColor = textColor,
                            focusedTextColor = textColor,
                            unfocusedTextColor = textColor,
                            focusedLabelColor = textColor,
                            unfocusedLabelColor = textColor.copy(alpha = 0.5f),
                            focusedPlaceholderColor = textColor.copy(alpha = 0.5f),
                            unfocusedPlaceholderColor = textColor.copy(alpha = 0.4f)
                        )
                    )

                }

                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(filteredNews) { news ->
                        NewsItem(
                            news = news,
                            onEdit = {
                                idEdit = it.id
                                tituloEdit = it.titulo
                                contenidoEdit = it.contenido
                                isEditing = true
                                showDialog = true
                            },
                            onDelete = { viewModel.deleteNews(it) },
                            cardColor = cardColor,
                            textColor = textColor
                        )
                    }
                }
            }

            if (showDialog) {
                AddNewsDialog(
                    initialTitulo = tituloEdit,
                    initialContenido = contenidoEdit,
                    onDismiss = {
                        showDialog = false
                        isEditing = false
                    },
                    onAdd = { titulo, contenido, fecha ->
                        if (isEditing) {
                            viewModel.updateNews(News(idEdit, titulo, contenido, fecha))
                        } else {
                            viewModel.addNews(titulo, contenido, fecha)
                        }
                        showDialog = false
                        isEditing = false
                    }
                )
            }

            if (showColorDialog) {
                ColorPickerDialog(
                    initialTopBar = topBarColor,
                    initialBackground = backgroundColor,
                    initialCard = cardColor,
                    initialText = textColor,
                    onDismiss = { showColorDialog = false },
                    onColorsSelected = { top, bg, card, text ->
                        topBarColor = top
                        backgroundColor = bg
                        cardColor = card
                        textColor = text
                    }
                )
            }

            if (showHelpDialog) {
                HelpDialog(onDismiss = { showHelpDialog = false })
            }

            if (isLoading) {
                AlertDialog(
                    onDismissRequest = {},
                    title = { Text("Sincronizando...") },
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                            Spacer(modifier = Modifier.width(16.dp))
                            Text("Por favor espera")
                        }
                    },
                    confirmButton = {},
                    dismissButton = {},
                    shape = RoundedCornerShape(12.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopBar(
    onMenuClick: () -> Unit,
    onAddClick: () -> Unit,
    onSyncClick: () -> Unit,
    backgroundColor: Color,
    textColor: Color
) {
    TopAppBar(
        title = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(
                    text = "NEWSAPP",
                    color = Color.Black,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        navigationIcon = {
            Button(
                onClick = onMenuClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Icon(Icons.Default.Menu, contentDescription = "Menú")
            }
        },
        actions = {
            Button(
                onClick = onAddClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }

            Button(
                onClick = onSyncClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Icon(Icons.Default.Sync, contentDescription = "Sincronizar")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor),
        modifier = Modifier.border(width = 1.dp, color = Color.Gray)
    )

}

@Composable
fun NewsItem(
    news: News,
    onEdit: (News) -> Unit,
    onDelete: (Int) -> Unit,
    cardColor: Color,
    textColor: Color
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp)),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = news.titulo, color = textColor, style = MaterialTheme.typography.titleLarge)
            Text(text = news.contenido, color = textColor, style = MaterialTheme.typography.titleMedium)
            Text(text = news.fecha, color = textColor.copy(alpha = 0.8f), style = MaterialTheme.typography.labelSmall)

            Row(
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = { onEdit(news) },
                    modifier = Modifier.padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black)
                ) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar")
                }
                Button(
                    onClick = { showDeleteDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red, contentColor = Color.Black)
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Eliminar Noticia") },
            text = { Text("¿Seguro que deseas eliminar esta noticia?") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete(news.id)
                    showDeleteDialog = false
                }) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun ColorPickerDialog(
    initialTopBar: Color,
    initialBackground: Color,
    initialCard: Color,
    initialText: Color,
    onDismiss: () -> Unit,
    onColorsSelected: (topBar: Color, background: Color, card: Color, text: Color) -> Unit
) {
    val colorOptions = listOf(
        Color.White, Color.Black, Color.Gray,
        Color.Red, Color.Green, Color.Blue,
        Color.Yellow, Color.Cyan, Color.Magenta,
        Color(0xFFFF9800), // Naranja
        Color(0xFF4CAF50), // Verde oscuro
        Color(0xFF2196F3), // Azul claro
        Color(0xFFE91E63), // Rosa fuerte
        Color(0xFF9C27B0), // Morado
        Color(0xFF795548), // Marrón
        Color(0xFF00BCD4), // Turquesa
        Color(0xFFFFEB3B), // Amarillo claro
        Color(0xFF3F51B5)  // Azul oscuro
    )

    val context = LocalContext.current
    val dataStore = remember { AppColorStore(context) }
    val scope = rememberCoroutineScope()

    var topBarColor by remember { mutableStateOf(initialTopBar) }
    var backgroundColor by remember { mutableStateOf(initialBackground) }
    var cardColor by remember { mutableStateOf(initialCard) }
    var textColor by remember { mutableStateOf(initialText) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Selecciona los colores") },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

                @Composable
                fun ColorRow(
                    title: String,
                    selected: Color,
                    onSelect: (Color) -> Unit
                ) {
                    Text(title)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        colorOptions.forEach { color ->
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .border(
                                        width = if (color == selected) 3.dp else 1.dp,
                                        color = if (color == selected) Color.Black else Color.Gray,
                                        shape = CircleShape
                                    )
                                    .clickable { onSelect(color) }
                            )
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                }

                ColorRow("Color TopBar:", topBarColor) { topBarColor = it }
                ColorRow("Color Fondo:", backgroundColor) { backgroundColor = it }
                ColorRow("Color Tarjetas:", cardColor) { cardColor = it }
                ColorRow("Color Texto:", textColor) { textColor = it }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onColorsSelected(topBarColor, backgroundColor, cardColor, textColor)
                scope.launch {
                    dataStore.saveColors(topBarColor, backgroundColor, cardColor, textColor)
                }
                onDismiss()
            }) {
                Text("Aplicar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },

    )
}

@Composable
fun ColorSelectionRow(
    colors: List<Color>,
    selectedColor: Color,
    onColorSelected: (Color) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        colors.forEach { color ->
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(RoundedCornerShape(50))
                    .background(color)
                    .border(
                        width = if (color == selectedColor) 3.dp else 1.dp,
                        color = if (color == selectedColor) Color.Black else Color.Gray,
                        shape = RoundedCornerShape(50)
                    )
                    .clickable { onColorSelected(color) }
            )
        }
    }
}

@Composable
fun HelpDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ayuda") },
        text = {
            Column {
                Text("Cómo usar la app:")
                Spacer(Modifier.height(8.dp))
                Text("• ➕ Agrega una nueva noticia.")
                Text("• 🔄 Sincroniza noticias con la base de datos.")
                Text("• 🎨 Cambia los colores de la interfaz.")
                Text("• ❓ Muestra esta ayuda.")
                Text("✏️ Edita una noticia.")
                Text("• 🗑️ Elimina una noticia.")
                Text("• 🔍 Usa la barra para buscar por título o contenido.")
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}
