package com.on.callapps.utils

import android.app.Dialog
import android.content.Context
import android.util.Log
import androidx.core.view.isVisible
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.on.callapps.R
import com.on.callapps.databinding.DialogTargetBinding

class RewardAd(private val context: Context) {

    var rewardedAd: RewardedAd? = null
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

    fun loadAd(
        text: Int,
        key: String,
        dialog: Pair<DialogTargetBinding, Dialog>
    ) {
        if (rewardedAd == null) {
            val adRequest = AdRequest.Builder().build()
            dialog.first.progressBar.isVisible = true
            dialog.first.btnYes.isEnabled = false
            RewardedAd.load(context,
                context.getString(R.string.reward_key),
                adRequest,
                object : RewardedAdLoadCallback() {
                    override fun onAdFailedToLoad(adError: LoadAdError) {
                        adError.toString().let { it1 -> Log.d("ololoFailed", it1) }
                        rewardedAd = null
                    }

                    override fun onAdLoaded(ad: RewardedAd) {
                        Log.d("ololoLoaded", "Ad was loaded.")
                        rewardedAd = ad
                        dialog.first.progressBar.isVisible = false
                        dialog.first.btnYes.isEnabled = true
                        rewardedAd?.fullScreenContentCallback = adListener(text, key, dialog)
                    }
                })
        }
    }
}