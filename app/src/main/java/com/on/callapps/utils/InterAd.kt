package com.on.callapps.utils

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.on.callapps.R
import com.on.callapps.data.local.Pref

class InterAd(private val context: Context, private val activity: Activity) {

    private var mInterstitialAd: InterstitialAd? = null
    private val pref: Pref = Pref(context)

    fun showInter() {
        pref.interAd += 1
        if (pref.interAd == 2) {
            mInterstitialAd?.show(activity)
        }
        if (pref.interAd > 2){
            pref.interAd = 0
        }
    }


    fun loadAd() {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            context.getString(R.string.inter_key),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    adError.toString().let { it1 -> Log.d("ololoInterFailed", it1) }
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d("ololoInterLoaded", "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                    mInterstitialAd?.fullScreenContentCallback = adListener()
                }
            })
    }

    private fun adListener() = object : FullScreenContentCallback() {
        override fun onAdDismissedFullScreenContent() {
            mInterstitialAd = null
            loadAd()
            pref.interAd = 0
        }

        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
            mInterstitialAd = null
            loadAd()
        }
    }
}