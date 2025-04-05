package io.wookoo.network

inline fun <reified T : Comparable<T>> anyTestData(): T {
    return when (T::class) {
        Double::class -> 0.0 as T
        Long::class -> 0L as  T
        String::class -> "test" as T
        else -> throw IllegalArgumentException("Unsupported type: ${T::class}")
    }
}
