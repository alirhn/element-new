/*
 * Copyright (c) 2025 Element Creations Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package io.element.android.libraries.mediaviewer.impl.local

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import timber.log.Timber

/**
 * Helper to start/stop the MediaPlaybackService for background audio/video playback.
 *
 * Uses ComponentName to avoid a compile-time dependency on the mediaplayer impl module.
 * The service class name must match [io.element.android.libraries.mediaplayer.impl.MediaPlaybackService].
 */
object MediaPlaybackServiceHelper {
    private const val SERVICE_CLASS = "io.element.android.libraries.mediaplayer.impl.MediaPlaybackService"
    private const val EXTRA_TITLE = "io.element.android.mediaplayer.EXTRA_TITLE"

    @Volatile
    var isActive: Boolean = false
        private set

    fun startService(context: Context, title: String?) {
        try {
            isActive = true
            val intent = Intent().apply {
                component = ComponentName(context.packageName, SERVICE_CLASS)
                putExtra(EXTRA_TITLE, title ?: "Media playback")
            }
            ContextCompat.startForegroundService(context, intent)
        } catch (e: Exception) {
            Timber.e(e, "Failed to start MediaPlaybackService for background playback")
            isActive = false
        }
    }

    fun stopService(context: Context) {
        try {
            isActive = false
            val intent = Intent().apply {
                component = ComponentName(context.packageName, SERVICE_CLASS)
            }
            context.stopService(intent)
        } catch (e: Exception) {
            Timber.e(e, "Failed to stop MediaPlaybackService")
        }
    }
}
