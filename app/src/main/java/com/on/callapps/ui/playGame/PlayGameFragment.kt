package com.on.callapps.ui.playGame

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.on.callapps.R
import com.on.callapps.WebViewActivity
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

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnPlay.setOnClickListener {
                val intent = Intent(requireContext(), WebViewActivity::class.java)
                intent.putExtra("url", "https://www.gamezop.com/?id=3178")
                startActivity(intent)
        }
        binding.btnExit.setOnClickListener {
            requireActivity().finish()
        }
    }
}