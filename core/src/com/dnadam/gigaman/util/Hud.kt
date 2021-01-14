package com.dnadam.gigaman.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Colors
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.utils.viewport.FitViewport
import com.dnadam.gigaman.util.constants.GameConstants
import com.dnadam.gigaman.util.constants.LevelLoadingConstants
import java.io.File

class Hud(private val spriteBatch: SpriteBatch) {
    val hudCam: OrthographicCamera = OrthographicCamera()
    private val hudViewport: FitViewport = FitViewport(GameConstants.WORLD_SIZE, GameConstants.WORLD_SIZE, hudCam)

    private val path = LevelLoadingConstants.SKIN_DIR + File.separator + "neon-ui.json";
    private val skin = Skin(Gdx.files.internal(path))
    private val scoreLabel: Label = Label("Score:", skin)
    private val lifeLabel: Label = Label("Life: ", skin)
    private val hudTable = Table()

    val stage = Stage(hudViewport, spriteBatch)

    init {

        scoreLabel.color = Color.BLACK
        lifeLabel.color = Color.BLACK

        hudTable.top()
        hudTable.setFillParent(true)
        hudTable.add(scoreLabel).expandX().padTop(10f)
        hudTable.add().expandX().padTop(10f)
        hudTable.add().expandX().padTop(10f)
        hudTable.add(lifeLabel).expandX().padTop(10f)
        stage.addActor(hudTable)
    }

    fun setScore(score: Int) {
        scoreLabel.setText("Score: $score")
    }


    fun setLife(life: Int) {
        lifeLabel.setText("Life: $life")

    }
}