package dadm.quixada.ufc.lavandery.fragments

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dadm.quixada.ufc.lavandery.LoginActivity
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.AccountSettingAdapter
import dadm.quixada.ufc.lavandery.internalModels.AccountSetting
import java.util.*
import kotlin.collections.ArrayList


class AccountSettingsFragment : Fragment() {

    private lateinit var accountSettingsList: ArrayList<AccountSetting>
    private lateinit var exitButton: TextView
    private lateinit var mAuth: FirebaseAuth
    private var userName: String = ""
    private var userSurname: String = ""
    private var userEmail: String = ""
    private var userCellPhone: String = ""
    private var profileImageUrl: String = ""
    private lateinit var profileImageView: ImageView
    private lateinit var btnChangeImageProfile: Button
    private lateinit var selectedImageUri: Uri


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_account_settings, container, false)

        mAuth = FirebaseAuth.getInstance()
        getAccountDataFromDB(view)

        return view
    }

    private fun getAccountDataFromDB(view: View) {
        val currentUserId = mAuth.currentUser!!.uid
        val db = Firebase.firestore

        val documentRef = db.collection("users").document(currentUserId)
        documentRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    this.userName = document.data!!["name"].toString()
                    this.userSurname = document.data!!["surname"].toString()
                    this.userCellPhone = document.data!!["telephone"].toString()
                    this.userEmail = mAuth.currentUser!!.email.toString()
                    this.profileImageUrl = document.data!!["profile_img_url"].toString()
                    this.initializeViews(view)
                }
            }
            .addOnFailureListener { _ ->
                Toast.makeText(
                    context,
                    "Erro ao buscar as configurações da conta",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun initializeViews(view: View) {
        val accountSettingsListView: ListView = view.findViewById(R.id.account_settings_list_view)
        accountSettingsList = this.populateAccountSettingsList()
        accountSettingsListView.adapter = AccountSettingAdapter(requireActivity(), accountSettingsList)

        exitButton = view.findViewById(R.id.btn_sign_out_app)
        exitButton.setOnClickListener { exitTheApp() }


        profileImageView = view.findViewById(R.id.profile_image_view)
        btnChangeImageProfile = view.findViewById(R.id.button_change_image_profile)
        btnChangeImageProfile.setOnClickListener { selectImageProfile() }

        Glide.with(requireContext())
            .load(profileImageUrl)
            .into(profileImageView)

    }

    private fun exitTheApp(){
        mAuth.signOut()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        googleSignInClient.signOut()

        val loginIntent = Intent(context, LoginActivity::class.java)
        startActivity(loginIntent)

        requireActivity().finish()
    }

    private fun populateAccountSettingsList(): ArrayList<AccountSetting> {
        val list: ArrayList<AccountSetting> = ArrayList()

        list.add(AccountSetting("Nome", this.userName, this.userSurname))
        list.add(AccountSetting("Email", this.userEmail))
        list.add(AccountSetting("Celular", this.userCellPhone))

        return list
    }

    private fun selectImageProfile(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, R.integer.REQUEST_IMAGE_PROFILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        updateProfileImage(data)

    }

    private fun updateProfileImage(data: Intent?){
        val imageUri = data?.data
        selectedImageUri = imageUri!!
        val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
        profileImageView.setImageDrawable(BitmapDrawable(bitmap))
        uploadProfileToStorage()
    }

    private fun uploadProfileToStorage(){
        val userId = mAuth.currentUser!!.uid
        val filename = userId+"_profile_img"
        var storageRef = FirebaseStorage.getInstance().getReference("/images/" + filename)
        storageRef.putFile(selectedImageUri)
            .addOnSuccessListener {
                storageRef.downloadUrl.addOnSuccessListener { uri ->
                    saveProfileImageUrlInDB(uri.toString())
                }

            }
            .addOnFailureListener {
                Toast.makeText(
                    context,
                    "Erro ao salvar imagem no Storage",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun saveProfileImageUrlInDB(url: String){
        val db = Firebase.firestore
        val userID = FirebaseAuth.getInstance().currentUser!!.uid

        db.collection("users").document(userID)
            .update("profile_img_url", url)
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Toast.makeText(
                        context,
                        "Imagem salva com sucesso",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else {
                    Toast.makeText(
                        context,
                        "Erro ao salvar referencia da imagem",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }
}