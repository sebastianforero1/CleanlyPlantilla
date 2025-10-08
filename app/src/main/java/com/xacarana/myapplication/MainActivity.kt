package com.xacarana.myapplication

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.res.painterResource
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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

        // Repo en memoria con persistencia en SharedPreferences
        repo = SharedPrefsTaskRepository(applicationContext).apply { load(applicationContext) }
        Notifications.ensureChannel(this)

        // Pedir permiso de notificaciones (13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { } // no-op
                .launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        setContent {
            MyApplicationTheme {
                val nav = rememberNavController()
                var currentRoute by remember { mutableStateOf(Screen.Splash.route) }

                Scaffold(
                    bottomBar = {
                        if (currentRoute in listOf(
                                Screen.Dashboard.route,
                                Screen.CreateTask.route,
                                Screen.Categories.route,
                                Screen.Profile.route
                            )
                        ) {
                            NavigationBar {
                                listOf(
                                    Screen.Dashboard to R.string.dashboard,
                                    Screen.CreateTask to R.string.create_task,
                                    Screen.Categories to R.string.categories,
                                    Screen.Profile to R.string.profile
                                ).forEach { (screen, labelRes) ->
                                    NavigationBarItem(
                                        selected = currentRoute.startsWith(screen.route),
                                        onClick = { nav.navigate(screen.route) },
                                        icon = { Icon(painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = null) },
                                        label = { Text(stringResource(id = labelRes)) }
                                    )
                                }
                            }
                        }
                    }
                ) { padding ->
                    NavHost(
                        navController = nav,
                        startDestination = Screen.Splash.route,
                        modifier = androidx.compose.ui.Modifier.padding(padding)
                    ) {
                        composable(Screen.Splash.route) { currentRoute = Screen.Splash.route; SplashScreen(nav, auth) }
                        composable(Screen.Welcome.route) { currentRoute = Screen.Welcome.route; WelcomeScreen(nav) }
                        composable(Screen.Login.route) { currentRoute = Screen.Login.route; LoginScreen(nav, auth) }
                        composable(Screen.SignUp.route) { currentRoute = Screen.SignUp.route; SignUpScreen(nav, auth) }

                        composable(Screen.Dashboard.route) { currentRoute = Screen.Dashboard.route; DashboardScreen(nav, repo) }
                        composable(Screen.CreateTask.route) { currentRoute = Screen.CreateTask.route; CreateTaskScreen(nav, repo) }
                        composable(
                            route = Screen.TaskDetail.route,
                            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
                        ) {
                            currentRoute = Screen.TaskDetail.route
                            val taskId = it.arguments?.getString("taskId")!!
                            TaskDetailScreen(nav, repo, taskId)
                        }
                        composable(Screen.Categories.route) { currentRoute = Screen.Categories.route; CategoriesScreen(repo) }
                        composable(Screen.Profile.route) { currentRoute = Screen.Profile.route; ProfileScreen(nav, auth) }
                        composable(Screen.Settings.route) { currentRoute = Screen.Settings.route; SettingsScreen() }
                        composable(Screen.Credits.route) { currentRoute = Screen.Credits.route; CreditsScreen() }
                    }
                }
            }
        }
    }
}
