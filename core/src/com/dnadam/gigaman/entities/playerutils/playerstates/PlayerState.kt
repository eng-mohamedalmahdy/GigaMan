package com.dnadam.gigaman.entities.playerutils.playerstates

import com.badlogic.gdx.utils.TimeUtils
import com.dnadam.gigaman.entities.Bullet
import com.dnadam.gigaman.entities.Enemy
import com.dnadam.gigaman.entities.GigaMan
import com.dnadam.gigaman.util.UtilFunctions
import com.dnadam.gigaman.util.constants.GameConstants

abstract class PlayerState(val player: GigaMan) {
    val animationStartTime: Long = TimeUtils.nanoTime()

    open fun fall() {
        player.playerState = PlayerFallingState(player)
    }

    open fun stand() {
        player.playerState = PlayerStandingState(player)
    }

    open fun walk(walkDirection: Int) {
        player.playerState = PlayerWalkingState(player, walkDirection)
    }

    open fun jump() {
        player.playerState = PlayerJumpingState(player)
    }

    open fun dash(walkDirection: Int) {
        player.playerState = PlayerDashingState(player, walkDirection)
    }

    open fun endDash() {
        player.acceleration.x = 0f
        player.acceleration.y = GameConstants.GRAVITY.y
        player.playerState = PlayerFallingState(player)

    }

    open fun land() {
        UtilFunctions.playSound("land")
        player.acceleration.y = 0f
        player.velocity.y = 0f
        player.position.y = player.currentGroundLevel.y
        player.playerState = PlayerStandingState(player)
    }

    open fun hit(enemy: Enemy) {
        player.playerState = PlayerDamagedState(player, enemy)
        UtilFunctions.playSound("hurt")
    }

    open fun endDamage() {
        player.playerState = PlayerFallingState(player)
    }

    open fun fire() {
        if (player.bullets.isEmpty || UtilFunctions.timeSinceInSec(player.bullets.last().creationTime) > player.bullets[0].attackSpeed) {
            val newBullet = Bullet(1, 7, player, player.lookingLeft, .5f, targetLayer = Enemy.LAYER)
            newBullet.playFireSound("regular-shot")
            player.bullets.add(newBullet)
        }
    }

    abstract fun holdFire()

}