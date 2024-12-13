# Event-Room-Management-System
#### **Project Overview**
This project is an **Event and Room Management System** designed to manage event lifecycles, room bookings, and user roles. The code implements core Object-Oriented Programming (OOP) principles to ensure scalability and maintainability.

<a href="https://youtu.be/jQfo1JYeuZ4" target="_blank">
  <img src="https://github.com/user-attachments/assets/82a4bef2-9445-4fa2-9413-cf948651aebc" alt="Watch the video" style="height:300px;">
</a>

### link to playList
[https://www.youtube.com/playlist?list=PL5mRhauReKdsCVN-bsTxC6kvcl_J_b7tA](https://www.youtube.com/playlist?list=PL5mRhauReKdsCVN-bsTxC6kvcl_J_b7tA)
---

### Implementation Details

Our project is developed in two versions:

1. **Java Terminal Application**  
   A simple and straightforward implementation using Java, designed to run in the terminal. The code for this version is included in this repository.

2. **Web Application**  
   An advanced version with a polished frontend and a robust backend built using Flask and RESTful APIs. The code for this implementation is available in a dedicated repository: [https://github.com/YelavarthiLalitya/Room-Booking-System](https://github.com/YelavarthiLalitya/Room-Booking-System). 

**Please go through both**

We opted to keep the two implementations in separate repositories to maintain clarity and avoid confusion.

---

## 1. **Java Application**

#### **Prerequisites**
Before running the project, ensure you have the following installed:
1. **Java Development Kit (JDK)** (version 8 or above)
2. **Java Runtime Environment (JRE)** (compatible with your JDK)
3. **An IDE** (e.g., IntelliJ IDEA, Eclipse) or a text editor with a terminal (e.g., VSCode).
4. **Room Data File**:
   - Create a file named `rooms.csv` in the project directory with the following structure:
     ```
     Room Name,Type,Capacity,Location,Floor
     ECR 1,Classroom,120,Ecole Central School of Engineering,Ground Floor
     RConvention Center,Convention Hall,600,Convention Center,Ground Floor
     ```
---
#### **Setup Instructions**
1. **Download and Extract**:
   - Place all the provided `.java` files in a single directory named `EventManagementSystem`.
2. **Compile the Project**:
   - Open a terminal and navigate to the project directory.
   - Compile all Java files using:
     ```bash
     javac *.java
     ```
   - Ensure no compilation errors occur.
3. **Run the Project**:
   - Execute the `Main` class:
     ```bash
     java Main
     ```
   - The application will start and display the main menu.
---
#### **How to Use**
1. **Login as a User**:
   - Use the main menu to log in as a `StandardUser`, `ApprovalAdmin`, `RoomBookingAdmin`, or `SuperAdmin`.
   - Default credentials can be customized in the `Main.java` file during initialization.
2. **Functionality**:
   - **StandardUser**: Create events, view notifications, and cancel events.
   - **ApprovalAdmin**: Review and approve/reject event requests.
   - **RoomBookingAdmin**: Confirm room bookings or handle conflicts.
   - **SuperAdmin**: Override decisions, manage users, and configure settings.
---
#### **Customizing Settings**
- Modify system-wide configurations in `Settings.java`:
  - Enable/disable notifications.
  - Set event capacity limits.
  - Define room usage rules.
---
#### **File Dependencies**
- **`rooms.csv`**: Contains room data. Update this file to add or modify rooms.
- **Main.java**: Entry point for the application.
- **UserManager.java, RoomManager.java, EventManager.java**: Handle user, room, and event management logic.
---
#### **Troubleshooting**
- **Compilation Errors**: Ensure all `.java` files are in the same directory and compiled together.
- **Room Availability Issues**: Verify the `rooms.csv` file is properly formatted and includes enough capacity for test events.
- **Login Issues**: Ensure usernames and passwords match the ones initialized in `Main.java`.
