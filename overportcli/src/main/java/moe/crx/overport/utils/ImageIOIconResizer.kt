package moe.crx.overport.utils

import moe.crx.overport.patches.IconResizer
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

object ImageIOIconResizer : IconResizer {
    override fun resize(bytes: ByteArray, width: Int, height: Int): ByteArray {
        val original = ImageIO.read(ByteArrayInputStream(bytes))
        val resized = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)

        resized.createGraphics().apply {
            setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC)
            setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
            setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

            drawImage(original, 0, 0, width, height, null)
            dispose()
        }

        val baos = ByteArrayOutputStream()
        ImageIO.write(resized, "png", baos)

        return baos.toByteArray()
    }
}