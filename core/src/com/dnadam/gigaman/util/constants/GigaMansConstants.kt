package com.dnadam.gigaman.util.constants

import com.badlogic.gdx.math.Vector2

class GigaMansConstants {
    companion object {

        const val PLAYER_NAME = "MM"
        const val STANDING = "standing"
        const val WALKING = "walking"
        const val JUMPING = "jumping"
        const val DASHING: String = "dashing"
        const val DAMAGED: String = "damaged"
        const val FIRE_STANDING: String = "firestanding"
        const val FIRE_WALKING: String = "firewalking"
        const val FIRE_JUMPING: String = "firejumping"
        const val FIRE_FALLING: String = "firefalling"

        val GIGA_MANS_EYE_POSITION: Vector2 = Vector2(16f, 24f)
        const val GIGA_MAN_MOVEMENT_SPEED: Float = (GameConstants.WORLD_SIZE * .4).toFloat()
        val KNOCK_BACK_ACCELERATION: Vector2 = Vector2((.25 * GameConstants.WORLD_SIZE).toFloat(), (.25 * GameConstants.WORLD_SIZE).toFloat())
        const val FRAME_DURATION = .1f
        const val JUMPING_POWER: Float = (1.5 * GameConstants.WORLD_SIZE).toFloat()
        const val JUMP_DURATION = FRAME_DURATION * 3
        const val GIGA_MAN_EYE_HEIGHT = 16.0f
        const val GIGA_MAN_STANCE_WIDTH = 35f
        const val GIGA_MAN_DAMAGE_DURATION = FRAME_DURATION * 4
        const val GIGA_MAN_DASH_DURATION = FRAME_DURATION * 4

    }
}