package com.dnadam.gigaman.screens

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.InputListener
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.dnadam.gigaman.util.InputListeners
import com.dnadam.gigaman.util.LevelLoader
import com.dnadam.gigaman.util.UtilFunctions
import com.dnadam.gigaman.util.constants.GameConstants
import com.dnadam.gigaman.util.constants.LevelLoadingConstants
import java.io.File

class GameOverScreen(val game: Game) : ScreenAdapter() {

    private val camera: OrthographicCamera = OrthographicCamera()
    private val viewport = StretchViewport(GameConstants.WORLD_SIZE * 1.5f, GameConstants.WORLD_SIZE * 1.5f, camera)
    private val stage: Stage = Stage(viewport)
    private val path = LevelLoadingConstants.SKIN_DIR + File.separator + "neon-ui.json";
    private val skin = Skin(Gdx.files.internal(path))

    private val menuTable = Table()

    private val gameOver = Label("Game Over", skin)
    private val continueLabel = Label("Continue", skin)
    private val yes = TextButton("YES", skin)
    private val no = TextButton("NO", skin)

    private lateinit var texture: Texture
    private lateinit var image: Image

    init {
        InputListeners.inputMultiplexer.addProcessor(stage)
        initComponents()
        initListeners()


    }


    private fun initComponents() {
        texture = Texture(Gdx.files.internal("images/main-screen.gif"))
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear)
        val region = TextureRegion(texture, 0, 0, 800, 420)


        gameOver.setFontScale(2f)
        image = Image(texture)

        menuTable.add(gameOver)

        menuTable.row()
        menuTable.add(yes).size(200f, 50f)
        menuTable.row()
        menuTable.add(no).size(200f, 50f)

        menuTable.setFillParent(true)
    }

    private fun initListeners() {
        yes.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                val currentLevel = Gdx.app.getPreferences(GameConstants.GAME_PREFERENCE).getString(LevelLoadingConstants.LEVELS_PREFERENCE, "level1")
                game.screen = GamePlayScreen(LevelLoader.loadLevel(currentLevel, game))
                return true
            }
        })

        no.addListener(object : InputListener() {
            override fun touchDown(event: InputEvent?, x: Float, y: Float, pointer: Int, button: Int): Boolean {
                val currentLevel = Gdx.app.getPreferences(GameConstants.GAME_PREFERENCE).getString(LevelLoadingConstants.LEVELS_PREFERENCE, "level1")
                game.screen = MainMenuScreen(game)
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
}