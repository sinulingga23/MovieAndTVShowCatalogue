package com.example.consumerapp.adapter.tv_adapter;

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
import com.example.consumerapp.FavoriteTVShow;
import com.example.consumerapp.R;
import com.example.consumerapp.details.details_tv.TVShowDetails;
import com.example.consumerapp.model.TVShow;

import java.util.List;

public class FavoriteTVShowAdapter extends RecyclerView.Adapter<FavoriteTVShowAdapter.FavTVShowHolder> {
    private List<FavoriteTVShow> listFavoriteTVShow;
    private Context mContext;

    public FavoriteTVShowAdapter(Context context, List<FavoriteTVShow> favoriteTVShows) {
        this.mContext = context;
        this.listFavoriteTVShow  = favoriteTVShows;
    }

    @NonNull
    @Override
    public FavTVShowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_tv_show, viewGroup, false);
        return new FavTVShowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavTVShowHolder holder, int position) {
        if (!listFavoriteTVShow.isEmpty()) {
            final FavoriteTVShow favoriteTVShow = listFavoriteTVShow.get(position);
            String urlPoster = "https://image.tmdb.org/t/p/w185" + favoriteTVShow.getPosterPath();

            Glide.with(holder.itemView.getContext())
                    .load(urlPoster)
                    .apply(new RequestOptions()).override(80, 120)
                    .into(holder.imgPoster);

            holder.firstAirDate.setText(favoriteTVShow.getFirstAirDate());
            holder.tvOverview.setText(favoriteTVShow.getOverview());
            holder.tvTitle.setText(favoriteTVShow.getName());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TVShow extraTVShow = new TVShow();
                    extraTVShow.setId(favoriteTVShow.getId());
                    extraTVShow.setName(favoriteTVShow.getName());
                    extraTVShow.setBackdropPath(favoriteTVShow.getBackdropPathh());
                    extraTVShow.setPoster(favoriteTVShow.getPosterPath());
                    extraTVShow.setFirstAirDate(favoriteTVShow.getFirstAirDate());
                    extraTVShow.setOverview(favoriteTVShow.getOverview());
                    extraTVShow.setVoteAverage(favoriteTVShow.getVoteAverage());
                    Intent toDetailsTVShow = new Intent(mContext.getApplicationContext(), TVShowDetails.class);
                    toDetailsTVShow.putExtra(TVShowDetails.EXTRA_TV_SHOW, extraTVShow);
                    mContext.startActivity(toDetailsTVShow);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listFavoriteTVShow.size();
    }

    public class FavTVShowHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvTitle, tvOverview, firstAirDate;
        Button btnFavorite;
        public FavTVShowHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.img_poster);
            tvTitle = itemView.findViewById(R.id.tv_name);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            firstAirDate = itemView.findViewById(R.id.tv_first_air_date);
            btnFavorite = itemView.findViewById(R.id.btn_favorite);
        }
    }
}
