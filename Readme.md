# 🧹 CleanlyPlantilla

Aplicación móvil desarrollada en *Kotlin, diseñada para ayudarte a **organizar y recordar tus tareas de limpieza*. Crea listas personalizadas, programa recordatorios y mantén tu espacio siempre impecable.

---

## 🌟 Funcionalidades principales

- 📝 *Creación de listas de limpieza* con tareas personalizadas.
- ⏰ *Recordatorios* para que no olvides ninguna actividad importante.
- 📋 *Gestión y edición de tareas*: marca como completadas, elimina o actualiza tus actividades de limpieza.
- 📅 *Visualización de tareas pendientes y completadas* de manera intuitiva.
- 🎨 *Interfaz amigable y moderna*, pensada para facilitar el uso diario.

---

## 🧱 Arquitectura

El proyecto sigue una estructura limpia, basada en *MVVM (Model-View-ViewModel)* y buenas prácticas de desarrollo Android:

proyecto_kotlin/
├─ app/
│  ├─ build.gradle.kts                ← Gradle del módulo app
│  └─ src/main/
│     ├─ AndroidManifest.xml          ← Declaración de actividad, permisos, canal notificaciones
│     ├─ java/com/xacarana/myapplication/
│     │  ├─ MainActivity.kt           ← Arranque de Compose, NavHost, bottom bar y rutas
│     │  ├─ auth/
│     │  │  └─ AuthService.kt         ← “Auth” local (fake). Guarda/lee usuario en SharedPrefs
│     │  ├─ data/
│     │  │  ├─ TaskRepository.kt      ← Interfaz del repositorio (API de datos)
│     │  │  └─ SharedPrefsTaskRepository.kt
│     │  │                               Implementación con SharedPreferences + JSON.
│     │  │                               Maneja tareas, categorías y repetición.
│     │  ├─ model/
│     │  │  └─ Models.kt              ← Modelos: Task, Repeat, ActivityRecord, DayStats, etc.
│     │  ├─ navigation/
│     │  │  └─ Screen.kt              ← Sellado de rutas: Splash, Welcome, Login, Dashboard, etc.
│     │  ├─ notifications/
│     │  │  ├─ Notifications.kt       ← Creación de NotificationChannel
│     │  │  └─ ReminderScheduler.kt   ← Programa recordatorios (por hora de la tarea)
│     │  ├─ ui/
│     │  │  ├─ auth/
│     │  │  │  ├─ WelcomeScreen.kt    ← Pantalla de bienvenida (botones Registrar / Login)
│     │  │  │  ├─ LoginScreen.kt      ← Email/Password, llama a AuthService.login(...)
│     │  │  │  └─ SignUpScreen.kt     ← Nombre/Email/Password, valida Gmail y crea usuario
│     │  │  ├─ categories/
│     │  │  │  └─ CategoriesScreen.kt ← Crear/listar/borrar categorías (usa repo.categories)
│     │  │  ├─ components/
│     │  │  │  ├─ CalendarRow.kt      ← Semana visible y selección de día
│     │  │  │  └─ TaskItem.kt         ← Ítem de tarea (checkbox, hora, acciones)
│     │  │  ├─ dashboard/
│     │  │  │  └─ DashboardScreen.kt  ← “Hoy”, semana, lista de tareas, compartir, “Añadir”
│     │  │  ├─ profile/
│     │  │  │  └─ ProfileScreen.kt    ← Saludo y acciones (Créditos, Cerrar sesión)
│     │  │  ├─ settings/
│     │  │  │  └─ SettingsScreen.kt   ← Preferencias (si las habilitas)
│     │  │  ├─ tasks/
│     │  │  │  ├─ CreateTaskScreen.kt ← Formulario de nueva tarea (título, categoría, fecha, hora, repetición)
│     │  │  │  └─ TaskDetailScreen.kt ← Detalle/edición de una tarea
│     │  │  └─ credits/
│     │  │     └─ CreditsScreen.kt    ← Pantalla de créditos
│     │  ├─ util/
│     │  │  └─ DateUtils.kt           ← Utilidades de fecha (rango de semana, etc.)
│     │  └─ ui/theme/
│     │     ├─ Color.kt / Theme.kt / Type.kt
│     │     └─ Shape.kt               ← Paleta, tipografías y theming Material 3
│     └─ res/
│        ├─ drawable*/mipmap*/raw/…   ← Iconos, logos
│        └─ values/strings.xml        ← Textos de UI (y algunos colores/temas)
├─ build.gradle.kts                    ← Gradle del proyecto (versiones, repos)
└─ gradle.properties                   ← Flags (por ej., android.suppressUnsupportedCompileSdk)

---

## ⚙️ Requerimientos

- *Android Studio Hedgehog (o superior)*
- *Gradle 8.x*
- *Kotlin 1.9+*
- Mínimo SDK: *Android 8.0 (API 26)*
- No requiere conexión a internet para funcionalidades básicas

---

## 🧩 Dependencias principales

gradle
// Jetpack Compose
implementation "androidx.compose.ui:ui:1.7.0"
implementation "androidx.compose.material3:material3:1.3.0"
implementation "androidx.navigation:navigation-compose:2.7.7"

// Room (persistencia local)
implementation "androidx.room:room-runtime:2.6.1"
kapt "androidx.room:room-compiler:2.6.1"
implementation "androidx.room:room-ktx:2.6.1"

// Coroutines
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1"

// Lifecycle y ViewModel
implementation "androidx.lifecycle:lifecycle-runtime-compose:2.7.0"
implementation "androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0"


---

## 🛠️ Cómo ejecutar el proyecto

1. Clona este repositorio:
   bash
   git clone https://github.com/sebastianforero1/CleanlyPlantilla.git
   
2. Ábrelo en Android Studio (Open an existing project).
3. Espera a que se sincronicen las dependencias.
4. Ejecuta la app en un emulador o dispositivo físico.

---

## 👤 Administrador

- [Sebastián Forero](https://github.com/sebastianforero1)

¿Tienes dudas o sugerencias? ¡Abre un issue o contacta al ADMIN!
