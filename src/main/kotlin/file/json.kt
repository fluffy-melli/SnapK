package dev.melli.file

import java.io.File
import com.google.gson.GsonBuilder

data class ImagePlace(
    val file: String,

    val width:Int,
    val height:Int,

    var x: Int,
    var y: Int
)

data class Config(
    val key: String,
    val display: Int,
    val clipboard: Boolean,
    val fileSave: Boolean,
    val fileOutput: String,
    val imagePlace: List<ImagePlace>
)

fun default(): Config {
    return Config(
        key = "F12",
        display = 0,
        clipboard = true,
        fileSave = true,
        fileOutput = "./output",
        imagePlace = listOf(
            ImagePlace(
                file = "./example.png",
                width = 200,
                height = 200,
                x = -28,
                y = -28
            )
        )
    )
}

fun loadConfig(): Config {
    val configFile = File("./config.json")
    val gson = GsonBuilder().setPrettyPrinting().create()

    if (!configFile.exists()) {
        val default = default()
        configFile.writeText(gson.toJson(default))
        return default
    }

    val json = configFile.readText()
    return gson.fromJson(json, Config::class.java)
}