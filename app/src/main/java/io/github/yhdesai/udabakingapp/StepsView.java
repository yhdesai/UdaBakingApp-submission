package io.github.yhdesai.udabakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

public class StepsView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_view);

        /*  String ids = getIntent().getStringExtra("id");*/
        String description = getIntent().getStringExtra("description");
        String shortDescription = getIntent().getStringExtra("shortDescription");
        String videoUrl = getIntent().getStringExtra("videoUrl");
        String thumbnailUrl = getIntent().getStringExtra("thumbnailUrl");


        /*    TextView idView = findViewById(R.id.stepsId);*/

        TextView descriptionView = findViewById(R.id.stepsDescription);
      /*  TextView videoView = findViewById(R.id.videoVideoView);
        TextView thumbnailView = findViewById(R.id.videoThumbnailView);*/

        SimpleExoPlayerView VideoView = findViewById(R.id.videoView);


        VideoView.setVisibility(View.GONE);


        if (thumbnailUrl != null) {
            Log.d("Thattt Thumbnail", thumbnailUrl);
            Uri uri = Uri.parse(thumbnailUrl);
            VideoView.setVisibility(View.VISIBLE);

            // 1. Create a default TrackSelector
            Handler mainHandler = new Handler();
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

// 2. Create the player
            SimpleExoPlayer player =
                    ExoPlayerFactory.newSimpleInstance(this, trackSelector);

            // Bind the player to the view.
            VideoView.setPlayer(player);


// Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                    Util.getUserAgent(this, "yourApplicationName"), (TransferListener<? super DataSource>) bandwidthMeter);
// This is the MediaSource representing the media to be played.
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(uri);
// Prepare the player with the source.
            player.prepare(videoSource);

            // Add a listener to receive events from the player.
            //     player.addListener(eventListener);



            /*  videoView.setText(videoUrl);*/
        } else {
            /* videoView.setVisibility(View.GONE);*/
            VideoView.setVisibility(View.GONE);

        }

        if (videoUrl != null) {
            Log.d("Thattt Video", videoUrl);
            Uri uri = Uri.parse(videoUrl);
            VideoView.setVisibility(View.VISIBLE);

            // 1. Create a default TrackSelector
            Handler mainHandler = new Handler();
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

// 2. Create the player
            SimpleExoPlayer player =
                    ExoPlayerFactory.newSimpleInstance(this, trackSelector);

            // Bind the player to the view.
            VideoView.setPlayer(player);


// Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                    Util.getUserAgent(this, "yourApplicationName"), (TransferListener<? super DataSource>) bandwidthMeter);
// This is the MediaSource representing the media to be played.
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(uri);
// Prepare the player with the source.
            player.prepare(videoSource);

            // Add a listener to receive events from the player.
            //     player.addListener(eventListener);


            Log.d("videourl", videoUrl);
            /*  videoView.setText(videoUrl);*/
        } else {
            /* videoView.setVisibility(View.GONE);*/
            VideoView.setVisibility(View.GONE);

        }





        /*  idView.setText(ids);*/
        descriptionView.setText(description);
    /*    videoView.setText(videoUrl);
        thumbnailView.setText(thumbnailUrl);*/
        setTitle(shortDescription);

    }
}
