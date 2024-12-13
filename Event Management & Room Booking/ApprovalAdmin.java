import java.util.List;

public class ApprovalAdmin extends Admin {

    public ApprovalAdmin(String username, String password, String adminId) {
        super(username, password, adminId);
    }

    public void reviewEventRequests() {
        List<Event> pendingEvents = EventManager.getPendingEvents();
        // Logic to review each event can be implemented here
    }

    @Override
    public void approveEvent(Event event) {
        event.updateStatus(EventStatus.APPROVED);
        EventManager.moveToApproved(event);
        // Notify RoomBookingAdmin
        Notification notification = new Notification(
            "N" + System.currentTimeMillis(),
            "An event has been approved and needs room booking confirmation.",
            UserManager.getUserByRole("RoomBookingAdmin")
        );
        notification.send();
    }

    @Override
    public void rejectEvent(Event event, String reason) {
        event.updateStatus(EventStatus.REJECTED);
        event.addComment(reason);
        // Notify user
        Notification notification = new Notification(
            "N" + System.currentTimeMillis(),
            "Your event has been rejected. Reason: " + reason,
            event.getCreator()
        );
        notification.send();
    }
}
