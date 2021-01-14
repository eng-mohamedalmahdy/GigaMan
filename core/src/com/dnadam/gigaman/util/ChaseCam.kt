package com.dnadam.gigaman.util

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.graphics.OrthographicCamera
import com.dnadam.gigaman.entities.GigaMan

class ChaseCam(private val camera: Camera, private val player: GigaMan) {


    fun update() {
        camera.position.set(player.position, 0f)
    }
}