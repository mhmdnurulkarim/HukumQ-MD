package com.mhmdnurulkarim.hukumq.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mhmdnurulkarim.hukumq.R
import com.mhmdnurulkarim.hukumq.data.Result
import com.mhmdnurulkarim.hukumq.databinding.FragmentHomeBinding
import com.mhmdnurulkarim.hukumq.ui.ViewModelFactory
import com.mhmdnurulkarim.hukumq.utils.Utils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(
            requireActivity()
        )
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        val firebaseUser = auth.currentUser
        updateMessage(firebaseUser?.uid.toString())

        homeViewModel.insertUser(
            uid = firebaseUser?.uid.toString(),
            name = firebaseUser?.displayName.toString(),
            photoUrl = firebaseUser?.photoUrl.toString()
        )

        binding.btnSend.setOnClickListener {
            message(firebaseUser)
        }

        val manager = LinearLayoutManager(requireActivity())
        manager.stackFromEnd = true
        binding.messageRecyclerView.layoutManager = manager

        adapter = HomeAdapter(false)
        binding.messageRecyclerView.adapter = adapter
    }

    private fun updateMessage(uid: String) {
        homeViewModel.getMyChat(uid).observe(requireActivity()) {
            adapter.submitList(it)
        }
    }

    private fun message(firebaseUser: FirebaseUser?) {
        if (binding.edtMessage.text.toString().isEmpty()) {
            binding.edtMessage.requestFocus()
            val snackBar = Snackbar.make(
                binding.fragmentHome,
                getString(R.string.fill_the_chat_first),
                Snackbar.LENGTH_SHORT
            )

            snackBar.setAction(getString(R.string.ok)) {
                snackBar.dismiss()
            }.show()

        } else if (!Utils.isInternetAvailable(requireActivity())) {
            Snackbar.make(
                binding.fragmentHome,
                getString(R.string.check_your_internet),
                Snackbar.LENGTH_SHORT
            ).show()

        } else {
            val text = binding.edtMessage.text.toString().trim()
            homeViewModel.postChatBot(text).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Result.Loading -> {
                        isLoading(true)
                    }

                    is Result.Success -> {
                        homeViewModel.insertMessage(
                            uid = firebaseUser?.uid.toString(),
                            text = text,
                            timestamp = Date().time,
                            currentUser = true
                        )
                        binding.edtMessage.setText("")

                        lifecycleScope.launch {
                            delay(1500)
                            homeViewModel.insertMessage(
                                uid = firebaseUser?.uid.toString(),
                                text = result.data.response,
                                timestamp = Date().time,
                                currentUser = false
                            )
                            isLoading(false)
                        }
                    }

                    is Result.Error -> {
                        isLoading(false)
                    }
                }
            }

            updateMessage(firebaseUser?.uid.toString())
        }
    }

    private fun isLoading(data: Boolean) {
        if (data) {
            binding.btnSend.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.btnSend.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}