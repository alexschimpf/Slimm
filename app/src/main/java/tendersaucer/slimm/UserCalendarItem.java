package tendersaucer.slimm;

/**
 * Created by Alex on 2/27/2016.
 *
 * Contains data items tracked in UserCalendar.
 */
public final class UserCalendarItem {

    private float weight;
    private int caloriesConsumed;
    private int caloriesBurned;

    public UserCalendarItem() {
    }

    public UserCalendarItem(float weight, int caloriesConsumed, int caloriesBurned) {
        this.weight = weight;
        this.caloriesConsumed = caloriesConsumed;
        this.caloriesBurned = caloriesBurned;
    }

    public float getWeight() {
        return User.getInstance().usesPounds() ? ConversionUtils.kg2lb(weight) : weight;
    }

    public void setWeight(float weight) {
        if (User.getInstance().usesPounds()) {
            weight = ConversionUtils.lb2kg(weight);
        }

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
