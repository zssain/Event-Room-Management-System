public class Settings {
    private boolean notificationPreferences;
    private int eventCapacityLimits;
    private String roomUsageRules;

    // Singleton pattern
    private static Settings instance = null;

    private Settings() {
        // Default settings
        notificationPreferences = true;
        eventCapacityLimits = 100;
        roomUsageRules = "Default room usage rules.";
    }

    public static Settings getInstance() {
        if (instance == null) {
            instance = new Settings();
        }
        return instance;
    }

    public void updateSettings(Settings newSettings) {
        this.notificationPreferences = newSettings.notificationPreferences;
        this.eventCapacityLimits = newSettings.eventCapacityLimits;
        this.roomUsageRules = newSettings.roomUsageRules;
    }

    // Setters
    public void setNotificationPreferences(boolean notificationPreferences) {
        this.notificationPreferences = notificationPreferences;
    }

    public void setEventCapacityLimits(int eventCapacityLimits) {
        this.eventCapacityLimits = eventCapacityLimits;
    }

    public void setRoomUsageRules(String roomUsageRules) {
        this.roomUsageRules = roomUsageRules;
    }
}
