package com.mdgroup.nasawallpapers.presentation.screens.wallpapers

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.mdgroup.nasawallpapers.R
import com.mdgroup.nasawallpapers.core.utils.nullIfEmpty
import com.mdgroup.nasawallpapers.domain.models.WallpaperModel
import org.koin.androidx.compose.inject

@Composable
fun WallpaperDetailsView(
    context: Context,
    wallpaper: WallpaperModel,
    onClickSave: () -> Unit = {},
    onClickShare: () -> Unit = {},
    onClickAsWallpaper: () -> Unit = {}
) {

    val imageLoader: ImageLoader by inject()

    val isSaved = wallpaper.uri != null

    LazyColumn(
        modifier = Modifier
            .fillMaxHeight()
            .padding(bottom = 56.dp),
        horizontalAlignment = Alignment.Start
    ) {
        item {
            wallpaper.uri?.let { uri ->
                Image(
                    painter = rememberImagePainter(uri),
                    contentDescription = wallpaper.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    contentScale = ContentScale.Crop
                )
            } ?: run {
                AsyncImage(
                    model = wallpaper.url,
                    contentDescription = wallpaper.title,
                    imageLoader = imageLoader,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }

        wallpaper.title.nullIfEmpty()?.let {
            item {
                Text(
                    text = it,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier
                        .padding(top = 28.dp)
                        .padding(horizontal = 16.dp),
                )
            }
        }

//        item {
//            Text(
//                text = wallpaper.date,
//                style = MaterialTheme.typography.body2,
//                modifier = Modifier
//                    .padding(top = 8.dp)
//                    .padding(horizontal = 16.dp),
//            )
//        }

        item {
            Row(modifier = Modifier
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp)
                .height(32.dp)
            ) {
                IconButton(onClick = onClickSave) {
                    Icon(
                        painterResource(id = if (isSaved) R.drawable.ic_bookmark_fill else R.drawable.ic_bookmark),
                        contentDescription = stringResource(id = R.string.save_content_description),
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colors.primaryVariant
                    )
                }
                IconButton(onClick = onClickShare) {
                    Icon(
                        painterResource(id = R.drawable.ic_share),
                        contentDescription = stringResource(id = R.string.share_content_description),
                        modifier = Modifier.testTag("shareIconButton") .size(24.dp),
                        tint = MaterialTheme.colors.primaryVariant
                    )
                }
                IconButton(onClick = onClickAsWallpaper) {
                    Icon(
                        modifier = Modifier.testTag("setWallpaperIconButton") .size(24.dp),
                        painter = painterResource(id = R.drawable.ic_phone_fill),
                        contentDescription = stringResource(id = R.string.as_wallpaper_content_description),
                        tint = MaterialTheme.colors.primaryVariant
                    )
                }
            }
        }

        wallpaper.explanation?.let {
            item {
                Text(
                    text = it,
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .padding(horizontal = 16.dp)
                )
            }
        }

        item {
            wallpaper.copyright?.let {
                Text(
                    text = "${stringResource(id = R.string.copyright)} $it",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
                )
            } ?: run {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

}