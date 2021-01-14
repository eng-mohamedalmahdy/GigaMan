package com.dnadam.gigaman.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import com.dnadam.gigaman.util.constants.GameConstants
import java.io.File


class UtilFunctions {
    companion object {
        fun clearScreen(color: Color) {
            Gdx.gl.glClearColor(color.r, color.g, color.b, color.a)
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        }

        fun isKeyPressed(key: Int) = Gdx.input.isKeyPressed(key)

        fun log(vararg message: Any) = message.forEach { Gdx.app.log("STATE", it.toString()) }

        fun timeSinceInSec(time: Long): Float {
            return MathUtils.nanoToSec * (TimeUtils.nanoTime() - time)
        }

        fun drawTextureRegion(batch: SpriteBatch, region: TextureRegion, x: Float, y: Float) {
            batch.draw(
                    region.texture,
                    x,
                    y, 0f, 0f,
                    region.regionWidth.toFloat(),
                    region.regionHeight.toFloat(), 1f, 1f, 0f,
                    region.regionX,
                    region.regionY,
                    region.regionWidth,
                    region.regionHeight,
                    false,
                    false)
        }

        fun drawTextureRegion(batch: SpriteBatch, region: TextureRegion, position: Vector2, offset: Vector2) {
            drawTextureRegion(batch, region, position.x - offset.x, position.y - offset.y)
        }

        fun getOverlapType(box1: Rectangle, box2: Rectangle): String {
            val overlap = Intersector.overlaps(box1, box2)
            return when {
                overlap && box1.x >= box2.x -> "STF"
                overlap && box1.x < box2.x -> "FTS"
                !overlap -> "NA"
                else -> ""
            }
        }

        fun playSound(fileName: String, runOnCompletion: () -> Unit = {}) {
            val soundEnabled = Gdx.app.getPreferences(GameConstants.GAME_PREFERENCE).getBoolean(GameConstants.SOUND_ENABLED, true)
            if (soundEnabled) {
                Thread() {
                    val path = GameConstants.SOUNDS_DIR + File.separator + fileName + "." + "wav";
                    val file = Gdx.files.internal(path)
                    val sound = Gdx.audio.newMusic(file)
                    sound.setOnCompletionListener {
                        runOnCompletion()
                        sound.dispose()
                    }
                    sound.volume = .1f
                    sound.play()
                }.run()
            }
        }

    }
}