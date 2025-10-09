package com.xacarana.myapplication

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Apps
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.foundation.layout.padding
import com.xacarana.myapplication.auth.AuthService
import com.xacarana.myapplication.data.SharedPrefsTaskRepository
import com.xacarana.myapplication.data.TaskRepository
import com.xacarana.myapplication.navigation.Screen
import com.xacarana.myapplication.notifications.Notifications
import com.xacarana.myapplication.ui.auth.LoginScreen
import com.xacarana.myapplication.ui.auth.SignUpScreen
import com.xacarana.myapplication.ui.auth.SplashScreen
import com.xacarana.myapplication.ui.auth.WelcomeScreen
import com.xacarana.myapplication.ui.categories.CategoriesScreen
import com.xacarana.myapplication.ui.dashboard.DashboardScreen
import com.xacarana.myapplication.ui.profile.ProfileScreen
import com.xacarana.myapplication.ui.tasks.CreateTaskScreen
import com.xacarana.myapplication.ui.tasks.TaskDetailScreen
import com.xacarana.myapplication.ui.credits.CreditsScreen
import com.xacarana.myapplication.ui.settings.SettingsScreen
import com.xacarana.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private lateinit var repo: TaskRepository
    private lateinit var auth: AuthService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializar servicios
        repo = SharedPrefsTaskRepository(applicationContext).apply { load(applicationContext) }
        auth = AuthService(applicationContext)
        Notifications.ensureChannel(this)

        // Pedir permiso de notificaciones en Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { }
                .launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        setContent {
            MyApplicationTheme {
                val nav = rememberNavController()
                var currentRoute by remember { mutableStateOf(Screen.Splash.route) }

                // Elementos de la barra inferior (pantalla, label, ícono)
                val bottomItems = listOf(
                    Triple(Screen.Dashboard, R.string.dashboard, Icons.Rounded.Home),
                    Triple(Screen.CreateTask, R.string.create_task, Icons.Rounded.AddCircle),
                    Triple(Screen.Categories, R.string.categories, Icons.Rounded.Apps),
                    Triple(Screen.Profile, R.string.profile, Icons.Rounded.Person)
                )

                Scaffold(
                    bottomBar = {
                        val showBar = currentRoute in listOf(
                            Screen.Dashboard.route,
                            Screen.CreateTask.route,
                            Screen.Categories.route,
                            Screen.Profile.route
                        )
                        if (showBar) {
                            NavigationBar(containerColor = Color(0xFFF8F8FA)) {
                                bottomItems.forEach { (screen, labelId, icon) ->
                                    val selected = currentRoute.startsWith(screen.route)
                                    NavigationBarItem(
                                        selected = selected,
                                        onClick = {
                                            if (currentRoute != screen.route) {
                                                nav.navigate(screen.route) {
                                                    popUpTo(Screen.Dashboard.route)
                                                    launchSingleTop = true
                                                }
                                            }
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = icon,
                                                contentDescription = null,
                                                tint = if (selected)
                                                    Color(0xFF1B2A49)
                                                else Color(0xFF8A8A8A)
                                            )
                                        },
                                        label = {
                                            Text(
                                                text = stringResource(labelId),
                                                color = if (selected)
                                                    Color(0xFF1B2A49)
                                                else Color(0xFF8A8A8A)
                                            )
                                        },
                                        alwaysShowLabel = true
                                    )
                                }
                            }
                        }
                    }
                ) { padding ->
                    NavHost(
                        navController = nav,
                        startDestination = Screen.Splash.route,
                        modifier = Modifier.padding(padding)
                    ) {
                        composable(Screen.Splash.route) {
                            currentRoute = Screen.Splash.route
                            SplashScreen(nav, auth)
                        }
                        composable(Screen.Welcome.route) {
                            currentRoute = Screen.Welcome.route
                            WelcomeScreen(nav)
                        }
                        composable(Screen.Login.route) {
                            currentRoute = Screen.Login.route
                            LoginScreen(nav, auth)
                        }
                        composable(Screen.SignUp.route) {
                            currentRoute = Screen.SignUp.route
                            SignUpScreen(nav, auth)
                        }

                        composable(Screen.Dashboard.route) {
                            currentRoute = Screen.Dashboard.route
                            DashboardScreen(nav, repo)
                        }
                        composable(Screen.CreateTask.route) {
                            currentRoute = Screen.CreateTask.route
                            CreateTaskScreen(nav, repo)
                        }
                        composable(
                            route = Screen.TaskDetail.route,
                            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
                        ) {
                            currentRoute = Screen.TaskDetail.route
                            val taskId = it.arguments?.getString("taskId")!!
                            TaskDetailScreen(nav, repo, taskId)
                        }
                        composable(Screen.Categories.route) {
                            currentRoute = Screen.Categories.route
                            CategoriesScreen(repo)
                        }
                        composable(Screen.Profile.route) {
                            currentRoute = Screen.Profile.route
                            ProfileScreen(nav, auth)
                        }
                        composable(Screen.Settings.route) {
                            currentRoute = Screen.Settings.route
                            SettingsScreen()
                        }
                        composable(Screen.Credits.route) {
                            currentRoute = Screen.Credits.route
                            CreditsScreen()
                        }
                    }
                }
            }
        }
    }
}
