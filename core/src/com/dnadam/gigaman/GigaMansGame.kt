package com.dnadam.gigaman

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.dnadam.gigaman.screens.GameOverScreen
import com.dnadam.gigaman.screens.GamePlayScreen
import com.dnadam.gigaman.screens.MainMenuScreen
import com.dnadam.gigaman.util.InputListeners.Companion.inputMultiplexer
import com.dnadam.gigaman.util.LevelLoader

class GigaMansGame : Game() {

    override fun create() {
        Gdx.input.inputProcessor = inputMultiplexer;
        setScreen(MainMenuScreen(this))
    }

}