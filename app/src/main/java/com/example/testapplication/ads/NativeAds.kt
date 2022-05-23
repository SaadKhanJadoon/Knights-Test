package com.example.testapplication.ads

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import com.google.android.gms.ads.*
import com.example.testapplication.R
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions
import com.google.android.gms.ads.nativead.NativeAdView

open class NativeAds {

    fun loadNativeAdRecyclerview(
        context: Context,
        ad_frame: FrameLayout,
        cardView: CardView,
        linearLayout: LinearLayout,
        customAdLayout: Int,
        nativeId: String
    ) {
        with(context)
        {
            val builder = AdLoader.Builder(this, nativeId)
            builder.forNativeAd { native ->
                val adView =
                    LayoutInflater.from(context).inflate(customAdLayout, null) as NativeAdView
                populateNativeAdView(native, adView)
                ad_frame.removeAllViews()
                ad_frame.addView(adView)
            }

            val videoOptions = VideoOptions.Builder()
                .setStartMuted(true)
                .build()

            val adOptions = NativeAdOptions.Builder()
                .setVideoOptions(videoOptions)
                .build()

            builder.withNativeAdOptions(adOptions)

            val adLoader = builder.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    Log.e("TAG", "onAdLoaded: coming false")
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    cardView.visibility = View.VISIBLE
                    linearLayout.visibility = View.GONE
                    Log.e("TAG", "onAdLoaded: coming true")
                }
            }).build()
            adLoader.loadAd(AdRequest.Builder().build())
        }
    }

    private fun populateNativeAdView(
        nativeAd: NativeAd,
        adView: NativeAdView
    ) {
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)
        adView.iconView = adView.findViewById(R.id.ad_app_icon)
        nativeAd.mediaContent?.let {
            adView.mediaView?.setMediaContent(it)
        }
        nativeAd.body?.let {
            (adView.bodyView as TextView).text = nativeAd.body
        } ?: run {
            adView.bodyView?.visibility = View.GONE
        }
        nativeAd.headline?.let {
            adView.headlineView?.visibility = View.VISIBLE
            (adView.headlineView as TextView).text = it
        } ?: run {
            adView.headlineView?.visibility = View.GONE
        }
        nativeAd.callToAction?.let {
            adView.callToActionView?.visibility = View.VISIBLE
            (adView.callToActionView as Button).text = nativeAd.callToAction
        } ?: run {
            adView.callToActionView?.visibility = View.INVISIBLE
        }
        nativeAd.icon?.let {
            (adView.iconView as ImageView).setImageDrawable(
                nativeAd.icon?.drawable
            )
        } ?: run {
            adView.iconView?.visibility = View.GONE
        }
        adView.setNativeAd(nativeAd)
    }
}