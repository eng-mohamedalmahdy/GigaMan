package com.dnadam.gigaman.entities.playerutils.playerstates

import com.dnadam.gigaman.entities.GigaMan
import com.dnadam.gigaman.util.constants.GigaMansConstants

class PlayerWalkingState(player: GigaMan, private val walkDirection: Int) : PlayerState(player) {

    init {
        walk(walkDirection)
    }

    override fun walk(walkDirection: Int) {
        player.velocity.x = walkDirection * GigaMansConstants.GIGA_MAN_MOVEMENT_SPEED
    }

    override fun fire() {
        player.playerState = PlayerFireWalkingState(player)
    }


    override fun holdFire() {}
}