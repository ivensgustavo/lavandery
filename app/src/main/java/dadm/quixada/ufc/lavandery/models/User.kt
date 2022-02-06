package dadm.quixada.ufc.lavandery.models

class User(
    var id: String,
    var name: String,
    var surname: String,
    var email: String,
    var telephone: String,
    var accountType: String,
    var preferences: HashMap<String, Boolean>,
    var profileImageUrl: String = "-"
) {

}