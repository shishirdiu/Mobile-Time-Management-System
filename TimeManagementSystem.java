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
        System.out.println("\n Usage Summary for " + user.getName() + " ");
        for (Session s : sessions) {
            System.out.println("  " + s.getAppName() + " [" + s.getCategory() + "] : " + s.getUsedMinutes() + " min on " + s.getDay());
        }
        System.out.println("  Total used today: " + totalUsedToday + " min");
    }
}
public class MobileTimeManagement {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        User user = null;
        TimeTracker tracker = null;

        while (true) {
            System.out.println("\nMENU ");
            System.out.println("1. Create User");
            System.out.println("2. Add Usage");
            System.out.println("3. Show Report");
            System.out.println("4. Show Daily Summary");
            System.out.println("5. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.println("1. Child  2. Adult");
                    int type = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Age: ");
                    int age = sc.nextInt();
                    sc.nextLine();

                    if (type == 1) {
                        System.out.print("Enter Parent Name: ");
                        String parent = sc.nextLine();
                        user = new ChildUser(name, age, parent);
                    } else {
                        System.out.print("Enter Job: ");
                        String job = sc.nextLine();
                        user = new AdultUser(name, age, job);
                    }

                    tracker = new TimeTracker(user);
                    System.out.println("User Created Successfully!");
                    break;

                case 2:
                    if (tracker == null) {
                        System.out.println("Create user first!");
                        break;
                    }

                    System.out.print("App Name: ");
                    String app = sc.nextLine();

                    System.out.print("Minutes: ");
                    int min = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Category: 1.SOCIAL 2.GAMING 3.EDUCATION 4.OTHER");
                    int c = sc.nextInt();
                    sc.nextLine();

                    AppCategory cat = AppCategory.values()[c - 1];

                    System.out.print("Day: ");
                    String day = sc.nextLine();

                    tracker.addUsage(app, min, cat, day);
                    break;

                case 3:
                    if (user != null) {
                        user.showReport();
                        user.showWeeklyReport();
                    } else {
                        System.out.println("No user found!");
                    }
                    break;

                case 4:
                    if (tracker != null) {
                        tracker.showDailySummary();
                    } else {
                        System.out.println("No data found!");
                    }
                    break;

                case 5:
                    System.out.println("Exiting program...");
                    System.exit(0);
            }
        }
    }
}






