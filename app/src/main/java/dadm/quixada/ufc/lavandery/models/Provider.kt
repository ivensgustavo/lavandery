package dadm.quixada.ufc.lavandery.models

class Provider(
    id: String,
    name: String,
    surname: String,
    email: String,
    telephone: String,
    accountType: String,
    profileImageUrl: String = "-",
) : User(id, name, surname, email, telephone, accountType, profileImageUrl) {

    var address: Address? = null
    var ordersInWeek = 0

    constructor(
        id: String,
        name: String,
        surname: String,
        email: String,
        telephone: String,
        accountType: String,
        profileImageUrl: String,
        address: Address
    ) : this(id, name, surname, email, telephone, accountType, profileImageUrl){
        this.address = address
    }

}