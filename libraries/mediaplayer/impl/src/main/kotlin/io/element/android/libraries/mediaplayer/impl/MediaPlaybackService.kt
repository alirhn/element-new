/*
 * Copyright (c) 2025 Element Creations Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package io.element.android.libraries.mediaplayer.impl

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.ServiceCompat
import androidx.core.content.ContextCompat
import androidx.media.app.NotificationCompat as MediaNotificationCompat
import io.element.android.libraries.core.extensions.runCatchingExceptions
import io.element.android.libraries.designsystem.utils.CommonDrawables
import io.element.android.libraries.push.api.notifications.ForegroundServiceType
import io.element.android.libraries.push.api.notifications.NotificationIdProvider
import timber.log.Timber

/**
 * Foreground service that keeps audio/video playback alive when the app is in the background.
 * Uses MediaSessionCompat for lock screen and notification media controls.
 */
class MediaPlaybackService : Service() {
    companion object {
        private const val CHANNEL_ID = "media_playback_channel"
        private const val ACTION_PLAY = "io.element.android.mediaplayer.ACTION_PLAY"
        private const val ACTION_PAUSE = "io.element.android.mediaplayer.ACTION_PAUSE"
        private const val ACTION_STOP = "io.element.android.mediaplayer.ACTION_STOP"
        private const val EXTRA_TITLE = "io.element.android.mediaplayer.EXTRA_TITLE"
        private const val EXTRA_DURATION = "io.element.android.mediaplayer.EXTRA_DURATION"
        private const val EXTRA_POSITION = "io.element.android.mediaplayer.EXTRA_POSITION"
        private const val EXTRA_IS_PLAYING = "io.element.android.mediaplayer.EXTRA_IS_PLAYING"

        @Volatile
        private var callback: Callback? = null

        fun start(context: Context, title: String?) {
            val intent = Intent(context, MediaPlaybackService::class.java).apply {
                putExtra(EXTRA_TITLE, title ?: "Media playback")
            }
            runCatchingExceptions {
                ContextCompat.startForegroundService(context, intent)
            }.onFailure {
                Timber.e(it, "Failed to start media playback foreground service")
            }
        }

        fun stop(context: Context) {
            runCatchingExceptions {
                context.stopService(Intent(context, MediaPlaybackService::class.java))
            }
        }

        fun updateState(
            context: Context,
            isPlaying: Boolean,
            position: Long,
            duration: Long,
            title: String?,
        ) {
            val intent = Intent(context, MediaPlaybackService::class.java).apply {
                putExtra(EXTRA_IS_PLAYING, isPlaying)
                putExtra(EXTRA_POSITION, position)
                putExtra(EXTRA_DURATION, duration)
                title?.let { putExtra(EXTRA_TITLE, it) }
            }
            runCatchingExceptions { context.startService(intent) }
        }

        fun setCallback(cb: Callback?) {
            callback = cb
        }
    }

    interface Callback {
        fun onPlay()
        fun onPause()
        fun onStop()
        fun onSeekTo(positionMs: Long)
    }

    private var mediaSession: MediaSessionCompat? = null
    private lateinit var notificationManager: NotificationManagerCompat
    private var currentTitle: String = "Media playback"

    override fun onCreate() {
        super.onCreate()
        notificationManager = NotificationManagerCompat.from(this)
        createNotificationChannel()
        createMediaSession()
        promoteToForeground()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY -> {
                callback?.onPlay()
                return START_NOT_STICKY
            }
            ACTION_PAUSE -> {
                callback?.onPause()
                return START_NOT_STICKY
            }
            ACTION_STOP -> {
                callback?.onStop()
                stopSelf()
                return START_NOT_STICKY
            }
        }

        intent?.getStringExtra(EXTRA_TITLE)?.let { currentTitle = it }
        val isPlaying = intent?.getBooleanExtra(EXTRA_IS_PLAYING, true) ?: true
        val position = intent?.getLongExtra(EXTRA_POSITION, 0L) ?: 0L
        val duration = intent?.getLongExtra(EXTRA_DURATION, 0L) ?: 0L

        updateMediaSessionState(isPlaying, position, duration)
        updateNotification(isPlaying)

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        mediaSession?.apply {
            isActive = false
            release()
        }
        mediaSession = null
        stopForeground(STOP_FOREGROUND_REMOVE)
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotificationChannel() {
        val channel = NotificationChannelCompat.Builder(
            CHANNEL_ID,
            NotificationManagerCompat.IMPORTANCE_LOW,
        )
            .setName("Media playback")
            .setDescription("Shows when media is playing in the background")
            .build()
        notificationManager.createNotificationChannel(channel)
    }

    private fun createMediaSession() {
        mediaSession = MediaSessionCompat(this, "ElementMediaPlayback").apply {
            setCallback(object : MediaSessionCompat.Callback() {
                override fun onPlay() {
                    Companion.callback?.onPlay()
                }

                override fun onPause() {
                    Companion.callback?.onPause()
                }

                override fun onStop() {
                    Companion.callback?.onStop()
                    stopSelf()
                }

                override fun onSeekTo(pos: Long) {
                    Companion.callback?.onSeekTo(pos)
                }
            })
            isActive = true
        }
    }

    private fun promoteToForeground() {
        val notification = buildNotification(isPlaying = true)
        val notificationId = NotificationIdProvider.getForegroundServiceNotificationId(
            ForegroundServiceType.MEDIA_PLAYBACK
        )
        val serviceType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
        } else {
            0
        }
        runCatchingExceptions {
            ServiceCompat.startForeground(this, notificationId, notification, serviceType)
        }.onFailure {
            Timber.e(it, "Failed to start media playback foreground service")
        }
    }

    private fun updateMediaSessionState(isPlaying: Boolean, position: Long, duration: Long) {
        val session = mediaSession ?: return
        val pbState = if (isPlaying) PlaybackStateCompat.STATE_PLAYING else PlaybackStateCompat.STATE_PAUSED
        val playbackSpeed = if (isPlaying) 1f else 0f

        session.setPlaybackState(
            PlaybackStateCompat.Builder()
                .setActions(
                    PlaybackStateCompat.ACTION_PLAY or
                        PlaybackStateCompat.ACTION_PAUSE or
                        PlaybackStateCompat.ACTION_STOP or
                        PlaybackStateCompat.ACTION_SEEK_TO or
                        PlaybackStateCompat.ACTION_PLAY_PAUSE
                )
                .setState(pbState, position, playbackSpeed)
                .build()
        )

        session.setMetadata(
            MediaMetadataCompat.Builder()
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, currentTitle)
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, duration)
                .build()
        )
    }

    private fun updateNotification(isPlaying: Boolean) {
        val notification = buildNotification(isPlaying)
        val notificationId = NotificationIdProvider.getForegroundServiceNotificationId(
            ForegroundServiceType.MEDIA_PLAYBACK
        )
        runCatchingExceptions {
            notificationManager.notify(notificationId, notification)
        }
    }

    private fun buildNotification(isPlaying: Boolean): android.app.Notification {
        val session = mediaSession ?: error("MediaSession must be initialized before building notification")

        val playPauseAction = if (isPlaying) {
            NotificationCompat.Action.Builder(
                android.R.drawable.ic_media_pause,
                "Pause",
                buildActionPendingIntent(ACTION_PAUSE),
            ).build()
        } else {
            NotificationCompat.Action.Builder(
                android.R.drawable.ic_media_play,
                "Play",
                buildActionPendingIntent(ACTION_PLAY),
            ).build()
        }

        val stopAction = NotificationCompat.Action.Builder(
            android.R.drawable.ic_delete,
            "Stop",
            buildActionPendingIntent(ACTION_STOP),
        ).build()

        val style = MediaNotificationCompat.MediaStyle()
            .setMediaSession(session.sessionToken)
            .setShowActionsInCompactView(0, 1)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(CommonDrawables.ic_notification)
            .setContentTitle(currentTitle)
            .setContentText(if (isPlaying) "Playing" else "Paused")
            .setStyle(style)
            .addAction(playPauseAction)
            .addAction(stopAction)
            .setOngoing(isPlaying)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()
    }

    private fun buildActionPendingIntent(action: String): PendingIntent {
        val intent = Intent(this, MediaPlaybackService::class.java).apply {
            this.action = action
        }
        return PendingIntent.getService(
            this,
            action.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }
}
