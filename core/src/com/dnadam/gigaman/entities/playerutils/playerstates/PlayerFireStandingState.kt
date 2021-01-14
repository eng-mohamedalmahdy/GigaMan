package com.dnadam.gigaman.entities.playerutils.playerstates

import com.dnadam.gigaman.entities.GigaMan

class PlayerFireStandingState constructor(player: GigaMan) : PlayerState(player) {

    override fun fire() {
        super.fire()

    }

    override fun stand() {

    }

    override fun holdFire() {
        player.playerState = PlayerStandingState(player)
    }

}