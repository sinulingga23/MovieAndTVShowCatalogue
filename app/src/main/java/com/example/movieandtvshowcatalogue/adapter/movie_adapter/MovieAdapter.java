package com.example.movieandtvshowcatalogue.adapter.movie_adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.movieandtvshowcatalogue.R;
import com.example.movieandtvshowcatalogue.details.details_movie.MovieDetails;
import com.example.movieandtvshowcatalogue.model.Movie;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    public static final String TAG = MovieDetails.class.getSimpleName();
    private ArrayList<Movie> movieData = new ArrayList<>();
    private Context mContext;

    public void setData(ArrayList<Movie> movie) {
        movieData.clear();
        movieData.addAll(movie);
        notifyDataSetChanged();
    }

    public MovieAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_movie, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder movieViewHolder, final int position) {
//        movieViewHolder.bind(movieData.get(position));
        final Movie movie = movieData.get(position);

        String urlPoster = "https://image.tmdb.org/t/p/w185" + movie.getPoster();
        Glide.with(movieViewHolder.itemView.getContext())
                .load(urlPoster)
                .apply(new RequestOptions().override(80, 120))
                .into(movieViewHolder.imgPoster);

        movieViewHolder.releaseDate.setText(movie.getReleaseDate());
        movieViewHolder.tvOverview.setText(movie.getOverview());
        movieViewHolder.tvTitle.setText(movie.getTitle());


        movieViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie extraMovie = new Movie();
                extraMovie.setId(movie.getId());
                extraMovie.setTitle(movie.getTitle());
                extraMovie.setBackdropPath(movie.getBackdropPath());
                extraMovie.setPoster(movie.getPoster());
                extraMovie.setReleaseDate(movie.getReleaseDate());
                extraMovie.setOverview(movie.getOverview());
                extraMovie.setVoteAverage(movie.getVoteAverage());
                Intent toMovieDetails = new Intent(mContext.getApplicationContext(), MovieDetails.class);
                toMovieDetails.putExtra(MovieDetails.EXTRA_MOVIE, extraMovie);
                mContext.startActivity(toMovieDetails);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieData.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvOverview, releaseDate;
        Button btnLike;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_name);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            releaseDate = itemView.findViewById(R.id.tv_first_air_date);
            btnLike = itemView.findViewById(R.id.btn_favorite);
        }
    }
}
