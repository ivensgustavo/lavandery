package dadm.quixada.ufc.lavandery.models

class Consumer(
    id: String,
    name: String,
    surname: String,
    email: String,
    telephone: String,
    accountType: String,
    preferences: HashMap<String, Boolean>,
    profileImageUrl: String = "-"
): User(id, name, surname, email, telephone, accountType, profileImageUrl) {
    var preferences = preferences
}