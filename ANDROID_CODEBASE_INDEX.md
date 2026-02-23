# Element-X Android Codebase Index

**Generated:** February 16, 2026  
**Purpose:** Comprehensive mapping of existing features vs. requirements from instruction.pdf

---

## Table of Contents

1. [Project Architecture](#project-architecture)
2. [Module Structure](#module-structure)
3. [Feature Status Matrix](#feature-status-matrix)
4. [Detailed Feature Analysis](#detailed-feature-analysis)
5. [Implementation Gaps & Recommendations](#implementation-gaps--recommendations)
6. [Key File Locations](#key-file-locations)

---

## Project Architecture

### Technology Stack

- **Language:** Kotlin
- **UI Framework:** Jetpack Compose
- **Navigation:** Appyx (Bumble)
- **Dependency Injection:** Metro (custom DI framework)
- **Matrix SDK:** Matrix Rust SDK (via FFI)
- **Build System:** Gradle (Kotlin DSL)
- **Minimum SDK:** Android 7.0 (API 24)

### Architecture Pattern

- **Multi-module architecture** with clear separation:
  - `app` - Main application module
  - `appnav` - Navigation layer
  - `features/*` - Feature modules (UI screens/flows)
  - `libraries/*` - Shared libraries
  - `services/*` - Service modules
  - `enterprise/*` - Enterprise features

### Entry Points

- **Application:** `app/src/main/kotlin/io/element/android/x/ElementXApplication.kt`
- **Main Activity:** `app/src/main/kotlin/io/element/android/x/MainActivity.kt`
- **Main Node:** `app/src/main/kotlin/io/element/android/x/MainNode.kt`
- **DI Graph:** `app/src/main/kotlin/io/element/android/x/di/AppGraph.kt`

### Dependency Injection

- Uses **Metro** framework for DI
- Graph-based dependency injection
- Session-scoped dependencies for Matrix clients
- Entry points pattern for feature modules

---

## Module Structure

### Features Modules (`features/`)

| Module | Purpose | Status |
|--------|---------|--------|
| `call` | Voice/video calls via Element Call | ✅ Implemented |
| `forward` | Message forwarding | ⚠️ Partial (single message only) |
| `poll` | Poll creation and responses | ✅ Implemented |
| `messages` | Chat messages UI and timeline | ✅ Implemented |
| `home` | Home screen with room list | ✅ Implemented |
| `login` | Authentication flow | ✅ Implemented |
| `preferences` | Settings and preferences | ✅ Implemented |
| `roomcall` | Room call management | ✅ Implemented |
| `roomdetails` | Room settings and details | ✅ Implemented |
| `userprofile` | User profile viewing | ✅ Implemented |
| `securebackup` | E2EE backup management | ✅ Implemented |
| `verifysession` | Session verification | ✅ Implemented |
| `lockscreen` | PIN/biometric lock | ✅ Implemented |
| `migration` | Data migration | ✅ Implemented |
| `ftue` | First-time user experience | ✅ Implemented |
| `share` | Content sharing | ✅ Implemented |
| `location` | Location sharing | ✅ Implemented |
| `space` | Spaces (rooms organization) | ✅ Implemented |
| `announcement` | Announcements | ✅ Implemented |
| `analytics` | Analytics tracking | ✅ Implemented |
| `rageshake` | Bug reporting | ✅ Implemented |

### Libraries Modules (`libraries/`)

| Module | Purpose |
|--------|---------|
| `matrix` | Matrix SDK wrappers (API + Impl) |
| `matrixmedia` | Media handling for Matrix |
| `matrixui` | UI components for Matrix data |
| `mediaplayer` | Media playback (audio/video) |
| `mediaupload` | Media upload handling |
| `mediaviewer` | Media viewer UI |
| `mediapickers` | Media picker UI |
| `voiceplayer` | Voice message playback |
| `voicerecorder` | Voice message recording |
| `push` | Push notification handling |
| `pushproviders` | Push providers (FCM, UnifiedPush) |
| `pushstore` | Push notification storage |
| `session-storage` | Session data storage |
| `accountselect` | Account selection UI |
| `roomselect` | Room selection UI |
| `textcomposer` | Message composer |
| `designsystem` | Design system components |
| `architecture` | Architecture utilities |
| `core` | Core utilities |
| `preferences` | Preferences storage |
| `network` | Network utilities |
| `cryptography` | Cryptographic utilities |
| `permissions` | Permission handling |
| `workmanager` | WorkManager integration |
| `featureflag` | Feature flag system |
| `indicator` | UI indicators |
| `troubleshoot` | Troubleshooting tools |
| `wellknown` | Well-known configuration |
| `oidc` | OIDC authentication |
| `usersearch` | User search functionality |
| `recentemojis` | Recent emojis storage |
| `qrcode` | QR code generation/scanning |
| `fullscreenintent` | Full-screen intent handling |
| `maplibre-compose` | Map integration |
| `ui-common` | Common UI utilities |
| `ui-strings` | String resources |
| `ui-utils` | UI utilities |
| `testtags` | Testing tags |
| `previewutils` | Preview utilities |
| `dateformatter` | Date formatting |
| `eventformatter` | Event formatting |
| `deeplink` | Deep link handling |
| `androidutils` | Android utilities |
| `encrypted-db` | Encrypted database |
| `di` | Dependency injection utilities |
| `compound` | Compound design tokens |
| `rustsdk` | Rust SDK bindings |

### Services Modules (`services/`)

| Module | Purpose |
|--------|---------|
| `analytics` | Analytics service |
| `apperror` | Error handling service |
| `appnavstate` | Navigation state service |
| `toolbox` | Developer tools |

---

## Feature Status Matrix

### Android Application Features

| # | Feature | Status | Implementation Details |
|---|---------|--------|----------------------|
| 1 | **Voice/Video Calls (1-to-1)** | ✅ **EXISTS** | Element Call integration complete |
| 2 | **Video Sessions/Conferences** | ✅ **EXISTS** | Element Call supports group calls |
| 3 | **Multi-Account Support** | ⚠️ **PARTIAL** | Behind feature flag, experimental |
| 4 | **User Status Display** | ❌ **MISSING** | No status/presence feature found |
| 5 | **Media Progress Display** | ⚠️ **PARTIAL** | Basic progress exists, no percentage for all types |
| 6 | **Background Media Playback** | ❌ **MISSING** | Media pauses when leaving app |
| 7 | **Multi-Message Forwarding** | ❌ **MISSING** | Only single message forwarding exists |
| 8 | **Poll Responses** | ✅ **EXISTS** | Full poll support with responses |
| 9 | **Push Notifications (Default Enabled)** | ⚠️ **PARTIAL** | Push exists but default state unclear |
| 10 | **Chat Backgrounds** | ❌ **MISSING** | No custom chat backgrounds found |
| 11 | **Auto-Scroll to Unread** | ⚠️ **PARTIAL** | Has jump button, no auto-scroll |
| 12 | **Reply Depth Limitation** | ⚠️ **PARTIAL** | Threads exist, depth limit unclear |

### Admin Dashboard Features

| # | Feature | Status | Notes |
|---|---------|--------|-------|
| 1-11 | **All Admin Features** | ❌ **MISSING** | No admin dashboard exists (backend feature) |

### Server Features

| # | Feature | Status | Notes |
|---|---------|--------|-------|
| 1-4 | **All Server Features** | ❌ **MISSING** | Backend project not started |

---

## Detailed Feature Analysis

### ✅ 1. Voice/Video Calls (1-to-1 and Group)

**Status:** ✅ **FULLY IMPLEMENTED**

**Location:**
- `features/call/` - Main call feature module
- `features/roomcall/` - Room call management
- `libraries/matrix/impl/src/main/kotlin/io/element/android/libraries/matrix/impl/widget/DefaultCallWidgetSettingsProvider.kt`

**Implementation Details:**
- Uses **Element Call** (not Jitsi) ✅
- Supports 1-to-1 calls ✅
- Supports group video conferencing ✅
- Can switch between voice and video ✅
- Picture-in-picture support ✅
- Foreground service for calls ✅

**Key Files:**
- `features/call/impl/src/main/kotlin/io/element/android/features/call/impl/ui/ElementCallActivity.kt`
- `features/call/impl/src/main/kotlin/io/element/android/features/call/impl/ui/CallScreenView.kt`
- `features/call/impl/src/main/kotlin/io/element/android/features/call/impl/utils/DefaultCallWidgetProvider.kt`

**Notes:** This feature is complete and matches requirements.

---

### ⚠️ 2. Multi-Account Support

**Status:** ⚠️ **PARTIAL - EXPERIMENTAL**

**Location:**
- `libraries/session-storage/` - Session storage
- `libraries/accountselect/` - Account selection UI
- `appnav/src/main/kotlin/io/element/android/appnav/RootFlowNode.kt`

**Implementation Details:**
- Multi-account support exists but is **behind feature flag** (`FeatureFlags.MultiAccount`)
- Feature flag defaults to `false` (disabled by default)
- Session storage supports multiple sessions ✅
- Account switching UI exists ✅
- Room key management when switching: **Needs verification**

**Key Files:**
- `libraries/session-storage/api/src/main/kotlin/io/element/android/libraries/sessionstorage/api/SessionStore.kt`
- `libraries/accountselect/impl/src/main/kotlin/io/element/android/libraries/accountselect/impl/AccountSelectPresenter.kt`
- `libraries/featureflag/api/src/main/kotlin/io/element/android/libraries/featureflag/api/FeatureFlags.kt` (line 105-112)

**Gaps:**
- Feature is experimental and unstable (per code comments)
- Default disabled - needs to be enabled
- Room key management during account switch needs verification

**Recommendation:**
- Enable feature flag by default
- Test and stabilize multi-account functionality
- Verify room key management works correctly

---

### ❌ 3. User Status Display

**Status:** ❌ **NOT IMPLEMENTED**

**Location:** Not found in codebase

**Gaps:**
- No status/presence feature found
- No user status display in UI
- No status management API

**Recommendation:**
- Implement Matrix presence API integration
- Add status display in user profile and room member lists
- Add status management UI in preferences

---

### ⚠️ 4. Media Download/Upload Progress

**Status:** ⚠️ **PARTIAL**

**Location:**
- `libraries/mediaviewer/impl/src/main/kotlin/io/element/android/libraries/mediaviewer/impl/viewer/MediaViewerView.kt`
- `features/messages/impl/src/main/kotlin/io/element/android/features/messages/impl/attachments/preview/AttachmentsPreviewView.kt`
- `features/messages/impl/src/main/kotlin/io/element/android/features/messages/impl/timeline/components/event/TimelineItemVoiceView.kt`

**Implementation Details:**
- Basic progress indicators exist ✅
- Upload progress shown during attachment preview ✅
- Download progress shown for voice messages ✅
- **Missing:** Percentage display for all media types (images, videos, files)
- **Missing:** Real-time progress updates for all media

**Key Files:**
- `libraries/mediaviewer/impl/src/main/kotlin/io/element/android/libraries/mediaviewer/impl/viewer/MediaViewerView.kt` (line 424-442)
- `features/messages/impl/src/main/kotlin/io/element/android/features/messages/impl/attachments/preview/AttachmentsPreviewView.kt` (line 129-162)

**Gaps:**
- No percentage display for images, videos, and general files
- Progress indicators are basic (indeterminate or simple loading)

**Recommendation:**
- Add percentage progress display for all media types
- Integrate with Matrix SDK progress callbacks
- Show progress in timeline items for downloading media

---

### ❌ 5. Background Media Playback

**Status:** ❌ **NOT IMPLEMENTED**

**Location:**
- `libraries/mediaplayer/impl/` - Media player implementation
- `libraries/mediaviewer/impl/src/main/kotlin/io/element/android/libraries/mediaviewer/impl/local/audio/MediaAudioView.kt`
- `libraries/mediaviewer/impl/src/main/kotlin/io/element/android/libraries/mediaviewer/impl/local/video/MediaVideoView.kt`

**Current Behavior:**
- Media pauses when leaving the app ❌
- Media pauses when leaving the room ❌
- No background service for media playback ❌

**Key Files:**
- `libraries/mediaviewer/impl/src/main/kotlin/io/element/android/libraries/mediaviewer/impl/local/audio/MediaAudioView.kt` (line 186-191)
- `libraries/mediaviewer/impl/src/main/kotlin/io/element/android/libraries/mediaviewer/impl/local/video/MediaVideoView.kt` (line 298-301)

**Gaps:**
- No MediaSession integration
- No foreground service for media playback
- Media stops when UI is not visible

**Recommendation:**
- Implement MediaSession API
- Create foreground service for background playback
- Add media controls in notification
- Allow playback to continue when leaving room/app

---

### ⚠️ 6. Message Forwarding

**Status:** ⚠️ **PARTIAL - SINGLE MESSAGE ONLY**

**Location:**
- `features/forward/` - Forward feature module
- `features/messages/impl/src/main/kotlin/io/element/android/features/messages/impl/MessagesPresenter.kt`

**Implementation Details:**
- Single message forwarding exists ✅
- Forward to multiple rooms supported ✅
- **Missing:** Multi-message selection and forwarding ❌

**Key Files:**
- `features/forward/impl/src/main/kotlin/io/element/android/features/forward/impl/ForwardMessagesPresenter.kt`
- `features/forward/impl/src/main/kotlin/io/element/android/features/forward/impl/ForwardMessagesNode.kt`
- `features/messages/impl/src/main/kotlin/io/element/android/features/messages/impl/actionlist/model/TimelineItemAction.kt` (line 22)

**Gaps:**
- No multi-select UI for messages
- Forward action only works on single message
- No batch forwarding capability

**Recommendation:**
- Add multi-select mode to timeline
- Extend forward feature to handle multiple messages
- Update UI to show selected message count
- Batch forward implementation

---

### ✅ 7. Poll Responses in Channels

**Status:** ✅ **FULLY IMPLEMENTED**

**Location:**
- `features/poll/` - Poll feature module
- `features/messages/impl/src/main/kotlin/io/element/android/features/messages/impl/timeline/components/event/TimelineItemPollView.kt`

**Implementation Details:**
- Poll creation ✅
- Poll responses ✅
- Poll display in timeline ✅
- Poll history ✅
- Disclosed and undisclosed polls ✅
- Poll ending ✅

**Key Files:**
- `features/poll/api/src/main/kotlin/io/element/android/features/poll/api/pollcontent/PollContentView.kt`
- `features/poll/impl/src/main/kotlin/io/element/android/features/poll/impl/history/PollHistoryPresenter.kt`
- `features/messages/impl/src/main/kotlin/io/element/android/features/messages/impl/timeline/components/event/TimelineItemPollView.kt`

**Notes:** This feature is complete and matches requirements.

---

### ⚠️ 8. Push Notifications (Default Enabled)

**Status:** ⚠️ **PARTIAL - DEFAULT STATE UNCLEAR**

**Location:**
- `libraries/push/` - Push notification handling
- `libraries/pushproviders/` - Push providers (FCM, UnifiedPush)
- `features/preferences/impl/src/main/kotlin/io/element/android/features/preferences/impl/notifications/NotificationSettingsPresenter.kt`

**Implementation Details:**
- Push notifications implemented ✅
- FCM and UnifiedPush support ✅
- Notification settings UI exists ✅
- **Unclear:** Default enabled state on first run

**Key Files:**
- `libraries/push/impl/src/main/kotlin/io/element/android/libraries/push/impl/DefaultPusherSubscriber.kt`
- `libraries/pushstore/impl/src/main/kotlin/io/element/android/libraries/pushstore/impl/UserPushStoreDataStore.kt`
- `features/preferences/impl/src/main/kotlin/io/element/android/features/preferences/impl/notifications/NotificationSettingsPresenter.kt`

**Gaps:**
- Need to verify default enabled state
- May need to ensure push is enabled by default on first run

**Recommendation:**
- Verify default push notification state
- Ensure push is enabled by default for new users
- Update initialization code if needed

---

### ❌ 9. Chat Backgrounds

**Status:** ❌ **NOT IMPLEMENTED**

**Location:** Not found in codebase

**Current State:**
- No custom chat backgrounds
- No emoji backgrounds
- No background selection UI

**Gaps:**
- No room background customization
- No emoji pattern backgrounds
- No background settings

**Recommendation:**
- Add room background settings
- Implement emoji pattern backgrounds
- Add background selection UI in room details
- Store background preference per room

---

### ⚠️ 10. Auto-Scroll to Unread Messages

**Status:** ⚠️ **PARTIAL - HAS JUMP BUTTON**

**Location:**
- `features/messages/impl/src/main/kotlin/io/element/android/features/messages/impl/timeline/TimelineView.kt`
- `features/messages/impl/src/main/kotlin/io/element/android/features/messages/impl/MessagesPresenter.kt`

**Current Behavior:**
- Has "Jump to Bottom" button ✅
- Auto-scrolls to bottom for new messages ✅
- **Missing:** Auto-scroll to first unread message on room entry ❌
- **Missing:** Remove jump button requirement ❌

**Key Files:**
- `features/messages/impl/src/main/kotlin/io/element/android/features/messages/impl/timeline/TimelineView.kt` (line 279-352)
- `features/messages/impl/src/main/kotlin/io/element/android/features/messages/impl/MessagesPresenter.kt` (line 182-193)

**Gaps:**
- No automatic navigation to first unread message
- Jump button exists (requirement says no jump button)
- Unread detection exists but not used for auto-scroll

**Recommendation:**
- Implement auto-scroll to first unread message on room entry
- Remove or hide jump button (or make it optional)
- Use unread flag to determine scroll position

---

### ⚠️ 11. Reply Depth Limitation

**Status:** ⚠️ **PARTIAL - THREADS EXIST**

**Location:**
- `libraries/textcomposer/impl/src/main/kotlin/io/element/android/libraries/textcomposer/model/MessageComposerMode.kt`
- `libraries/matrixui/src/main/kotlin/io/element/android/libraries/matrix/ui/messages/reply/InReplyToView.kt`
- `features/messages/impl/src/main/kotlin/io/element/android/features/messages/impl/MessagesPresenter.kt`

**Current Behavior:**
- Reply functionality exists ✅
- Threads feature exists (behind feature flag) ✅
- **Unclear:** Reply depth limitation (single level)

**Key Files:**
- `libraries/textcomposer/impl/src/main/kotlin/io/element/android/libraries/textcomposer/model/MessageComposerMode.kt` (line 37-42)
- `libraries/matrixui/src/main/kotlin/io/element/android/libraries/matrix/ui/messages/reply/InReplyToView.kt`

**Gaps:**
- Need to verify reply depth limitation
- May need to enforce single-level reply display
- Threads allow deep nesting

**Recommendation:**
- Verify current reply depth behavior
- Enforce single-level reply display in UI
- Limit reply preview to one level only

---

## Implementation Gaps & Recommendations

### High Priority (Required Features)

1. **User Status Display** ❌
   - **Priority:** High
   - **Effort:** Medium
   - **Files to create:** New feature module `features/userstatus/`

2. **Background Media Playback** ❌
   - **Priority:** High
   - **Effort:** High
   - **Files to modify:** `libraries/mediaplayer/`, add MediaSession service

3. **Multi-Message Forwarding** ⚠️
   - **Priority:** High
   - **Effort:** Medium
   - **Files to modify:** `features/forward/`, `features/messages/`

4. **Chat Backgrounds** ❌
   - **Priority:** Medium
   - **Effort:** Medium
   - **Files to create:** Background selection UI, storage

5. **Auto-Scroll to Unread** ⚠️
   - **Priority:** Medium
   - **Effort:** Low
   - **Files to modify:** `features/messages/impl/`

### Medium Priority

6. **Media Progress Percentage** ⚠️
   - **Priority:** Medium
   - **Effort:** Low-Medium
   - **Files to modify:** Media upload/download components

7. **Reply Depth Limitation** ⚠️
   - **Priority:** Medium
   - **Effort:** Low
   - **Files to modify:** Reply UI components

8. **Multi-Account Stabilization** ⚠️
   - **Priority:** Medium
   - **Effort:** High
   - **Files to modify:** Multi-account feature, enable by default

9. **Push Notifications Default** ⚠️
   - **Priority:** Low
   - **Effort:** Low
   - **Files to modify:** Push initialization

---

## Key File Locations

### Architecture & Entry Points

```
app/src/main/kotlin/io/element/android/x/
├── ElementXApplication.kt          # Application entry point
├── MainActivity.kt                 # Main activity
├── MainNode.kt                     # Main navigation node
└── di/
    ├── AppGraph.kt                 # DI graph definition
    ├── AppModule.kt                # App-level dependencies
    └── AppBindings.kt              # App bindings interface
```

### Navigation

```
appnav/src/main/kotlin/io/element/android/appnav/
├── RootFlowNode.kt                 # Root navigation node
└── LoggedInFlowNode.kt            # Logged-in flow navigation
```

### Matrix SDK Integration

```
libraries/matrix/
├── api/                            # Matrix API interfaces
└── impl/                           # Matrix SDK implementation
    └── RustMatrixClient.kt         # Main Matrix client wrapper
```

### Feature Modules

```
features/
├── call/                           # Voice/video calls
├── messages/                       # Chat messages
├── forward/                        # Message forwarding
├── poll/                           # Polls
├── home/                           # Home screen
├── preferences/                    # Settings
└── [other features]/
```

### Libraries

```
libraries/
├── mediaplayer/                    # Media playback
├── mediaupload/                    # Media upload
├── mediaviewer/                    # Media viewer
├── push/                           # Push notifications
├── session-storage/                # Session storage
├── accountselect/                  # Account selection
└── [other libraries]/
```

---

## Summary Statistics

### Feature Completion

- **✅ Fully Implemented:** 3 features (Calls, Polls, Basic Forwarding)
- **⚠️ Partially Implemented:** 6 features (Multi-Account, Media Progress, Push, Auto-Scroll, Reply Depth)
- **❌ Missing:** 3 features (Status, Background Playback, Chat Backgrounds)

### Codebase Metrics

- **Total Feature Modules:** ~40+
- **Total Library Modules:** ~35+
- **Total Service Modules:** ~4
- **Architecture:** Multi-module, Compose-based, MVVM-like

### Next Steps

1. **Immediate:** Review and verify partial implementations
2. **Short-term:** Implement missing high-priority features
3. **Medium-term:** Stabilize and enhance partial features
4. **Long-term:** Begin backend project for admin dashboard

---

## Notes

- This index is based on codebase exploration as of February 16, 2026
- Some features may exist but were not found during initial exploration
- Feature flags may hide some functionality
- Backend/admin dashboard features are not part of Android codebase
- Matrix protocol features are handled by Rust SDK, not Android code

---

**Document Version:** 1.0  
**Last Updated:** February 16, 2026
