package com.mhmdnurulkarim.hukumq.ui.main.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mhmdnurulkarim.hukumq.R
import com.mhmdnurulkarim.hukumq.databinding.FragmentSettingsBinding
import com.mhmdnurulkarim.hukumq.ui.ViewModelFactory
import com.mhmdnurulkarim.hukumq.ui.login.SignInActivity
import com.mhmdnurulkarim.hukumq.ui.splash.SplashActivity
import com.mhmdnurulkarim.hukumq.ui.splash.SplashViewModel

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val settingsViewModel: SettingsViewModel by viewModels {
        ViewModelFactory.getInstance(
            requireActivity()
        )
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        val firebaseUser = auth.currentUser

        if (firebaseUser == null) {
            startActivity(Intent(activity, SignInActivity::class.java))
            activity?.finishAffinity()
            return
        }

        binding.tvName.text = firebaseUser.displayName.toString()
        binding.tvEmail.text = firebaseUser.email.toString()
        Glide.with(this)
            .load(firebaseUser.photoUrl.toString())
            .circleCrop()
            .into(binding.ivUser)

        binding.btnChangePassword.setOnClickListener {
            changePassword(firebaseUser.email.toString())
        }
        binding.btnDeleteAccount.setOnClickListener {
            MaterialAlertDialogBuilder(requireActivity())
                .setTitle(resources.getString(R.string.title))
                .setMessage("Delete?")
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton("Continue") { _, _ ->
                    deleteAccount(firebaseUser)
                    startActivity(Intent(requireActivity(), SplashActivity::class.java))
                    requireActivity().finishAffinity()
                }
                .setCancelable(true)
                .show()
        }

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            settingsViewModel.saveThemeSetting(isChecked)
        }

        settingsViewModel.getThemeSetting().observe(requireActivity()) { isDarkModeActive ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }

        binding.btnAboutUs.setOnClickListener {
            Snackbar.make(
                binding.fragmentSettings,
                getString(R.string.in_development),
                Snackbar.LENGTH_SHORT
            ).show()
        }

        binding.btnPrivacyPolicy.setOnClickListener {
            Snackbar.make(
                binding.fragmentSettings,
                getString(R.string.in_development),
                Snackbar.LENGTH_SHORT
            ).show()
        }

        binding.btnTermConditions.setOnClickListener {
            Snackbar.make(
                binding.fragmentSettings,
                getString(R.string.in_development),
                Snackbar.LENGTH_SHORT
            ).show()
        }

        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(requireActivity())
                .setTitle(resources.getString(R.string.title))
                .setMessage("Sign Out?")
                .setNegativeButton("Cancel") { _, _ ->
                }
                .setPositiveButton("SignOut") { _, _ ->
                    signOut()
                }
                .setCancelable(true)
                .show()
        }
    }

    private fun changePassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Snackbar.make(
                        binding.fragmentSettings,
                        getString(R.string.password_reset_email, email),
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    Snackbar.make(
                        binding.fragmentSettings,
                        getString(R.string.failed_to_send_password_reset_email),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun deleteAccount(user: FirebaseUser) {
        user.delete()
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Snackbar.make(
                        binding.fragmentSettings,
                        getString(R.string.account_deleted_successfully),
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    Snackbar.make(
                        binding.fragmentSettings,
                        getString(R.string.failed_to_delete_account),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun signOut() {
        auth.signOut()
        startActivity(Intent(requireActivity(), SignInActivity::class.java))
        requireActivity().finishAffinity()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}