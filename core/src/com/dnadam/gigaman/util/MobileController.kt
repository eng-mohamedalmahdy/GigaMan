package com.dnadam.gigaman.util

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.dnadam.gigaman.util.InputListeners.Companion.inputMultiplexer
import com.dnadam.gigaman.util.constants.GameConstants
import com.dnadam.gigaman.util.constants.LevelLoadingConstants
import java.io.File


class MobileController private constructor() {

    private val camera: OrthographicCamera = OrthographicCamera()
    private val viewport = ExtendViewport(GameConstants.WORLD_SIZE * 1.5f, GameConstants.WORLD_SIZE * 1.5f, camera)
    private val stage = Stage(viewport)
    private val path = LevelLoadingConstants.SKIN_DIR + File.separator + "neon-ui.json";
    private val skin = Skin(Gdx.files.internal(path))
    private var assetManager: AssetManager = AssetManager()
    private val movementTable = Table()
    private val actionsTable = Table()


    val touchPad = Touchpad(50f, skin)

     var jumpImage: Image
     var dashImage: Image
     var fireImage: Image

    private val factor: Float = 3f

    init {
        assetManager.load(GameConstants.TEXTURE_ATLAS, TextureAtlas::class.java)
        assetManager.finishLoading()
        val atlas: TextureAtlas = assetManager[GameConstants.TEXTURE_ATLAS]


        fireImage = Image(TextureRegion(atlas.findRegion("fire-btn")))
        dashImage = Image(TextureRegion(atlas.findRegion("dash-btn")))
        jumpImage = Image(TextureRegion(atlas.findRegion("jump-btn")))

        fireImage.setSize(GameConstants.WORLD_SIZE / factor,GameConstants.WORLD_SIZE / factor)
        dashImage.setSize(GameConstants.WORLD_SIZE / factor,GameConstants.WORLD_SIZE / factor)
        jumpImage.setSize(GameConstants.WORLD_SIZE / factor,GameConstants.WORLD_SIZE / factor)

        inputMultiplexer.addProcessor(stage)

        setMovementTable()
        setActionsTable()

        stage.addActor(movementTable)
        stage.addActor(actionsTable)

    }

    private fun setMovementTable() {

        movementTable.setFillParent(true)
        movementTable.bottom().left()
        movementTable.pad(20f)
        movementTable.add(touchPad)
    }

    private fun setActionsTable() {
        actionsTable.setFillParent(true)
        actionsTable.pad(10f)
        actionsTable.bottom().right()
        actionsTable.add()
        actionsTable.add(fireImage).size(GameConstants.WORLD_SIZE / factor)
        actionsTable.add()
        actionsTable.row()
        actionsTable.add(jumpImage).size(GameConstants.WORLD_SIZE / factor)
        actionsTable.add()
        actionsTable.add(dashImage).size(GameConstants.WORLD_SIZE / factor)

    }



    fun render(delta: Float) {
        stage.act(delta)
        stage.draw()
    }

    companion object {
        val instance = MobileController()
    }
}