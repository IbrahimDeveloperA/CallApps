package com.on.callapps.ui.playGame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.on.callapps.R
import com.on.callapps.databinding.FragmentPlayGameBinding

class PlayGameFragment : Fragment() {

    private lateinit var binding:FragmentPlayGameBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlayGameBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnExit.setOnClickListener {
            requireActivity().finish()
        }
    }
}