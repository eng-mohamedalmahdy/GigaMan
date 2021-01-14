package com.dnadam.gigaman.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.dnadam.gigaman.util.Assets
import com.badlogic.gdx.utils.Array


class Platform(var left: Float, var top: Float, width: Float, height: Float) : GameObject() {

    var bottom: Float = top - height
    var right: Float = left + width
    override var position: Vector2 = Vector2(left, top)
    override var box = Rectangle(left, top, width, height)
    override fun render(spriteBatch: SpriteBatch) {
        val width = right - left
        val height = top - bottom

        Assets.instance.platformsAssets.getPlatformAsset().draw(spriteBatch, left - 1, bottom - 1, width + 2, height + 2)
    }

    override fun update(delta: Float, platforms: Array<Platform>) {

    }


    override fun move(delta: Float) {
    }

}