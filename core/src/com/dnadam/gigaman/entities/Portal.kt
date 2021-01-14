package com.dnadam.gigaman.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.dnadam.gigaman.util.Assets

class Portal(override var position: Vector2) : GameObject() {
    override var box: Rectangle = Rectangle()


    override fun update(delta: Float, platforms: Array<Platform>) {
    }

    override fun render(spriteBatch: SpriteBatch) {

        val region = Assets.instance.portalAssets.getProperFrame(creationTime);
        box = Rectangle(position.x, position.y, region.regionWidth.toFloat(), region.regionHeight.toFloat())
        spriteBatch.draw(
                region.texture,
                position.x,
                position.y, 0f, 0f,
                region.regionWidth.toFloat(),
                region.regionHeight.toFloat(), 1f, 1f, 0f,
                region.regionX,
                region.regionY,
                region.regionWidth,
                region.regionHeight,
                false,
                false)
    }

    override fun move(delta: Float) {
    }
}