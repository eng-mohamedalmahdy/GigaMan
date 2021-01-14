package com.dnadam.gigaman.entities.playerutils.playerstates

import com.badlogic.gdx.math.Vector2
import com.dnadam.gigaman.entities.GigaMan
import com.dnadam.gigaman.util.constants.GameConstants
import com.dnadam.gigaman.util.constants.GigaMansConstants

class PlayerFireFallingState(player: GigaMan) : PlayerState(player) {
    init {
        player.acceleration.y = GameConstants.GRAVITY.y
    }

    override fun fall() {
    }

    override fun jump() {

    }

    override fun fire() {
        super.fire()

    }

    override fun holdFire() {
        player.playerState = PlayerFallingState(player)
    }

    override fun stand() {
        player.acceleration.set(Vector2())
        player.velocity.set(Vector2())
        player.position.y = player.currentGroundLevel.y - 1
        super.stand()
    }

    override fun walk(walkDirection: Int) {
        player.velocity.x = walkDirection * GigaMansConstants.GIGA_MAN_MOVEMENT_SPEED
    }
}