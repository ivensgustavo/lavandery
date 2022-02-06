package dadm.quixada.ufc.lavandery.models

class Address(
    var street: String,
    var number: Int,
    var cep: Int,
    var complement: String,
    var latitude: Double,
    var longitude: Double
) {

    var id: String = ""

    constructor(
        id: String,
        street: String,
        number: Int,
        cep: Int,
        complement: String,
        latitude: Double,
        longitude: Double
    ) : this(
        street,
        number,
        cep,
        complement,
        latitude,
        longitude
    ) {
        this.id = id
    }
}