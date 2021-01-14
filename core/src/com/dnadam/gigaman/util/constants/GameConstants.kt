package com.dnadam.gigaman.util.constants

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Vector2

class GameConstants {

    companion object {
        const val SOUND_ENABLED: String = "SOUND_ENABLED"
        const val SOUNDS_DIR = "sounds"
        const val GAME_PREFERENCE = "GIGA_PREF"
        const val PORTAL: String = "portal"
        val BACKGROUND_COLOR: Color = Color.SKY


        const val WORLD_SIZE = 256f

        const val TEXTURE_ATLAS = "images/gigaman.pack.atlas"

        val GRAVITY = Vector2(0f, -WORLD_SIZE)

        const val PLATFORM_NAME = "platform"

        const val NINE_PATCH_EDGE = 8

        val EXPLOSION_CENTER = Vector2(8f, 8f)


    }
}