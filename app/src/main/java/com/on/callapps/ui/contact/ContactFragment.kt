package com.on.callapps.ui.contact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.on.callapps.R
import com.on.callapps.data.local.Pref
import com.on.callapps.databinding.DialogTargetBinding
import com.on.callapps.databinding.FragmentContactBinding
import com.on.callapps.ui.contact.adapter.ContactAdapter
import com.on.callapps.utils.Key
import com.on.callapps.utils.createDialog

class ContactFragment : Fragment() {

    private lateinit var binding: FragmentContactBinding

    private val pref by lazy { Pref(requireContext()) }

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


        val ratingText = pref.getNameVolume(Key.KEY_ONE)
        binding.tvRatingTwo.text = "$ratingText/4"


        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }


        binding.contactFour.setOnClickListener {
            onClick(ratingText?.toInt() ?: 3, Key.KEY_ONE) // Используйте ratingText вместо преобразования его в Int
        }


        binding.contactFive.setOnClickListener {
             onClick(binding.tvRatingThree.text.toString().toInt(), Key.KEY_TWO)
        }

        binding.contactSix.setOnClickListener {
            // onClick(binding.tvRateTwo.text.toString(), Key.KEY_THREE)
        }
    }

    private fun onClick(text: Int, key: String,) {
        val dialog = requireContext().createDialog(DialogTargetBinding::inflate)
        dialog.first.tvTitle.text = "Watch the short video to unlock the character "
        dialog.first.tvNumber.text = "$text/4"
        dialog.first.btnYes.setOnClickListener {

            // Увеличьте значение text на 1
            val newText = text + 1

            // Сохраните новое значение в pref
            pref.saveNumVolume(key, newText)

            // Обновите текст в вашем диалоговом окне
            dialog.first.tvNumber.text = newText.toString()

            dialog.second.dismiss()
        }
        dialog.first.btnNo.setOnClickListener {
            dialog.second.dismiss()
        }
    }}