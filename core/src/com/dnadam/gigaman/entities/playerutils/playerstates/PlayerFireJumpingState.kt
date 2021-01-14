package com.dnadam.gigaman.entities.playerutils.playerstates

import com.badlogic.gdx.utils.TimeUtils
import com.dnadam.gigaman.entities.GigaMan
import com.dnadam.gigaman.util.constants.GigaMansConstants

class PlayerFireJumpingState(player: GigaMan) : PlayerState(player) {



    override fun jump() {

    }

    override fun walk(walkDirection: Int) {
        player.velocity.x = walkDirection * GigaMansConstants.GIGA_MAN_MOVEMENT_SPEED

    }


    override fun holdFire() {
        player.playerState = PlayerJumpingState(player, acceleration = 0f)
    }

    override fun dash(walkDirection: Int) {
        player.acceleration.y = 0f
        player.velocity.y = 0f
        super.dash(walkDirection)
    }
}