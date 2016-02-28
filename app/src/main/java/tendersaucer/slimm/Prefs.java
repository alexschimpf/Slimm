package tendersaucer.slimm;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

/**
 * Created by Alex on 2/27/2016.
 */
public final class Prefs {

    private final static Prefs INSTANCE = new Prefs();

    public static Prefs getInstance() {
        return INSTANCE;
    }

    private MainActivity activity;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private Prefs() {
    }

    public void init(MainActivity activity) {
        this.activity = activity;
        prefs = activity.getPreferences(Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }
    
    public String getString(int resId, String defaultVal) {
        return prefs.getString(getResString(resId), defaultVal);
    }

    public Boolean getBoolean(int resId, Boolean defaultVal) {
        return prefs.getBoolean(getResString(resId), defaultVal);
    }

    public Integer getInt(int resId, Integer defaultVal) {
        return prefs.getInt(getResString(resId), defaultVal);
    }

    public Float getFloat(int resId, Float defaultVal) {
        return prefs.getFloat(getResString(resId), defaultVal);
    }

    public String getLong(int resId, String defaultVal) {
        return prefs.getString(getResString(resId), defaultVal);
    }

    public Set<String> getStringSet(int resId, Set<String> defaultVal) {
        return prefs.getStringSet(getResString(resId), defaultVal);
    }

    public void putString(int resId, String val) {
        editor.putString(getResString(resId), val);
        editor.commit();
    }

    public void putBoolean(int resId, Boolean val) {
        editor.putBoolean(getResString(resId), val);
        editor.commit();
    }

    public void putInt(int resId, Integer val) {
        editor.putInt(getResString(resId), val);
        editor.commit();
    }

    public void putFloat(int resId, Float val) {
        editor.putFloat(getResString(resId), val);
        editor.commit();
    }

    public void putLong(int resId, Long val) {
        editor.putLong(getResString(resId), val);
        editor.commit();
    }

    public void putStringSet(int resId, Set<String> val) {
        editor.putStringSet(getResString(resId), val);
        editor.commit();
    }

    public String getResString(int resId) {
        return activity.getString(resId);
    }
}
