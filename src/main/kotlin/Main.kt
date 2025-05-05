package dev.melli

import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.NativeHookException
import com.github.kwhat.jnativehook.GlobalScreen
import java.time.format.DateTimeFormatter
import java.awt.image.BufferedImage
import dev.melli.image.displayList
import dev.melli.image.allDisplays
import dev.melli.file.loadConfig
import dev.melli.image.clipboard
import java.time.LocalDateTime
import dev.melli.image.display
import dev.melli.image.capture
import dev.melli.image.save

fun init() {
    val list = displayList()
    val config = loadConfig()
    println("You can take a screenshot by pressing '${config.key}'")
    try {
        GlobalScreen.registerNativeHook()
        GlobalScreen.addNativeKeyListener(object : NativeKeyListener {
            override fun nativeKeyPressed(event: NativeKeyEvent) {
                if (NativeKeyEvent.getKeyText(event.keyCode) == config.key) {
                    var screenShot: BufferedImage

                    if (config.display < 1) {
                        screenShot = allDisplays(list)
                    } else {
                        screenShot = display(list, config.display - 1)
                    }

                    val image = capture(config, screenShot)
                    if (config.clipboard) {
                        clipboard(image)
                    }

                    if (config.fileSave) {
                        val now = LocalDateTime.now()
                        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")
                        save(image, config.fileOutput+"\\"+now.format(formatter)+".png")
                    }
                }
            }
        })
    } catch (e: NativeHookException) {
        e.printStackTrace()
    }
}

fun main() {
    init()
}