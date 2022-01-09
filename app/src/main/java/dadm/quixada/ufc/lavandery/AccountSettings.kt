package dadm.quixada.ufc.lavandery

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView

import android.widget.ListView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.google.firebase.auth.FirebaseAuth

import dadm.quixada.ufc.lavandery.adapters.AccountSettingAdapter
import dadm.quixada.ufc.lavandery.internalModels.AccountSetting

class AccountSettings : AppCompatActivity() {

    private lateinit var accountSettingsList: ArrayList<AccountSetting>
    private lateinit var  accountSettingsAdapter: AccountSettingAdapter
    private lateinit var btnSignOutApp: TextView
    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var profileImageView: ImageView
    private lateinit var btnChangeImageProfile: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_settings)

        accountSettingsList = this.populateAccountSettingsList()
        val accountSettingsListView: ListView = findViewById(R.id.account_settings_list_view)
        accountSettingsAdapter = AccountSettingAdapter(this, accountSettingsList)
        accountSettingsListView.adapter = accountSettingsAdapter

        initializeViews()

        mAuth = FirebaseAuth.getInstance()

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        configureSignOutAppButton()

        val currentUser = mAuth.currentUser
        Glide.with(this).load(currentUser?.photoUrl).into(profileImageView)

    }

    private fun initializeViews(){
        profileImageView = findViewById(R.id.profile_image_view)
        btnChangeImageProfile = findViewById(R.id.button_change_image_profile)
        btnSignOutApp = findViewById(R.id.btn_sign_out_app)

        btnChangeImageProfile.setOnClickListener { selectImageProfile() }


    }

    private fun selectImageProfile(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, R.integer.REQUEST_IMAGE_PROFILE)
    }

    private fun populateAccountSettingsList(): ArrayList<AccountSetting> {
        val list: ArrayList<AccountSetting> = ArrayList()

        list.add(AccountSetting("Nome", "Gustavo Ivens"))
        //list.add(AccountSetting("E-mail", "gustavoivens@gmail.com", EditEmailActivity()))
        //list.add(AccountSetting("Celular", "+55 88 992436247", EditCellPhoneActivity()))

        return list
    }

    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(resultCode) {
            R.integer.RESULT_EDIT_NAME -> updateName(data)
            R.integer.RESULT_EDIT_EMAIL -> updateEmail(data)
            R.integer.RESULT_EDIT_CELL_PHONE -> updateCellPhone(data)
            else -> updateProfileImage(data)
        }
    }

    private fun updateProfileImage(data: Intent?){
        val imageUri = data?.data
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        profileImageView.setImageDrawable(BitmapDrawable(bitmap))
    }

    private fun updateName(data: Intent?) {
        val name = data?.extras?.get("name") as String
        val surname = data.extras!!.get("surname") as String
        val fullName = name + " " + surname
        val editedName = AccountSetting("Nome", fullName, EditName())
        this.accountSettingsList[0] = editedName
        this.accountSettingsAdapter.notifyDataSetChanged()
    }

    private fun updateEmail(data: Intent?) {
        val email = data?.extras?.get("email") as String
        val editedEmail = AccountSetting("E-mail", email, EditEmailActivity())
        this.accountSettingsList[1] = editedEmail
        this.accountSettingsAdapter.notifyDataSetChanged()
    }

    private fun updateCellPhone(data: Intent?){
        val cellPhone = data?.extras?.get("cell_phone") as String
        val editedCellPhone = AccountSetting("Celular", cellPhone, EditCellPhoneActivity())
        this.accountSettingsList[2] = editedCellPhone
        this.accountSettingsAdapter.notifyDataSetChanged()
    }*/

    private fun configureSignOutAppButton(){
        btnSignOutApp.setOnClickListener {
            mAuth.signOut()
            googleSignInClient.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

}