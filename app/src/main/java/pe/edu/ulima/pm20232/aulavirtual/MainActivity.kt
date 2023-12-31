package pe.edu.ulima.pm20232.aulavirtual

import android.content.SharedPreferences
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import kotlinx.coroutines.CoroutineScope
import pe.edu.ulima.pm20232.aulavirtual.components.BottomNavigationBar
import pe.edu.ulima.pm20232.aulavirtual.components.TopNavigationBar
import pe.edu.ulima.pm20232.aulavirtual.configs.BottomBarScreen
import pe.edu.ulima.pm20232.aulavirtual.configs.TopBarScreen
import pe.edu.ulima.pm20232.aulavirtual.screenmodels.*
import pe.edu.ulima.pm20232.aulavirtual.screens.*
import pe.edu.ulima.pm20232.aulavirtual.ui.theme.AulaVirtualTheme
import androidx.compose.ui.res.vectorResource


class MainActivity : ComponentActivity() {
    private val loginScrennViewModel by viewModels<LoginScreenViewModel>()
    private val CreateAcountModel by viewModels<CreateAcountModel>()

    private val profileScrennViewModel by viewModels<ProfileScreenViewModel>()
    private val homeScrennViewModel by viewModels<HomeScreenViewModel>()
    private val pokemonDetailScrennViewModel by viewModels<PokemonDetailScreenViewModel>()
    private val ExercisesScreenModel by viewModels<ExercisesScreenViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            AulaVirtualTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val blackList: List<String> = listOf("profile", "profile2", "login","reset_password", "create")
                    val currentRoute = navBackStackEntry?.destination?.route
                    var showDialog by remember { mutableStateOf(false) }
                    Scaffold(
                        topBar = {
                            if(blackList.contains(currentRoute) == false) {
                                val screens: List<TopBarScreen> = listOf(
                                    TopBarScreen(
                                        route = "profile2",
                                        title = "Editar Perfil",
                                    ),
                                    TopBarScreen(
                                        route = "about",
                                        title = "Acerca de",
                                    ),
                                    TopBarScreen(
                                        route = "sign_out",
                                        title = "Cerrar Sesión",
                                    ),
                                )
                                TopNavigationBar(navController, screens)
                            }
                        },
                        bottomBar = {
                            if(blackList.contains(currentRoute) == false) {
                                val screens: List<BottomBarScreen> = listOf(
                                    BottomBarScreen(

                                        route = "home",
                                        title = "Mi Rutina",
                                        icon = ImageVector.vectorResource(id = R.drawable.ic_checklist)
                           
                                    ),
                                    BottomBarScreen(
                                        route = "exercises",
                                        title = "Ejercicios",
                                        icon = ImageVector.vectorResource(id = R.drawable.ic_squarelist)
                                    ),
                                    BottomBarScreen(
                                        route = "share",
                                        title = "Compartir",
                                        icon = ImageVector.vectorResource(id = R.drawable.ic_share)
                                    ),
                                )
                                BottomNavigationBar(navController = navController, screens)
                            }
                        },
                        content = {
                            if (showDialog) {
                                AlertDialog(
                                    onDismissRequest = {
                                        showDialog = false
                                    },
                                    title = {
                                        Text(text = "Título del cuadro de diálogo")
                                    },
                                    text = {
                                        val imageUrl = "https://pokefanaticos.com/pokedex/assets/images/pokemon_imagenes/25.png"
                                        val uri = Uri.parse(imageUrl)
                                        val painter = rememberImagePainter(
                                            data = uri.scheme + "://" + uri.host + uri.path + (if (uri.query != null) uri.query else ""),
                                            builder = {
                                                // You can apply transformations here if needed
                                                transformations(CircleCropTransformation())
                                            }
                                        )
                                        Column(){
                                            Text("Este es un cuadro de diálogo de alerta en Compose. Puedes personalizar su contenido aquí.")
                                            Row(){
                                                Image(
                                                    painter = painter,
                                                    contentDescription = null, // Set a proper content description if required
                                                    modifier = Modifier.size(40.dp, 40.dp)
                                                )
                                                Image(
                                                    painter = painter,
                                                    contentDescription = null, // Set a proper content description if required
                                                    modifier = Modifier.size(40.dp, 40.dp)
                                                )
                                            }
                                        }
                                    },
                                    confirmButton = {
                                        TextButton(
                                            onClick = {
                                                // Lógica para manejar el botón de confirmación
                                                showDialog = false
                                            }
                                        ) {
                                            Text("Aceptar")
                                        }
                                    },
                                    dismissButton = {
                                        TextButton(
                                            onClick = {
                                                // Lógica para manejar el botón de descartar
                                                showDialog = false
                                            }
                                        ) {
                                            Text("Cancelar")
                                        }
                                    }
                                )
                            }


                            NavHost(navController, startDestination = "login") {

                                composable(route = "splash") {
                                    SplashScreen {
                                        navController.navigate("login")
                                    }
                                }
                                composable(route = "exercises") {
                                    Log.d("EXERCISES", "exercises")
                                    ExercisesScreen(navController, ExercisesScreenModel)
                                }
                                composable(route = "home") {
                                    Log.d("HOME", "home screen")
                                    HomeScreen(navController, homeScrennViewModel)
                                }
                                composable(route = "pokemon") {
                                    Log.d("POKEMON", "pokemons screen")
                                    PokemonScreen(navController)
                                }
                                composable(route = "reset_password") {
                                    Log.d("ROUTER", "reset password")
                                    ResetPasswordScreen(navController)
                                }
                                composable(route = "profile") {
                                    Log.d("ROUTER", "profile")
                                    ProfileScreen(navController, profileScrennViewModel)
                                }
                                composable(route = "profile2") {
                                    Log.d("ROUTER2", "profile2")
                                    ProfileScreen2(navController)
                                }
                                composable(route = "create") {
                                    Log.d("ROUTER2", "profile2")
                                    CreateAcountScreen( CreateAcountModel, navController)
                                }
                                composable(route = "pokemon/edit?pokemon_id={pokemon_id}", arguments = listOf(
                                    navArgument("pokemon_id") {
                                        type = NavType.IntType
                                        defaultValue = 0
                                    }
                                ), content = { entry ->
                                    val pokemonId = entry.arguments?.getInt("pokemon_id")!!
                                    pokemonDetailScrennViewModel.pokemonId = pokemonId
                                    PokemonDetailScreen(navController, pokemonDetailScrennViewModel)
                                })
                                composable(route = "login") {
                                    Log.d("ROUTER", "login")
                                    LoginScreen(loginScrennViewModel, navController)
                                }
                            }
                        }
                    )

                }
            }
        }
    }
}
