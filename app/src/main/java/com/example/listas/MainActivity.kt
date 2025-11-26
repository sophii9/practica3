package com.example.listas

import android.os.Bundle
import android.util.Log
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
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
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.Date
import java.text.SimpleDateFormat
import java.util.*
import java.util.Locale
import java.util.concurrent.TimeUnit

data class ModeloRegistro(
    val dato1: String,
    val dato2: Double,
    val dato3: Int
)

data class CalificacionResponse(
    val id: Int,
    val id_libro: Int,
    val libro: String,
    val calificacion: Int,
    val resena: String
)

data class Libro(
    val id: Int,
    val titulo: String,
    val autor: String,
    val fecha: String,
    val resena: String
)

data class LibroSimple(
    val id: Int,
    val titulo: String
)

interface ApiService {
    @POST("login.php")
    @FormUrlEncoded
    suspend fun iniciarSesion(
        @Field("iniciarSesion") iniciarSesion: String = "true",
        @Field("usuario") usuario: String,
        @Field("contrasena") contrasena: String,
    ): Response<String>

    @GET("servicio.php?registros")
    suspend fun registros(): List<ModeloRegistro>

    @POST("servicio.php")
    @FormUrlEncoded
    suspend fun agregarRegistro(
        @Field("titulo") titulo: String,
        @Field("autor") autor: String,
        @Field("fecha") fecha: String, //lo convertiré al llamar la función
        @Field("resena") resena: String
    ): Response<String>

    @POST("servicio.php")
    @FormUrlEncoded
    suspend fun modificarRegistro(
        @Field("id") id: Int,
        @Field("titulo") titulo: String,
        @Field("autor") autor: String,
        @Field("fecha") fecha: String, //lo convertiré al llamar la función
        @Field("resena") resena: String
    ): Response<String>

    @POST("servicio.php")
    @FormUrlEncoded
    suspend fun eliminarRegistro(
        @Field("id") id: Int,
    ): Response<String>

    @GET("servicio.php?obtenerLibros")
    suspend fun obtenerLibros(): List<Libro>

    @GET("servicio.php?obtenerLibro")
    suspend fun obtenerLibro(@Query("id") id: Int): Libro

    @POST("servicio.php?agregarLibro")
    @FormUrlEncoded
    suspend fun agregarLibro(
        @Field("titulo") titulo: String,
        @Field("autor") autor: String,
        @Field("fecha") fecha: String,
        @Field("resena") resena: String
    ): Response<String>

    @POST("servicio.php?modificarLibro")
    @FormUrlEncoded
    suspend fun modificarLibro(
        @Field("id") id: Int,
        @Field("titulo") titulo: String,
        @Field("autor") autor: String,
        @Field("fecha") fecha: String,
        @Field("resena") resena: String
    ): Response<String>

    @POST("servicio.php?eliminarLibro")
    @FormUrlEncoded
    suspend fun eliminarLibro(
        @Field("id") id: Int
    ): Response<String>

    @GET("servicio.php?obtenerCalificaciones")
    suspend fun obtenerCalificaciones(): List<CalificacionResponse>

    @GET("servicio.php?obtenerCalificacion")
    suspend fun obtenerCalificacion(@Query("id") id: Int): CalificacionResponse

    @POST("servicio.php?agregarCalificacion")
    @FormUrlEncoded
    suspend fun agregarCalificacion(
        @Field("id_libro") idLibro: Int,
        @Field("calificacion") calificacion: Int,
        @Field("resena") resena: String
    ): Response<String>

    @POST("servicio.php?modificarCalificacion")
    @FormUrlEncoded
    suspend fun modificarCalificacion(
        @Field("id") id: Int,
        @Field("id_libro") idLibro: Int,
        @Field("calificacion") calificacion: Int,
        @Field("resena") resena: String
    ): Response<String>

    @POST("servicio.php?eliminarCalificacion")
    @FormUrlEncoded
    suspend fun eliminarCalificacion(
        @Field("id") id: Int
    ): Response<String>

}

val retrofit = Retrofit.Builder()
    .baseUrl("http://10.0.2.2/")
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(GsonConverterFactory.create())
    .client(createOkHttpClient()) // Agregar configuración de timeout
    .build()

fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val request = chain.request()
            Log.d("API_REQUEST", "URL: ${request.url}")
            Log.d("API_REQUEST", "Method: ${request.method}")
            val response = chain.proceed(request)
            Log.d("API_RESPONSE", "Code: ${response.code}")
            response
        }
        .build()
}

val api = retrofit.create(ApiService::class.java)

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
        startDestination = "Login"
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

    val scope = rememberCoroutineScope() //envíos de petición

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
                scope.launch {
                    try {
                        Log.d("API", "Iniciando petición...")
                        Log.d("API", "Usuario: $usuario")

                        val respuesta: Response<String> = api.iniciarSesion(
                            usuario = usuario,
                            contrasena = contrasena
                        )

                        Log.d("API", "Código respuesta: ${respuesta.code()}")
                        Log.d("API", "Respuesta completa: ${respuesta.body()}")
                        Log.d("API", "Error body: ${respuesta.errorBody()?.string()}")

                        val cuerpo = respuesta.body()?.trim()

                        if (cuerpo == "correcto") {
                            Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                            navController.navigate("Menu")
                        } else {
                            Toast.makeText(context, "Error: $cuerpo", Toast.LENGTH_LONG).show()
                        }
                    } catch (e: Exception) {
                        Log.e("API", "Error en la petición: ${e.message}", e)
                        Toast.makeText(context, "Error de conexión: ${e.message}", Toast.LENGTH_LONG).show()
                    }
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

                scope.launch {
                    try {
                        Log.d("API", "Iniciando petición...")
                        Log.d("API", "Usuario: $usuario")

                        val respuesta: Response<String> = api.iniciarSesion(
                            usuario = usuario,
                            contrasena = contrasena
                        )

                        Log.d("API", "Código respuesta: ${respuesta.code()}")
                        Log.d("API", "Respuesta completa: ${respuesta.body()}")
                        Log.d("API", "Error body: ${respuesta.errorBody()?.string()}")

                        val cuerpo = respuesta.body()?.trim()

                        if (cuerpo == "correcto") {
                            Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                            navController.navigate("Menu")
                        } else {
                            Toast.makeText(context, "Error: $cuerpo", Toast.LENGTH_LONG).show()
                        }

                    } catch (e: Exception) {
                        Log.e("API", "Error al agregar registro: ${e.message}")
                    }
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
fun LstLibrosContent(navController: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    var libros by remember { mutableStateOf<List<Libro>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                libros = api.obtenerLibros()
            } catch (e: Exception) {
                Log.e("API", "Error al obtener libros: ${e.message}")
                Toast.makeText(context, "Error al cargar libros", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    Column(modifier = modifier.fillMaxSize().padding(24.dp).verticalScroll(scrollState).padding(8.dp), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top) {
        Button(onClick = { navController.navigate("Menu") }, colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.Blue), modifier = Modifier.fillMaxWidth()) {
            Text("Menú", style = TextStyle(textDecoration = TextDecoration.Underline), textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Lista de Libros", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("frmLibros/0") }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50), contentColor = Color.White), modifier = Modifier.align(Alignment.End)) {
            Text("+ Agregar Libro")
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            val horizontalScrollState = rememberScrollState()
            Column(modifier = Modifier.horizontalScroll(horizontalScrollState)) {
                Row {
                    Text("Título", modifier = Modifier.width(150.dp), fontWeight = FontWeight.Bold)
                    Text("Autor", modifier = Modifier.width(150.dp), fontWeight = FontWeight.Bold)
                    Text("Fecha", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)
                    Text("Reseña", modifier = Modifier.width(200.dp), fontWeight = FontWeight.Bold)
                    Text("Acciones", modifier = Modifier.width(200.dp), fontWeight = FontWeight.Bold)
                }
                Divider()
                libros.forEachIndexed { index, libro ->
                    val bgColor = if (index % 2 == 0) Color(0xFFF6F7F8) else Color.White
                    Row(modifier = Modifier.background(bgColor).padding(vertical = 4.dp)) {
                        Text(text = libro.titulo, modifier = Modifier.width(150.dp))
                        Text(text = libro.autor, modifier = Modifier.width(150.dp))
                        Text(text = libro.fecha, modifier = Modifier.width(100.dp))
                        Text(text = libro.resena, modifier = Modifier.width(200.dp))
                        Row(modifier = Modifier.width(200.dp)) {
                            Button(onClick = { navController.navigate("frmLibros/${libro.id}") }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)), modifier = Modifier.padding(end = 4.dp)) {
                                Text("Editar", fontSize = 12.sp)
                            }
                            Button(onClick = {
                                scope.launch {
                                    try {
                                        val response = api.eliminarLibro(libro.id)
                                        if (response.body() == "success") {
                                            Toast.makeText(context, "Libro eliminado", Toast.LENGTH_SHORT).show()
                                            libros = api.obtenerLibros()
                                        } else {
                                            Toast.makeText(context, "No se pudo eliminar", Toast.LENGTH_SHORT).show()
                                        }
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))) {
                                Text("Eliminar", fontSize = 12.sp)
                            }
                        }
                    }
                    Divider()
                }
            }
        }
    }
}

@Composable
fun FrmLibrosContent(navController: NavHostController, modifier: Modifier = Modifier, libroId: Int? = null) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    var titulo by rememberSaveable { mutableStateOf("") }
    var autor by rememberSaveable { mutableStateOf("") }
    var fecha by rememberSaveable { mutableStateOf("") }
    var resena by rememberSaveable { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    val esEdicion = libroId != null && libroId > 0

    LaunchedEffect(libroId) {
        if (esEdicion) {
            isLoading = true
            scope.launch {
                try {
                    val libro = api.obtenerLibro(libroId!!)
                    titulo = libro.titulo
                    autor = libro.autor
                    fecha = libro.fecha
                    resena = libro.resena
                } catch (e: Exception) {
                    Toast.makeText(context, "Error al cargar libro", Toast.LENGTH_SHORT).show()
                } finally {
                    isLoading = false
                }
            }
        }
    }

    Column(modifier = modifier.fillMaxSize().padding(24.dp).verticalScroll(scrollState).padding(8.dp), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top) {
        Button(onClick = { navController.navigate("lstLibros") }, colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.Blue), modifier = Modifier.fillMaxWidth()) {
            Text("Tabla de Libros", style = TextStyle(textDecoration = TextDecoration.Underline), textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = if (esEdicion) "Editar Libro" else "Agregar Libro", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            Text(text = "Título:")
            OutlinedTextField(value = titulo, onValueChange = { titulo = it }, placeholder = { Text("Ingresa el Título") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Autor:")
            OutlinedTextField(value = autor, onValueChange = { autor = it }, placeholder = { Text("Ingresa el Autor") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Fecha (YYYY-MM-DD):")
            OutlinedTextField(value = fecha, onValueChange = { fecha = it }, placeholder = { Text("Ej: 2024-11-17") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Reseña:")
            OutlinedTextField(value = resena, onValueChange = { resena = it }, placeholder = { Text("Ingresa una reseña") }, modifier = Modifier.fillMaxWidth(), maxLines = 6)
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(onClick = {
                    if (titulo.isBlank() || autor.isBlank() || fecha.isBlank()) {
                        Toast.makeText(context, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    scope.launch {
                        try {
                            val response = if (esEdicion) {
                                api.modificarLibro(libroId!!, titulo, autor, fecha, resena)
                            } else {
                                api.agregarLibro(titulo, autor, fecha, resena)
                            }
                            if (response.body() == "success") {
                                val mensaje = if (esEdicion) "Libro actualizado" else "Libro agregado"
                                Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
                                navController.navigate("lstLibros")
                            } else {
                                Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Log.e("API", "Error: ${e.message}")
                            Toast.makeText(context, "Error de conexión", Toast.LENGTH_SHORT).show()
                        }
                    }
                }) {
                    Text(if (esEdicion) "Actualizar" else "Guardar")
                }
            }
        }
    }
}

@Composable
fun LstCalificacionesContent(navController: NavHostController, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    var calificaciones by remember { mutableStateOf<List<CalificacionResponse>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                calificaciones = api.obtenerCalificaciones()
            } catch (e: Exception) {
                Log.e("API", "Error al obtener calificaciones: ${e.message}")
                Toast.makeText(context, "Error al cargar calificaciones", Toast.LENGTH_SHORT).show()
            } finally {
                isLoading = false
            }
        }
    }

    Column(modifier = modifier.fillMaxSize().padding(24.dp).verticalScroll(scrollState).padding(8.dp), horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top) {
        Button(onClick = { navController.navigate("Menu") }, colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color.Blue), modifier = Modifier.fillMaxWidth()) {
            Text("Menú", style = TextStyle(textDecoration = TextDecoration.Underline), textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Lista de Calificaciones", fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.align(Alignment.CenterHorizontally))
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("frmCalificaciones/0") }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50), contentColor = Color.White), modifier = Modifier.align(Alignment.End)) {
            Text("+ Agregar Calificación")
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            val horizontalScrollState = rememberScrollState()
            Column(modifier = Modifier.horizontalScroll(horizontalScrollState)) {
                Row {
                    Text("ID", modifier = Modifier.width(50.dp), fontWeight = FontWeight.Bold)
                    Text("Libro", modifier = Modifier.width(150.dp), fontWeight = FontWeight.Bold)
                    Text("Calificación", modifier = Modifier.width(100.dp), fontWeight = FontWeight.Bold)
                    Text("Reseña", modifier = Modifier.width(200.dp), fontWeight = FontWeight.Bold)
                    Text("Acciones", modifier = Modifier.width(200.dp), fontWeight = FontWeight.Bold)
                }
                Divider()
                calificaciones.forEachIndexed { index, calif ->
                    val bgColor = if (index % 2 == 0) Color(0xFFF5F5F5) else Color.White
                    Row(modifier = Modifier.background(bgColor).padding(vertical = 4.dp)) {
                        Text(text = calif.id.toString(), modifier = Modifier.width(50.dp))
                        Text(text = calif.libro, modifier = Modifier.width(150.dp))
                        Text(text = calif.calificacion.toString(), modifier = Modifier.width(100.dp))
                        Text(text = calif.resena, modifier = Modifier.width(200.dp))
                        Row(modifier = Modifier.width(200.dp)) {
                            Button(onClick = { navController.navigate("frmCalificaciones/${calif.id}") }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)), modifier = Modifier.padding(end = 4.dp)) {
                                Text("Editar", fontSize = 12.sp)
                            }
                            Button(onClick = {
                                scope.launch {
                                    try {
                                        val response = api.eliminarCalificacion(calif.id)
                                        if (response.body() == "success") {
                                            Toast.makeText(context, "Calificación eliminada", Toast.LENGTH_SHORT).show()
                                            calificaciones = api.obtenerCalificaciones()
                                        } else {
                                            Toast.makeText(context, "No se pudo eliminar", Toast.LENGTH_SHORT).show()
                                        }
                                    } catch (e: Exception) {
                                        Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE53935))) {
                                Text("Eliminar", fontSize = 12.sp)
                            }
                        }
                    }
                    Divider()
                }
            }
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

        Text(text = "Calificación:")
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
                            calif = opcion
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
                    Toast.makeText(context, "Calificación: ${calif}", Toast.LENGTH_SHORT)
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