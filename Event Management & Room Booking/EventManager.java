import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private static List<Event> eventRequests = new ArrayList<>();
    private static List<Event> approvedEvents = new ArrayList<>();

    public static void submitEventRequest(Event event) {
        eventRequests.add(event);
        // Notify ApprovalAdmin
        Notification notification = new Notification(
            "N" + System.currentTimeMillis(),
            "A new event request has been submitted.",
            UserManager.getUserByRole("ApprovalAdmin")
        );
        notification.send();
    }

    public static List<Event> getPendingEvents() {
        List<Event> pendingEvents = new ArrayList<>();
        for (Event event : eventRequests) {
            if (event.getStatus() == EventStatus.PENDING_APPROVAL) {
                pendingEvents.add(event);
            }
        }
        return pendingEvents;
    }

    public static List<Event> getApprovedEvents() {
        return new ArrayList<>(approvedEvents);
    }

    public static void moveToApproved(Event event) {
        eventRequests.remove(event);
        approvedEvents.add(event);
    }

    public static void cancelEventRequest(Event event) {
        event.updateStatus(EventStatus.CANCELLED);
        eventRequests.remove(event);
        // Notify relevant parties
    }

    public static Event getEventById(String eventId) {
        for (Event event : eventRequests) {
            if (event.getEventId().equals(eventId)) {
                return event;
            }
        }
        for (Event event : approvedEvents) {
            if (event.getEventId().equals(eventId)) {
                return event;
            }
        }
        return null;
    }
}
