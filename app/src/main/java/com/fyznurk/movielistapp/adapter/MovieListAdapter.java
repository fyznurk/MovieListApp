package com.fyznurk.movielistapp.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fyznurk.movielistapp.R;
import com.fyznurk.movielistapp.data.models.MovieDetail;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import static com.fyznurk.movielistapp.utils.Constants.format1;
import static com.fyznurk.movielistapp.utils.Constants.format2;
import static com.fyznurk.movielistapp.utils.Constants.imagePath;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> implements Filterable {
    public ArrayList<MovieDetail> movieList, filteredData;
    RecyclerItemClick clickHandler;

    public MovieListAdapter(ArrayList<MovieDetail> movieList, RecyclerItemClick clickHandler) {
        this.clickHandler = clickHandler;
        this.movieList = movieList;
        filteredData = movieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListAdapter.ViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Glide.with(context).load(imagePath + filteredData.get(position).getPoster_path()).into(holder.movieImage);
        holder.itemView.setOnClickListener(view -> clickHandler.onItemClick(position));
        holder.txtDetail.setOnClickListener(view -> clickHandler.onItemClick(position));
        holder.txtMovieName.setText(filteredData.get(position).getTitle());
        if (filteredData.get(position).getRelease_date() != null) {
            Date date = null;
            try {
                date = format1.parse(filteredData.get(position).getRelease_date());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            assert date != null;
            holder.txtDate.setText(format2.format(date));
        }

    }


    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                if (TextUtils.isEmpty(charSequence)) {
                    filteredData.clear();
                    filteredData.addAll(movieList);
                } else {
                    ArrayList<MovieDetail> filteredList = new ArrayList<>();
                    for (int i = 0; i < movieList.size(); i++) {
                        if (movieList.get(i).getTitle().toLowerCase().contains(charSequence.toString())) {
                            filteredList.add(movieList.get(i));
                        }
                    }
                    filteredData = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredData = (ArrayList<MovieDetail>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImage;
        TextView txtMovieName, txtDate, txtDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.movieImage);
            txtMovieName = itemView.findViewById(R.id.txtMovieName);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtDetail = itemView.findViewById(R.id.txtDetail);
        }
    }
}
