package com.example.systemassesment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.systemassesment.R

@Composable
fun EmptyPostsState(modifier: Modifier = Modifier, onRetry: () -> Unit) {
    EmptyListState(
        icon = Icons.Outlined.Article,
        titleRes = R.string.empty_posts_title,
        subtitleRes = R.string.empty_posts_subtitle,
        modifier = modifier,
        onRetry = onRetry,
        showRetry = true
    )
}

@Composable
fun EmptyFavoritesState(modifier: Modifier = Modifier) {
    EmptyListState(
        icon = Icons.Outlined.FavoriteBorder,
        titleRes = R.string.empty_favorites_title,
        subtitleRes = R.string.empty_favorites_subtitle,
        modifier = modifier,
        showRetry = false
    )
}

@Composable
private fun EmptyListState(
    icon: ImageVector,
    titleRes: Int,
    subtitleRes: Int,
    modifier: Modifier = Modifier,
    showRetry: Boolean,
    onRetry: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .size(120.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainerHighest,
                    shape = CircleShape
                ),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.65f)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = stringResource(id = titleRes),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = subtitleRes),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        if (showRetry){
            Button(
                onClick = { onRetry() },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "Retry")
            }
        }
    }
}
