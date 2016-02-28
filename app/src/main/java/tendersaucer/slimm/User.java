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

    private final Prefs prefs;

    private User() {
        prefs = Prefs.user();
    }

    public boolean isNew() {
        return prefs.getBoolean(R.string.prefs_is_new, true);
    }

    public Gender getGender() {
        return Gender.valueOf(prefs.getString(R.string.prefs_gender, null));
    }

    public boolean isMale() {
        return getGender().equals(Gender.MALE);
    }

    public int getAge() {
        return prefs.getInt(R.string.prefs_age, 0);
    }

    /**
     * Returns the user's original weight.
     * @return - original weight in preferred units
     */
    public float getOrigWeight() {
        return getOrigWeight(getWeightUnit());
    }

    /**
     * Returns the user's current weight.
     * @return - current weight in preferred units
     */
    public float getCurrWeight() {
        return getCurrWeight(getWeightUnit());
    }

    /**
     * Returns the user's current height.
     * @return - current height in preferred units
     */
    public int getHeight() {
        return getHeight(getHeightUnit());
    }

    public float getOrigWeight(ConversionUtils.WeightUnit unit) {
        float weight = prefs.getFloat(R.string.prefs_orig_weight, 0.0f);
        if (unit.equals(ConversionUtils.WeightUnit.POUNDS)) {
            weight = ConversionUtils.kg2lb(weight);
        }

        return weight;
    }

    public float getCurrWeight(ConversionUtils.WeightUnit unit) {
        float weight = prefs.getFloat(R.string.prefs_curr_weight, 0.0f);
        if (weight == 0) {
            return getOrigWeight(unit);
        }

        if (unit.equals(ConversionUtils.WeightUnit.POUNDS)) {
            weight = ConversionUtils.kg2lb(weight);
        }

        return weight;
    }

    public int getHeight(ConversionUtils.HeightUnit unit) {
        int height = prefs.getInt(R.string.prefs_height, 0);
        if (unit.equals(ConversionUtils.HeightUnit.INCHES)) {
            height = ConversionUtils.cm2in(height);
        }

        return height;
    }

    public ConversionUtils.WeightUnit getWeightUnit() {
        return ConversionUtils.WeightUnit.valueOf(prefs.getString(R.string.prefs_weight_unit, null));
    }

    public ConversionUtils.HeightUnit getHeightUnit() {
        return ConversionUtils.HeightUnit.valueOf(prefs.getString(R.string.prefs_height_unit, null));
    }

    public ReminderFrequency getReminderFreq() {
        return ReminderFrequency.valueOf(prefs.getString(R.string.prefs_reminder_freq, null));
    }

    public WeightLossPace getWeightLossPace() {
        return WeightLossPace.valueOf(prefs.getString(R.string.prefs_weight_loss_pace, null));
    }

    public int getCurrBMR() {
        float weight = getCurrWeight(ConversionUtils.WeightUnit.KILOGRAMS);
        int height = getHeight(ConversionUtils.HeightUnit.CENTIMETERS);
        return CalorieUtils.getBMR(isMale(), getAge(), weight, height);
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

    /**
     * Saves as kg.
     * @param weight
     * @param unit
     */
    public void setOrigWeight(float weight, ConversionUtils.WeightUnit unit) {
        if (unit.equals(ConversionUtils.WeightUnit.POUNDS)) {
            weight = ConversionUtils.lb2kg(weight);
        }

        prefs.putFloat(R.string.prefs_orig_weight, weight);
    }

    /**
     * Saves as kg.
     * @param weight
     * @param unit
     */
    public void setCurrWeight(float weight, ConversionUtils.WeightUnit unit) {
        if (unit.equals(ConversionUtils.WeightUnit.POUNDS)) {
            weight = ConversionUtils.lb2kg(weight);
        }

        prefs.putFloat(R.string.prefs_curr_weight, weight);
    }

    /**
     * Saves as cm.
     * @param height
     * @param unit
     */
    public void setHeight(int height, ConversionUtils.HeightUnit unit) {
        if (unit.equals(ConversionUtils.HeightUnit.INCHES)) {
            height = ConversionUtils.in2cm(height);
        }

        prefs.putInt(R.string.prefs_height, height);
    }

    public void setWeightUnit(ConversionUtils.WeightUnit unit) {
        prefs.putString(R.string.prefs_weight_unit, unit.name());
    }

    public void setHeightUnit(ConversionUtils.HeightUnit unit) {
        prefs.putString(R.string.prefs_height_unit, unit.name());
    }

    public void setReminderFreq(ReminderFrequency freq) {
        prefs.putString(R.string.prefs_reminder_freq, freq.name());
    }

    public void setWeightLossPace(WeightLossPace pace) {
        prefs.putString(R.string.prefs_weight_loss_pace, pace.name());
    }
}
