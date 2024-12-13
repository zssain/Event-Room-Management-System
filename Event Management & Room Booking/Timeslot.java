import java.time.LocalDateTime;

public class Timeslot {
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Timeslot(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time.");
        }
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public boolean overlapsWith(Timeslot other) {
        return startTime.isBefore(other.endTime) && endTime.isAfter(other.startTime);
    }

    // Getters
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
