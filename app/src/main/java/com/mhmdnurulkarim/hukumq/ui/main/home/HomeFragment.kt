package com.mhmdnurulkarim.hukumq.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mhmdnurulkarim.hukumq.R
import com.mhmdnurulkarim.hukumq.data.Result
//import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
//import com.google.firebase.ml.modeldownloader.DownloadType
//import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import com.mhmdnurulkarim.hukumq.databinding.FragmentHomeBinding
import com.mhmdnurulkarim.hukumq.ui.ViewModelFactory
//import org.tensorflow.lite.task.text.nlclassifier.NLClassifier
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
            } else {
                homeViewModel.insertMessage(
                    uid = firebaseUser?.uid.toString(),
                    text = binding.edtMessage.text.toString(),
                    timestamp = Date().time,
                    currentUser = true
                )

                homeViewModel.postChatBot(binding.edtMessage.text.toString()).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is Result.Loading -> {
//                            onLoading()
                        }

                        is Result.Success -> {
                            homeViewModel.insertMessage(
                                uid = firebaseUser?.uid.toString(),
                                text = result.data.response,
                                timestamp = Date().time,
                                currentUser = false
                            )

//                            loginViewModel.saveTokenUser(result.data.loginResult.token)
//                            onSuccess()
                        }

                        is Result.Error -> {
//                            onError(resources.getString(R.string.check_email_password_wrong))
                        }
                    }
                }

//                homeViewModel.insertMessage(
//                    uid = firebaseUser?.uid.toString(),
//                    text = binding.edtMessage.text.toString(),
//                    timestamp = Date().time,
//                    currentUser = false
//                )

                binding.edtMessage.setText("")
                updateMessage(firebaseUser?.uid.toString())
            }
        }

        val manager = LinearLayoutManager(requireActivity())
        manager.stackFromEnd = true
        binding.messageRecyclerView.layoutManager = manager

        adapter = HomeAdapter(false)
        binding.messageRecyclerView.adapter = adapter
    }

//    override fun onResume() {
//        super.onResume()
//        adapter.startListening()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        adapter.stopListening()
//    }

    private fun updateMessage(uid: String){
        homeViewModel.getMyChat(uid).observe(requireActivity()) {
            adapter.submitList(it)
        }
    }

//    private fun responseBot(input: String){
//        homeViewModel.postChatBot(input).observe(viewLifecycleOwner){ result ->
//            if (result != null) {
//                when (result) {
//                    is Result.Loading -> {
//                        binding.progressBar.visibility = View.VISIBLE
//                    }
//                    is Result.Success -> {
//                        binding.progressBar.visibility = View.GONE
//
//                    }
//                    is Result.Error -> {
//                        binding.progressBar.visibility = View.GONE
//                        binding.viewError.root.visibility = View.VISIBLE
//                        binding.viewError.tvError.text = getString(R.string.something_wrong)
//                    }
//                }
//            }
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}