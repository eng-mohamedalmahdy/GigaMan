package com.dnadam.gigaman.util

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.DelayedRemovalArray
import com.dnadam.gigaman.entities.*
import com.dnadam.gigaman.pojo.LevelModel
import com.dnadam.gigaman.util.constants.GameConstants
import com.dnadam.gigaman.util.constants.LevelLoadingConstants
import com.google.gson.Gson
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.File


class LevelLoader {
    companion object {
        fun loadLevel(levelName: String, game: Game): Level {

            val path = LevelLoadingConstants.LEVEL_DIR + File.separator + levelName + "." + LevelLoadingConstants.LEVEL_FILE_EXTENSION;
            val levelFile = Gdx.files.internal(path)
            val parser = JSONParser()
            val gSon = Gson()

            val rootJsonObject = parser.parse(levelFile.reader()) as JSONObject
            val levelModel = gSon.fromJson(rootJsonObject.toJSONString(), LevelModel::class.java)
            val sprites = levelModel.composite.sImages
            val ninePatches = levelModel.composite.sImage9patchs


            val gigaMan: GigaMan = sprites.first { it.itemIdentifier == "player" }.mapToPlayer()

            val endPortal: Portal = sprites.first { it.itemIdentifier == "portal" }.mapToPortal()

            val platforms = Array<Platform>()
            ninePatches.forEach { platforms.add(it.mapToPlatform()) }

            val enemies = DelayedRemovalArray<Enemy>()
            sprites.forEach {
                if (it.itemIdentifier != "player" && it.itemIdentifier != "portal") enemies.add(it.mapToEnemy())
            }
            Gdx.app.getPreferences(GameConstants.GAME_PREFERENCE).putString(LevelLoadingConstants.LEVELS_PREFERENCE, levelName).flush()
            return Level(game = game, levelName = levelName, gigaMan = gigaMan, platforms = platforms, enemies = enemies, endPortal = endPortal)
        }

        fun getNextLevel(currentLevel: Level, game: Game): Level {
            val path = LevelLoadingConstants.LEVEL_DIR + File.separator + LevelLoadingConstants.LEVELS_LIST + "." + "TXT";
            val handle = Gdx.files.internal(path)
            val text = handle.readString()
            val wordsArray = text.split("\\r?\\n".toRegex()).toTypedArray()
            val words = mutableListOf<String>()
            for (word in wordsArray) {
                words.add(word)
            }
            var currLevelIdx = words.indexOf(currentLevel.levelName)
            if (currLevelIdx == words.size - 1) currLevelIdx--
            return loadLevel(words[currLevelIdx + 1], game)
        }
    }

}