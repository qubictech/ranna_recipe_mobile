package rannaghor.recipe.tarmsbd.com.viewholder

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.gms.ads.formats.UnifiedNativeAd
import com.google.android.gms.ads.formats.UnifiedNativeAdView
import rannaghor.recipe.tarmsbd.com.R
import rannaghor.recipe.tarmsbd.com.model.Recipe
import rannaghor.recipe.tarmsbd.com.ui.recipedetails.RecipeDetails

class RecipeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @SuppressLint("DefaultLocale", "SetTextI18n")
    fun bind(context: Context, recipe: Recipe) {
        val name = itemView.findViewById<TextView>(R.id.comment_username)
        val likes = itemView.findViewById<TextView>(R.id.recipe_likes_count)
        val icon = itemView.findViewById<ImageView>(R.id.recipe_image)

        name.text = recipe.name?.trim()
        likes.text = "${recipe.likes}"

        Glide.with(context).load(R.mipmap.ic_launcher).into(icon)

        icon.clipToOutline = true
    }

    fun onClickListener(context: Context, recipe: Recipe) {
        val intent = Intent(context, RecipeDetails::class.java)
        intent.putExtra(RecipeDetails.RECIPE_DETAIL, recipe)

        val icon: View = itemView.findViewById<ImageView>(R.id.recipe_image)
        val pairImage = Pair.create(icon, "recipe_icon")
        val options = ActivityOptionsCompat
            .makeSceneTransitionAnimation(context as Activity, pairImage)

        context.startActivity(intent, options.toBundle())
    }

    fun bindAdView(unifiedNativeAd: UnifiedNativeAd) {
        val adView: UnifiedNativeAdView =
            itemView.findViewById(R.id.ad_view) as UnifiedNativeAdView

        adView.mediaView = adView.findViewById(R.id.ad_media)
        adView.headlineView = adView.findViewById(R.id.ad_headline)
        adView.bodyView = adView.findViewById(R.id.ad_body)
        adView.callToActionView = adView.findViewById(R.id.ad_call_to_action)

        adView.iconView = adView.findViewById(R.id.ad_icon)
        adView.priceView = adView.findViewById(R.id.ad_price)
        adView.starRatingView = adView.findViewById(R.id.ad_stars)
        adView.storeView = adView.findViewById(R.id.ad_store)
        adView.advertiserView = adView.findViewById(R.id.ad_advertiser)

        populateNativeAdView(unifiedNativeAd, adView)
    }

    private fun populateNativeAdView(nativeAd: UnifiedNativeAd, adView: UnifiedNativeAdView) {
        // Some assets are guaranteed to be in every UnifiedNativeAd.
        // Some assets are guaranteed to be in every UnifiedNativeAd.
        (adView.headlineView as TextView).text = nativeAd.headline
        (adView.bodyView as TextView).text = nativeAd.body
        (adView.callToActionView as Button).text = (nativeAd.callToAction)

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        val icon = nativeAd.icon

        if (icon == null) {
            adView.iconView.visibility = View.INVISIBLE
        } else {
            (adView.iconView as ImageView).setImageDrawable(icon.drawable)
            adView.iconView.visibility = View.VISIBLE
        }

        if (nativeAd.price == null) {
            adView.priceView.visibility = View.INVISIBLE
        } else {
            adView.priceView.visibility = View.VISIBLE
            (adView.priceView as TextView).text = nativeAd.price
        }

        if (nativeAd.store == null) {
            adView.storeView.visibility = View.INVISIBLE
        } else {
            adView.storeView.visibility = View.VISIBLE
            (adView.storeView as TextView).text = nativeAd.store
        }

        if (nativeAd.starRating == null) {
            adView.starRatingView.visibility = View.INVISIBLE
        } else {
            (adView.starRatingView as RatingBar).rating = nativeAd.starRating.toFloat()
            adView.starRatingView.visibility = View.VISIBLE
        }

        if (nativeAd.advertiser == null) {
            adView.advertiserView.visibility = View.INVISIBLE
        } else {
            (adView.advertiserView as TextView).text = nativeAd.advertiser
            adView.advertiserView.visibility = View.VISIBLE
        }

        // Assign native ad object to the native view.
        // Assign native ad object to the native view.
        adView.setNativeAd(nativeAd)
    }
}