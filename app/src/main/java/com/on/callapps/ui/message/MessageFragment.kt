package com.on.callapps.ui.message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.on.callapps.databinding.FragmentMessageBinding
import com.on.callapps.ui.message.adapter.AdapterCorrespondence
import com.on.callapps.ui.message.adapter.MessageAdapter

class MessageFragment : Fragment() {

    private lateinit var binding: FragmentMessageBinding
    private val adapterCorrespondence = AdapterCorrespondence()
    private val adapter = MessageAdapter(this::onClick)

    private fun onClick(text: String) {
        Toast.makeText(requireContext(), "$text", Toast.LENGTH_SHORT).show()
        adapterCorrespondence.addText(text)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMessageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

         binding.recyclerManager.adapter = adapterCorrespondence
    }
}