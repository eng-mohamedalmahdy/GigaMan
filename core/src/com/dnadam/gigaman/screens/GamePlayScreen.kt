package com.dnadam.gigaman.screens

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.maps.MapRenderer
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import com.dnadam.gigaman.entities.Level
import com.dnadam.gigaman.util.*
import com.dnadam.gigaman.util.constants.GameConstants


class GamePlayScreen(private val level: Level) : ScreenAdapter() {

    private lateinit var assets: Assets
    private lateinit var spriteBatch: SpriteBatch
    private lateinit var extendViewport: ExtendViewport
    private lateinit var cam: ChaseCam
    private lateinit var mobileController: MobileController
    private lateinit var hud: Hud

    override fun show() {
        assets = Assets.instance
        assets.init()
        spriteBatch = SpriteBatch()
        extendViewport = ExtendViewport(GameConstants.WORLD_SIZE, GameConstants.WORLD_SIZE)
        cam = ChaseCam(extendViewport.camera, level.gigaMan)
        hud = Hud(spriteBatch)
        mobileController = MobileController.instance

    }


    override fun resize(width: Int, height: Int) {
        extendViewport.update(width, height, true)
    }

    override fun dispose() {

        assets.dispose()

        spriteBatch.dispose()
    }

    override fun render(delta: Float) {
        level.update(delta)
        hud.setLife(level.gigaMan.life)
        hud.setScore(level.gigaMan.score)

        cam.update()

        extendViewport.apply()

        UtilFunctions.clearScreen(GameConstants.BACKGROUND_COLOR)


        spriteBatch.projectionMatrix = extendViewport.camera.combined

        spriteBatch.begin()

        level.render(spriteBatch)

        spriteBatch.end()

        spriteBatch.projectionMatrix = hud.hudCam.combined
        hud.stage.draw()

        if ((Gdx.app.type == Application.ApplicationType.Android) || (Gdx.app.type == Application.ApplicationType.iOS))
            mobileController.render(delta)

    }


}