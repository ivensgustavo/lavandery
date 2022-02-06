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
import dadm.quixada.ufc.lavandery.LoginActivity
import dadm.quixada.ufc.lavandery.R
import dadm.quixada.ufc.lavandery.adapters.AccountSettingAdapter
import dadm.quixada.ufc.lavandery.internalModels.AccountSetting
import dadm.quixada.ufc.lavandery.logic.UserService
import dadm.quixada.ufc.lavandery.models.User
import java.util.*
import kotlin.collections.ArrayList


class AccountSettingsFragment : Fragment() {

    private lateinit var exitButton: TextView
    private val mAuth = FirebaseAuth.getInstance()
    private lateinit var profileImageView: ImageView
    private lateinit var btnChangeImageProfile: Button
    private lateinit var user: User
    private val userService = UserService()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_account_settings, container, false)

        getAccountDataFromDB(view)

        return view
    }

    private fun getAccountDataFromDB(view: View) {
        val currentUserId = mAuth.currentUser!!.uid

        userService.getUser(currentUserId){ recoveredUser ->
            if(recoveredUser != null){
                this.user = recoveredUser
                this.initializeViews(view)
            }else{
                showErrorWhenFetchingUserData()
            }
        }
    }

    private fun showErrorWhenFetchingUserData(){
        Toast.makeText(
            context,
            "Ocorreu um erro ao buscar as configurações da conta.",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun initializeViews(view: View) {

        this.initializeAccountSettingsList(view)

        this.exitButton = view.findViewById(R.id.btn_sign_out_app)
        this.exitButton.setOnClickListener { exitTheApp() }

        this.profileImageView = view.findViewById(R.id.profile_image_view)

        this.btnChangeImageProfile = view.findViewById(R.id.button_change_image_profile)
        this.btnChangeImageProfile.setOnClickListener { selectImageProfile() }

        this.initializeProfileImage()

    }

    private fun initializeAccountSettingsList(view: View){
        val accountSettingsListView: ListView = view.findViewById(R.id.account_settings_list_view)

        val accountSettingsList: ArrayList<AccountSetting> = ArrayList()

        accountSettingsList.add(AccountSetting("Nome", this.user.name, this.user.surname))
        accountSettingsList.add(AccountSetting("Email", this.user.email))
        accountSettingsList.add(AccountSetting("Celular", this.user.telephone))

        accountSettingsListView.adapter = AccountSettingAdapter(requireActivity(), accountSettingsList)
    }

    private fun initializeProfileImage(){

        if(this.user.profileImageUrl.equals("-")){
            this.profileImageView.setImageResource(R.drawable.icon_user)
        }else {
            Glide.with(requireContext())
                .load(this.user.profileImageUrl)
                .into(this.profileImageView)
        }
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
        val selectedImageUri = imageUri!!
        val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, imageUri)
        profileImageView.setImageDrawable(BitmapDrawable(bitmap))
        saveProfileImageInDB(selectedImageUri)
    }

    private fun saveProfileImageInDB(selectedImageUri: Uri){
        val userId = mAuth.currentUser!!.uid
        userService.saveProfileImage(userId, selectedImageUri){ result ->
            if(!result){
                Toast.makeText(
                    context,
                    "Ocorreu um erro ao atualizar a imagem do perfil.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun exitTheApp(){
        Log.d("acount", "Chegou no deslograr")
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

}