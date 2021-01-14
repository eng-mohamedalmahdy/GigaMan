package com.dnadam.gigaman.entities

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.TimeUtils
import com.dnadam.gigaman.entities.playerutils.playerstates.*
import com.dnadam.gigaman.util.Assets
import com.dnadam.gigaman.util.UtilFunctions.Companion.isKeyPressed
import com.dnadam.gigaman.util.UtilFunctions.Companion.timeSinceInSec
import com.dnadam.gigaman.util.constants.GigaMansConstants
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.DelayedRemovalArray
import com.dnadam.gigaman.util.MobileController
import com.dnadam.gigaman.util.UtilFunctions
import com.dnadam.gigaman.util.UtilFunctions.Companion.log
import com.sun.org.apache.xpath.internal.operations.Bool

class GigaMan(override var position: Vector2) : GameObject() {


    private val KILL_PLANE = -100f
    var life = 10
    var score = 0
    var enabled = true
    private val assets = Assets.instance
    private val controller = MobileController.instance
    private var preventLeft = false
    private var preventRight = false
    private var lastPosition = position.cpy()
    private var fireTouched = false
    private var jumpTouched = false
    private var dashTouched = false
    var lookingLeft = false
    private val groundLevel = Vector2(0f, 0f)
    var currentGroundLevel = groundLevel
    val acceleration = Vector2()
    val velocity = Vector2()
    var lastJumpStartTime = -1L
    private var lastHitTime = -1L
    var lastFramePosition: Vector2 = Vector2(position)
    var playerState: PlayerState = PlayerStandingState(this)
    val bullets: DelayedRemovalArray<Bullet> = DelayedRemovalArray()
    override var box = Rectangle()

    companion object {
        val layer: Int = 1
    }

    init {
        assets.init()
        initListeners()
    }


    override fun update(delta: Float, platforms: Array<Platform>) {

        lastFramePosition.set(position)
        val preventResult = handlePlatformLanding(platforms)
        preventLeft = preventResult.first
        preventRight = preventResult.second

        if (enabled) move(delta)
        fixMove(delta, platforms)
        bullets.forEach { it.update(delta, platforms) }
    }


    private fun handlePlatformLanding(platforms: Array<Platform>): Pair<Boolean, Boolean> {
        var preventLeft: Boolean = false
        var preventRight: Boolean = false
        var landedOnce = false
        for (i in 0 until platforms.size) {
            if (landedOnPlatform(platforms[i])) {
                currentGroundLevel.y = platforms[i].top
                lastPosition = Vector2(position.x, platforms[i].top)
                landedOnce = true
            }
            if (touchLeftFoot(platforms[i])) {
                preventLeft = true
            }
            if (touchRightFoot(platforms[i])) {
                preventRight = true

            }
        }
        if (!landedOnce) currentGroundLevel.y = KILL_PLANE
        return Pair(preventLeft, preventRight)
    }

    override fun render(spriteBatch: SpriteBatch) {
        val time = if (playerState is PlayerStandingState) creationTime else playerState.animationStartTime
        val region: TextureRegion = Assets.instance.gigaGalAssets.getProperFrame(playerState, time)
        box = Rectangle(position.x, position.y, region.regionWidth.toFloat(), region.regionHeight.toFloat())
        spriteBatch.draw(
                region.texture,
                position.x,
                position.y, 0f, 0f,
                region.regionWidth.toFloat(),
                region.regionHeight.toFloat(), 1f, 1f, 0f,
                region.regionX,
                region.regionY,
                region.regionWidth,
                region.regionHeight,
                lookingLeft,
                false)
        bullets.forEach { it.render(spriteBatch) }
    }

    override fun move(delta: Float) {
        velocity.add(acceleration)
        position.add(velocity.scl(delta, delta))

        val walksLeft = isKeyPressed(Input.Keys.LEFT) || isKeyPressed(Input.Keys.A) || controller.touchPad.knobPercentX < 0
        val walksRight = isKeyPressed(Input.Keys.RIGHT) || isKeyPressed(Input.Keys.D) || controller.touchPad.knobPercentX > 0
        val jumpPressed = isKeyPressed(Input.Keys.SPACE) || jumpTouched
        val firing = isKeyPressed(Input.Keys.K) || fireTouched
        val dashing = (Gdx.input.isKeyJustPressed(Input.Keys.Z) || dashTouched) && playerState !is PlayerDashingState
        val jumpEnded = lastJumpStartTime != -1L && timeSinceInSec(lastJumpStartTime) >= GigaMansConstants.JUMP_DURATION && (playerState is PlayerJumpingState || playerState is PlayerFireJumpingState)
        val dashEnded = timeSinceInSec(playerState.animationStartTime) >= GigaMansConstants.GIGA_MAN_DASH_DURATION && playerState is PlayerDashingState
        val damageEnded = lastHitTime != -1L && timeSinceInSec(lastHitTime) >= GigaMansConstants.GIGA_MAN_DAMAGE_DURATION && playerState is PlayerDamagedState
        val standing = position.y <= currentGroundLevel.y
        val floating = position.y > currentGroundLevel.y && (playerState !is PlayerJumpingState && playerState !is PlayerFireJumpingState)
        val landing = position.y <= currentGroundLevel.y && (playerState is PlayerFallingState || playerState is PlayerFireFallingState)
        handleLooking(walksLeft, walksRight)

        if (dashing) {
            if ((lookingLeft && !preventLeft) || (!lookingLeft && !preventRight))
                playerState.dash(if (lookingLeft) -1 else 1)
        }
        if (firing) playerState.fire()
        else playerState.holdFire()

        if (walksLeft && (!preventLeft)) playerState.walk(-1)
        else if (walksRight && (!preventRight)) playerState.walk(1)
        else if (standing) playerState.stand()

        if (jumpPressed) playerState.jump()
        if (landing) playerState.land()
        if (jumpEnded || floating) playerState.fall()
        if (damageEnded) playerState.endDamage()
        if (dashEnded) playerState.endDash()
        if (standing && position.y == KILL_PLANE) {
            respawn()
        }

    }


    private fun handleLooking(walksLeft: Boolean, walksRight: Boolean) {
        if (walksLeft) lookingLeft = true
        else if (walksRight) lookingLeft = false
    }

    private fun footTouch(platform: Platform): Boolean {
        val leftFootIn: Boolean
        val rightFootIn: Boolean
        val straddle: Boolean


        val leftFoot: Float = position.x - GigaMansConstants.GIGA_MAN_STANCE_WIDTH / 2
        val rightFoot: Float = position.x + GigaMansConstants.GIGA_MAN_STANCE_WIDTH / 2

        leftFootIn = platform.left < leftFoot && platform.right > leftFoot
        rightFootIn = platform.left < rightFoot && platform.right > rightFoot

        straddle = platform.left > leftFoot && platform.right < rightFoot


        return (leftFootIn || rightFootIn || straddle)

    }

    private fun landedOnPlatform(platform: Platform): Boolean {

        return footTouch(platform) && (box.y >= platform.top)
    }

    private fun touchLeftFoot(platform: Platform): Boolean {
        return position.x in platform.left..platform.right && !landedOnPlatform(platform) && position.y < platform.top && position.y > platform.bottom
    }

    private fun touchRightFoot(platform: Platform): Boolean {
        return position.x + box.width in platform.left..platform.right && !landedOnPlatform(platform) && position.y < platform.top && position.y > platform.bottom

    }

    private fun fixMove(delta: Float, platforms: Array<Platform>) {
        for (platform in platforms) {
            if (position.x > platform.left && position.x < platform.right && position.y < platform.top && position.y > platform.bottom) {

                if (touchRightFoot(platform)) {
                    position.x = platform.left - GigaMansConstants.GIGA_MAN_STANCE_WIDTH
                    acceleration.x = 0f
                    velocity.x = 0f
                }
                if (touchLeftFoot(platform)) {
                    if (!lookingLeft) {


                    } else {
                        position.x = platform.right

                    }
                    acceleration.x = 0f
                    velocity.x = 0f
                    break
                }
            }
        }
    }

    fun hit(enemy: Enemy) {
        if (playerState !is PlayerDamagedState) {
            lastHitTime = TimeUtils.nanoTime()
            playerState.hit(enemy)
            life--
        }
    }

    private fun initListeners() {
        controller.fireImage.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                fireTouched = true
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                fireTouched = false
            }

        })
        controller.dashImage.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                dashTouched = true
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                dashTouched = false
            }

        })
        controller.jumpImage.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                jumpTouched = true
                return true
            }

            override fun touchUp(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int) {
                jumpTouched = false
            }

        })
    }

    private fun respawn() {
        life--
        position = lastPosition.cpy()
    }


}