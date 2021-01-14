package com.dnadam.gigaman.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LevelModel(@SerializedName("composite")
                      @Expose
                      var composite: Composite = Composite(),

                      @SerializedName("physicsPropertiesVO")
                      @Expose
                      var physicsPropertiesVO: PhysicsPropertiesVO = PhysicsPropertiesVO(),

                      @SerializedName("lightsPropertiesVO")
                      @Expose
                      var lightsPropertiesVO: LightsPropertiesVO = LightsPropertiesVO())