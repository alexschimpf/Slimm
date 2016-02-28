package tendersaucer.slimm;

/**
 * Created by Alex on 2/27/2016.
 *
 * Contains all data tracked daily in UserCalendar.
 */
public final class UserCalendarItem {

    private int weight;
    private int caloriesConsumed;
    private int caloriesBurned;

    public UserCalendarItem() {
    }

    public UserCalendarItem(int weight, int caloriesConsumed, int caloriesBurned) {
        this.weight = weight;
        this.caloriesConsumed = caloriesConsumed;
        this.caloriesBurned = caloriesBurned;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getCaloriesConsumed() {
        return caloriesConsumed;
    }

    public void setCaloriesConsumed(int caloriesConsumed) {
        this.caloriesConsumed = caloriesConsumed;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public int getNetCalories() {
        return caloriesConsumed - caloriesBurned;
    }
}
