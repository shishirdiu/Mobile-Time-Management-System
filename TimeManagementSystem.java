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
