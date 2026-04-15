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
 public void showBasicInfo() {
        System.out.println("  Name       : " + name);
        System.out.println("  Age        : " + age);
        System.out.println("  User Type  : " + getUserType());
        System.out.println("  Daily Limit: " + dailyLimitMinutes + " min");
    }
}

class ChildUser extends User {
    private String parentName;

    public ChildUser(String name, int age, String parentName) {
        super(name, age, 60);
        this.parentName = parentName;
    }

    @Override
    public String getUserType() { return "Child"; }

    @Override
    public void showReport() {
        System.out.println("\n Child User Report ");
        showBasicInfo();
        System.out.println("  Parent     : " + parentName);
    }
    @Override
    public void showWeeklyReport() {
        System.out.println("  [Child] Total Weekly capacity = " + (getDailyLimit() * 7) + " min");
    }
}
class AdultUser extends User {
    private String job;

    public AdultUser(String name, int age, String job) {
        super(name, age, 180); 
        this.job = job;
    }

    @Override
    public String getUserType() { return "Adult"; }

    @Override
    public void showReport() {
        System.out.println("\n Adult User Report ");
        showBasicInfo();
        System.out.println("  Job        : " + job);
       
    }

 @Override
    public void showWeeklyReport() {
        System.out.println("  [Adult] Total Weekly capacity = " + (getDailyLimit() * 7) + " min");
    }
}

class Session {
    private String appName;
    private int usedMinutes;
    private AppCategory category;
    private String day;

    public Session(String appName, int usedMinutes, AppCategory category, String day) {
        this.appName = appName;
        this.usedMinutes = usedMinutes;
        this.category = category;
        this.day = day;
    }

