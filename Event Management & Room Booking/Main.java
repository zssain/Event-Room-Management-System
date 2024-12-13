import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static StandardUser currentUser = null;

    public static void main(String[] args) {
        // Load rooms from the CSV file
        String csvFilePath = "rooms.csv";
        RoomManager.loadRoomsFromCSV(csvFilePath);

        // Initialize system settings
        Settings settings = Settings.getInstance();
        settings.setEventCapacityLimits(100);
        settings.setNotificationPreferences(true);
        settings.setRoomUsageRules("Standard rules apply.");

        // Create users
        StandardUser userJohn = new StandardUser("john_doe", "password123");
        ApprovalAdmin approvalAdmin = new ApprovalAdmin("admin_approval", "securePass", "A001");
        RoomBookingAdmin roomBookingAdmin = new RoomBookingAdmin("admin_room", "securePass", "R001");
        SuperAdmin superAdmin = new SuperAdmin("super_admin", "superSecure", "S001");

        // Add users to the system
        UserManager.manageUser(userJohn, "create");
        UserManager.manageUser(approvalAdmin, "create");
        UserManager.manageUser(roomBookingAdmin, "create");
        UserManager.manageUser(superAdmin, "create");

        // Start the application
        showMainMenu();
    }

    private static void showMainMenu() {
        while (true) {
            System.out.println("\n--- Event Management System ---");
            System.out.println("1. Login as Standard User");
            System.out.println("2. Login as Approval Admin");
            System.out.println("3. Login as Room Booking Admin");
            System.out.println("4. Login as Super Admin");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    loginAsStandardUser();
                    break;
                case "2":
                    loginAsApprovalAdmin();
                    break;
                case "3":
                    loginAsRoomBookingAdmin();
                    break;
                case "4":
                    loginAsSuperAdmin();
                    break;
                case "5":
                    System.out.println("Exiting the system.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void loginAsStandardUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = UserManager.getUserByUsername(username);
        if (user instanceof StandardUser && user.login(username, password)) {
            currentUser = (StandardUser) user;
            System.out.println("Login successful.");
            standardUserMenu();
        } else {
            System.out.println("Invalid credentials or user type.");
        }
    }

    private static void standardUserMenu() {
        while (true) {
            System.out.println("\n--- Standard User Menu ---");
            System.out.println("1. View Events");
            System.out.println("2. Create Event");
            System.out.println("3. View Notifications");
            System.out.println("4. Logout");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    viewEvents();
                    break;
                case "2":
                    createEvent();
                    break;
                case "3":
                    viewNotifications();
                    break;
                case "4":
                    currentUser = null;
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void viewEvents() {
        List<Event> events = currentUser.viewEvents();
        System.out.println("\n--- Approved Events ---");
        for (Event event : events) {
            System.out.println("Event ID: " + event.getEventId());
            System.out.println("Name: " + event.getName());
            System.out.println("Description: " + event.getDescription());
            System.out.println("Timeslot: " + event.getTimeslot().getStartTime() + " to " + event.getTimeslot().getEndTime());
            System.out.println("Room: " + event.getRoom().getName());
            System.out.println("Status: " + event.getStatus());
            System.out.println("-------------------------");
        }
    }

    private static void createEvent() {
        System.out.print("Enter event name: ");
        String name = scanner.nextLine();
        System.out.print("Enter event description: ");
        String description = scanner.nextLine();
        System.out.print("Enter expected attendance: ");
        int attendance = Integer.parseInt(scanner.nextLine());

        System.out.print("Enter start time (YYYY-MM-DDTHH:MM): ");
        LocalDateTime startTime = LocalDateTime.parse(scanner.nextLine());
        System.out.print("Enter end time (YYYY-MM-DDTHH:MM): ");
        LocalDateTime endTime = LocalDateTime.parse(scanner.nextLine());
        Timeslot timeslot = new Timeslot(startTime, endTime);

        // Get rooms with available capacity greater than or equal to the required attendance
        List<Room> availableRooms = RoomManager.getAvailableRooms(timeslot, attendance);
        if (availableRooms.isEmpty()) {
            System.out.println("No rooms available for the selected timeslot and capacity.");
            return;
        }

        System.out.println("Available Rooms:");
        int index = 1;
        for (Room room : availableRooms) {
            System.out.println(index + ". " + room.getName() + " (Capacity: " + room.getCapacity() + ")");
            index++;
        }
        System.out.print("Select a room by number: ");
        int roomChoice = Integer.parseInt(scanner.nextLine());
        Room selectedRoom = availableRooms.get(roomChoice - 1);

        Event event = new Event("E" + System.currentTimeMillis(), name, description, timeslot, attendance, currentUser);
        event.setRoom(selectedRoom);

        currentUser.createEvent(event);
        System.out.println("Event created and submitted for approval.");
    }

    private static void viewNotifications() {
        List<Notification> notifications = currentUser.getNotifications();
        if (notifications.isEmpty()) {
            System.out.println("No notifications.");
        } else {
            System.out.println("\n--- Notifications ---");
            for (Notification notification : notifications) {
                System.out.println(notification.getMessage());
            }
        }
    }

    private static void loginAsApprovalAdmin() {
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();

        User user = UserManager.getUserByUsername(username);
        if (user instanceof ApprovalAdmin && user.login(username, password)) {
            ApprovalAdmin approvalAdmin = (ApprovalAdmin) user;
            System.out.println("Login successful.");
            approvalAdminMenu(approvalAdmin);
        } else {
            System.out.println("Invalid credentials or user type.");
        }
    }

    private static void approvalAdminMenu(ApprovalAdmin admin) {
        while (true) {
            System.out.println("\n--- Approval Admin Menu ---");
            System.out.println("1. Review Pending Events");
            System.out.println("2. Logout");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    reviewPendingEvents(admin);
                    break;
                case "2":
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void reviewPendingEvents(ApprovalAdmin admin) {
        List<Event> pendingEvents = EventManager.getPendingEvents();
        if (pendingEvents.isEmpty()) {
            System.out.println("No pending events.");
            return;
        }

        for (Event event : pendingEvents) {
            System.out.println("\nEvent ID: " + event.getEventId());
            System.out.println("Name: " + event.getName());
            System.out.println("Description: " + event.getDescription());
            System.out.println("Timeslot: " + event.getTimeslot().getStartTime() + " to " + event.getTimeslot().getEndTime());
            System.out.println("Expected Attendance: " + event.getExpectedAttendance());
            System.out.println("Room Requested: " + event.getRoom().getName());

            boolean hasConflict = RoomManager.checkForConflicts(event);
            if (hasConflict) {
                System.out.println("Conflict Warning: This room has a pending booking for the same timeslot.");
            }

            System.out.print("Approve this event? (yes/no): ");
            String decision = scanner.nextLine();
            if (decision.equalsIgnoreCase("yes")) {
                admin.approveEvent(event);
                System.out.println("Event approved.");
            } else {
                System.out.print("Enter rejection reason: ");
                String reason = scanner.nextLine();
                admin.rejectEvent(event, reason);
                System.out.println("Event rejected.");
            }
        }
    }

    private static void loginAsRoomBookingAdmin() {
        System.out.print("Enter admin username: ");
        String username = scanner.nextLine();
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();

        User user = UserManager.getUserByUsername(username);
        if (user instanceof RoomBookingAdmin && user.login(username, password)) {
            RoomBookingAdmin roomBookingAdmin = (RoomBookingAdmin) user;
            System.out.println("Login successful.");
            roomBookingAdminMenu(roomBookingAdmin);
        } else {
            System.out.println("Invalid credentials or user type.");
        }
    }

    private static void roomBookingAdminMenu(RoomBookingAdmin admin) {
        while (true) {
            System.out.println("\n--- Room Booking Admin Menu ---");
            System.out.println("1. Confirm Room Bookings");
            System.out.println("2. Logout");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    confirmRoomBookings(admin);
                    break;
                case "2":
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void confirmRoomBookings(RoomBookingAdmin admin) {
        List<Event> approvedEvents = EventManager.getApprovedEvents();
        for (Event event : approvedEvents) {
            if (event.getStatus() == EventStatus.APPROVED) {
                System.out.println("\nEvent ID: " + event.getEventId());
                System.out.println("Name: " + event.getName());
                System.out.println("Room: " + event.getRoom().getName());
                admin.confirmRoomBooking(event);
                System.out.println("Room booking confirmed for event: " + event.getName());
            }
        }
    }

    private static void loginAsSuperAdmin() {
        System.out.print("Enter super admin username: ");
        String username = scanner.nextLine();
        System.out.print("Enter super admin password: ");
        String password = scanner.nextLine();

        User user = UserManager.getUserByUsername(username);
        if (user instanceof SuperAdmin && user.login(username, password)) {
            SuperAdmin superAdmin = (SuperAdmin) user;
            System.out.println("Login successful.");
            superAdminMenu(superAdmin);
        } else {
            System.out.println("Invalid credentials or user type.");
        }
    }

    private static void superAdminMenu(SuperAdmin admin) {
        while (true) {
            System.out.println("\n--- Super Admin Menu ---");
            System.out.println("1. Manage Users");
            System.out.println("2. Override Event Decision");
            System.out.println("3. Logout");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    manageUsers(admin);
                    break;
                case "2":
                    overrideEventDecision(admin);
                    break;
                case "3":
                    System.out.println("Logged out.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void manageUsers(SuperAdmin admin) {
        System.out.println("\n--- Manage Users ---");
        System.out.println("1. Create User");
        System.out.println("2. Deactivate User");
        System.out.print("Select an option: ");
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                System.out.print("Enter new username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();
                StandardUser newUser = new StandardUser(username, password);
                admin.manageUsers(newUser, "create");
                System.out.println("User created.");
                break;
            case "2":
                System.out.print("Enter username to deactivate: ");
                String userToDeactivate = scanner.nextLine();
                User user = UserManager.getUserByUsername(userToDeactivate);
                if (user != null) {
                    admin.manageUsers(user, "deactivate");
                    System.out.println("User deactivated.");
                } else {
                    System.out.println("User not found.");
                }
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private static void overrideEventDecision(SuperAdmin admin) {
        System.out.print("Enter event ID to override: ");
        String eventId = scanner.nextLine();
        Event event = EventManager.getEventById(eventId);
        if (event != null) {
            System.out.print("Enter new status (APPROVED/REJECTED): ");
            String status = scanner.nextLine();
            if (status.equalsIgnoreCase("APPROVED")) {
                admin.overrideDecision(event, EventStatus.APPROVED);
                System.out.println("Event status overridden to APPROVED.");
            } else if (status.equalsIgnoreCase("REJECTED")) {
                admin.overrideDecision(event, EventStatus.REJECTED);
                System.out.println("Event status overridden to REJECTED.");
            } else {
                System.out.println("Invalid status.");
            }
        } else {
            System.out.println("Event not found.");
        }
    }
}

