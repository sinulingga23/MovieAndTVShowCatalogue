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

import java.util.ArrayList;
import java.util.List;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.TVShowViewHolder> {

    private List<FavoriteTVShow> tvShowData = new ArrayList<>();
    private Context mContext;

    public void setData(List<FavoriteTVShow> favoriteTVShows) {
        tvShowData.clear();
        tvShowData.addAll(favoriteTVShows);
        notifyDataSetChanged();
    }

    public TVShowAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_tv_show, viewGroup, false);
        return new TVShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowViewHolder tvShowViewHolder, int position) {
//        tvShowViewHolder.bind(tvShowData.get(position));
        final FavoriteTVShow tvShow = tvShowData.get(position);

        String urlPoster = "https://image.tmdb.org/t/p/w185" + tvShow.getPosterPath();
        Glide.with(tvShowViewHolder.itemView.getContext())
                .load(urlPoster)
                .apply(new RequestOptions().override(80, 120))
                .into(tvShowViewHolder.imgPoster);

        tvShowViewHolder.tvFirstAirDate.setText(tvShow.getFirstAirDate());
        tvShowViewHolder.tvOverview.setText(tvShow.getOverview());
        tvShowViewHolder.tvName.setText(tvShow.getName());


        tvShowViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TVShow extraTVShow = new TVShow();
                extraTVShow.setId(tvShow.getId());
                extraTVShow.setName(tvShow.getName());
                extraTVShow.setBackdropPath(tvShow.getBackdropPathh());
                extraTVShow.setPoster(tvShow.getPosterPath());
                extraTVShow.setFirstAirDate(tvShow.getFirstAirDate());
                extraTVShow.setOverview(tvShow.getOverview());
                extraTVShow.setVoteAverage(tvShow.getVoteAverage())
                ;
                Intent toDetailsTVShow = new Intent(mContext.getApplicationContext(), TVShowDetails.class);
                toDetailsTVShow.putExtra(TVShowDetails.EXTRA_TV_SHOW, extraTVShow);
                mContext.startActivity(toDetailsTVShow);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvShowData.size();
    }

    class TVShowViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvName, tvOverview, tvFirstAirDate;
        Button btnLike;
        TVShowViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.img_poster);
            tvName = itemView.findViewById(R.id.tv_name);
            tvOverview = itemView.findViewById(R.id.tv_overview);
            tvFirstAirDate = itemView.findViewById(R.id.tv_first_air_date);
            btnLike = itemView.findViewById(R.id.btn_favorite);
        }

    }
}
