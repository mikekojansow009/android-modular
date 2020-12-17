package com.food.thridparty.ads

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.ads.*

/**
 * Created by mikekojansow on 10/10/20.
 * Senior Android Developer
 */
object RecipeAds {

    fun initiateAds(context: Context) {
        MobileAds.initialize(context) { initializationStatus ->
            for (mapKey in initializationStatus.adapterStatusMap.entries) {
                println("Map key : ${mapKey.key} - Value : ${mapKey.value.description}")
            }
//            println("Status : ${initializationStatus.adapterStatusMap.keys}")
        }
    }

    fun showInterstitialAd(context: Context, adId: String): InterstitialAd {
        val interstitialAd = InterstitialAd(context)
        interstitialAd.adUnitId = adId
        interstitialAd.loadAd(AdRequest.Builder().build())

        interstitialAd.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()

                println("Ad has been loaded")
                interstitialAd.show()
            }

            override fun onAdFailedToLoad(p0: LoadAdError?) {
                super.onAdFailedToLoad(p0)
                println("Error to load ads : ${p0?.message}")
            }
        }

        return interstitialAd
    }

//    fun getBannerAds(adsId: String, context: Context): AdView {
//        val adView = AdView(context)
//        adView.adUnitId = adsId
//        adView.adSize = AdSize.LARGE_BANNER
//
//        return adView
//    }

}