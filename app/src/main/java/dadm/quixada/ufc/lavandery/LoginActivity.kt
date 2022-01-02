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
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val homeIntent = Intent(this, HomeActivity::class.java)
                        startActivity(homeIntent)
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Ocorreu um erro ao realizar login no app. Tente novamente",
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
                    // O login com o google ocorreu com sucesso
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("LoginActivity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Ocorreu falha ao executar o login
                    Log.w("LoginActivity", "Google sign in failed", e)
                }
            } else {
                Log.w("LoginActivity", exception.toString())
            }

        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Quando o login ocorre com sucesso atualize o app com as informações do usuário
                    val homeIntent = Intent(this, HomeActivity::class.java)
                    startActivity(homeIntent)
                    finish()
                } else {
                    // Quando ocorrer uma falah mostrar uma mensagem ao usuário
                    Log.w("LoginActivty", "signInWithCredential:failure", task.exception)
                }
            }
    }
}