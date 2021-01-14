package com.dnadam.gigaman.entities.playerutils.playerstates

import com.dnadam.gigaman.entities.GigaMan
import com.dnadam.gigaman.util.constants.GigaMansConstants

class PlayerFireWalkingState(player: GigaMan) : PlayerState(player) {

    override fun fire() {
        super.fire()

    }

    override fun holdFire() {
        player.playerState = PlayerStandingState(player)
    }

    override fun walk(walkDirection: Int) {
        player.velocity.x = walkDirection * GigaMansConstants.GIGA_MAN_MOVEMENT_SPEED
    }

}