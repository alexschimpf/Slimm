package tendersaucer.slimm;

import com.google.gson.Gson;

import org.joda.time.DateTime;

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

    private final Prefs prefs;

    public UserCalendar() {
        prefs = Prefs.userCalendar();
    }

    public int getNetWeekCalories(DateTime mondayDateTime) {
        int netWeekCalories = 0;
        DateTime dateTime = mondayDateTime;
        for(int dayNum = 0; dayNum < 7; dayNum++) {
            netWeekCalories += getItem(dateTime).getNetCalories();
            dateTime = dateTime.plusDays(1);
        }

        return netWeekCalories;
    }

    public UserCalendarItem getItem(DateTime dateTime) {
        String dateKey = getDateKey(dateTime);
        String itemStr = prefs.getPrefs().getString(dateKey, null);
        if (itemStr == null) {
            return new UserCalendarItem();
        }

        return (new Gson()).fromJson(itemStr, UserCalendarItem.class);
    }

    public void setItem(DateTime dateTime, UserCalendarItem item) {
        prefs.getEditor().putString(getDateKey(dateTime), (new Gson()).toJson(item));
    }

    private String getDateKey(DateTime dateTime) {
        return String.valueOf(dateTime.millisOfDay());
    }
}
