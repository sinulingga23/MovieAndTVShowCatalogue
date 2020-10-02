package com.example.consumerapp.adapter.movie_adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.consumerapp.FavoriteMovie;
import com.example.consumerapp.R;
import com.example.consumerapp.details.details_movie.MovieDetails;
import com.example.consumerapp.model.Movie;

import java.util.List;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavMovieHolder> {
    public static final String TAG = FavoriteMovieAdapter.class.getSimpleName();
    private List<FavoriteMovie> listFavoriteMovie;
    private Context mContext;


    public FavoriteMovieAdapter(Context context, List<FavoriteMovie> favoriteMovies) {
        this.mContext = context;
        this.listFavoriteMovie = favoriteMovies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavMovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int positon) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_movie, viewGroup, false);
        return new FavMovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavMovieHolder holder, int position) {
        if (!listFavoriteMovie.isEmpty()) {
            final FavoriteMovie movie = listFavoriteMovie.get(position);
            final String urlPoster = "https://image.tmdb.org/t/p/w185" + movie.getPosterPath();

            Glide.with(holder.itemView.getContext())
                    .load(urlPoster)
                    .apply(new RequestOptions()).override(80,120)
                    .into(holder.imgPoster);

            holder.releaseDate.setText(movie.getReleaseDate());
            holder.tvOverview.setText(movie.getOverview());
            holder.tvTitle.setText(movie.getTitle());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Movie extraMovie = new Movie();
                    extraMovie.setId(movie.getId());
                    extraMovie.setTitle(movie.getTitle());
                    extraMovie.setBackdropPath(movie.getBackdropPath());
                    extraMovie.setPoster(movie.getPosterPath());
                    extraMovie.setReleaseDate(movie.getReleaseDate());
                    extraMovie.setOverview(movie.getOverview());
                    extraMovie.setVoteAverage(movie.getVoteAverage());
                    Intent toMovieDetails = new Intent(mContext.getApplicationContext(), MovieDetails.class);
                    toMovieDetails.putExtra(MovieDetails.EXTRA_MOVIE, extraMovie);
                    mContext.startActivity(toMovieDetails);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listFavoriteMovie.size();
    }

    public class FavMovieHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvOverview, releaseDate;
        Button btnLike;

        public FavMovieHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_name);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            releaseDate =  itemView.findViewById(R.id.tv_first_air_date);
            btnLike = itemView.findViewById(R.id.btn_favorite);
        }
    }
}
