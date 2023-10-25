package com.on.callapps.ui.contact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.on.callapps.R
import com.on.callapps.databinding.DialogTargetBinding
import com.on.callapps.databinding.FragmentContactBinding
import com.on.callapps.ui.contact.adapter.ContactAdapter
import com.on.callapps.utils.createDialog

class ContactFragment : Fragment() {

    private lateinit var binding: FragmentContactBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun onClick() {
        val dialog = requireContext().createDialog(DialogTargetBinding::inflate)
        dialog.first.tvTitle.text = "Watch the short video to unlock the character "
        dialog.first.btnYes.setOnClickListener {
            dialog.second.dismiss()
        }
        dialog.first.btnNo.setOnClickListener{
            dialog.second.dismiss()
        }
    }
}