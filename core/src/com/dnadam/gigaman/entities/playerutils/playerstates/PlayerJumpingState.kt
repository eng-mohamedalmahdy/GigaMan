package com.dnadam.gigaman.entities.playerutils.playerstates

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.TimeUtils
import com.dnadam.gigaman.entities.GigaMan
import com.dnadam.gigaman.util.UtilFunctions
import com.dnadam.gigaman.util.constants.GigaMansConstants

class PlayerJumpingState(player: GigaMan, val acceleration: Float = GigaMansConstants.JUMPING_POWER) : PlayerState(player) {

    init {
        if (acceleration != 0f) player.lastJumpStartTime = TimeUtils.nanoTime()
        player.acceleration.y += acceleration
        UtilFunctions.playSound("jump")
    }

    override fun jump() {

    }

    override fun fire() {
        player.playerState = PlayerFireJumpingState(player)
    }

    override fun walk(walkDirection: Int) {
        player.velocity.x = walkDirection * GigaMansConstants.GIGA_MAN_MOVEMENT_SPEED

    }

    override fun dash(walkDirection: Int) {
        player.acceleration.y = 0f
        player.velocity.y = 0f
        super.dash(walkDirection)
    }

    override fun holdFire() {
    }

}