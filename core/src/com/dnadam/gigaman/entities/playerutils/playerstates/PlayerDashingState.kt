package com.dnadam.gigaman.entities.playerutils.playerstates

import com.badlogic.gdx.math.Vector2
import com.dnadam.gigaman.entities.Enemy
import com.dnadam.gigaman.entities.GigaMan
import com.dnadam.gigaman.util.UtilFunctions
import com.dnadam.gigaman.util.constants.GameConstants
import com.dnadam.gigaman.util.constants.GigaMansConstants

class PlayerDashingState(player: GigaMan, private val walkDirection: Int) : PlayerState(player) {
    init {
        UtilFunctions.playSound("dash")
        player.acceleration.x = walkDirection * GigaMansConstants.GIGA_MAN_MOVEMENT_SPEED * 4
    }

    override fun fire() {

    }

    override fun jump() {
    }
    override fun holdFire() {}

    override fun walk(walkDirection: Int) {}

    override fun stand() {
        player.acceleration.y=0f
        player.velocity.set(Vector2())
    }


    override fun fall() {

    }


    override fun dash(walkDirection: Int) {}


}