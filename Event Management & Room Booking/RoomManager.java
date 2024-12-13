import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoomManager {
    private static List<Room> rooms = new ArrayList<>();

    // Method to load rooms from a CSV file
    public static void loadRoomsFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                // Split line into parts, handling quotes and commas
                String[] data = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                if (data.length < 5) {
                    System.err.println("Invalid line format: " + line);
                    continue;
                }

                String name = data[0].trim();
                // Ignoring `Type` column (data[1])
                int capacity;
                try {
                    capacity = Integer.parseInt(data[2].trim());
                } catch (NumberFormatException e) {
                    System.err.println("Invalid capacity value for room " + name + ": " + data[2]);
                    continue;
                }
                String location = data[3].trim();
                String floor = data[4].trim();

                // Combine `location` and `floor` for a more descriptive location
                Room room = new Room(name, name, location + ", Floor " + floor, capacity);
                rooms.add(room);
            }
            System.out.println("Rooms loaded from CSV successfully.");
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }
    }

    public static void addRoom(Room room) {
        rooms.add(room);
    }

    // Method to get rooms with a minimum capacity and availability
    public static List<Room> getAvailableRooms(Timeslot timeslot, int minCapacity) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getCapacity() >= minCapacity && room.isAvailable(timeslot)) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    public static boolean checkForConflicts(Event event) {
        for (Event e : EventManager.getApprovedEvents()) {
            if (e.getRoom().equals(event.getRoom()) && e.getTimeslot().overlapsWith(event.getTimeslot())) {
                return true;
            }
        }
        return false;
    }
}
