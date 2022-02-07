package dadm.quixada.ufc.lavandery.models

open class User(
    var id: String,
    var name: String,
    var surname: String,
    var email: String,
    var telephone: String,
    var accountType: String,
    var profileImageUrl: String = "-"
) {

}