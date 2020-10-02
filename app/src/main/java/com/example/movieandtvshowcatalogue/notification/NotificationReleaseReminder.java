package com.example.movieandtvshowcatalogue.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.movieandtvshowcatalogue.R;
import com.example.movieandtvshowcatalogue.details.details_movie.MovieDetails;
import com.example.movieandtvshowcatalogue.model.Movie;
import com.example.movieandtvshowcatalogue.model.MovieRelease;
import com.example.movieandtvshowcatalogue.network.ApiClient;
import com.example.movieandtvshowcatalogue.network.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationReleaseReminder extends BroadcastReceiver {
    private final static String TAG = NotificationReleaseReminder.class.getSimpleName();
    private final static String MY_ACTION = "com.example.movieandtvshowcatalogue.notification";
    private final static String GROUP_KEY_EMAILS = "group_key_emails";
    private final static int MAX_NOTIFICATION_MOVIE = 12;
    private final static int NOTIFICATION_REQUEST_CODE = 104;
    private final static String CHANNEL_NAME = "Channel_Movie_Release";
    private int movieId = 0;

    public NotificationReleaseReminder() {

    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED")) {
            getDataMovieRelease(context);
        } else {
            getDataMovieRelease(context);
        }
    }

    private void getDataMovieRelease(final Context context) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        final String currentDate = simpleDateFormat.format(date);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieRelease> call = apiInterface.getMovieRelease(currentDate, currentDate, new ApiClient().getAPiClient());
        call.enqueue(new Callback<MovieRelease>() {
            @Override
            public void onResponse(Call<MovieRelease> call, Response<MovieRelease> response) {
                if (response.isSuccessful()) {
                    ArrayList<Movie> movies = new ArrayList<>();
                    for (int i=0; i< response.body().getResults().size(); i++) {
                        movies.add(i, response.body().getResults().get(i));
                    }

                    for (int i=1; i<movies.size(); i++) {
                        if (movies.get(i).getReleaseDate().equals(currentDate)) {
                            showAlarmNotification(context, movies, i);
                        }
                    }
                    for (int i=0; i<response.body().getResults().size(); i++) {
                        Log.d(TAG, "Data Movie Release->" + response.body().getResults().get(i).getTitle());
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieRelease> call, Throwable t) {
                Log.d(TAG, t.toString());
            }
        });
    }



    private void showAlarmNotification(Context context, ArrayList<Movie> movies, int Id) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Movie extraMovie = new Movie();
        extraMovie.setId(movies.get(Id).getId());
        extraMovie.setTitle(movies.get(Id).getTitle());
        if (extraMovie.getBackdropPath() == null) {
            extraMovie.setBackdropPath(movies.get(Id).getPoster());
        } else {
            extraMovie.setBackdropPath(movies.get(Id).getBackdropPath());
        }

        if (extraMovie.getPoster() == null) {
            extraMovie.setPoster(movies.get(Id).getBackdropPath());
        } else {
            extraMovie.setPoster(movies.get(Id).getPoster());
        }
        extraMovie.setReleaseDate(movies.get(Id).getReleaseDate());
        extraMovie.setOverview(movies.get(Id).getOverview());
        extraMovie.setVoteAverage(movies.get(Id).getVoteAverage());

        Intent intent = new Intent(context.getApplicationContext(), MovieDetails.class);
        intent.putExtra(MovieDetails.EXTRA_MOVIE, extraMovie);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, Id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder;
        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_movie_black_24dp);

        String CHANNEL_ID = "release movie channel";
        if (movieId < MAX_NOTIFICATION_MOVIE) {


            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(movies.get(Id).getTitle())
                    .setContentText(movies.get(Id).getOverview())
                    .setVibrate(new long[]{1000,1000,1000,1000,1000})
                    .setSmallIcon(R.drawable.ic_movie_black_24dp)
                    .setLargeIcon(largeIcon)
                    .setGroup(GROUP_KEY_EMAILS)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);
            movieId++;
        } else {

            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                    .addLine(movies.get(Id).getTitle() + ":" + movies.get(Id).getTitle())
                    .addLine(movies.get(Id-1).getTitle() + ": " + movies.get(Id-1).getTitle())
                    .setBigContentTitle(context.getString(R.string.new_movie_release))
                    .setSummaryText(context.getString(R.string.more));
            Log.d(TAG, "Masuk ke inbox style...");

            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setContentTitle(movies.get(Id).getTitle())
                    .setContentText(movies.get(Id).getOverview())
                    .setVibrate(new long[]{1000,1000,1000,1000,1000})
                    .setSmallIcon(R.drawable.ic_movie_black_24dp)
                    .setLargeIcon(largeIcon)
                    .setGroup(GROUP_KEY_EMAILS)
                    .setGroupSummary(true)
                    .setContentIntent(pendingIntent)
                    .setStyle(inboxStyle)
                    .setAutoCancel(true);
            movieId++;
        }
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            
            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        Notification notification = builder.build();
        if (notificationManager != null) {
            notificationManager.notify(Id, notification); 
        }
    }

    public void setRepeatingAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReleaseReminder.class);


        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 102, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 102, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND, 0);


        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
//            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//            Toast.makeText(context, "Release Today Reminder Berhasil di Set", Toast.LENGTH_SHORT).show();
        }
    }

    public void cancelRepeatingAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotificationReleaseReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 102, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
