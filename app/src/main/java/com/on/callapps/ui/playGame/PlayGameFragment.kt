package com.on.callapps.ui.playGame

import android.animation.AnimatorInflater
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
import com.on.callapps.utils.InterAd
import kotlin.system.exitProcess

class PlayGameFragment : Fragment() {

    private lateinit var binding:FragmentPlayGameBinding
    private val interAd by lazy { InterAd(requireContext(), requireActivity()) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayGameBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        interAd.loadAd()
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        animBtn()
        initListener()
    }

    private fun animBtn() {
        binding.btnExit.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.btnPlay.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
        binding.btnBack.stateListAnimator = AnimatorInflater.loadStateListAnimator(
            requireContext(),
            R.animator.button_click_animation
        )
    }

    private fun initListener() {
        binding.btnBack.setOnClickListener {
            interAd.showInter()
            findNavController().navigateUp()
        }
        binding.btnExit.setOnClickListener {
            interAd.showInter()
            requireActivity().finishAffinity()
            exitProcess(0)
        }

        binding.btnPlay.setOnClickListener {
            interAd.showInter()
            val intent = Intent(requireContext(), WebViewActivity::class.java)
            intent.putExtra("url", getString(R.string.gamezop))
            startActivity(intent)
        }
    }
}