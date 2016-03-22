package tendersaucer.slimm;

import org.joda.time.LocalDate;
import org.joda.time.Years;

/**
 * Created by Alex on 2/27/2016.
 *
 * Used for accessing / modifying basic user info and settings.
 */
public final class User {

    public enum Gender {
        MALE, FEMALE
    }

    public enum ReminderFrequency {
        NEVER, DAILY, WEEKLY
    }

    private static final User INSTANCE = new User();

    public static User getInstance() {
        return INSTANCE;
    }

    private final DataStore dataStore;

    private User() {
        dataStore = new DataStore(DataStore.Type.USER);
    }

    public boolean isNew() {
        return dataStore.getBoolean(R.string.prefs_is_new, true);
    }

    public Gender getGender() {
        return Gender.valueOf(dataStore.getString(R.string.prefs_gender, null));
    }

    public boolean isMale() {
        return getGender().equals(Gender.MALE);
    }

    public int getAge() {
        LocalDate birthdate = new LocalDate(getBirthdate());
        LocalDate now = new LocalDate();
        return Years.yearsBetween(birthdate, now).getYears();
    }

    public long getBirthdate() {
        return dataStore.getLong(R.string.prefs_birth_date, 0L);
    }

    /**
     * Returns the user's original weight.
     * @return - original weight in preferred unit
     */
    public float getOrigWeight() {
        return getOrigWeight(getWeightUnit());
    }

    /**
     * Returns the user's original weight in the given unit.
     * @param unit - return weight unit
     * @return - original weight in given unit
     */
    public float getOrigWeight(MeasurementUnit unit) {
        float weight = dataStore.getFloat(R.string.prefs_orig_weight, 0.0f);
        if (unit.equals(MeasurementUnit.POUNDS)) {
            weight = ConversionUtils.kg2lb(weight);
        }

        return weight;
    }

    /**
     * Returns the user's current weight.
     * @return - current weight in preferred unit
     */
    public float getCurrWeight() {
        return getCurrWeight(getWeightUnit());
    }

    /**
     * Returns the user's current weight in the given unit.
     * @param unit - return weight unit
     * @return - current weight in given unit
     */
    public float getCurrWeight(MeasurementUnit unit) {
        float weight = dataStore.getFloat(R.string.prefs_curr_weight, 0.0f);
        if (weight == 0) {
            return getOrigWeight(unit);
        }

        if (unit.equals(MeasurementUnit.POUNDS)) {
            weight = ConversionUtils.kg2lb(weight);
        }

        return weight;
    }

    /**
     * Returns the user's current height.
     * @return - current height in preferred unit
     */
    public float getHeight() {
        return getHeight(getHeightUnit());
    }

    /**
     * Returns the user's current height.
     * @param unit - return height unit
     * @return - current height in preferred unit
     */
    public float getHeight(MeasurementUnit unit) {
        float height = dataStore.getFloat(R.string.prefs_height, 0f);
        if (unit.equals(MeasurementUnit.INCHES)) {
            height = ConversionUtils.cm2in(height);
        }

        return height;
    }

    public boolean usesInches() {
        return getHeightUnit().equals(MeasurementUnit.INCHES);
    }

    public boolean usesPounds(){
        return getWeightUnit().equals(MeasurementUnit.POUNDS);
    }

    public MeasurementUnit getWeightUnit() {
        return MeasurementUnit.valueOf(dataStore.getString(R.string.prefs_weight_unit, null));
    }

    public MeasurementUnit getHeightUnit() {
        return MeasurementUnit.valueOf(dataStore.getString(R.string.prefs_height_unit, null));
    }

    public ReminderFrequency getReminderFreq() {
        return ReminderFrequency.valueOf(dataStore.getString(R.string.prefs_reminder_freq, null));
    }

    public float getWeightLossPace() {
        return dataStore.getFloat(R.string.prefs_weight_loss_pace, 0f);
    }

    public int getCurrBMR() {
        float weight = getCurrWeight(MeasurementUnit.KILOGRAMS);
        float height = getHeight(MeasurementUnit.CENTIMETERS);
        return CalorieUtils.getBMR(isMale(), getAge(), weight, height);
    }

    public void setInitialized() {
        dataStore.putBoolean(R.string.prefs_is_new, true);
    }

    public void setGender(Gender gender) {
        dataStore.putString(R.string.prefs_gender, gender.name());
    }

    public void setBirthdate(long date) {
        dataStore.putLong(R.string.prefs_birth_date, date);
    }

    /**
     * Saves as kg.
     * @param weight
     * @param unit
     */
    public void setOrigWeight(float weight, MeasurementUnit unit) {
        if (unit.equals(MeasurementUnit.POUNDS)) {
            weight = ConversionUtils.lb2kg(weight);
        }

        dataStore.putFloat(R.string.prefs_orig_weight, weight);
    }

    /**
     * Saves as kg.
     * @param weight
     * @param unit
     */
    public void setCurrWeight(float weight, MeasurementUnit unit) {
        if (unit.equals(MeasurementUnit.POUNDS)) {
            weight = ConversionUtils.lb2kg(weight);
        }

        dataStore.putFloat(R.string.prefs_curr_weight, weight);
    }

    /**
     * Saves as cm.
     * @param height
     * @param unit
     */
    public void setHeight(float height, MeasurementUnit unit) {
        if (unit.equals(MeasurementUnit.INCHES)) {
            height = ConversionUtils.in2cm(height);
        }

        dataStore.putFloat(R.string.prefs_height, height);
    }

    public void setWeightUnit(MeasurementUnit unit) {
        dataStore.putString(R.string.prefs_weight_unit, unit.name());
    }

    public void setHeightUnit(MeasurementUnit unit) {
        dataStore.putString(R.string.prefs_height_unit, unit.name());
    }

    public void setReminderFreq(ReminderFrequency freq) {
        dataStore.putString(R.string.prefs_reminder_freq, freq.name());
    }

    /**
     * Saves as kg.
     * @param pace - weight to lose per week
     * @param unit
     */
    public void setWeightLossPace(float pace, MeasurementUnit unit) {
        if (unit.equals(MeasurementUnit.POUNDS)) {
            pace = ConversionUtils.lb2kg(pace);
        }

        dataStore.putFloat(R.string.prefs_weight_loss_pace, pace);
    }
}
