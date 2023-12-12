package com.mhmdnurulkarim.hukumq.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import com.mhmdnurulkarim.hukumq.data.model.Message
import com.mhmdnurulkarim.hukumq.databinding.FragmentHomeBinding
import com.mhmdnurulkarim.hukumq.ui.adapter.MessageAdapter
import org.tensorflow.lite.task.text.nlclassifier.NLClassifier
import java.io.IOException
import java.util.Date

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseDatabase
    private lateinit var adapter: MessageAdapter
//    private lateinit var textClassifier: NLClassifier

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

        db = Firebase.database
        val messagesRef = db.reference.child(firebaseUser?.uid.toString())

//        downloadModel("HukumQ")

        binding.sendButton.setOnClickListener {
            messagesRef.push().setValue(
                Message(
                    firebaseUser?.uid.toString(),
                    binding.messageEditText.text.toString(),
                    firebaseUser?.displayName.toString(),
                    firebaseUser?.photoUrl.toString(),
                    Date().time
                )
            )
            binding.messageEditText.setText("")
        }

        val manager = LinearLayoutManager(requireActivity())
        manager.stackFromEnd = true
        binding.messageRecyclerView.layoutManager = manager

        val options = FirebaseRecyclerOptions.Builder<Message>()
            .setQuery(messagesRef, Message::class.java)
            .build()
        adapter = MessageAdapter(options, firebaseUser?.displayName)
        binding.messageRecyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        adapter.startListening()
    }

    override fun onPause() {
        super.onPause()
        adapter.stopListening()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun downloadModel(modelName: String) {
//        val conditions = CustomModelDownloadConditions.Builder()
//            .requireWifi()
//            .build()
//        FirebaseModelDownloader.getInstance()
//            .getModel(modelName, DownloadType.LOCAL_MODEL, conditions)
//            .addOnSuccessListener { model ->
//                try {
//                    textClassifier = NLClassifier.createFromFile(model.file)
//                    binding.sendButton.isEnabled = true
//                } catch (e: IOException) {
//                    Toast.makeText(
//                        requireActivity(),
//                        "Model initialization failed.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    binding.sendButton.isEnabled = false
//                }
//            }
//            .addOnFailureListener {
//                Toast.makeText(
//                    requireActivity(),
//                    "Model download failed, please check your connection.",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//    }
}