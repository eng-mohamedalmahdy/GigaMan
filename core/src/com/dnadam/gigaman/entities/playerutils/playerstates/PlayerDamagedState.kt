package com.dnadam.gigaman.entities.playerutils.playerstates

import com.dnadam.gigaman.entities.Enemy
import com.dnadam.gigaman.entities.GigaMan
import com.dnadam.gigaman.util.constants.GigaMansConstants

class PlayerDamagedState(player: GigaMan, enemy: Enemy) : PlayerState(player) {

    init {
        val dir = if (enemy.position.x > player.position.x) -1f else 1f
        player.acceleration.set(GigaMansConstants.KNOCK_BACK_ACCELERATION.scl(dir, 1f))
    }

    override fun walk(walkDirection: Int) {}

    override fun jump() {}

    override fun hit(enemy: Enemy) {}

    override fun fall() {}

    override fun land() {}

    override fun fire() {}

    override fun holdFire() {}

    override fun stand() {}

}