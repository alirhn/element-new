/*
 * Copyright (c) 2025 Element Creations Ltd.
 * Copyright 2023-2025 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package io.element.android.libraries.mediaplayer.impl

import android.content.Context
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import io.element.android.libraries.audio.api.AudioFocus
import io.element.android.libraries.audio.api.AudioFocusRequester
import io.element.android.libraries.di.RoomScope
import io.element.android.libraries.di.annotations.ApplicationContext
import io.element.android.libraries.di.annotations.SessionCoroutineScope
import io.element.android.libraries.mediaplayer.api.MediaPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.timeout
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

/**
 * Default implementation of [MediaPlayer] backed by a [SimplePlayer].
 */
@ContributesBinding(RoomScope::class)
@SingleIn(RoomScope::class)
class DefaultMediaPlayer(
    private val player: SimplePlayer,
    @SessionCoroutineScope
    private val sessionCoroutineScope: CoroutineScope,
    private val audioFocus: AudioFocus,
    @ApplicationContext
    private val context: Context,
) : MediaPlayer {
    private val listener = object : SimplePlayer.Listener {
        override fun onIsPlayingChanged(isPlaying: Boolean) {
            _state.update {
                it.copy(
                    currentPosition = player.currentPosition,
                    duration = duration,
                    isPlaying = isPlaying,
                )
            }
            if (isPlaying) {
                job = sessionCoroutineScope.launch { updateCurrentPosition() }
                if (_backgroundPlaybackEnabled) {
                    notifyServiceOfStateChange()
                }
            } else {
                audioFocus.releaseAudioFocus()
                job?.cancel()
                if (_backgroundPlaybackEnabled) {
                    notifyServiceOfStateChange()
                }
            }
        }

        override fun onMediaItemTransition(mediaItem: MediaItem?) {
            _state.update {
                it.copy(
                    currentPosition = player.currentPosition,
                    duration = duration,
                    mediaId = mediaItem?.mediaId,
                )
            }
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            _state.update {
                it.copy(
                    isReady = playbackState == Player.STATE_READY,
                    isEnded = playbackState == Player.STATE_ENDED,
                    currentPosition = player.currentPosition,
                    duration = duration,
                )
            }
            if (playbackState == Player.STATE_ENDED && _backgroundPlaybackEnabled) {
                disableBackgroundPlayback()
            }
        }
    }

    private val serviceCallback = object : MediaPlaybackService.Callback {
        override fun onPlay() = play()
        override fun onPause() = pause()
        override fun onStop() {
            pause()
            disableBackgroundPlayback()
        }
        override fun onSeekTo(positionMs: Long) = seekTo(positionMs)
    }

    init {
        player.addListener(listener)
    }

    private var job: Job? = null
    private var _backgroundPlaybackEnabled = false
    private var currentTitle: String? = null

    private val _state = MutableStateFlow(
        MediaPlayer.State(
            isReady = false,
            isPlaying = false,
            isEnded = false,
            mediaId = null,
            currentPosition = 0L,
            duration = null,
        )
    )

    override val state: StateFlow<MediaPlayer.State> = _state.asStateFlow()

    override val isBackgroundPlaybackEnabled: Boolean
        get() = _backgroundPlaybackEnabled

    @OptIn(FlowPreview::class)
    override suspend fun setMedia(
        uri: String,
        mediaId: String,
        mimeType: String,
        startPositionMs: Long,
        title: String?,
    ): MediaPlayer.State {
        currentTitle = title
        player.pause()
        player.clearMediaItems()
        player.setMediaItem(
            MediaItem.Builder()
                .setUri(uri)
                .setMediaId(mediaId)
                .setMimeType(mimeType)
                .build(),
            startPositionMs,
        )
        player.prepare()
        return state.timeout(1.seconds).first { it.isReady && it.mediaId == mediaId }
    }

    override fun play() {
        audioFocus.requestAudioFocus(
            requester = AudioFocusRequester.VoiceMessage,
            onFocusLost = {
                if (player.isPlaying()) {
                    player.pause()
                }
            },
        )
        if (player.playbackState == Player.STATE_ENDED) {
            player.getCurrentMediaItem()?.let {
                player.setMediaItem(it, 0)
                player.prepare()
            }
        }
        player.play()
    }

    override fun pause() {
        player.pause()
    }

    override fun seekTo(positionMs: Long) {
        player.seekTo(positionMs)
        _state.update {
            it.copy(currentPosition = player.currentPosition)
        }
        if (_backgroundPlaybackEnabled) {
            notifyServiceOfStateChange()
        }
    }

    override fun enableBackgroundPlayback() {
        if (_backgroundPlaybackEnabled) return
        _backgroundPlaybackEnabled = true
        MediaPlaybackService.setCallback(serviceCallback)
        MediaPlaybackService.start(context, currentTitle)
        notifyServiceOfStateChange()
    }

    override fun disableBackgroundPlayback() {
        if (!_backgroundPlaybackEnabled) return
        _backgroundPlaybackEnabled = false
        MediaPlaybackService.setCallback(null)
        MediaPlaybackService.stop(context)
    }

    override fun close() {
        disableBackgroundPlayback()
        player.release()
    }

    private fun notifyServiceOfStateChange() {
        val s = _state.value
        MediaPlaybackService.updateState(
            context = context,
            isPlaying = s.isPlaying,
            position = s.currentPosition,
            duration = s.duration ?: 0L,
            title = currentTitle,
        )
    }

    private suspend fun updateCurrentPosition() {
        while (true) {
            if (!_state.value.isPlaying) return
            delay(100)
            _state.update {
                it.copy(currentPosition = player.currentPosition)
            }
        }
    }

    private val duration: Long?
        get() = player.duration.let {
            if (it == C.TIME_UNSET) null else it
        }
}
