package com.dnadam.gigaman.entities.playerutils.playerstates

import com.badlogic.gdx.math.Vector2
import com.dnadam.gigaman.entities.GigaMan

class PlayerStandingState(player: GigaMan) : PlayerState(player) {

    init {
        player.acceleration.set(Vector2())
        player.velocity.set(Vector2())
    }

    override fun stand() {

    }

    override fun fire() {
        player.playerState = PlayerFireStandingState(player)
    }

    override fun holdFire() {
        player.playerState = PlayerStandingState(player)
    }
}