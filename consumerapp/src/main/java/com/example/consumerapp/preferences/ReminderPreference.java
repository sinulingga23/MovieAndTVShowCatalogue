package com.example.consumerapp.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class ReminderPreference {
    private static final String CHECK_REMINDER = "check_reminder";
    private SharedPreferences preferences;

    public ReminderPreference(Context context, String prefsName) {
        preferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
    }

    public Boolean getReminder() {
        return preferences.getBoolean(CHECK_REMINDER, false);
    }

    public void setReminder(Boolean b) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(CHECK_REMINDER, b);
        editor.apply();
    }
}
