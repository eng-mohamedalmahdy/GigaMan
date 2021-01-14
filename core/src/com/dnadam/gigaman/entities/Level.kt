package com.dnadam.gigaman.entities

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.DelayedRemovalArray
import com.dnadam.gigaman.screens.GameOverScreen
import com.dnadam.gigaman.screens.GamePlayScreen
import com.dnadam.gigaman.util.LevelLoader
import com.dnadam.gigaman.util.UtilFunctions
import com.google.gson.annotations.SerializedName

open class Level(val game: Game,
                 val levelName: String,
                 var gigaMan: GigaMan,
                 var enemies: DelayedRemovalArray<Enemy>,
                 var platforms: Array<Platform>,
                 val endPortal: Portal) {


    private val explosions = DelayedRemovalArray<Explosion>()
    open fun update(delta: Float) {
        gigaMan.update(delta, platforms)

        if (gigaMan.life <= 0) game.screen = GameOverScreen(game)


        gigaMan.bullets.begin()
        gigaMan.bullets.forEach {
            if (it.lifeTime < UtilFunctions.timeSinceInSec(it.creationTime)) {
                gigaMan.bullets.removeValue(it, false);

            }
        }
        enemies.begin()
        gigaMan.bullets.forEach { bullet ->
            enemies.forEach { enemy ->
                val col = Intersector.overlaps(enemy.box, bullet.box)
                if (col && bullet.targetLayer == Enemy.LAYER) {
                    enemy.life--
                    if (enemy.life == 0) {
                        enemies.removeValue(enemy, false)
                        gigaMan.score += 100
                        explosions.add(Explosion(0, 6, Vector2(enemy.position.x, enemy.position.y + enemy.box.height / 2)))
                        UtilFunctions.playSound("enemy-die-${enemy.id}")
                    }
                    gigaMan.bullets.removeValue(bullet, false)
                    explosions.add(Explosion(1, 4, bullet.position))
                }
            }

        }
        enemies.end()
        gigaMan.bullets.end()


        explosions.begin()
        explosions.forEach { if (it.isFinished) explosions.removeValue(it, false) }
        explosions.end()
        platforms.forEach {
            it.update(delta, platforms)
        }
        enemies.forEach {
            it.update(delta, platforms)
            val col = Intersector.overlaps(it.box as Rectangle, gigaMan.box)
            if (col) gigaMan.hit(it)
        }

        endPortal.update(delta, platforms)
        if (gigaMan.box.overlaps(endPortal.box)) {
            gigaMan.enabled = false
            UtilFunctions.playSound("level-end") {
                game.screen = GamePlayScreen(LevelLoader.getNextLevel(this, game))
                gigaMan.enabled = true
            }

        }
    }

    open fun render(spriteBatch: SpriteBatch) {
        gigaMan.render(spriteBatch)
        platforms.forEach {
            it.render(spriteBatch)
        }
        enemies.forEach { it.render(spriteBatch) }
        explosions.forEach { it.render(spriteBatch) }
        endPortal.render(spriteBatch)

    }
}