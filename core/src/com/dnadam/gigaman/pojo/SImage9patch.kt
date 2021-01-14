package com.dnadam.gigaman.pojo

import com.dnadam.gigaman.entities.Enemy
import com.dnadam.gigaman.entities.Platform
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SImage9patch(
        @SerializedName("uniqueId")
        @Expose
        var uniqueId: Int = 0,

        @SerializedName("itemIdentifier")
        @Expose
        var itemIdentifier: String = "",

        @SerializedName("tags")
        @Expose
        var tags: List<Any> = listOf(),

        @SerializedName("x")
        @Expose
        var x: Float = 0f,

        @SerializedName("y")
        @Expose
        var y: Float = 0f,

        @SerializedName("originX")
        @Expose
        var originX: Float = 0f,

        @SerializedName("originY")
        @Expose
        var originY: Float = 0f,

        @SerializedName("layerName")
        @Expose
        var layerName: String = "",

        @SerializedName("imageName")
        @Expose
        var imageName: String = "",

        @SerializedName("width")
        @Expose
        var width: Float = 0f,

        @SerializedName("height")
        @Expose
        var height: Float = 0f

) {
    fun mapToPlatform(): Platform {
        return Platform(x, y+height, width, height)
    }



}