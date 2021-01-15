package com.dnadam.gigaman.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.dnadam.gigaman.util.InputListeners.Companion.inputMultiplexer
import com.dnadam.gigaman.util.LevelLoader
import com.dnadam.gigaman.util.UtilFunctions
import com.dnadam.gigaman.util.constants.GameConstants
import com.dnadam.gigaman.util.constants.LevelLoadingConstants
import java.io.File


class MainMenuScreen(val game: Game) : ScreenAdapter() {
    private val camera: OrthographicCamera = OrthographicCamera()
    private val viewport = StretchViewport(GameConstants.WORLD_SIZE * 1.5f, GameConstants.WORLD_SIZE * 1.5f, camera)
    private val stage: Stage = Stage(viewport)
    private val path = LevelLoadingConstants.SKIN_DIR + File.separator + "neon-ui.json";
    private val skin = Skin(Gdx.files.internal(path))

    private val menuTable = Table()
    private val startGame: TextButton = TextButton("New Game", skin)
    private val continueGame: TextButton = TextButton("Continue", skin)
    private val sound: TextButton = TextButton("Sound: ON", skin)
    private val exitGame: TextButton = TextButton("Exit", skin)

    private val texture: Texture
    private val image: Image

    init {
        inputMultiplexer.addProcessor(stage)
        initComponents()
        initListeners()
        val soundEnabled = Gdx.app.getPreferences(GameConstants.GAME_PREFERENCE).getBoolean(GameConstants.SOUND_ENABLED, true)
        sound.setText("Sound: ${if (soundEnabled) "ON" else "OFF"}")

        texture = Texture(Gdx.files.internal("images/main-screen.gif"))
        texture.setFilter(TextureFilter.Linear, TextureFilter.Linear)
        val region = TextureRegion(texture, 0, 0, 800, 420)

        image = Image(texture)


    }


    private fun initComponents() {
        menuTable.add(startGame).size(300f, 60f)
        menuTable.row()
        menuTable.add(continueGame).size(300f, 60f)
        menuTable.row()
        menuTable.add(sound).size(300f, 60f)
        menuTable.row()
        menuTable.add(exitGame).size(300f, 60f)
        menuTable.setFillParent(true)
    }

    private fun initListeners() {
        startGame.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                game.screen = GamePlayScreen(LevelLoader.loadLevel("level1", game))
                dispose()
                return true
            }
        })

        continueGame.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                val currentLevel = Gdx.app.getPreferences(GameConstants.GAME_PREFERENCE).getString(LevelLoadingConstants.LEVELS_PREFERENCE, "level1")
                game.screen = GamePlayScreen(LevelLoader.loadLevel(currentLevel, game))
                dispose()
                return true
            }
        })

        sound.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                var soundEnabled = Gdx.app.getPreferences(GameConstants.GAME_PREFERENCE).getBoolean(GameConstants.SOUND_ENABLED, true)
                soundEnabled = !soundEnabled
                Gdx.app.getPreferences(GameConstants.GAME_PREFERENCE).putBoolean(GameConstants.SOUND_ENABLED, soundEnabled).flush()
                sound.setText("Sound: ${if (soundEnabled) "ON" else "OFF"}")

                return true
            }
        })


        exitGame.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                Gdx.app.exit()
                return true
            }
        })
    }

    override fun render(delta: Float) {
        stage.addActor(menuTable)
        UtilFunctions.clearScreen(Color.BLUE)
        stage.act()
        stage.batch.begin();
        stage.batch.draw(texture, 0f, 0f, GameConstants.WORLD_SIZE * 1.5f, GameConstants.WORLD_SIZE * 1.5f);
        stage.batch.end()
        stage.draw()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height)
    }

    override fun dispose() {
        stage.dispose()
        super.dispose()
    }
}