# SmartKharch

## 📌 App Overview
SmartKharch is an expense tracking and reporting app built with **Jetpack Compose**, **MVVM architecture**, and **Hilt** for dependency injection.  
It provides users with visual insights into daily and category-wise spending using **bar charts** and **category summaries**.

---

## 🤖 AI Usage Summary
During the development of SmartKharch, AI tool **ChatGPT** were used to:
- Generate Jetpack Compose UI components like `SimpleBarChart` and custom-styled dialogs.
- Debug layout alignment issues (e.g., bars growing from bottom to top).
- Optimize `ViewModel` data flow using `StateFlow` and mock data generation.

---

## 📝 Prompt Logs (Key Examples)
- I want to build an Android expense tracking app from scratch using Jetpack Compose, MVVM, and Hilt. Can you give me a feature list and folder structure?
- Suggest ways to make my expense report UI look modern and professional in Jetpack Compose.
- I need to handle loading, success, and error states in my expense report screen. Can you show me how to model UI state using sealed classes with StateFlow?
- Help me set up Hilt in my app. I want to inject my ExpenseRepository into the ExpenseReportViewModel using constructor injection.
- Now that the app is mostly done, can you create a README.md with an overview, how I used AI tools like ChatGPT, prompt logs, features implemented, screenshots, and an APK link?

---

## ✅ Features Implemented
- 📊 Bar chart visualization for last 7 days expenses (MOCK).
- 📂 Category-wise expense breakdown (Food, Travel, Staff, Utility).
- 📅 Date picker with min/max date constraints.
- 🗄 MVVM architecture with Hilt dependency injection.
- 🔄 Reactive UI updates with `StateFlow`.
- 🧪 Mock data generation for development/testing.

---

## 📥 APK Download
[Download APK](https://www.dropbox.com/scl/fi/2qdeokjtezvdym3teceoc/SmartKharch.apk?rlkey=kdu9din4msz292fi4zks90pes&st=i7qp18vx&dl=0)

---

## 📸 Screenshots
<h4>Expense List</h4>
<img src="screenshots/ExpenseList.png" width="300"/>

<h4>Add Expense</h4>
<img src="screenshots/AddExpense.png" width="300"/>

<h4>Expense Report</h4>
<img src="screenshots/ExpenseReport.png" width="300"/>
---
