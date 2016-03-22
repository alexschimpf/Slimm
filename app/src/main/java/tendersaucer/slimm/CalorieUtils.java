package tendersaucer.slimm;

/**
 * Created by Alex on 2/28/2016.
 */
public final class CalorieUtils {

    public static int getBMR(boolean isMale, int ageYears, float weightKg, float heightCm) {
        int correction = isMale ? 5 : -161;
        return Math.round((10 * weightKg) + (6.25f * heightCm) - (5 * ageYears) + correction);
    }
}
