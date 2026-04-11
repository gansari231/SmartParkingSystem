# 🚗 Smart Parking System – Backend (Spring Boot)

## 📌 Overview
This project is a **Smart Parking Lot Backend System** built using **Spring Boot, Java, MySQL, and Gradle**.  
It simulates a real-world parking management system that handles **vehicle entry, spot allocation, exit processing, and fee calculation** with proper concurrency handling.

---

## 🧠 Key Features

### 🚘 1. Vehicle Entry (Parking)
- Accepts vehicle details (number + type)
- Automatically assigns an available parking spot based on vehicle type:
  - BIKE
  - CAR
  - BUS
- Generates a parking ticket
- Marks the spot as occupied

---

### 🅿️ 2. Smart Spot Allocation
- Allocates parking spots dynamically based on availability
- Uses **pessimistic locking** to prevent double booking
- Ensures only one vehicle gets a spot at a time

---

### ⏱️ 3. Vehicle Exit
- Fetches parking ticket using ticket ID
- Calculates total parking duration
- Frees the parking spot
- Marks ticket as **COMPLETED**

---

### 💰 4. Fee Calculation

Parking charges are calculated based on duration and vehicle type:

| Vehicle Type | Rate (per hour) |
|-------------|-----------------|
| BIKE        | ₹10             |
| CAR         | ₹20             |
| BUS         | ₹50             |

- Minimum charge: 1 hour  
- Uses `Duration` API for accurate time calculation  

---

### 📊 5. Real-Time Parking Stats API

Provides real-time parking insights:
- Total parking spots  
- Available spots  
- Occupied spots  

---

### ⚡ 6. Concurrency Handling

- Implemented using pessimistic locking (@Lock(PESSIMISTIC_WRITE))
- Prevents multiple users from booking the same spot simultaneously
- Handles race conditions safely

---

### 🧱 7. Clean Architecture

Layered Design:

- Controller Layer → Handles API requests
- Service Layer → Business logic
- Repository Layer → Database operations
- DTO Layer → Clean request/response models

---

### 📦 8. DTO-Based API Design

- Avoids exposing internal entity structure
- Uses: EntryRequestDTO, TicketResponseDTO, ParkingStatsDTO

---

### 🚨 9. Global Exception Handling

- Centralized error handling using @RestControllerAdvice
- Handles: Parking exceptions, Database errors, Concurrency conflicts
- Returns clean JSON responses

---

### 🌐 API Endpoints

▶️ Entry (Park Vehicle)
```http
POST /parking/entry
```

⏹️ Exit (Unpark Vehicle)
```http
POST /parking/exit/{ticketId}
```

📊 Get Parking Stats
```http
GET /parking/stats
```

---

### 🗃️ Database Design

Tables:

- parking_spot
- vehicle
- ticket

Relationships:

- One Vehicle → One Ticket
- One Ticket → One Parking Spot
- One Spot → Many Tickets (over time)
