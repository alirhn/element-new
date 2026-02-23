# Project Features Implementation Plan

This document outlines all the features that need to be implemented based on the instruction PDF for the Element-X Android project enhancement.

## Project Overview

**Title (Persian):** راهاندازی و ریزتوسعه یک پروتکل متنباز برای ارتباطات لحظهای غیرمتمرکز با پشتیبانی ذاتی از E2EE

**Title (English):** Deploy and micro development an open source protocol for decentralized real-time communications with native support E2EE

**Project Duration:** 10 months (equivalent to 1050 person-hours)

---

## Android Application Features

### 1. Voice and Video Calls
- **One-to-One Calls**: In private chats (two-person rooms), users can make voice and video calls one-to-one
- **Switch Capability**: Ability to switch between voice and video calls and vice versa
- **Implementation**: Use Element Call instead of Jitsi

### 2. Video Sessions/Conferences
- **Group Video Calls**: Ability to hold video sessions in rooms with more than 2 members
- **Audio/Video Conferencing**: Support for audio and video conferencing
- **Implementation**: Use Element Call instead of Jitsi

### 3. Multi-Account Support
- **Multiple Accounts**: Capability to manage multiple accounts
- **Account Switching**: Users can switch between their accounts seamlessly
- **Room Key Management**: Room keys should be properly managed when switching accounts
- **Server Support**: Accounts can be on separate servers or the same server

### 4. User Status Display
- **Status Feature**: Users can display their status in the application
- **Status Management**: Users can set and update their status

### 5. Media Download/Upload Progress
- **Progress Display**: Show download/upload percentage for all media types:
  - Images
  - Videos
  - Audio files
  - Other files
- **Real-time Updates**: Progress should update in real-time

### 6. Background Media Playback
- **Continue Playback**: Audio/video files should continue playing even after:
  - Leaving the group
  - Exiting the application
- **Background Service**: Implement background service for media playback

### 7. Message Forwarding
- **Multi-Message Forward**: Ability to select multiple messages and forward them to:
  - Another person
  - Another group
- **Forwarding UI**: User-friendly interface for selecting and forwarding messages

### 8. Poll Responses in Channels
- **Poll Support**: Members can respond to polls in channels
- **Poll UI**: Interface for viewing and responding to polls

### 9. Push Notifications
- **Default Enabled**: Push notifications should be enabled by default
- **Notification Management**: Users can manage notification settings

### 10. Chat Backgrounds
- **Custom Backgrounds**: Chat pages should have backgrounds similar to standard messengers
- **Emoji Backgrounds**: Beautiful emoji backgrounds in chat backgrounds
- **Background Selection**: Users can select/change chat backgrounds

### 11. Auto-Scroll to Unread Messages
- **Auto-Navigation**: When entering a chat page (personal, channel, group), automatically navigate to the first unread message
- **No Jump Button**: Do not use a "jump to unread" button - automatic navigation instead
- **Unread Detection**: Proper detection of unread messages

### 12. Reply Depth Limitation
- **Single Level Reply**: In reply to text, only one previous text level should be inserted
- **No Deep Threading**: Limit reply depth to prevent deep threading

---

## Admin Dashboard Panel Features

### 1. User Access Levels
- **Access Control**: Ability to define different access levels for different users
- **Role Management**: Create and manage user roles with different permissions

### 2. Federation Support
- **Federated Architecture**: Ability to deploy software on federated decentralized architecture
- **Server Federation**: Support for connecting to other federated servers

### 3. User Management
- **Add Users**: Ability to add new users through the admin panel
- **User List**: Display and manage list of users

### 4. Bulk User Creation
- **Excel Import**: Excel-based solution in admin panel for creating users (Bulk List)
- **Bulk Operations**: Import multiple users from Excel file

### 5. User List Export
- **Export Formats**: Ability to export user list as:
  - Excel file (.xlsx)
  - PDF file
- **Export Options**: Various export options and filters

### 6. Alert System
- **Alert Types**: Send alerts to users in different modes:
  - Single broadcast (to one user)
  - Multi broadcast (to multiple users)
  - All broadcast (to all users)
- **Alert Management**: Create, send, and manage alerts

### 7. Online Users Monitoring
- **Real-time Display**: Display online users in real-time
- **User Information**: Show:
  - IP address
  - Mobile device specifications
- **Live Updates**: Real-time updates of online status

### 8. Resource Usage Monitoring
- **Hardware Resources**: Display hardware resource usage in real-time
- **Time Periods**: Show resources by:
  - Hour
  - Day
  - Week
  - Month
- **Visualization**: Display in graphic charts (real-time and aggregated)
- **Resource Types**: Monitor different resource types (CPU, Memory, Network, etc.)

### 9. Work Groups Management
- **Group Creation**: Ability to define work groups for users and admins
- **User Assignment**: Add or remove different users to/from groups
- **Group Management**: Manage group memberships

### 10. Room Management
- **User Removal**: Remove users from rooms
- **User Addition**: Add users to rooms
- **Room Administration**: Administrative control over room membership

### 11. File Upload Configuration
- **Size Limits**: Ability to configure file upload size limits
- **File Type Restrictions**: Configure allowed file types
- **Upload Settings**: Various upload-related settings

---

## Server Features

### 1. Dockerized Services
- **Containerization**: Design and configure services as Dockerized
- **Docker Compose**: Use Docker Compose for service orchestration
- **Service Isolation**: Each service should be containerized

### 2. Kubernetes Management
- **Container Orchestration**: Manage containers using Kubernetes
- **K8s Deployment**: Deploy services on Kubernetes cluster
- **Scaling**: Support for horizontal scaling

### 3. Federation Support
- **Server Federation**: Ability to connect to other servers as federation
- **Federation Protocol**: Implement Matrix federation protocol
- **Server Discovery**: Discover and connect to federated servers

### 4. Admin Dashboard
- **Dashboard Panel**: Server should be equipped with admin dashboard panel
- **Web Interface**: Web-based admin interface
- **All Admin Features**: Support all admin dashboard features listed above

---

## Technical Requirements

### Database
- **PostgreSQL**: Use PostgreSQL as the database
- **Security**: Implement database security best practices
- **Recovery**: Implement recovery mechanisms
- **Concurrency**: Handle concurrency properly

### Security
- **E2EE**: End-to-End Encryption support
- **CIS Benchmark**: Harden operating system based on CIS Benchmark security guidelines
- **Penetration Testing**: Perform penetration testing and security evaluation
- **Threat Analysis**: Analyze threats and vulnerabilities

### DevOps
- **Docker**: Containerization with Docker
- **Kubernetes**: Container orchestration with Kubernetes
- **CI/CD**: Continuous Integration and Deployment
- **Monitoring**: System monitoring and logging

### Documentation
- **Technical Documentation**: Protocol documentation
- **Implementation Guide**: Implementation guide
- **Security Report**: Security testing and evaluation report
- **Code Documentation**: Code comments and documentation

---

## Project Phases

### Phase 1: Analysis and Design
- Requirements analysis
- Study of existing state
- PostgreSQL database review
- E2EE algorithm review

### Phase 2: Initial Server Implementation
- Server design and configuration
- Dockerization of services
- Kubernetes container management
- OS hardening based on CIS Benchmark

### Phase 3: Initial Client Implementation
- Android client design and implementation
- All Android features implementation

### Phase 4: Admin Dashboard Implementation
- Web dashboard design and implementation
- All admin features implementation

### Phase 5: Optimization and Documentation
- Protocol documentation
- Implementation guide preparation
- Results report

### Phase 6: Final Phase and Delivery
- Final report preparation
- Oral presentation/defense session
- Code sample publication in Git repository (if permitted)

---

## Evaluation Methods

- **Functional Testing**: Functional tests
- **Server Stress Testing**: Server stress tests
- **Security Testing**: Security tests for client and server
- **Compliance Check**: Verify output matches specifications

---

## Notes

- This project is a research/initial prototype implementation
- For commercial use, additional performance reviews and confirmations will be needed
- All features should be implemented following best practices
- Code should be well-documented and maintainable
- Security should be a top priority throughout development
