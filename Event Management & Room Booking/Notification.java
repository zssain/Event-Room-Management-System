import java.time.LocalDateTime;

public class Notification {
    private String notificationId;
    private String message;
    private User recipient;
    private LocalDateTime timestamp;

    public Notification(String notificationId, String message, User recipient) {
        this.notificationId = notificationId;
        this.message = message;
        this.recipient = recipient;
        this.timestamp = LocalDateTime.now();
    }

    public void send() {
        recipient.receiveNotification(this);
    }

    // Getters
    public String getMessage() {
        return message;
    }
}
