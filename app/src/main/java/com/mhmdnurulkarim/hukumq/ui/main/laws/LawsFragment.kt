package com.mhmdnurulkarim.hukumq.ui.main.laws

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mhmdnurulkarim.hukumq.R
import com.mhmdnurulkarim.hukumq.data.model.Legal
import com.mhmdnurulkarim.hukumq.databinding.FragmentLawsBinding
import com.mhmdnurulkarim.hukumq.ui.ViewModelFactory

class LawsFragment : Fragment() {

    private var _binding: FragmentLawsBinding? = null
    private val binding get() = _binding!!
    private val lawsViewModel: LawsViewModel by viewModels {
        ViewModelFactory.getInstance(
            requireActivity()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLawsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val legalAdapter = LegalAdapter { legal ->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(legal.link)
            startActivity(intent)
        }

        binding.rvLegal.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = legalAdapter
        }

        legalAdapter.submitList(getListLegal())
    }

    private fun getListLegal(): ArrayList<Legal> {
        val dataTitle = resources.getStringArray(R.array.title_legal)
        val dataLink = resources.getStringArray(R.array.link_legal)
        val listLegal = ArrayList<Legal>()
        for (i in dataTitle.indices) {
            val legal = Legal(dataTitle[i], dataLink[i])
            listLegal.add(legal)
        }
        return listLegal
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}