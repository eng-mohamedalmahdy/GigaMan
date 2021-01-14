package com.dnadam.gigaman.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.dnadam.gigaman.util.Assets
import com.badlogic.gdx.utils.Array
import com.dnadam.gigaman.util.UtilFunctions
import com.dnadam.gigaman.util.constants.GameConstants
import com.dnadam.gigaman.util.constants.LevelLoadingConstants
import java.io.File


class Bullet(
        val id: Long,
        val numberOfFrames: Int,
        private val source: GameObject,
        private val lookingLeft: Boolean,
        weaponHeightPercentageLocation: Float,
        val targetLayer: Int,
        val attackSpeed: Float = .25f,
        val velocity: Vector2 = Vector2(5f, 0f)
) : GameObject() {
    companion object {
        val layer: Int = 3
    }

    override var box = Rectangle()
    override var position = Vector2()
    val lifeTime: Float = 1f

    init {
        val x = source.box.x + (if (!lookingLeft) source.box.width / 2 + 7f else 0f)
        val y = source.box.y + source.box.height * weaponHeightPercentageLocation
        position = Vector2(x, y)

    }

    override fun update(delta: Float, platforms: Array<Platform>) {
        move(delta)
    }

    override fun render(spriteBatch: SpriteBatch) {
        val bulletRegion = Assets.instance.bulletAssets.getBulletById(this)
        box = Rectangle(position.x, position.y, bulletRegion.regionWidth.toFloat(), bulletRegion.regionHeight.toFloat())
        spriteBatch.draw(
                bulletRegion.texture,
                position.x,
                position.y, 0f, 0f,
                bulletRegion.regionWidth.toFloat(),
                bulletRegion.regionHeight.toFloat(), 1f, 1f, 0f,
                bulletRegion.regionX,
                bulletRegion.regionY,
                bulletRegion.regionWidth,
                bulletRegion.regionHeight,
                lookingLeft,
                false)
    }


    override fun move(delta: Float) {
        position.x += velocity.x * if (lookingLeft) -1 else 1
    }

    fun playFireSound(fileName: String) {
        UtilFunctions.playSound(fileName)
    }

}