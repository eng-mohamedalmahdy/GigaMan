package com.dnadam.gigaman.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Composite(@SerializedName("sImages")
                     @Expose
                     var sImages: List<SImage> = listOf(),

                     @SerializedName("sImage9patchs")
                     @Expose
                     var sImage9patchs: List<SImage9patch> = listOf(),

                     @SerializedName("layers")
                     @Expose
                     var layers: List<Layer> = listOf())