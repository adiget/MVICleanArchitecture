package com.example.mvi_clean_architecture.presentation.ui.common

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.mvi_clean_architecture.R

@Composable
fun RoundedImage(
    url: String,
    @DrawableRes placeholder: Int,
    modifier: Modifier = Modifier,
    crossfade: Boolean = true,
) {
    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest
                .Builder(LocalContext.current)
                .data(data = url).apply(
                    block = fun ImageRequest.Builder.() {
                        crossfade(crossfade)
                        placeholder(placeholder)
                        transformations(CircleCropTransformation())
                    }
                ).build()
            ),
        contentDescription = null,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun RoundedImagePreview() {
    RoundedImage(
        url = "",
        placeholder = R.drawable.avatar_placeholder,
        modifier = Modifier.size(dimensionResource(id = R.dimen.avatar_size_medium))
    )
}