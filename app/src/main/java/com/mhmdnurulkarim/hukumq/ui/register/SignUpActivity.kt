package com.mhmdnurulkarim.hukumq.ui.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mhmdnurulkarim.hukumq.R
import com.mhmdnurulkarim.hukumq.databinding.ActivitySignUpBinding
import com.mhmdnurulkarim.hukumq.ui.login.SignInActivity
import com.mhmdnurulkarim.hukumq.ui.main.MainActivity
import com.mhmdnurulkarim.hukumq.utils.Utils

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val gso = GoogleSignInOptions
            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        googleSignInClient.revokeAccess()

        binding.btnSignInGoogle.setOnClickListener {
            if (!Utils.isInternetAvailable(this)) {
                Snackbar.make(
                    binding.activitySignUp,
                    "Please Check your internet!",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                signIn()
            }
        }

        binding.btnSignUp.setOnClickListener {
            if (binding.edtEmail.text.toString().isEmpty()) {
                binding.edtEmail.requestFocus()
                Snackbar.make(
                    binding.activitySignUp,
                    getString(R.string.fill_the_email_field_first),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (binding.edtUsername.text.toString().isEmpty()) {
                binding.edtUsername.requestFocus()
                Snackbar.make(
                    binding.activitySignUp,
                    getString(R.string.fill_the_username_field_first),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (binding.edtPassword.text.toString().isEmpty()) {
                binding.edtPassword.requestFocus()
                Snackbar.make(
                    binding.activitySignUp,
                    getString(R.string.fill_the_password_field_first),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (!Utils.isInternetAvailable(this)) {
                Snackbar.make(
                    binding.activitySignUp,
                    getString(R.string.check_your_internet),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                firebaseCreateAuthWithEmail(
                    binding.edtEmail.text.toString(),
                    binding.edtPassword.text.toString()
                )

                binding.edtEmail.setText("")
                binding.edtUsername.setText("")
                binding.edtPassword.setText("")
            }
        }

        binding.alreadyHaveAccount.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private var resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    updateUI(auth.currentUser)
                } else {
                    updateUI(null)
                }
            }
    }

    private fun firebaseCreateAuthWithEmail(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val currentUser = auth.currentUser
                    currentUser?.sendEmailVerification()
                        ?.addOnCompleteListener(this) {
                            if (task.isSuccessful) {
                                startActivity(Intent(this, SignInActivity::class.java))
                                finish()
                                Snackbar.make(
                                    binding.activitySignUp,
                                    getString(R.string.verification_email, auth.currentUser?.email),
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            } else {
                                Snackbar.make(
                                    binding.activitySignUp,
                                    getString(R.string.failed_to_send_verification_email),
                                    Snackbar.LENGTH_SHORT
                                ).show()
                            }
                        }
                } else {
                    Snackbar.make(
                        binding.activitySignUp,
                        getString(R.string.authentication_failed),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }
    }

    companion object {
        private const val TAG = "Sign Up Activity"
    }
}