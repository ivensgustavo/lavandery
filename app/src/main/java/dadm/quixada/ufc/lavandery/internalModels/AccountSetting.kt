package dadm.quixada.ufc.lavandery.internalModels

import androidx.fragment.app.Fragment

class AccountSetting(var name: String, var value: String) {
    var complementValue = ""

    constructor(name: String, value: String, complementValue: String = ""): this (name, value){
        this.complementValue = complementValue
    }
}