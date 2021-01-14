package com.dnadam.gigaman.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Layer(@SerializedName("layerName")
                 @Expose
                 var layerName: String = ""
                 ,
                 @SerializedName("isVisible")
                 @Expose
                 var isVisible: Boolean = false)