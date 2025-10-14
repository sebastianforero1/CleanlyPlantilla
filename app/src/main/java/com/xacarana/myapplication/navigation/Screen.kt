package com.xacarana.myapplication.navigation

/**
 * Definición de las pantallas principales de la aplicación y sus rutas de navegación.
 */
sealed class Screen(val route: String, val title: String) {
    // Pantallas de autenticación y flujo inicial
    data object Splash : Screen("splash", "Splash")
    data object Welcome : Screen("welcome", "Bienvenida")
    data object Login : Screen("login", "Iniciar Sesión")
    data object SignUp : Screen("signup", "Registrarse")

    // Pantallas principales de la aplicación
    data object Dashboard : Screen("dashboard", "Dashboard")
    data object CreateTask : Screen("createTask", "Crear tarea")
    data object TaskDetail : Screen("taskDetail/{taskId}", "Detalle") {
        fun route(taskId: String) = "taskDetail/$taskId"
    }
    data object Categories : Screen("categories", "Categorías")
    data object Profile : Screen("profile", "Perfil")
    data object Settings : Screen("settings", "Configuración")
    data object Credits : Screen("credits", "Créditos")

    /**
     * Lista de pantallas que aparecen en la barra de navegación inferior.
     */
    companion object {
        val bottom = listOf(Dashboard, CreateTask, Categories, Profile)
    }
}
