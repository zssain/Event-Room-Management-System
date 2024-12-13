import java.util.List;

public class StandardUser extends User {

    public StandardUser(String username, String password) {
        super(username, password);
    }

    public void createEvent(Event event) {
        // Submit event for approval
        EventManager.submitEventRequest(event);
    }

    public List<Event> viewEvents() {
        return EventManager.getApprovedEvents();
    }

    public void cancelEvent(Event event) {
        // Logic to cancel a pending event
        EventManager.cancelEventRequest(event);
    }
}
