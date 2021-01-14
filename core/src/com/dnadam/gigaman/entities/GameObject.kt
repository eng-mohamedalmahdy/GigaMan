package com.dnadam.gigaman.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import com.badlogic.gdx.utils.Array
import com.google.gson.annotations.SerializedName


abstract class GameObject {

    val TAG: String = this::class.java.name
    abstract var box: Rectangle


    abstract var position: Vector2


    companion object {
        open val layer: Int = -1
    }

    val creationTime = TimeUtils.nanoTime()

    abstract fun update(delta: Float, platforms: Array<Platform>)

    abstract fun render(spriteBatch: SpriteBatch)

    abstract fun move(delta: Float)
}