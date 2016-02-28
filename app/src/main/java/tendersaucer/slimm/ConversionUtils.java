package tendersaucer.slimm;

/**
 * Created by Alex on 2/28/2016.
 */
public final class ConversionUtils {

    private static final float KG_2_LB = 2.20462f;
    private static final float CM_2_IN = 0.393701f;

    public static float kg2lb(float kg) {
        return kg * KG_2_LB;
    }

    public static float lb2kg(float lb) {
        return lb / KG_2_LB;
    }

    public static int cm2in(int cm) {
        return Math.round(cm * CM_2_IN);
    }

    public static int in2cm(int in) {
        return Math.round(in / CM_2_IN);
    }
}
