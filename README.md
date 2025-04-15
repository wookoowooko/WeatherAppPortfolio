# 🌦️ WeatherApp

A weather forecast application developed as a showcase project using modern Android technologies.

## 📱 Screenshots

![](docs/screenshots/scr_1.png)
![](docs/screenshots/scr_2.png)
![](docs/screenshots/scr_3.png)

## 🌍 Localization and Adaptivity

The application supports **three languages**:

- 🇷🇺 Russian
- 🇪🇸 Spanish
- 🇬🇧 English

🖥️ **Adaptive design** support:

- Smartphones
- Tablets
- Foldable devices (Foldables)

📐 Account for **screen orientation**:

- Portrait
- Landscape

🧩 Hybrid UI:

- Jetpack **Compose**
- **XML** View Binding

## 🌐 Data Sources

The application uses **open and free APIs**:

- **[Open-Meteo Weather API](https://open-meteo.com/)** — for weather forecasting (temperature,
  wind, humidity, etc.)
- **[Open-Meteo Geocoding API](https://geocoding-api.open-meteo.com/)** — geocoding and determining
  coordinates by city name
- **[GeoNames](https://www.geonames.org/)** — reverse geocoding. Requires a free API key:

> 🔐 To use GeoNames, you need to get a **free API key**. Register
> on [geonames.org](https://www.geonames.org/login) and create a user, then specify the key in
`gradle.properties`:
>
> ```
> REVERSE_GEOCODING_API_KEY=your_key
> ```

## 🧰 Stack and Libraries

The project is based on **Kotlin 2+**, **KSP**, **AGP 8.8+**.

### 🖼️ UI

- [XML View with ViewBindings](https://developer.android.com/topic/libraries/view-binding)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Compose ConstraintLayout](https://developer.android.com/jetpack/compose/layouts/constraintlayout)
- [Material 3 Adaptive](https://m3.material.io/foundations/adaptive-design)
- [Material Icons Extended](https://mvnrepository.com/artifact/androidx.compose.material/material-icons-extended)
- [Android SplashScreen](https://developer.android.com/develop/ui/views/launch/splash-screen)
- [ViewBindingPropertyDelegate](https://github.com/androidbroadcast/ViewBindingPropertyDelegate)
- [Android Compose Fragment](https://developer.android.com/develop/ui/compose/migrate/interoperability-apis/compose-in-views)
- [Lottie](https://github.com/airbnb/lottie-android)
- [Adapter Delegates](https://github.com/sockeqwe/AdapterDelegates)

### 🧬 Asynchrony

- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Kotlin Flows](https://kotlinlang.org/docs/flow.html#flow-cancellation-basics)

### ♻️ Background

- [Work Manager](https://developer.android.com/develop/background-work/background-tasks/persistent/getting-started)

### 📦 DI

- [Dagger Hilt](https://dagger.dev/hilt/)

### 🛰️ Networking

- [Retrofit](https://square.github.io/retrofit/)
- [OkHttp](https://square.github.io/okhttp/)
- [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)

### 💾 Data Storage

- [Room](https://developer.android.com/jetpack/androidx/releases/room)
- [Proto Datastore](https://developer.android.com/topic/libraries/architecture/datastore)

### 📍 Geolocation

- [Play Services Location](https://developer.android.com/training/location)

### 🧭 Navigation

- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)

### 🧱 Widgets

- [Glance Compose](https://developer.android.com/jetpack/compose/glance)

### 🧪 Testing

- [JUnit](https://junit.org/)
- [MockK](https://mockk.io/)
- [Hilt Testing](https://dagger.dev/hilt/testing.html)

### 📅 Working with Date and Time

- [Kotlinx Date Time](https://github.com/Kotlin/kotlinx-datetime)

### 🎨 Code Style and Analysis

- [Detekt](https://detekt.dev/)
- [Detekt Compose Rules](https://github.com/appKODE/detekt-rules-compose)

## 📦 Project Modules

The project is built with a **modular architecture**, which simplifies scaling, reuse, and testing.

### 🔧 Build Logic

- [`build-logic`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/build-logic) —
  Gradle plugins and build configurations

### 🚀 Main Module

- [`app`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/app) — entry point of the
  application, initialization, navigation

### 🧩 Core Modules

- [
  `androidresources`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/androidresources) —
  string resources, translations (RU, EN, ES)
- [`common`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/common) — base
  interfaces, utilities
- [
  `connectivity-observer`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/connectivity-observer) —
  internet connection monitoring
- [`data`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/data) — common
  interfaces and repository implementations
- [`database`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/database) — local
  storage implementation (Room)
- [`datastore`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/datastore) —
  local storage implementation (Proto DataStore)
- [
  `design-system`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/design-system) —
  UI components and theme (Compose + XML)
- [`domain`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/domain) — business
  logic and use-cases
- [`geolocation`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/geolocation) —
  coordinates determination
- [`mappers`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/mappers) — data
  transformation between layers
- [`models`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/models) — data
  models (DTO, Domain, UI, Entity)
- [`network`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/network) —
  Retrofit, OkHttp, API interfaces
- [`permissions`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/permissions) —
  runtime permission management
- [
  `synchronizer`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/synchronizer) —
  data synchronization logic
- [`widgets`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/widgets) — widgets
- [`worker`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/worker) —
  background tasks using WorkManager

### 🧩 Feature Modules

Each handles specific functionality and is implemented with a feature-based architecture:

- [`cities`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/features/cities) —
  search and manage city list
- [`main`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/features/main) — main
  screen with current weather
- [`settings`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/features/settings) —
  application settings
- [`weekly`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/features/weekly) —
  weekly forecast screen
- [`welcome`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/features/welcome) —
  onboarding

## 🧠 Architectural Approach

The application implements a **custom MVI approach** with elements of Redux, designed specifically
for the project's tasks. It combines **reactive programming** with **unidirectional data flow**,
ensuring predictability, scalability, and ease of testing.

### 💡 Key Concepts

| Component                        | Purpose                                                                                    |
|----------------------------------|--------------------------------------------------------------------------------------------|
| **State**                        | Represents the current UI state (e.g., WeeklyState)                                        |
| **Intent**                       | User actions or screen events (e.g., WeeklyIntent)                                         |
| **Reducer**                      | A pure function that transforms State + Intent into a new State                            |
| **Effect**                       | One-time side effects (e.g., showing Snackbar, navigation), implemented through SharedFlow |
| **Store<State, Intent, Effect>** | A centralized state and effect handler, based on coroutines and Flow                       |
| **ViewModel**                    | Provides UI StateFlow of state and SharedFlow of effects                                   |

### 🔁 Data Flow

```text
Intent (user action)
       ↓
  Reducer (business logic)
       ↓
State (updated UI state)
       ↓
   UI updated through StateFlow
```

## 🔓 Open Source

This project is **Open Source** — you can freely use it for personal and commercial purposes, make
modifications, create forks, and propose improvements.

If the project has been helpful to you, please give it a ⭐️ — it motivates us to continue supporting
and developing it!

License: [Apache 2.0](LICENSE)

***

# 🌦️ WeatherApp

Приложение прогноза погоды, разработанное как showcase-проект с использованием современных
Android-технологий.

## 📱 Скриншоты

![](docs/screenshots/scr_1.png)
![](docs/screenshots/scr_2.png)
![](docs/screenshots/scr_3.png)

## 🌍 Локализация и адаптивность

Приложение поддерживает **три языка**:

- 🇷🇺 Русский
- 🇪🇸 Испанский
- 🇬🇧 Английский

🖥️ Поддержка **адаптивного дизайна**:

- Смартфоны
- Планшеты
- Складные устройства (Foldables)

📐 Учет **ориентации экрана**:

- Вертикальная (Portrait)
- Горизонтальная (Landscape)

🧩 Гибридный UI:

- Jetpack **Compose**
- **XML** View Binding

## 🌐 Источники данных

Приложение использует **открытые и бесплатные API**:

- **[Open-Meteo Weather API](https://open-meteo.com/)** — для получения прогноза погоды (
  температура, ветер, влажность и пр.)
- **[Open-Meteo Geocoding API](https://geocoding-api.open-meteo.com/)** — геокодинг и определение
  координат по названию города
- **[GeoNames](https://www.geonames.org/)**  — реверс геокодинг. Требуется бесплатный API-ключ:

> 🔐 Для использования GeoNames необходимо получить **бесплатный API-ключ**. Зарегистрируйтесь
> на [geonames.org](https://www.geonames.org/login) и создайте пользователя, укажите ключ в
`gradle.properties`:
>
> ```
> REVERSE_GEOCODING_API_KEY=your_key
> ```

## 🧰 Стек и библиотеки

Проект построен на базе **Kotlin 2+** , **KSP** , **AGP 8.8+**

### 🖼️ UI

- [XML View с ViewBindings](https://developer.android.com/topic/libraries/view-binding)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Compose ConstraintLayout](https://developer.android.com/jetpack/compose/layouts/constraintlayout)
- [Material 3 Adaptive](https://m3.material.io/foundations/adaptive-design)
- [Material Icons Extended](https://mvnrepository.com/artifact/androidx.compose.material/material-icons-extended)
- [Android SplashScreen](https://developer.android.com/develop/ui/views/launch/splash-screen)
- [ViewBindingPropertyDelegate](https://github.com/androidbroadcast/ViewBindingPropertyDelegate)
- [Android Compose Fragment](https://developer.android.com/develop/ui/compose/migrate/interoperability-apis/compose-in-views)
- [Lottie](https://github.com/airbnb/lottie-android)
- [Adapter Delegates](https://github.com/sockeqwe/AdapterDelegates)

### 🧬 Асинхронность

- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
- [Kotlin Flows](https://kotlinlang.org/docs/flow.html#flow-cancellation-basics)

### ♻️ Бэкграунд

- [Work Manager](https://developer.android.com/develop/background-work/background-tasks/persistent/getting-started)

### 📦 DI

- [Dagger Hilt](https://dagger.dev/hilt/)

### 🛰️ Сеть

- [Retrofit](https://square.github.io/retrofit/)
- [OkHttp](https://square.github.io/okhttp/)
- [Kotlinx serialization](https://github.com/Kotlin/kotlinx.serialization)

### 💾 Хранение данных

- [Room](https://developer.android.com/jetpack/androidx/releases/room)
- [Proto Datastore](https://developer.android.com/topic/libraries/architecture/datastore)

### 📍 Геолокация

- [Play Services Location](https://developer.android.com/training/location)

### 🧭 Навигация

- [Navigation Compose](https://developer.android.com/jetpack/compose/navigation)

### 🧱 Виджеты

- [Glance Compose](https://developer.android.com/jetpack/compose/glance)

### 🧪 Тестирование

- [JUnit](https://junit.org/)
- [MockK](https://mockk.io/)
- [Hilt Testing](https://dagger.dev/hilt/testing.html)

### 📅 Работа с датой и временем

- [Kotlinx Date Time](https://github.com/Kotlin/kotlinx-datetime)

### 🎨 Code Style и анализ

- [Detekt](https://detekt.dev/)
- [Detekt Compose Rules](https://github.com/appKODE/detekt-rules-compose)

## 📦 Модули проекта

Проект построен на **модульной архитектуре**, что упрощает масштабирование, повторное использование
и тестирование.

### 🔧 Build Logic

- [`build-logic`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/build-logic) —
  Gradle-плагины и конфигурации сборки

### 🚀 Главный модуль

- [`app`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/app) — точка входа в
  приложение, инициализация, навигация

### 🧩 Core-модули

- [
  `androidresources`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/androidresources) —
  строковые ресурсы, переводы (RU, EN, ES)
- [`common`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/common) — базовые
  интерфейсы, утилиты
- [
  `connectivity-observer`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/connectivity-observer) —
  мониторинг интернет-соединения
- [`data`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/data) — общие
  интерфейсы и реализации репозиториев
- [`database`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/database) —
  реализация локального хранилища (Room)
- [`datastore`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/datastore) —
  реализация локального хранилища (Proto DataStore)
- [
  `design-system`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/design-system) —
  UI-компоненты и тема (Compose + XML)
- [`domain`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/domain) —
  бизнес-логика и use-cases
- [`geolocation`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/geolocation) —
  определение координат
- [`mappers`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/mappers) —
  преобразование данных между слоями
- [`models`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/models) — модели
  данных (DTO, Domain, UI, Entity)
- [`network`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/network) —
  Retrofit, OkHttp, API-интерфейсы
- [`permissions`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/permissions) —
  управление runtime-разрешениями
- [
  `synchronizer`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/synchronizer) —
  логика синхронизации данных
- [`widgets`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/widgets) — виджеты
- [`worker`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/core/worker) — фоновые
  задачи через WorkManager

### 🧩 Feature-модули

Каждый отвечает за конкретный функционал и реализован в стиле feature-based архитектуры:

- [`cities`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/features/cities) — поиск
  и управление списком городов
- [`main`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/features/main) — главный
  экран с текущей погодой
- [`settings`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/features/settings) —
  настройки приложения
- [`weekly`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/features/weekly) — экран
  прогноза на неделю
- [`welcome`](https://github.com/wookoowooko/WeatherAppPortfolio/tree/master/features/welcome) —
  онбординг

## 🧠 Архитектурный подход

В приложении реализован **кастомный MVI-подход** с элементами Redux, разработанный специально под
задачи проекта. Он объединяет **реактивное программирование** с **однонаправленным потоком данных**,
обеспечивая предсказуемость, масштабируемость и простоту тестирования.

### 💡 Основные концепции

| Компонент                        | Назначение                                                                                        |
|----------------------------------|---------------------------------------------------------------------------------------------------|
| **State**                        | Отражает текущее состояние UI (например, WeeklyState)                                             |
| **Intent**                       | Действия пользователя или события экрана (например, WeeklyIntent)                                 |
| **Reducer**                      | Чистая функция, преобразующая State + Intent в новый State                                        |
| **Effect**                       | Одноразовые побочные действия (например, показ Snackbar, навигация), реализованы через SharedFlow |
| **Store<State, Intent, Effect>** | Централизованный обработчик состояния и эффектов, основанный на корутинах и Flow                  |
| **ViewModel**                    | Предоставляет UI StateFlow состояния и SharedFlow эффектов                                        |

### 🔁 Поток данных

```text
Intent (действие пользователя)
       ↓
  Reducer (бизнес-логика)
       ↓
State (обновлённое состояние UI)
       ↓
   UI обновляется через StateFlow
```

## 🔓 Open Source

Этот проект является **Open Source** — вы можете свободно использовать его как в личных, так и в
коммерческих целях, вносить изменения, создавать форки и предлагать улучшения.

Если проект оказался вам полезен, поставьте ⭐️ — это мотивирует поддерживать и развивать его дальше!

Лицензия: [Apache 2.0](LICENSE)
