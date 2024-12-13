import java.util.ArrayList;
import java.util.List;

public class Event {
    private String eventId;
    private String name;
    private String description;
    private Timeslot timeslot;
    private int expectedAttendance;
    private Room room;
    private EventStatus status;
    private List<String> comments;
    private StandardUser creator;

    public Event(String eventId, String name, String description, Timeslot timeslot, int expectedAttendance, StandardUser creator) {
        this.eventId = eventId;
        this.name = name;
        this.description = description;
        this.timeslot = timeslot;
        this.expectedAttendance = expectedAttendance;
        this.comments = new ArrayList<>();
        this.status = EventStatus.PENDING_APPROVAL;
        this.creator = creator;
    }

    public void updateStatus(EventStatus status) {
        this.status = status;
    }

    public void addComment(String comment) {
        comments.add(comment);
    }

    // Getters and setters
    public String getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {   // Added missing getter
        return description;
    }

    public Timeslot getTimeslot() {
        return timeslot;
    }

    public int getExpectedAttendance() {
        return expectedAttendance;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public EventStatus getStatus() {
        return status;
    }

    public StandardUser getCreator() {
        return creator;
    }
}
