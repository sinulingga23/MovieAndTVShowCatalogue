package com.example.consumerapp.details.details_tv;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.consumerapp.FavoriteTVShow;
import com.example.consumerapp.MainActivity;
import com.example.consumerapp.R;
import com.example.consumerapp.model.TVShow;
import com.example.consumerapp.preferences.FavoritePreference;



public class TVShowDetails extends AppCompatActivity {
    public static final String ID_TV_SHOW = "id_tv_show";
    public static final String EXTRA_TV_SHOW = "extra_tv_show";
    public static final String TAG = TVShowDetails.class.getSimpleName();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_details);


        final TVShow extraTVShow = getIntent().getParcelableExtra(EXTRA_TV_SHOW);
        final FavoritePreference favoritePreference = new FavoritePreference(getApplicationContext(), String.valueOf(extraTVShow.getId()));
        TextView title = findViewById(R.id.tv_title_detail);
        ImageView imgPoster = findViewById(R.id.img_poster_detail);
        TextView firstAirDate = findViewById(R.id.tv_release_date_detail);
        TextView rating = findViewById(R.id.tv_star);
        TextView overview = findViewById(R.id.tv_overview_detail);
        final ProgressBar pbLoadPoster = findViewById(R.id.pb_load_poster);
        final Button btnLike = findViewById(R.id.btn_favorite);
        btnLike.setVisibility(View.GONE);

        if (extraTVShow != null) {
            title.setText(extraTVShow.getName());
            String urlPoster = "https://image.tmdb.org/t/p/w780/" + extraTVShow.getBackdropPath();
            Glide.with(getApplicationContext())
                    .load(urlPoster)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            pbLoadPoster.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            pbLoadPoster.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(imgPoster);

            title.setText(extraTVShow.getName());
            firstAirDate.setText(extraTVShow.getFirstAirDate());
            rating.setText(String.valueOf(extraTVShow.getVoteAverage()));
            overview.setText(extraTVShow.getOverview());
        }

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavoriteTVShow favoriteTVShow = new FavoriteTVShow();
                favoriteTVShow.setId(extraTVShow.getId());
                favoriteTVShow.setName(extraTVShow.getName());
                favoriteTVShow.setOverview(extraTVShow.getOverview());
                favoriteTVShow.setPosterPath(extraTVShow.getPoster());
                favoriteTVShow.setBackdropPathh(extraTVShow.getBackdropPath());
                favoriteTVShow.setRuntime(extraTVShow.getRuntime());
                favoriteTVShow.setVoteAverage(extraTVShow.getVoteAverage());
                favoriteTVShow.setFirstAirDate(extraTVShow.getFirstAirDate());

                String temp = "";
                if (favoritePreference.getFavorite()) {
//                    insertToFavoriteTVShow(favoriteTVShow);
//                    favoritePreference.setFavorite(false);
//                    TVShowBannerWidget.updateWidget(getApplicationContext());
                    btnLike.setText(getResources().getString(R.string.delete_from_favorite));
                    temp = getResources().getString(R.string.added_to_favorite);
                } else if (!favoritePreference.getFavorite()) {
//                    deleteFromFavorte(favoriteTVShow);
//                    favoritePreference.setFavorite(true);
//                    TVShowBannerWidget.updateWidget(getApplicationContext());
                    btnLike.setText(getResources().getString(R.string.add_to_favorite));
                    temp = getResources().getString(R.string.deleted_from_favorite);
                    Intent toFavTVShowActivity = new Intent(getApplicationContext(), MainActivity.class);
                }

                Toast.makeText(getBaseContext(), temp + " \"" + extraTVShow.getName() + "\"", Toast.LENGTH_SHORT).show();
            }
        });

        if (favoritePreference.getFavorite()) {
            btnLike.setText(getResources().getString(R.string.add_to_favorite));
        } else if (!favoritePreference.getFavorite()) {
            btnLike.setText(getResources().getString(R.string.delete_from_favorite));
        }
    }

//    private static void insertToFavoriteTVShow(final FavoriteTVShow favoriteTVShow) {
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                favoriteTVShowDatabase.favoriteTVShowDao().insertFavoriteTVShow(favoriteTVShow);
//                return null;
//            }
//        }.execute();
//    }
//
//    private static void deleteFromFavorte(final FavoriteTVShow favoriteTVShow) {
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                favoriteTVShowDatabase.favoriteTVShowDao().deleteFavoriteTVShow(favoriteTVShow);
//                return null;
//            }
//        }.execute();
//    }

}

