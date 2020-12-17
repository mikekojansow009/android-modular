package com.food.thirdparty.onesignal

import com.google.gson.annotations.SerializedName

/**
 * Created by mikekojansow on 08/10/20.
 * Senior Android Developer
 */
data class OneSignalData(@SerializedName("type_data")var typeData: String, var id: String) {

    var type: DataType = DataType.RECIPE
        private set
        get() {
            // TODO: We only have recipe only for now
            return DataType.RECIPE
        }

}

enum class DataType {
    RECIPE
}