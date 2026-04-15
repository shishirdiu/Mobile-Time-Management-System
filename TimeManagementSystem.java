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
    public String getAppName() { return appName; }
    public int getUsedMinutes() { return usedMinutes; }
    public AppCategory getCategory() { return category; }
    public String getDay() { return day; }
}
class TimeTracker {
    private User user;
    private List<Session> sessions;
    private int totalUsedToday;

    public TimeTracker(User user) {
        this.user = user;
        this.sessions = new ArrayList<>();
        this.totalUsedToday = 0;
    }

    public void addUsage(String appName, int minutes, AppCategory category, String day) {
        try {
            if (minutes <= 0) {
                throw new InvalidTimeException("Time cannot be zero or negative!");
            }

            if (totalUsedToday + minutes > user.getDailyLimit()) {
                throw new TimeLimitExceededException("Alert! " + user.getName() + " has exceeded the daily limit of " + user.getDailyLimit() + " mins.");
            }

            sessions.add(new Session(appName, minutes, category, day));
            totalUsedToday += minutes;
            System.out.println("  [Success] Added: " + appName + " (" + minutes + " min). Total used: " + totalUsedToday + " min.");
            double usagePercent = ((double) totalUsedToday / user.getDailyLimit()) * 100;
            if (usagePercent >= 80 && usagePercent < 100) {
                System.out.println("  WARNING: You have used " + (int)usagePercent + "% of your limit! ");
            }

        } catch (InvalidTimeException | TimeLimitExceededException e) {
            System.out.println("  [Error] " + e.getMessage());
        } finally {
            System.out.println(" System checked for " + appName + " ");
        }
    }
    public void showDailySummary() {
        System.out.println("\n--- Usage Summary for " + user.getName() + " ---");
        for (Session s : sessions) {
            System.out.println("  " + s.getAppName() + " [" + s.getCategory() + "] : " + s.getUsedMinutes() + " min on " + s.getDay());
        }
        System.out.println("  Total used today: " + totalUsedToday + " min");
    }
}
public class MobileTimeManagement {
    public static void main(String[] args) {
        System.out.println("STARTING MOBILE TIME MANAGEMENT SYSTEM...\n");

        User kid = new ChildUser("Rafi", 8, "Rahman Saheb");
        User person = new AdultUser("Sadia", 24, "Software Engineer");

        kid.showReport();
        person.showReport();
        System.out.println("\n>> Tracking Child Usage:");
        TimeTracker kidTracker = new TimeTracker(kid);
        kidTracker.addUsage("YouTube Kids", 25, AppCategory.SOCIAL, "Monday");
        kidTracker.addUsage("Roblox", 25, AppCategory.GAMING, "Monday");
        kidTracker.addUsage("Math App", 15, AppCategory.EDUCATION, "Monday");

        kidTracker.showDailySummary();
        System.out.println("\n>> Tracking Adult Usage:");
        TimeTracker adultTracker = new TimeTracker(person);
        adultTracker.addUsage("IntelliJ IDEA", 120, AppCategory.EDUCATION, "Monday");
        adultTracker.addUsage("Facebook", 30, AppCategory.SOCIAL, "Monday");
        adultTracker.addUsage("Negative Test", -10, AppCategory.OTHER, "Monday");

        adultTracker.showDailySummary();
        System.out.println("\n>> Polymorphism in Action (List of Users):");
        List<User> userList = new ArrayList<>();
        userList.add(kid);
        userList.add(person);
        userList.add(new ChildUser("Toma", 5, "Karim Saheb"));

        for(User u : userList) {
            System.out.println("User: " + u.getName() + " | Type: " + u.getUserType());
        }

        System.out.println("\nPROGRAM FINISHED.");
    }
}
