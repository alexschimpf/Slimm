package tendersaucer.slimm;

/**
 * Created by Alex on 2/27/2016.
 *
 * Used for accessing / modifying basic user info and settings.
 */
public final class User {

    public enum Gender {
        MALE, FEMALE
    }

    public enum WeightUnit {
        KILOGRAMS, POUNDS
    }

    public enum HeightUnit {
        CENTIMETERS, INCHES
    }

    public enum WeightLossPace {
        SLOW, MEDIUM, FAST
    }

    public enum ReminderFrequency {
        NEVER, DAILY, WEEKLY
    }

    private static final User INSTANCE = new User();

    public static User getInstance() {
        return INSTANCE;
    }

    private Prefs prefs;

    private User() {
        prefs = Prefs.getInstance();
    }

    public boolean isNew() {
        return prefs.getBoolean(R.string.prefs_is_new, true);
    }

    public Gender getGender() {
        return Gender.valueOf(prefs.getString(R.string.prefs_gender, null));
    }

    public int getAge() {
        return prefs.getInt(R.string.prefs_age, 0);
    }

    public float getOrigWeight() {
        return prefs.getFloat(R.string.prefs_orig_weight, 0.0f);
    }

    public float getCurrWeight() {
        return prefs.getFloat(R.string.prefs_curr_weight, 0.0f);
    }

    public int getHeight() {
        return prefs.getInt(R.string.prefs_height, 0);
    }

    public int getWaistSize() {
        return prefs.getInt(R.string.prefs_waist_size, 0);
    }

    public int getHipSize() {
        return prefs.getInt(R.string.prefs_hip_size, 0);
    }

    public int getWristSize() {
        return prefs.getInt(R.string.prefs_wrist_size, 0);
    }

    public WeightUnit getWeightUnit() {
        return WeightUnit.valueOf(prefs.getString(R.string.prefs_weight_unit, null));
    }

    public HeightUnit getHeightUnit() {
        return HeightUnit.valueOf(prefs.getString(R.string.prefs_height_unit, null));
    }

    public ReminderFrequency getReminderFreq() {
        return ReminderFrequency.valueOf(prefs.getString(R.string.prefs_reminder_freq, null));
    }

    public WeightLossPace getWeightLossPace() {
        return WeightLossPace.valueOf(prefs.getString(R.string.prefs_weight_loss_pace, null));
    }

    public void setInitialized() {
        prefs.putBoolean(R.string.prefs_is_new, true);
    }

    public void setGender(Gender gender) {
        prefs.putString(R.string.prefs_gender, gender.name());
    }

    public void setAge(int age) {
        prefs.putInt(R.string.prefs_age, age);
    }

    public void setOrigWeight(float weight) {
        prefs.putFloat(R.string.prefs_orig_weight, weight);
    }

    public void setCurrWeight(float weight) {
        prefs.putFloat(R.string.prefs_curr_weight, weight);
    }

    public void setHeight(int height) {
        prefs.putInt(R.string.prefs_height, height);
    }

    public void setWaistSize(Integer waistSize) {
        prefs.putInt(R.string.prefs_waist_size, waistSize);
    }

    public void setHipSize(Integer hipSize) {
        prefs.putInt(R.string.prefs_hip_size, hipSize);
    }

    public void setWristSize(Integer wristSize) {
        prefs.putInt(R.string.prefs_wrist_size, wristSize);
    }

    public void setWeightUnit(WeightUnit unit) {
        prefs.putString(R.string.prefs_weight_unit, unit.name());
    }

    public void setHeightUnit(HeightUnit unit) {
        prefs.putString(R.string.prefs_height_unit, unit.name());
    }

    public void setReminderFreq(ReminderFrequency freq) {
        prefs.putString(R.string.prefs_reminder_freq, freq.name());
    }

    public void setWeightLossPace(WeightLossPace pace) {
        prefs.putString(R.string.prefs_weight_loss_pace, pace.name());
    }
}
