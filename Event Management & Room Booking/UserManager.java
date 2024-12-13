import java.util.HashMap;
import java.util.Map;

public class UserManager {
    private static Map<String, User> users = new HashMap<>();

    public static void manageUser(User user, String action) {
        switch (action) {
            case "create":
                users.put(user.getUsername(), user);
                break;
            case "edit":
                users.put(user.getUsername(), user);
                break;
            case "deactivate":
                users.remove(user.getUsername());
                break;
            default:
                throw new IllegalArgumentException("Invalid action");
        }
    }

    public static User getUserByRole(String role) {
        for (User user : users.values()) {
            if (role.equals("ApprovalAdmin") && user instanceof ApprovalAdmin) {
                return user;
            } else if (role.equals("RoomBookingAdmin") && user instanceof RoomBookingAdmin) {
                return user;
            } else if (role.equals("SuperAdmin") && user instanceof SuperAdmin) {
                return user;
            }
        }
        return null;
    }

    public static User getUserByUsername(String username) {
        return users.get(username);
    }
}
