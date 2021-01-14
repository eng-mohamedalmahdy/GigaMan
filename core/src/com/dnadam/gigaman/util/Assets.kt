package com.dnadam.gigaman.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.assets.AssetErrorListener
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.NinePatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.Disposable
import com.badlogic.gdx.utils.TimeUtils
import com.dnadam.gigaman.entities.Bullet
import com.dnadam.gigaman.entities.Enemy
import com.dnadam.gigaman.entities.Explosion
import com.dnadam.gigaman.entities.playerutils.playerstates.*
import com.dnadam.gigaman.util.constants.EnemiesConstants
import com.dnadam.gigaman.util.constants.GameConstants
import com.dnadam.gigaman.util.constants.GigaMansConstants

class Assets private constructor() : Disposable, AssetErrorListener {

    companion object {
        val TAG = Assets::class.java.name
        val instance = Assets()
    }

    lateinit var gigaGalAssets: GigaManAssets
    lateinit var platformsAssets: PlatformsAssets
    lateinit var enemiesAssets: EnemiesAssets
    lateinit var bulletAssets: BulletAssets
    lateinit var explosionAssets: ExplosionAssets
    lateinit var portalAssets: PortalAssets

    private lateinit var assetManager: AssetManager


    fun init() {
        assetManager = AssetManager()
        assetManager.setErrorListener(this)
        assetManager.load(GameConstants.TEXTURE_ATLAS, TextureAtlas::class.java)
        assetManager.finishLoading()
        val atlas: TextureAtlas = assetManager[GameConstants.TEXTURE_ATLAS]
        gigaGalAssets = GigaManAssets(atlas)
        platformsAssets = PlatformsAssets(atlas)
        enemiesAssets = EnemiesAssets(atlas)
        bulletAssets = BulletAssets(atlas)
        explosionAssets = ExplosionAssets(atlas)
        portalAssets = PortalAssets(atlas)
    }

    override fun dispose() {
        assetManager.dispose()
    }

    override fun error(asset: AssetDescriptor<*>?, throwable: Throwable?) {
        Gdx.app.error(TAG, "Couldn't load asset: " + asset!!.fileName, throwable)
    }


    class GigaManAssets(private val atlas: TextureAtlas) {


        private val walkingFrames = Array<TextureAtlas.AtlasRegion>()
        private var walkingAnimation: Animation<TextureRegion>

        private val dashingFrames = Array<TextureAtlas.AtlasRegion>()
        private var dashingAnimation: Animation<TextureRegion>


        private val standingFrames = Array<TextureAtlas.AtlasRegion>()
        private var standingAnimation: Animation<TextureRegion>

        private val jumpingFrames = Array<TextureAtlas.AtlasRegion>()
        private var jumpingAnimation: Animation<TextureRegion>


        private val fallingFrames = Array<TextureAtlas.AtlasRegion>()
        private var fallingAnimation: Animation<TextureRegion>


        private val damagedFrames = Array<TextureAtlas.AtlasRegion>()
        private var damagedAnimation: Animation<TextureRegion>


        private val fireStandingFrames = Array<TextureAtlas.AtlasRegion>()
        private var fireStandingAnimation: Animation<TextureRegion>


        private val fireWalkingFrames = Array<TextureAtlas.AtlasRegion>()
        private var fireWalkingAnimation: Animation<TextureRegion>


        private val fireJumpingFrames = Array<TextureAtlas.AtlasRegion>()
        private var fireJumpingAnimation: Animation<TextureRegion>


        private val fireFallingFrames = Array<TextureAtlas.AtlasRegion>()
        private var fireFallingAnimation: Animation<TextureRegion>

        fun getProperFrame(state: PlayerState, timeStart: Long): TextureRegion {

            val frame = MathUtils.nanoToSec * (TimeUtils.nanoTime() - timeStart)

            val animation: Animation<TextureRegion> = when (state) {
                is PlayerStandingState -> standingAnimation
                is PlayerWalkingState -> walkingAnimation
                is PlayerJumpingState -> jumpingAnimation
                is PlayerFallingState -> fallingAnimation
                is PlayerDashingState -> dashingAnimation
                is PlayerDamagedState -> damagedAnimation
                is PlayerFireStandingState -> fireStandingAnimation
                is PlayerFireWalkingState -> fireWalkingAnimation
                is PlayerFireJumpingState -> fireJumpingAnimation
                is PlayerFireFallingState -> fireFallingAnimation
                else -> standingAnimation
            }
            return animation.getKeyFrame(frame)
        }

        init {
            for (i in 0..14) {
                walkingFrames.add(TextureAtlas.AtlasRegion(atlas.findRegion(GigaMansConstants.PLAYER_NAME + GigaMansConstants.WALKING + i)))
            }
            walkingAnimation = Animation(GigaMansConstants.FRAME_DURATION, walkingFrames, Animation.PlayMode.LOOP_PINGPONG)

            for (i in 0..4) {
                standingFrames.add(TextureAtlas.AtlasRegion(atlas.findRegion(GigaMansConstants.PLAYER_NAME + GigaMansConstants.STANDING + i)))
            }
            standingAnimation = Animation(GigaMansConstants.FRAME_DURATION, standingFrames, Animation.PlayMode.LOOP_PINGPONG)

            for (i in 1..6) {
                jumpingFrames.add(TextureAtlas.AtlasRegion(atlas.findRegion(GigaMansConstants.PLAYER_NAME + GigaMansConstants.JUMPING + i)))
            }
            jumpingAnimation = Animation(GigaMansConstants.FRAME_DURATION / 2, jumpingFrames, Animation.PlayMode.LOOP_PINGPONG)

            for (i in 7..11) {
                fallingFrames.add(TextureAtlas.AtlasRegion(atlas.findRegion(GigaMansConstants.PLAYER_NAME + GigaMansConstants.JUMPING + i)))
            }
            fallingAnimation = Animation(GigaMansConstants.FRAME_DURATION, fallingFrames, Animation.PlayMode.NORMAL)

            for (i in 0..9) {
                damagedFrames.add(TextureAtlas.AtlasRegion(atlas.findRegion(GigaMansConstants.PLAYER_NAME + GigaMansConstants.DAMAGED + i)))
            }
            damagedAnimation = Animation(GigaMansConstants.FRAME_DURATION, damagedFrames, Animation.PlayMode.LOOP_PINGPONG)

            for (i in 0..1) {
                fireStandingFrames.add(TextureAtlas.AtlasRegion(atlas.findRegion(GigaMansConstants.PLAYER_NAME + GigaMansConstants.FIRE_STANDING + i)))
            }
            fireStandingAnimation = Animation(GigaMansConstants.FRAME_DURATION, fireStandingFrames, Animation.PlayMode.LOOP_PINGPONG)

            for (i in 2..15) {
                fireWalkingFrames.add(TextureAtlas.AtlasRegion(atlas.findRegion(GigaMansConstants.PLAYER_NAME + GigaMansConstants.FIRE_WALKING + i)))
            }
            fireWalkingAnimation = Animation(GigaMansConstants.FRAME_DURATION, fireWalkingFrames, Animation.PlayMode.LOOP_PINGPONG)

            for (i in 1..12) {
                fireJumpingFrames.add(TextureAtlas.AtlasRegion(atlas.findRegion(GigaMansConstants.PLAYER_NAME + GigaMansConstants.FIRE_JUMPING + i)))

            }
            fireJumpingAnimation = Animation(GigaMansConstants.FRAME_DURATION / 4, fireJumpingFrames, Animation.PlayMode.LOOP_PINGPONG)

            for (i in 1..2) {
                fireFallingFrames.add(TextureAtlas.AtlasRegion(atlas.findRegion(GigaMansConstants.PLAYER_NAME + GigaMansConstants.FIRE_FALLING + i)))
            }
            fireFallingAnimation = Animation(GigaMansConstants.FRAME_DURATION, fireFallingFrames, Animation.PlayMode.LOOP_PINGPONG)

            for (i in 1..5) {
                dashingFrames.add(TextureAtlas.AtlasRegion(atlas.findRegion(GigaMansConstants.PLAYER_NAME + GigaMansConstants.DASHING + i)))
            }
            dashingAnimation = Animation(GigaMansConstants.FRAME_DURATION, dashingFrames, Animation.PlayMode.LOOP_PINGPONG)
        }
    }

    class PlatformsAssets(private val atlas: TextureAtlas) {
        private val edge = GameConstants.NINE_PATCH_EDGE
        private val ninePatch = NinePatch(atlas.findRegion(GameConstants.PLATFORM_NAME), edge, edge, edge, edge)
        fun getPlatformAsset() = ninePatch


    }

    class EnemiesAssets(private val atlas: TextureAtlas) {
        fun getEnemyById(enemy: Enemy): TextureRegion {
            val frame = MathUtils.nanoToSec * (TimeUtils.nanoTime() - enemy.creationTime)
            val frames = Array<TextureAtlas.AtlasRegion>()
            for (i in 1..enemy.numberOfFrames) {
                frames.add(TextureAtlas.AtlasRegion(atlas.findRegion("${EnemiesConstants.ENEMY_NAME}${enemy.id}-$i")))
            }
            val animation: Animation<TextureRegion> = Animation(EnemiesConstants.FRAME_DURATION, frames, Animation.PlayMode.LOOP_PINGPONG)
            return animation.getKeyFrame(frame)

        }
    }

    class BulletAssets(private val atlas: TextureAtlas) {
        fun getBulletById(bullet: Bullet): TextureRegion {
            val frame = MathUtils.nanoToSec * (TimeUtils.nanoTime() - bullet.creationTime)
            val frames = Array<TextureAtlas.AtlasRegion>()
            for (i in 1..bullet.numberOfFrames) {
                frames.add(TextureAtlas.AtlasRegion(atlas.findRegion("${EnemiesConstants.BULLET_NAME}${bullet.id}-$i")))
            }
            val animation: Animation<TextureRegion> = Animation(EnemiesConstants.BULLET_FRAME_DURATION, frames, Animation.PlayMode.NORMAL)
            return animation.getKeyFrame(frame)
        }
    }

    class ExplosionAssets(private val atlas: TextureAtlas) {
        fun getExplosionByName(explosion: Explosion): Animation<TextureRegion> {
            val frames = Array<TextureAtlas.AtlasRegion>()
            for (i in 1..explosion.numberOfFrames) {
                frames.add(TextureAtlas.AtlasRegion(atlas.findRegion("${EnemiesConstants.EXPLOSION}${explosion.id}-$i")))
            }
            return Animation(EnemiesConstants.BULLET_FRAME_DURATION, frames, Animation.PlayMode.NORMAL)

        }
    }

    class PortalAssets(private val atlas: TextureAtlas) {
        private val frames = Array<TextureAtlas.AtlasRegion>()
        private val portalAnimation: Animation<TextureRegion>

        init {
            for (i in 1..4) {
                frames.add(TextureAtlas.AtlasRegion(atlas.findRegion("${GameConstants.PORTAL}-$i")))
            }
            portalAnimation = Animation(GigaMansConstants.FRAME_DURATION, frames, Animation.PlayMode.LOOP)
        }

        fun getProperFrame(startTime: Long): TextureRegion {
            return portalAnimation.getKeyFrame(UtilFunctions.timeSinceInSec(startTime))
        }
    }

}