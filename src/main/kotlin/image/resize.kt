package dev.melli.image

import java.awt.Image
import java.awt.image.BufferedImage

fun resize(image: BufferedImage, width: Int, height: Int): BufferedImage {
    val resizedImage: Image = image.getScaledInstance(width, height, Image.SCALE_SMOOTH)
    val bufferedResizedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    val g = bufferedResizedImage.createGraphics()
    g.drawImage(resizedImage, 0, 0, null)
    g.dispose()
    return bufferedResizedImage
}