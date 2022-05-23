package com.example.testapplication.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.testapplication.R
import com.example.testapplication.ads.NativeAds
import com.example.testapplication.model.ImageModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ImageAdapter(context: Activity, themesImages: ArrayList<ImageModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var context: Context = context
    private var nativeAds: NativeAds? = null
    private var themeImages: ArrayList<ImageModel> = themesImages

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            1 -> {
                val v = inflater.inflate(R.layout.image_adapter, parent, false)
                viewHolder = ViewHolder(v)
            }
            2 -> {
                val v = inflater.inflate(R.layout.native_ad_image, parent, false)
                viewHolder = NativeAdHolder(v)
                val lp = v.layoutParams
                if (lp is StaggeredGridLayoutManager.LayoutParams) {
                    lp.isFullSpan = true
                }
            }
        }
        assert(viewHolder != null)
        return viewHolder!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            1 -> {
                CoroutineScope(Dispatchers.Main).launch {
                    val viewHolder = holder as ViewHolder
                    viewHolder.themeView.setImageResource(themeImages[position].image)
                }
            }

            2 -> {
                CoroutineScope(Dispatchers.Main).launch {
                    val viewHolder = holder as NativeAdHolder
                    nativeAds = NativeAds()
                    nativeAds?.loadNativeAdRecyclerview(
                        context,
                        viewHolder.adFrameSmall,
                        viewHolder.cardView,
                        viewHolder.linearLayout,
                        R.layout.native_ad_layout,
                        context.getString(R.string.nativeAd_id)
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return themeImages.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (themeImages[position].viewType == 2) {
            2
        } else 1
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var themeView: ImageView = itemView.findViewById(R.id.imageview)
    }

    private class NativeAdHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var linearLayout: LinearLayout = itemView.findViewById(R.id.progressBarLayout)
        var adFrameSmall: FrameLayout = itemView.findViewById(R.id.ad_frameSmall)
        var cardView: CardView = itemView.findViewById(R.id.nativeAdCard)
    }

}