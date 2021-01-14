package com.dnadam.gigaman.entities

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Shape2D
import com.badlogic.gdx.math.Vector2
import com.dnadam.gigaman.util.Assets
import com.dnadam.gigaman.util.constants.EnemiesConstants
import com.badlogic.gdx.utils.Array


class Enemy(
        val id: Long?,
        val numberOfFrames: Int,
        override var position: Vector2,
        private val patrol: Pair<Vector2, Vector2> = Pair(Vector2(), Vector2()),
        var life: Int = 5
) : GameObject() {

    private var lookingLeft = false
    private var accelration = Vector2()
    private var velocity = Vector2(EnemiesConstants.ENEMY_VELOCITY)

    override var box = Rectangle()

    companion object {
        val LAYER: Int = 2
    }


    override fun update(delta: Float, platforms: Array<Platform>) {

        move(delta)
    }

    override fun render(spriteBatch: SpriteBatch) {

        val region = Assets.instance.enemiesAssets.getEnemyById(this)
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
                lookingLeft,
                false)
    }

    override fun move(delta: Float) {

        if (patrol.first.x != patrol.second.x) {
            if (lookingLeft) position.sub(velocity.x * delta, velocity.y * delta)
            else
                position.add(velocity.x * delta, velocity.y * delta)

            if (!lookingLeft && position.x >= patrol.second.x && position.y >= patrol.second.y) lookingLeft = true
            if (lookingLeft && position.x <= patrol.first.x && position.y <= patrol.first.y) lookingLeft = false

        }

    }
}