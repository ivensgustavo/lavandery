package dadm.quixada.ufc.lavandery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dadm.quixada.ufc.lavandery.logic.AuthService
import dadm.quixada.ufc.lavandery.logic.UserService
import dadm.quixada.ufc.lavandery.models.User
import kotlin.math.log

class LoginActivity : AppCompatActivity() {

    companion object {
        private const val RC_SIGN_IN = 120
    }

    private lateinit var mAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var enterButton: Button
    private lateinit var signInWithGoogleButton: Button
    private lateinit var signUpLink: TextView
    private val authService = AuthService()
    private val userService = UserService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initializeViews()
        configureSignUpLink()
        setGoogleSignInClient()
        mAuth = FirebaseAuth.getInstance()
    }

    private fun initializeViews() {
        emailEditText = findViewById(R.id.login_email)
        passwordEditText = findViewById(R.id.login_password)
        enterButton = findViewById(R.id.enter_login_button)
        signInWithGoogleButton = findViewById(R.id.btn_sign_in_with_google)
        signUpLink = findViewById(R.id.sign_up_link)

        enterButton.setOnClickListener {
            signInWithEmailAndPassword()
        }

        signInWithGoogleButton.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun configureSignUpLink() {
        signUpLink.setOnClickListener {
            val registrationIntent = Intent(this, RegistrationActivity::class.java)
            startActivity(registrationIntent)
        }
    }

    private fun validateLoginData(): Boolean {
        if (emailEditText.text.isNullOrEmpty()) {
            return false
        }

        if (passwordEditText.text.isNullOrEmpty()) {
            return false
        }

        return true
    }

    private fun signInWithEmailAndPassword() {

        val isValid = validateLoginData()

        if (isValid) {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            authService.signInWithEmailAndPassword(email, password) { result ->
                if (result) {
                    browseTheUser()
                } else {
                    Toast.makeText(
                        this,
                        "Ocorreu um erro ao realizar login. Tente novamente",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } else {
            Toast.makeText(
                this,
                "É necessário informar usuário e senha",
                Toast.LENGTH_SHORT
            ).show()
        }


    }

    private fun setGoogleSignInClient() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(
            signInIntent,
            RC_SIGN_IN
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception

            if (task.isSuccessful) {
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    authService.firebaseAuthWithGoogle(account.idToken!!) { result ->
                        if (result) {
                            browseTheUser()
                        }
                    }
                } catch (e: ApiException) {
                    Log.w("LoginActivity", "Google sign in failed", e)
                }
            } else {
                Log.w("LoginActivity", exception.toString())
            }

        }
    }

    private fun browseTheUser() {

        userService.getUser(mAuth.currentUser!!.uid) { loggedUser ->
            if (loggedUser != null) {
                if (loggedUser.accountType == "Consumidor de serviços de lavanderia") {
                    val consumerHomeIntent = Intent(this, HomeActivity::class.java)
                    startActivity(consumerHomeIntent)
                    finish()
                } else {
                    val providerHomeIntent = Intent(this, ProviderHomeActivity::class.java)
                    startActivity(providerHomeIntent)
                    finish()
                }
            }
        }
    }
}