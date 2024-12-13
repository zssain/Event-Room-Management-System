import java.util.ArrayList;
import java.util.List;

public abstract class User {
    protected String username;
    protected String password;
    protected List<Notification> notifications;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.notifications = new ArrayList<>();
    }

    public boolean login(String username, String password) {
        // Implement authentication logic
        return this.username.equals(username) && this.password.equals(password);
    }

    public void receiveNotification(Notification notification) {
        notifications.add(notification);
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }
}
