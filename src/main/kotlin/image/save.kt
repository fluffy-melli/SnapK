package dev.melli.image

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import java.io.File

fun save(image: BufferedImage, path: String) {
    val file = File(path)

    file.parentFile?.let {
        if (!it.exists()) {
            it.mkdirs()
        }
    }

    ImageIO.write(image, "png", file)
}