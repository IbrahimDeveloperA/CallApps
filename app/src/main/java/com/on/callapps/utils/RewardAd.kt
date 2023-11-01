package com.on.callapps.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.on.callapps.R
import com.on.callapps.data.local.Pref
import com.on.callapps.databinding.DialogTargetBinding

class RewardAd(
    private val context: Context,
    private val activity: Activity
) {

    private var rewardedAd: RewardedAd? = null
    private val pref: Pref = Pref(context)

    private fun adListener(
        text: Int,
        key: String,
        dialog: Pair<DialogTargetBinding, Dialog>
    ) = object : FullScreenContentCallback() {
        override fun onAdDismissedFullScreenContent() {
            rewardedAd = null
            loadAd(text, key, dialog)
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            super.onAdFailedToShowFullScreenContent(p0)
            rewardedAd = null
        }
    }

    private fun loadAd(
        text: Int,
        key: String,
        dialog: Pair<DialogTargetBinding, Dialog>
    ) {
        if (rewardedAd == null) {
            val adRequest = AdRequest.Builder().build()
            dialog.first.progressBar.visibility = View.VISIBLE
            dialog.first.btnYes.isEnabled = false
            RewardedAd.load(context,
                context.getString(R.string.reward_key),
                adRequest,
                object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        rewardedAd = null
                    }

                    override fun onAdLoaded(ad: RewardedAd) {
                        rewardedAd = ad
                        dialog.first.progressBar.visibility = View.GONE
                        dialog.first.btnYes.isEnabled = true
                        rewardedAd?.fullScreenContentCallback = adListener(text, key, dialog)
                    }
                })
        }else{
            dialog.first.progressBar.visibility = View.GONE
            dialog.first.btnYes.isEnabled = true
        }
    }

    fun onClick(
        text: Int,
        key: String,
        tvRatingTwo: TextView? = null,
        tvRatingThree: TextView? = null,
        tvRateTwo: TextView? = null,
        whenTwo: (() -> Unit?)? = null,
        two: (() -> Unit?)? = null,
        whenFour: (() -> Unit?)? = null,
        four: (() -> Unit?)? = null,
        whenThree: (() -> Unit?)? = null,
        three: (() -> Unit?)? = null,
    ) {
        val dialog = context.createDialog(DialogTargetBinding::inflate)
        dialog.first.tvTitle.text =
            context.getString(R.string.watch_the_short_video_to_unlock_the_character)
        when (key) {
            Key.KEY_TWO -> {
                dialog.first.tvNumber.text =
                    context.getString(R.string._2, pref.getNameVolume(Key.KEY_TWO))
            }

            Key.KEY_THREE -> {
                dialog.first.tvNumber.text =
                    context.getString(R.string._3, pref.getNameVolume(Key.KEY_THREE))
            }

            Key.KEY_FOUR -> {
                dialog.first.tvNumber.text =
                    context.getString(R.string._4, pref.getNameVolume(Key.KEY_FOUR))
            }
        }
        if (rewardedAd == null) {
            loadAd(text, key, dialog)
        }else{
            dialog.first.progressBar.visibility = View.GONE
            dialog.first.btnYes.isEnabled = true
        }

        dialog.first.btnYes.setOnClickListener {
            rewardedAd?.show(activity) {
                pref.saveNumVolume(key, text + 1)
                if (pref.getNameVolume(Key.KEY_TWO) == 2) {
                    if (whenTwo != null) {
                        whenTwo()
                    }
                    if (two != null) {
                        two()
                    }
                }
                if (pref.getNameVolume(Key.KEY_FOUR) == 4) {
                    if (whenFour != null) {
                        whenFour()
                    }
                    if (four != null) {
                        four()
                    }
                }
                if (pref.getNameVolume(Key.KEY_THREE) == 3) {
                    if (whenThree != null) {
                        whenThree()
                    }
                    if (three != null) {
                        three()
                    }

                }
                tvRatingTwo?.text = context.getString(R.string._2, pref.getNameVolume(Key.KEY_TWO))
                tvRatingThree?.text =
                    context.getString(R.string._3, pref.getNameVolume(Key.KEY_THREE))
                tvRateTwo?.text = context.getString(R.string._4, pref.getNameVolume(Key.KEY_FOUR))
                dialog.second.dismiss()
            }
        }
        dialog.first.btnNo.setOnClickListener {
            dialog.second.dismiss()
        }
    }
}