package dadm.quixada.ufc.lavandery.helpers

import dadm.quixada.ufc.lavandery.R


object AvailableTimesHelper {

    fun getAllCollectionTimesId(): HashMap<String, Int> {
        val map = HashMap<String, Int>()

        map["8"] = R.id.radio_collection_time_8
        map["9"] = R.id.radio_collection_time_9
        map["10"] = R.id.radio_collection_time_10
        map["11"] = R.id.radio_collection_time_11
        map["12"] = R.id.radio_collection_time_12
        map["13"] = R.id.radio_collection_time_13
        map["14"] = R.id.radio_collection_time_14
        map["15"] = R.id.radio_collection_time_15
        map["16"] = R.id.radio_collection_time_16

        return map
    }

    fun getAllDeliveryTimesId(): HashMap<String, Int> {
        val map = HashMap<String, Int>()

        map["8"] = R.id.radio_delivery_time_8
        map["9"] = R.id.radio_delivery_time_9
        map["10"] = R.id.radio_delivery_time_10
        map["11"] = R.id.radio_delivery_time_11
        map["12"] = R.id.radio_delivery_time_12
        map["13"] = R.id.radio_delivery_time_13
        map["14"] = R.id.radio_delivery_time_14
        map["15"] = R.id.radio_delivery_time_15
        map["16"] = R.id.radio_delivery_time_16

        return map
    }
}