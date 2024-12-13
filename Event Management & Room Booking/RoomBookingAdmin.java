import java.util.List;

public class RoomBookingAdmin extends Admin {

    public RoomBookingAdmin(String username, String password, String adminId) {
        super(username, password, adminId);
    }

    public void confirmRoomBooking(Event event) {
        if (event.getRoom().isAvailable(event.getTimeslot())) {
            event.getRoom().bookRoom(event.getTimeslot());
            event.updateStatus(EventStatus.BOOKING_CONFIRMED);
            // Notify user
            Notification notification = new Notification(
                "N" + System.currentTimeMillis(),
                "Your event room booking has been confirmed.",
                event.getCreator()
            );
            notification.send();
        } else {
            event.updateStatus(EventStatus.BOOKING_PENDING_CONFLICT);
            // Notify ApprovalAdmin or SuperAdmin
            Notification notification = new Notification(
                "N" + System.currentTimeMillis(),
                "Room booking conflict detected for event: " + event.getName(),
                UserManager.getUserByRole("ApprovalAdmin")
            );
            notification.send();
        }
    }

    public void assignAlternativeRoom(Event event, Room alternativeRoom) {
        event.setRoom(alternativeRoom);
        confirmRoomBooking(event);
    }

    @Override
    public void approveEvent(Event event) {
        // Not applicable
    }

    @Override
    public void rejectEvent(Event event, String reason) {
        // Not applicable
    }
}
