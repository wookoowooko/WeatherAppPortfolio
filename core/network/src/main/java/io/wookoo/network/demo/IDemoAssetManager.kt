package io.wookoo.network.demo

import java.io.InputStream

fun interface IDemoAssetManager {
    fun open(fileName: String): InputStream
}