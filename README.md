# Flavyo 🍦 - Flavours that melt you!

Flavyo is a modern, full-stack Ice Cream ordering and management application. Built with **Jetpack Compose**, it features a seamless user experience for customers and a robust administrative dashboard for business owners.

## 🚀 Features

### **For Customers (Users)**
* **Intuitive UI:** A beautiful, responsive interface built entirely with Jetpack Compose.
* **Menu Browsing:** Real-time ice cream inventory synced from Google Sheets.
* **Smart Cart:** Add, remove, and manage items with instant total calculation.
* **Live Ordering:** Place orders that sync directly to a central ledger.
* **Order History:** View past orders and status updates.

### **For Business Owners (Admins)**
* **Admin Dashboard:** High-level summary of Today's Revenue, Pending, and Completed orders.
* **Order Management:** * **Accept Orders:** One-click acceptance that highlights the Google Sheet row **Green**.
    * **Cancel Orders:** Instantly cancel and mark the Google Sheet row **Red**.
* **Inventory Control:** Add, update, or hide menu items.
* **User Management:** Oversee user profiles and create new staff accounts.

---

## 🛠 Tech Stack

-   **Frontend:** Kotlin, Jetpack Compose, Material 3.
-   **Networking:** Retrofit 2, OkHttp, Gson.
-   **Architecture:** MVVM (Model-View-ViewModel), State Management.
-   **Backend:** Google Apps Script (Web App).
-   **Database:** Google Sheets (Inventory, Users, Admins, and Orders).
-   **Storage:** Shared Preferences for session management and user roles.
-   **Fonts:** Poppins (Interface), Covered By Your Grace (Branding).

---

## 📂 Project Structure

```text
com.example.flavyo
├── data                # Retrofit Client, API Interfaces, and Models
├── screens             # All UI Screen Composables (Login, Cart, AdminPanel, etc.)
├── ui.theme            # Custom Typography, Colors, and Theme definitions
├── components          # Reusable UI elements (Cards, Input Fields, Buttons)
└── Activities          # Lifecycle entry points (Home, Login, Admin, Onboarding)
