package com.example.movieandtvshowcatalogue.notification;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import com.example.movieandtvshowcatalogue.R;
import com.example.movieandtvshowcatalogue.preferences.ReminderPreference;

public class SettingsReminder extends AppCompatActivity {
    private Switch dailyReminder;
    private Switch releaseReminder;
    private static final String TAG = SettingsReminder.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_reminder);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.settings_reminder);
        }

        final ReminderPreference dailyReminderPreference = new ReminderPreference(getApplicationContext(), "daily_reminder");
        final ReminderPreference releaseReminderPreference = new ReminderPreference(getApplicationContext(), "release_reminder");
        dailyReminder = findViewById(R.id.daily_reminder);
        releaseReminder = findViewById(R.id.release_reminder);

        if (!dailyReminderPreference.getReminder()) {
            dailyReminder.setChecked(false);
        } else {
            dailyReminder.setChecked(true);
        }

        if (!releaseReminderPreference.getReminder()) {
            releaseReminder.setChecked(false);
        } else {
            releaseReminder.setChecked(true);
        }

        dailyReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    dailyReminderPreference.setReminder(true);
                    NotificationDaily notificationDaily = new NotificationDaily();
                    notificationDaily.setRepeatingAlarm(getBaseContext());

                } else {
                    dailyReminderPreference.setReminder(false);
                    NotificationDaily notificationDaily = new NotificationDaily();
                    notificationDaily.cancelRepeatingAlarm(getBaseContext());
                }
            }
        });

        releaseReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    releaseReminderPreference.setReminder(true);
                    NotificationReleaseReminder notificationReleaseReminder = new NotificationReleaseReminder();
                    notificationReleaseReminder.setRepeatingAlarm(getApplicationContext());
                } else {
                    releaseReminderPreference.setReminder(false);
                    NotificationReleaseReminder notificationReleaseReminder = new NotificationReleaseReminder();
                    notificationReleaseReminder.cancelRepeatingAlarm(getApplicationContext());
                }
            }
        });
    }

}
