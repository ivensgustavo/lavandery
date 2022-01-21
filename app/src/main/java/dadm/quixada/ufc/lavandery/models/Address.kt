package dadm.quixada.ufc.lavandery.models

class Address(
    var street: String,
    var number: Int,
    var cep: Int,
    var complement: String
) {

    var id: String = ""
    constructor(id: String, street: String, number: Int, cep: Int, complement: String) : this(
        street,
        number,
        cep,
        complement
    ){
        this.id = id
    }
}