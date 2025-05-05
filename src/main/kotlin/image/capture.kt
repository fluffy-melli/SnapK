package dev.melli.image

import dev.melli.file.Config
import dev.melli.file.loadImage
import java.awt.Rectangle
import java.awt.Robot
import java.awt.image.BufferedImage
import java.awt.GraphicsEnvironment

fun display(screen: Map<Int, Rectangle>, select: Int): BufferedImage {
    val rect = screen[select] ?: Rectangle(0, 0, 1920, 1080)
    return Robot().createScreenCapture(rect)
}

fun allDisplays(screen: Map<Int, Rectangle>): BufferedImage {
    var width = 0
    var height = 0

    for (rect in screen) {
        width += rect.value.width
        if (height < rect.value.height + rect.value.y) {
            height = rect.value.height + rect.value.y
        }
    }

    val image = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val g = image.createGraphics()

    for (rect in screen) {
        g.drawImage(display(screen, rect.key), rect.value.x, rect.value.y, null)
    }

    g.dispose()

    return image
}

fun displayList(): Map<Int, Rectangle> {
    val ge = GraphicsEnvironment.getLocalGraphicsEnvironment()
    val screenDevices = ge.screenDevices

    val screen = mutableMapOf<Int, Rectangle>()

    for ((index, device) in screenDevices.withIndex()) {
        val bounds = device.defaultConfiguration.bounds
        screen[index] = bounds
    }

    return screen
}

fun capture(config: Config, screenShot: BufferedImage): BufferedImage {
    val image = BufferedImage(screenShot.width, screenShot.height, BufferedImage.TYPE_INT_ARGB)
    val g = image.createGraphics()

    g.drawImage(screenShot, 0, 0, null)

    for (rect in config.imagePlace) {
        val icon = resize(loadImage(rect.file), rect.width, rect.height)

        if (rect.x < 0) {
            rect.x = screenShot.width - rect.width + rect.x
        }

        if (rect.y < 0) {
            rect.y = screenShot.height - rect.height + rect.y
        }

        g.drawImage(icon, rect.x, rect.y, null)
    }

    g.dispose()

    return image
}