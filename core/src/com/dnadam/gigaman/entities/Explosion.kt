package com.dnadam.gigaman.entities


import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import com.dnadam.gigaman.util.Assets
import com.dnadam.gigaman.util.UtilFunctions
import com.dnadam.gigaman.util.constants.GameConstants


class Explosion(val id: Long, val numberOfFrames: Int, private val position: Vector2) {

    private val startTime: Long = TimeUtils.nanoTime()
    fun render(batch: SpriteBatch) {
        UtilFunctions.drawTextureRegion(
                batch,
                Assets.instance.explosionAssets.getExplosionByName(this).getKeyFrame(UtilFunctions.timeSinceInSec(startTime)),
                position,
                GameConstants.EXPLOSION_CENTER
        )
    }

    val isFinished: Boolean
        get() {
            val elapsedTime: Float = UtilFunctions.timeSinceInSec(startTime)
            return Assets.instance.explosionAssets.getExplosionByName(this).isAnimationFinished(elapsedTime)
        }

}