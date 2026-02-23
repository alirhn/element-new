/*
 * Copyright (c) 2026 Element Creations Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial.
 * Please see LICENSE files in the repository root for full details.
 */

package io.element.android.features.preferences.impl.developer

import androidx.compose.runtime.Composable
import io.element.android.libraries.designsystem.components.preferences.DropdownOption

enum class ChatBackgroundStyleItem(val storageKey: String) : DropdownOption {
    Default("default") {
        @Composable
        override fun getText(): String = "Default"
    },
    Gradient("gradient") {
        @Composable
        override fun getText(): String = "Gradient"
    },
    Emoji("emoji") {
        @Composable
        override fun getText(): String = "Emoji"
    };

    companion object {
        fun fromStorageKey(key: String?): ChatBackgroundStyleItem = when (key) {
            "gradient" -> Gradient
            "emoji" -> Emoji
            else -> Default
        }
    }
}
