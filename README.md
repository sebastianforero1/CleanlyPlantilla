# 🧹 CleanlyPlantilla

Aplicación móvil desarrollada en *Kotlin*, diseñada para ayudarte a **organizar y recordar tus tareas de limpieza**. Crea listas personalizadas, programa recordatorios y mantén tu espacio siempre impecable.

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

```plaintext
proyecto_kotlin/
├── app/
│   ├── build.gradle.kts
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── java/
│           │   └── com/
│           │       └── xacarana/
│           │           └── myapplication/
│           │               ├── MainActivity.kt
│           │               ├── auth/
│           │               │   └── AuthService.kt
│           │               ├── data/
│           │               │   ├── TaskRepository.kt
│           │               │   └── SharedPrefsTaskRepository.kt
│           │               ├── model/
│           │               │   └── Models.kt
│           │               ├── navigation/
│           │               │   └── Screen.kt
│           │               ├── notifications/
│           │               │   ├── Notifications.kt
│           │               │   └── ReminderScheduler.kt
│           │               ├── ui/
│           │               │   ├── auth/
│           │               │   │   ├── WelcomeScreen.kt
│           │               │   │   ├── LoginScreen.kt
│           │               │   │   └── SignUpScreen.kt
│           │               │   ├── categories/
│           │               │   │   └── CategoriesScreen.kt
│           │               │   ├── components/
│           │               │   │   ├── CalendarRow.kt
│           │               │   │   └── TaskItem.kt
│           │               │   ├── dashboard/
│           │               │   │   └── DashboardScreen.kt
│           │               │   ├── profile/
│           │               │   │   └── ProfileScreen.kt
│           │               │   ├── settings/
│           │               │   │   └── SettingsScreen.kt
│           │               │   ├── tasks/
│           │               │   │   ├── CreateTaskScreen.kt
│           │               │   │   └── TaskDetailScreen.kt
│           │               │   └── credits/
│           │               │       └── CreditsScreen.kt
│           │               ├── util/
│           │               │   └── DateUtils.kt
│           │               └── ui/
│           │                   └── theme/
│           │                       ├── Color.kt
│           │                       ├── Theme.kt
│           │                       ├── Type.kt
│           │                       └── Shape.kt
│           └── res/
│               ├── drawable/
│               ├── mipmap/
│               ├── raw/
│               └── values/
│                   └── strings.xml
├── build.gradle.kts
└── gradle.properties
```

---

## 🎨 Wireframes

Puedes consultar los wireframes y prototipos en Figma aquí:<br>
👉 [Wireframes Figma](https://figma.com/design/EwqUQEwP2lWkZOjv5netCU/Sign-Up---Sign-In-Screen--Community-?node-id=105-1446&t=ZoPOhi5q0ipXHTwR-1)

---

## ⚙️ Requerimientos

- *Android Studio Hedgehog (o superior)*
- *Gradle 8.x*
- *Kotlin 1.9+*
- Mínimo SDK: *Android 8.0 (API 26)*
- No requiere conexión a internet para funcionalidades básicas

---

## 🧩 Dependencias principales

```gradle
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
```

---

## 🛠️ Cómo ejecutar el proyecto

1. Clona este repositorio:
   ```bash
   git clone https://github.com/sebastianforero1/CleanlyPlantilla.git
   ```
2. Ábrelo en Android Studio (Open an existing project).
3. Espera a que se sincronicen las dependencias.
4. Ejecuta la app en un emulador o dispositivo físico.

---

## 👤 Administrador

- [Sebastián Forero](https://github.com/sebastianforero1)

¿Tienes dudas o sugerencias? ¡Abre un issue o contacta al ADMIN!
