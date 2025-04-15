# 🌦️ WeatherApp

Приложение прогноза погоды, разработанное как showcase-проект с использованием современных Android-технологий.

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

- **[Open-Meteo Weather API](https://open-meteo.com/)** — для получения прогноза погоды (температура, ветер, влажность и пр.)
- **[Open-Meteo Geocoding API](https://geocoding-api.open-meteo.com/)** — геокодинг и определение координат по названию города
- **[GeoNames](https://www.geonames.org/)**  — ркверс геокодинг. Требуется бесплатный API-ключ:

> 🔐 Для использования GeoNames необходимо получить **бесплатный API-ключ**. Зарегистрируйтесь на [geonames.org](https://www.geonames.org/login) и создайте пользователя, укажите ключ в `gradle.properties`:
>
> ```
> REVERSE_GEOCODING_API_KEY=your_key
> ```

## 🧰 Стек и библиотеки

Проект построен на базе **Kotlin 2+** и **AGP 8.8+**, с акцентом на модульную архитектуру, современные подходы к UI и производительность.

### 🖼️ UI

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Compose ConstraintLayout](https://developer.android.com/jetpack/compose/layouts/constraintlayout)
- [Material 3 Adaptive](https://developer.android.com/jetpack/compose/adaptive)
- [Material Icons Extended](https://developer.android.com/reference/kotlin/androidx/compose/material/icons/extended/package-summary)
- [Android SplashScreen](https://developer.android.com/develop/ui/views/launch/splash-screen)
- XML View System с ViewBindings
- [ViewBindingPropertyDelegate](https://github.com/androidbroadcast/ViewBindingPropertyDelegate)
- Фрагменты с поддержкой Compose (`androidx.fragment:fragment-ktx`)
- [Lottie](https://github.com/airbnb/lottie-android) — анимации

### 🗂️ Архитектура

- Модульная структура (features, core, build-logic)
- Presentation: Compose + XML
- Поддержка **адаптивного дизайна**:
    - Планшеты
    - Складные устройства (foldables)
    - Горизонтальная и вертикальная ориентация
- Мультиязычность: 🇷🇺 🇪🇸 🇺🇸 (ru, es, en)

### 🧬 Асинхронность

- Kotlin Coroutines
- Flows

### 📦 DI

- [Dagger Hilt](https://dagger.dev/hilt/)

### 🛰️ Сеть

- [Retrofit](https://square.github.io/retrofit/)
- [OkHttp](https://square.github.io/okhttp/)
- [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization)

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

- [kotlinx-datetime](https://github.com/Kotlin/kotlinx-datetime)

### 🎨 Code Style и анализ

- [Detekt](https://detekt.dev/)
- [Detekt Compose Rules](https://github.com/appKODE/detekt-rules-compose)

### ♻️ Дополнительно

- [Adapter Delegates](https://github.com/sockeqwe/AdapterDelegates) — для работы со списками и RecyclerView

