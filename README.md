
# Java Console Music Platform

A lightweight, console-based Java application that simulates a simplified version of a music platform like Genius. Users can register with different roles (Regular User, Artist, Admin), interact through role-based menus, follow artists, and manage users and songs.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Project Structure](#project-structure)
- [How It Works](#how-it-works)
- [Follow System](#follow-system)
- [Data Storage](#data-storage)
- [How to Run](#how-to-run)
- [Future Improvements](#future-improvements)

---

## Overview
This project was built as a learning and practice exercise for Java backend development. It provides core functionalities of a music platform in a CLI environment, focusing on user management, file-based data storage, and role-based access logic.

---

## Features
- User registration and login
- Role-based menus:
  - Regular Users: Follow artists, explore music
  - Artists: Manage own music content
  - Admins: Manage all users
- File-based persistent storage
- Follow/unfollow system for users and artists
- Basic error handling and data validation

---

## Project Structure

```
src/
│
├── org.example/
│   ├── Main.java                     // Entry point, handles login/signup and user routing
│   ├── Role.java                     // Role definition class
│   ├── User.java                     // User model class
│
├── org.example.managers/
│   └── UserManager.java              // Manages user data, loading/saving users and follow info
│
├── org.example.userRole/
│   ├── UserMenu.java                 // Menu logic for regular users
│   ├── ArtistMenu.java               // Menu logic for artists
│   └── AdminMenu.java                // Menu logic for admins
```

---

## How It Works

### 1. Sign Up / Login
When the app starts, users can either register or login. During registration, they choose a role (Regular User or Artist). Admin role is reserved and not listed during signup.

### 2. Role-Based Menus
After login, the system checks the role of the user and redirects to a role-specific menu:

- `UserMenu` for regular users
- `ArtistMenu` for artists
- `AdminMenu` for admins

Each menu contains custom features depending on the user’s role.

---

## Follow System

Implemented in `UserManager.java`, the follow system allows:
- Regular users to follow or unfollow artists
- Users to see the list of artists they follow
- Artists to see their followers

### Key Methods Used:
- `followArtist(String user, String artist)`
- `unfollowArtist(String user, String artist)`
- `isFollowing(String user, String artist)` — prevents duplicate follows
- `getFollowers(String artist)` — returns set of usernames following an artist

### Optimization
To avoid redundant follows, the app uses `isFollowing()` inside the follow logic. This ensures that if a user tries to follow the same artist twice, the system alerts them instead.

---

## Data Storage

The app uses simple CSV files to store data persistently:
- `users.csv`: Stores all registered users along with their roles
- `followers.csv`: Stores user-artist follow relationships

These files are read at startup and written to after each major data change (e.g., new signup, new follow, etc.).

---

## How to Run

1. Clone the repo.
2. Open the project in an IDE (like IntelliJ or VS Code).
3. Ensure the working directory is set to the root of the project.
4. Run `Main.java`.

Ensure you have Java SDK (preferably Java 8 or later) installed.

---

## Future Improvements

- Add a song system for artists to upload and manage music
- Improve input validation and security (e.g., password encryption)
- Migrate from file-based storage to a database
- Add user comments and likes on songs
- Improve the UI using a web framework or GUI

---

## License

This project was developed for educational purposes and is not licensed for production use.
