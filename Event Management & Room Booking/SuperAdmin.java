public class SuperAdmin extends Admin {

    public SuperAdmin(String username, String password, String adminId) {
        super(username, password, adminId);
    }

    public void manageUsers(User user, String action) {
        // Create, edit, or deactivate user accounts
        UserManager.manageUser(user, action);
    }

    @Override
    public void approveEvent(Event event) {
        event.updateStatus(EventStatus.APPROVED);
        EventManager.moveToApproved(event);
        // Notify relevant parties
        Notification notification = new Notification(
            "N" + System.currentTimeMillis(),
            "Your event has been approved by Super Admin.",
            event.getCreator()
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
            "Your event has been rejected by Super Admin. Reason: " + reason,
            event.getCreator()
        );
        notification.send();
    }

    public void overrideDecision(Event event, EventStatus newStatus) {
        event.updateStatus(newStatus);
        // Notify relevant parties
        Notification notification = new Notification(
            "N" + System.currentTimeMillis(),
            "Event status has been overridden to: " + newStatus,
            event.getCreator()
        );
        notification.send();
    }

    public void configureSettings(Settings newSettings) {
        // Update application-level settings
        Settings.getInstance().updateSettings(newSettings);
    }
}
