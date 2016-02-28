package tendersaucer.slimm;

/**
 * Created by Alex on 2/27/2016.
 *
 * Used for accessing / modifying user data tracked over time.
 */
public final class UserCalendar {

    private static final UserCalendar INSTANCE = new UserCalendar();

    public static UserCalendar getInstance() {
        return INSTANCE;
    }

    private Prefs prefs;

    public UserCalendar() {
        prefs = Prefs.getInstance();
    }
}
