import java.util.HashMap;
import java.util.Map;

public class Room {
    private String roomId;
    private String name;
    private String location;
    private int capacity;
    private Map<Timeslot, Boolean> availability;

    public Room(String roomId, String name, String location, int capacity) {
        this.roomId = roomId;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.availability = new HashMap<>();
    }

    public boolean isAvailable(Timeslot timeslot) {
        for (Timeslot bookedSlot : availability.keySet()) {
            if (bookedSlot.overlapsWith(timeslot)) {
                return false;
            }
        }
        return true;
    }

    public void bookRoom(Timeslot timeslot) {
        availability.put(timeslot, true);
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }
}
