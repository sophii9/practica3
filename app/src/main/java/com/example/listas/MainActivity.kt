package com.example.listas

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.listas.ui.theme.ListasTheme
import java.util.Date
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListasTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppContent(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AppContent(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "frmCalificaciones"
    ) {
        composable("Login") { LoginContent(navController, modifier) }
        composable("Menu") { MenuContent(navController, modifier) }
        composable("lstCalificaciones") { LstCalificacionesContent(navController, modifier) }
        composable("frmCalificaciones") { FrmCalificacionesContent(navController, modifier) }
        composable("lstLibros") { LstLibrosContent(navController, modifier) }
        composable("frmLibros") { FrmLibrosContent(navController, modifier) }
    }
}

@Composable
fun LoginContent(navController: NavHostController, modifier: Modifier) {
    val context = LocalContext.current

    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {

        Button(
            onClick = {
                val usuarioCorrecto = "Admin"
                val contrasenaCorrecta = "123"


                if (usuario == usuarioCorrecto && contrasena == contrasenaCorrecta) {

                    Toast.makeText(context, "¡Bienvenido, ${usuario}!", Toast.LENGTH_SHORT).show()
                    navController.navigate("Menu")
                } else {

                    Toast.makeText(context, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG)
                        .show()
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Blue
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Menú",
                style = TextStyle(textDecoration = TextDecoration.Underline),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Inicio de Sesión",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Usuario:")
        TextField(
            value = usuario,
            onValueChange = { usuario = it },
            placeholder = { Text("Ingresa tu usuario") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Contraseña:")
        TextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            placeholder = { Text("Ingresa tu contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {

                val usuarioCorrecto = "Admin"
                val contrasenaCorrecta = "123"


                if (usuario == usuarioCorrecto && contrasena == contrasenaCorrecta) {

                    Toast.makeText(context, "¡Bienvenido, ${usuario}!", Toast.LENGTH_SHORT).show()
                    navController.navigate("menu")
                } else {

                    Toast.makeText(context, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG)
                        .show()
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Iniciar sesión")
        }
    }
}

@Composable
fun MenuContent(navController: NavHostController, modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .padding(8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Button(
            onClick = {
                navController.navigate("Login")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Blue
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Login",
                style = TextStyle(textDecoration = TextDecoration.Underline),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Text(
            text = "Menú",
            fontSize = 20.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                navController.navigate("lstCalificaciones")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier
                .size(width = 220.dp, height = 40.dp) //le cambié el width (ancho) de 150 a 220
                .align(Alignment.CenterHorizontally)
        ) {

            Text("Lista Calificaciones")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("lstLibros")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier
                .size(width = 220.dp, height = 40.dp)
                .align(Alignment.CenterHorizontally)
        ) {

            Text("Lista Libros")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("frmLibros")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier
                .size(width = 220.dp, height = 40.dp)
                .align(Alignment.CenterHorizontally)
        ) {

            Text("Formulario Libros")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("frmCalificaciones")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier
                .size(width = 220.dp, height = 40.dp)
                .align(Alignment.CenterHorizontally)
        ) {

            Text("Formulario Calificaciones")
        }

        Spacer(modifier = Modifier.height(16.dp))

    }
}

@Composable
fun LstLibrosContent(navController: NavHostController, modifier: Modifier) {
    data class Libro(
        val idLibro: Int,
        val titulo: String,
        val autor: String,
        val fecha: Date,
        val resena: String
    )

    val formato: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    val libros = remember {
        mutableStateListOf(
            Libro(
                1,
                "Tan Poca Vida",
                "Hanya Yanagihara",
                formato.parse("10/03/2015")!!,
                resena = "Una historia intensa y conmovedora."
            ),
            Libro(
                2,
                "Orgullo y Prejuicio",
                "Jane Austen",
                formato.parse("28/01/1813")!!,
                resena = "Un clásico de la literatura romántica."
            ),
            Libro(
                3,
                "50 leyes del poder",
                "Robert Greene",
                formato.parse("01/09/1998")!!,
                resena = "Estrategias para entender el poder y la influencia."
            )
        )
    }


    // productos[index] = Producto(nombre, precio, existencias)
    // productos[2] = Producto("Florentinas Fresa", 20.0, 5)

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .horizontalScroll(scrollState)
            .padding(8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Button(
            onClick = {
                navController.navigate("Menu")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Blue
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Menú",
                style = TextStyle(textDecoration = TextDecoration.Underline),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                libros.add(
                    Libro(
                        4, "Deja de ser tú",
                        "Joe Dispenza", formato.parse("31/12/2016")!!,
                        resena = "Es una guía poderosa para sanar, romper patrones y crear una nueva versión de ti."
                    )
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Agregar Libro de Prueba", //lo que me aparece en recuadro gris en el blog no aparece
                style = TextStyle(textDecoration = TextDecoration.Underline),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text("Título", modifier = Modifier.width(150.dp), fontWeight = FontWeight.Bold)
            Text("Autor", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)
            Text("Fecha", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)
            Text("Reseña", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)
            Text("Eliminar", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)

        }
        Divider()
        libros.forEachIndexed { index, libro ->
            val bgColor = if (index % 2 == 0) Color(0xFFF5F5F5) else Color.White

            Row(
                modifier = Modifier
                    .background(bgColor)
            ) {
                Text(
                    text = libro.titulo,
                    modifier = Modifier.width(150.dp)
                )
                Text(
                    text = libro.autor,
                    modifier = Modifier.width(100.dp)
                )
                Text(
                    text = formato.format(libro.fecha),
                    modifier = Modifier.width(100.dp)
                )
                Text(
                    text = libro.resena,
                    modifier = Modifier.width(200.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = {
                    libros.removeAt(index)

                }) {
                    Text("Eliminar")
                }
            }
            Divider()
        }
    }
}

@Composable
fun FrmLibrosContent(navController: NavHostController, modifier: Modifier) {
    data class Libro(
        val idLibro: Int,
        val titulo: String,
        val autor: String,
        val fecha: Date,
        val resena: String
    )

    val formato: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    val libros = remember {
        mutableStateListOf(
            Libro(
                1,
                "Tan Poca Vida",
                "Hanya Yanagihara",
                formato.parse("10/03/2015")!!,
                resena = "Una historia intensa y conmovedora."
            ),
            Libro(
                2,
                "Orgullo y Prejuicio",
                "Jane Austen",
                formato.parse("28/01/1813")!!,
                resena = "Un clásico de la literatura romántica."
            ),
            Libro(
                3,
                "50 leyes del poder",
                "Robert Greene",
                formato.parse("01/09/1998")!!,
                resena = "Estrategias para entender el poder y la influencia."
            )
        )
    }
    // productos[index] = Producto(nombre, precio, existencias)
    // productos[2] = Producto("Florentinas Fresa", 20.0, 5)

    val context = LocalContext.current

    val scrollState = rememberScrollState()

    var titulo by remember { mutableStateOf("") } //datos línea Usuario
    var autor by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var resena by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .horizontalScroll(scrollState)
            .padding(8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {

        val scrollState = rememberScrollState()

        Button(
            onClick = {
                navController.navigate("lstLibros")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Blue
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Tabla de Libros",
                style = TextStyle(textDecoration = TextDecoration.Underline),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))


        Spacer(modifier = Modifier.height(16.dp))


        Text(text = "Título:")
        TextField(
            value = titulo,
            onValueChange = { titulo = it },
            placeholder = { Text("Ingresa el Título:") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Autor")
        TextField(
            value = autor,
            onValueChange = { autor = it },
            placeholder = { Text("Ingresa el Autor:") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Fecha:")
        TextField(
            value = fecha,
            onValueChange = { fecha = it },
            placeholder = { Text("Ingresa la fecha en 8 dígitos:") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Reseña:")
        TextField(
            value = resena,
            onValueChange = { resena = it },
            placeholder = { Text("Ingresa una reseña:") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        )

        {
            Button(
                onClick = {
                    Toast.makeText(context, "Titulo: ${titulo}", Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, "Autor: ${autor}", Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, "Fecha: ${fecha}", Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, "Reseña: ${resena}", Toast.LENGTH_SHORT).show()
                },

                ) {
                Text("Enviar")
            }
        }
    }
}


@Composable
fun LstCalificacionesContent(navController: NavHostController, modifier: Modifier) {
    data class Calificacion(
        val idCalificacion: Int,
        val libro: String,
        val calificacion: Int,
        val resena: String
    )

    val calificaciones = remember {
        mutableStateListOf(
            Calificacion(
                1,
                "Prueba 1",
                5,
                "Una historia cruda pero bellamente narrada.",
            ),
            Calificacion(
                2,
                "Prueba 2",
                5,
                "Muy largo pero con personajes inolvidables."
            ),
            Calificacion(
                3,
                "Prueba 3",
                5,
                "Jane Austen siempre logra un equilibrio perfecto entre humor y crítica social."
            ),
        )
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .horizontalScroll(scrollState)
            .padding(8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Button(
            onClick = {
                navController.navigate("menu")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Blue
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Menú",
                style = TextStyle(textDecoration = TextDecoration.Underline),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                calificaciones.add(
                    Calificacion(
                        4, "Prueba 4",
                        5, "Excelente libro sobre estrategia y comportamiento humano."
                    )
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Agregar Producto Prueba", //lo que me aparece en recuadro gris en el blog no aparece
                style = TextStyle(textDecoration = TextDecoration.Underline),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Text("ID Calificación", modifier = Modifier.width(120.dp), fontWeight = FontWeight.Bold)
            Text("ID Libro", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)
            Text("Calificación", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)
            Text("Reseña", modifier = Modifier.width(200.dp), fontWeight = FontWeight.Bold)
            Text("Eliminar", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)

        }
        Divider()
        calificaciones.forEachIndexed { index, calificacion ->
            val bgColor = if (index % 2 == 0) Color(0xFFF5F5F5) else Color.White

            Row(
                modifier = Modifier
                    .background(bgColor)
            ) {
                Text(
                    text = calificacion.idCalificacion.toString(),
                    modifier = Modifier.width(150.dp)
                )
                Text(
                    text = calificacion.libro,
                    modifier = Modifier.width(100.dp)
                )
                Text(
                    text = calificacion.calificacion.toString(),
                    modifier = Modifier.width(200.dp)
                )
                Text(
                    text = calificacion.resena,
                    modifier = Modifier.width(200.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Button(onClick = {
                    calificaciones.removeAt(index)
                    //Remueve en base al index del producto en la lista
                }) {
                    Text("Eliminar")
                }
            }
            Divider()
        }
    }
}

@Composable
fun FrmCalificacionesContent(navController: NavHostController, modifier: Modifier) {
    data class Calificacion(
        val idCalificacion: Int,
        val libro: String,
        val calificacion: Int,
        val resena: String
    )

    val calificaciones = remember {
        mutableStateListOf(
            Calificacion(1, "El principito", 10, "INCREÍBLE"),
            Calificacion(2, "Hábitos Atómicos", 10, "Espectacular"),
            Calificacion(3, "Strange", 8, "Extraño")
        )
    }

    val scrollState = rememberScrollState()

    var libro by remember { mutableStateOf("") } //datos línea Usuario
    var expanded by remember { mutableStateOf(false) }
    var calificacion by remember { mutableStateOf("") }
    var resena by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
            .horizontalScroll(scrollState)
            .padding(8.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {

        val scrollState = rememberScrollState()

        Button(
            onClick = {
                navController.navigate("lstCalificaciones")
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Blue
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Tabla de Calificaciones",
                style = TextStyle(textDecoration = TextDecoration.Underline),
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))


        Text(text = "Libro:")
        Spacer(modifier = Modifier.height(8.dp))

        var libro by remember { mutableStateOf("") }
        var expanded by remember { mutableStateOf(false) }

        val opcionesLibros = listOf("Tan Poca Vida", "Orgullo y Prejuicio", "50 leyes del poder")

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = libro,
                onValueChange = { },
                placeholder = { Text("Selecciona un libro") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded }, // Cambia el estado al hacer clic
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "Expandir menú"
                        )
                    }
                },
                enabled = false
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                opcionesLibros.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            libro = opcion
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))

        /*Text(text = "Calificación:")
        TextField(
            value = calificacion,
            onValueChange = { calificacion = it },
            placeholder = { Text("Ingresa una calificación:") },
            modifier = Modifier.fillMaxWidth()
        )*/

        Text(text = "Libro:")
        Spacer(modifier = Modifier.height(8.dp))

        var calif by remember { mutableStateOf("") }
        var expand by remember { mutableStateOf(false) }

        val opcionesCalificacion = listOf("1", "2", "3")

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = calif,
                onValueChange = { },
                placeholder = { Text("Selecciona una calificación") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expand = !expand }, // Cambia el estado al hacer clic
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { expand = !expand }) {
                        Icon(
                            imageVector = if (expand) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = "Expandir Calificación"
                        )
                    }
                },
                enabled = false
            )

            DropdownMenu(
                expanded = expand,
                onDismissRequest = { expand = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                opcionesCalificacion.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            libro = opcion
                            expand = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Reseña:")
        TextField(
            value = resena,
            onValueChange = { resena = it },
            placeholder = { Text("Ingresa la reseña:") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        )

        {
            Button(
                onClick = {
                    Toast.makeText(context, "Libro: ${libro}", Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, "Calificación: ${calificacion}", Toast.LENGTH_SHORT)
                        .show()
                    Toast.makeText(context, "Reseña: ${resena}", Toast.LENGTH_SHORT).show()
                },

                ) {
                Text("Enviar")
            }

        }

    }
}



@Preview(showBackground = true)
@Composable
fun AppContentPreview() {
    ListasTheme {
        AppContent()
    }
}