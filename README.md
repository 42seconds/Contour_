# Contour Expense Tracker App

## Overview
Contour is an Android expense tracking application developed in **Kotlin**.  
It allows users to record, categorize, and analyze expenses over a user-selectable period.  
Data is stored locally using **RoomDB** for offline access.

## Features
- Add, view, and edit expense entries
- Attach photos to expense records
- View total spending per category
- Filter transactions by date and category
- Error handling for invalid inputs
- Local data storage with RoomDB
- Automated build and testing via GitHub Actions

## 🧱 Tech Stack

| Component | Description |
|------------|-------------|
| **Language** | Kotlin |
| **Architecture** | MVVM (Model–View–ViewModel) |
| **Database** | Room |
| **UI** | XML Layouts + ViewBinding |
| **Async Tasks** | Coroutines (with `viewModelScope`) |
| **Libraries** | AndroidX Lifecycle, Room, Material Components |


## 🗂️ Project Structure

app/
├── data/
│ ├── model/ → Expense.kt
│ ├── dao/ → ExpenseDao.kt
│ └── database/ → ExpenseDatabase.kt
│
├── repository/ → ExpenseRepository.kt
│
├── ui/
│ ├── adapter/ → ExpenseAdapter.kt
│ ├── viewmodel/ → ExpenseViewModel.kt
│ └── view/ → MainActivity.kt
│
└── utils/ (optional)

yaml
Copy code


## 🖼️ Screenshots
Here are screenshots of the app in action:

![Splash Screen]<img width="1421" height="1013" alt="Screenshot 2026-04-28 223220" src="https://github.com/user-attachments/assets/07e702d9-6f0d-418b-ada6-b3e08eb94f9b" />

![Dashboard]<img width="1406" height="1018" alt="Screenshot 2026-04-28 223256" src="https://github.com/user-attachments/assets/5e19caeb-8545-482e-85de-9cfc1d497aa5" />


![Budget Overview]<img width="1387" height="999" alt="Screenshot 2026-04-28 223323" src="https://github.com/user-attachments/assets/b8c30cb8-d60f-4cd5-807d-1afd8db4c3b3" />



##  Demonstration Video
Below is the link to our the full demonstration app video:  
👉 [Contour App Expense Tracker – Demonstration Video (Part2 POE Submission)](https://www.youtube.com/watch?v=10BxFu65m_8)

##  Video Creation Tools
The demonstration video was created using:
- **OBS Studio** – screen recording and narration capture.
- **Microsoft Clipchamp** – video editing, background audio integration, and final export.


##  APK Build
The compiled APK file is included in the repository for testing.  
To install:
1. Download the APK from the repo.
2. Transfer it to your Android device.
3. Open and install manually.
4. https://github.com/42seconds/Contour/raw/main/app-debug.apk 


##  Team Members
- **Lavelle Dalman** – Development  
- **Timothy Ofentse Moremi** – Development  
- **Ivant Wambo** – Development  
- **Eden Gwenda** – Presentation, narration, and demonstration video production  

##  Module Information
**Module:** OPSC6311 – Open Source Coding  

## Submission Notes
- Source code and README hosted on GitHub (no ZIPs).  
- Screenshots included in README.  
- Video uploaded to YouTube and linked above.  
- APK included in repository.  
- Word document submitted separately with GitHub + YouTube + APK links.
