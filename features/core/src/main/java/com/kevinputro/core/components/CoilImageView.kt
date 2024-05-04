package com.kevinputro.core.components

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import coil.annotation.ExperimentalCoilApi
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import com.google.android.material.imageview.ShapeableImageView

class CoilImageView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : ShapeableImageView(context, attrs, defStyleAttr) {

  fun loadImage(
    imgUrl: String,
    placeholderImg: Drawable? = null,
    errorImage: Drawable? = null,
  ) {
    this.load(imgUrl) {
      placeholderImg?.let { placeholder(it) }
      errorImage?.let { error(it) }
    }
  }

  @OptIn(ExperimentalCoilApi::class)
  fun loadImageWithCache(imgUrl: String, key: String) {
    val snapshot = context.imageLoader.diskCache?.openSnapshot(key)
    val imageRequest = ImageRequest.Builder(context)
      .target(this)

    val localCache = snapshot?.data?.toFile()
    if (localCache?.exists() == true && localCache.isFile) {
      imageRequest.data(localCache)
    } else {
      imageRequest.data(imgUrl)
      imageRequest.diskCacheKey(key)
    }
    context.imageLoader.enqueue(imageRequest.build())
    snapshot?.close()
  }

  fun loadImage(
    imgUrl: String,
    @DrawableRes placeholderImg: Int? = null,
    @DrawableRes errorImg: Int? = null,
  ) {
    this.load(imgUrl) {
      placeholderImg?.let { placeholder(it) }
      errorImg?.let { error(it) }
    }
  }

}
