package com.fyznurk.movielistapp.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.fyznurk.movielistapp.R;
import com.fyznurk.movielistapp.data.models.MovieDetail;

import java.text.ParseException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.fyznurk.movielistapp.utils.Constants.format1;
import static com.fyznurk.movielistapp.utils.Constants.format2;
import static com.fyznurk.movielistapp.utils.Constants.imagePath;

public class MovieDetailFragment extends Fragment {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.movieImage)
    ImageView movieImage;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txtOverview)
    TextView txtOverview;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txtTitle)
    TextView txtTitle;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.txtDate)
    TextView txtDate;

    @SuppressLint("NonConstantResourceId")
    @OnClick(R.id.btnBack)
    void backToList() {
        Navigation.findNavController(requireView()).navigateUp();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        assert getArguments() != null;
        MovieDetail movieDetail = (MovieDetail) getArguments().getSerializable("data");
        Glide.with(requireContext()).load(imagePath + movieDetail.getBackdrop_path()).into(movieImage);
        txtTitle.setText(movieDetail.getTitle());
        txtOverview.setText(movieDetail.getOverview());
        if (movieDetail.getRelease_date() != null) {
            Date date = null;
            try {
                date = format1.parse(movieDetail.getRelease_date());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            assert date != null;
            txtDate.setText(format2.format(date));
        }
    }
}
