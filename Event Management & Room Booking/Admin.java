public abstract class Admin extends User {
    protected String adminId;

    public Admin(String username, String password, String adminId) {
        super(username, password);
        this.adminId = adminId;
    }

    public abstract void approveEvent(Event event);

    public abstract void rejectEvent(Event event, String reason);
}
