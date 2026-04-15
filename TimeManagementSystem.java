import java.util.*;
interface Reportable {
    void showReport();
    void showWeeklyReport();
}

enum AppCategory {
    SOCIAL, GAMING, EDUCATION, OTHER
}
class TimeLimitExceededException extends Exception {
    public TimeLimitExceededException(String msg) { super(msg); }
}

class InvalidTimeException extends Exception {
    public InvalidTimeException(String msg) { super(msg); }
}
abstract class User implements Reportable {
    private String name;
    private int age;
    private int dailyLimitMinutes;

    public User(String name, int age, int dailyLimitMinutes) {
        this.name = name;
        this.age = age;
        this.dailyLimitMinutes = dailyLimitMinutes;
    }
    public String getName() { return name; }
    public int getAge() { return age; }
    public int getDailyLimit() { return dailyLimitMinutes; }
    public abstract String getUserType();
