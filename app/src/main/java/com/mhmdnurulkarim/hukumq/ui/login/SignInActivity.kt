package com.mhmdnurulkarim.hukumq.ui.login

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
import com.mhmdnurulkarim.hukumq.databinding.ActivitySignInBinding
import com.mhmdnurulkarim.hukumq.ui.main.MainActivity
import com.mhmdnurulkarim.hukumq.ui.register.SignUpActivity
import com.mhmdnurulkarim.hukumq.utils.Utils.isInternetAvailable

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
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
            if (!isInternetAvailable(this)) {
                Snackbar.make(
                    binding.activitySignIn,
                    getString(R.string.check_your_internet),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                signIn()
            }
        }

        binding.btnSignIn.setOnClickListener {
            if (binding.edtEmail.text.toString().isEmpty()) {
                binding.edtEmail.requestFocus()
                Snackbar.make(
                    binding.activitySignIn,
                    getString(R.string.fill_the_email_field_first),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (binding.edtPassword.text.toString().isEmpty()) {
                binding.edtPassword.requestFocus()
                Snackbar.make(
                    binding.activitySignIn,
                    getString(R.string.fill_the_password_field_first),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (!isInternetAvailable(this)) {
                Snackbar.make(
                    binding.activitySignIn,
                    getString(R.string.check_your_internet),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                firebaseAuthWithEmail(
                    binding.edtEmail.text.toString(),
                    binding.edtPassword.text.toString()
                )

                binding.edtEmail.setText("")
                binding.edtPassword.setText("")
            }
        }

        binding.tvForgotPassword.setOnClickListener {
            if (binding.edtEmail.text.toString().isEmpty()) {
                binding.edtEmail.requestFocus()
                Snackbar.make(
                    binding.activitySignIn,
                    getString(R.string.fill_the_email_field_first),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else if (!isInternetAvailable(this)) {
                Snackbar.make(
                    binding.activitySignIn,
                    getString(R.string.check_your_internet),
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                sendPasswordResetEmail(binding.edtEmail.text.toString())
                binding.edtEmail.setText("")
            }
        }

        binding.dontHaveAccount.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
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

    private fun signIn() {
        resultLauncher.launch(googleSignInClient.signInIntent)
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

    private fun firebaseAuthWithEmail(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    updateUI(auth.currentUser)
                } else {
                    Snackbar.make(
                        binding.activitySignIn,
                        getString(R.string.authentication_failed),
                        Snackbar.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun sendPasswordResetEmail(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Snackbar.make(
                        binding.activitySignIn,
                        getString(R.string.password_reset_email, email),
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    Snackbar.make(
                        binding.activitySignIn,
                        getString(R.string.failed_to_send_password_reset_email),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun updateUI(firebaseUser: FirebaseUser?) {
        if (firebaseUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        updateUI(auth.currentUser)
    }

    companion object {
        private const val TAG = "Sign In Activity"
    }
}