package dev.melli.file

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun loadImage(file: String): BufferedImage {
    try {
        return ImageIO.read(File(file))
    } catch (e: Exception) {
        return BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
    }
}