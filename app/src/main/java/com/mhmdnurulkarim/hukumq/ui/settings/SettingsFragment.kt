package com.mhmdnurulkarim.hukumq.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mhmdnurulkarim.hukumq.R
import com.mhmdnurulkarim.hukumq.databinding.FragmentNewsBinding
import com.mhmdnurulkarim.hukumq.databinding.FragmentSettingsBinding
import com.mhmdnurulkarim.hukumq.ui.login.LoginActivity

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        val firebaseUser = auth.currentUser

        if (firebaseUser == null) {
            // Not signed in, launch the Login activity
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finishAffinity()
            return
        }

        binding.textSettings.text = "Karim coi"
        binding.btnLogout.setOnClickListener {
            signOut()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun signOut() {
        auth.signOut()
        startActivity(Intent(activity, LoginActivity::class.java))
        activity?.finishAffinity()
    }
}