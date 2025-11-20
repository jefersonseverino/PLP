package li2.plp.expressions1.util;

public class Duration {
    public Integer totalSeconds;

    public Duration(Integer h, Integer m, Integer s) {
        Integer seconds = h * 3600;
        if (m != null) {
            seconds += m * 60;
        }
        if (s != null) {
            seconds += s;
        }
        this.totalSeconds = seconds;
    }

    public Duration(Integer totalSeconds) {
        this.totalSeconds = totalSeconds;
    }

    @Override
    public String toString() {
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}