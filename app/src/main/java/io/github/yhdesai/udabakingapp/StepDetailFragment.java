package io.github.yhdesai.udabakingapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.fxn.stash.Stash;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import io.github.yhdesai.udabakingapp.data.IngredientsItem;
import io.github.yhdesai.udabakingapp.data.StepsItem;


public class StepDetailFragment extends Fragment {

    private IngredientsItem[] ingredientsStepsArray;
    private IngredientsItem ingredientsSteps;
    private TextView ingredientsTextView;
    private StepsItem[] resultStepsArray;
    private StepsItem resultSteps;
    private StepsAdapter stepsAdapter;
    private String specialString;
    private SimpleExoPlayer player;
    public String videoUrl;
    private Parcelable listState;
    private SimpleExoPlayerView VideoView;
    private ImageView ThumbnailView;
    String servings;
    private Boolean Video;
    private Uri uri;
    private RecyclerView stepsRecyclerView;
    String shortDescription;
    String description;
    String id;
    String thumbnailUrl;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StepDetailFragment() {
    }

   /* private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new StepsAdapter(this, resultStepsArray, null));
    }*/

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {

            shortDescription = Stash.getString("shortDescription");
            description = Stash.getString("description");
            id = Stash.getString("id");
            videoUrl = Stash.getString("videoUrl");
            thumbnailUrl = Stash.getString("thumbnailUrl");

            Video = false;
            Log.d("shortdesc", shortDescription);

           // TextView descriptionView = getActivity().findViewById(R.id.stepsDescription);

            //     VideoView = getActivity().findViewById(R.id.videoView);
//            VideoView.setVisibility(View.GONE);

            //       ThumbnailView = getActivity().findViewById(R.id.thumbnailView);
            //   ThumbnailView.setVisibility(View.GONE);


        /*    if (!thumbnailUrl.equals("")) {
                if (!thumbnailUrl.contains(".mp4")) {
                    Log.d("Thumbnail", "image detected");
                    ThumbnailView.setVisibility(View.VISIBLE);
                    Picasso.get().load(thumbnailUrl).into(ThumbnailView);

                }
                if (thumbnailUrl.contains(".mp4")) {
                    Log.d("Thumbnail", "thumbnail is not an image");

                }

            } else {
                Log.d("Thumbnail", "thumbnail empty");
//                ThumbnailView.setVisibility(View.GONE);
            }
*/
/*
            if (!videoUrl.equals("")) {
                Log.d("Thattt Video", videoUrl);
                uri = Uri.parse(videoUrl);
                //      VideoView.setVisibility(View.VISIBLE);
                Video = true;
                initPlayer();
            } else {
                Video = false;
                //    VideoView.setVisibility(View.GONE);

            }*/
            /*  idView.setText(ids);*/
//            descriptionView.setText(description);

/*
            if (savedInstanceState != null) {
                long position = savedInstanceState.getLong("position");
                initPlayer();
                if (position != C.TIME_UNSET) player.seekTo(position);


                boolean isPlayWhenReady = savedInstanceState.getBoolean("playerState");
                player.setPlayWhenReady(isPlayWhenReady);


            }*/
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.step_detail, container, false);

        shortDescription = Stash.getString("shortDescription");
        description = Stash.getString("description");
        id = Stash.getString("id");
        videoUrl = Stash.getString("videoUrl");
        thumbnailUrl = Stash.getString("thumbnailUrl");

        Log.d("shortdesc", shortDescription);
        Video = false;


        TextView descriptionView = rootView.findViewById(R.id.stepsDescription);

        VideoView = rootView.findViewById(R.id.videoView);
        VideoView.setVisibility(View.GONE);

        ThumbnailView = rootView.findViewById(R.id.thumbnailView);
        //   ThumbnailView.setVisibility(View.GONE);


        if (!thumbnailUrl.equals("")) {
            if (!thumbnailUrl.contains(".mp4")) {
                Log.d("Thumbnail", "image detected");
                ThumbnailView.setVisibility(View.VISIBLE);
                Picasso.get().load(thumbnailUrl).into(ThumbnailView);

            }
            if (thumbnailUrl.contains(".mp4")) {
                Log.d("Thumbnail", "thumbnail is not an image");

            }

        } else {
            Log.d("Thumbnail", "thumbnail empty");
//                ThumbnailView.setVisibility(View.GONE);
        }


        if (!videoUrl.equals("")) {
            Log.d("Thattt Video", videoUrl);
            uri = Uri.parse(videoUrl);
            //      VideoView.setVisibility(View.VISIBLE);
            Video = true;
            initPlayer();
        } else {
            Video = false;
            //    VideoView.setVisibility(View.GONE);

        }
        /*  idView.setText(ids);*/
        descriptionView.setText(description);


        if (savedInstanceState != null) {
            long position = savedInstanceState.getLong("position");
            initPlayer();
            if (position != C.TIME_UNSET) player.seekTo(position);


            boolean isPlayWhenReady = savedInstanceState.getBoolean("playerState");
            player.setPlayWhenReady(isPlayWhenReady);


        }


        return rootView;
    }

    private void initPlayer() {
        // 1. Create a default TrackSelector
        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);
        // 2. Create the player
        player =
                ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector);

        // Bind the player to the view.
        VideoView.setPlayer(player);
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getActivity(),
                Util.getUserAgent(getActivity(), "bakingApp"), (TransferListener<? super DataSource>) bandwidthMeter);
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
        // Prepare the player with the source.
        player.prepare(videoSource);

        // Add a listener to receive events from the player.
        //     player.addListener(eventListener);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoUrl != null)
            uri = Uri.parse(videoUrl);
        //  initPlayer(uri);
    }

    @Override
    public void onPause() {
        super.onPause();
        //  releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT <= 23) {
            releasePlayer();

        }
    }

    private void releasePlayer() {
        if (player != null) {

            player.stop();
            player.release();
            player = null;

        }
    }


}
