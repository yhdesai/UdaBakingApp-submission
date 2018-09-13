package io.github.yhdesai.udabakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

public class StepsView extends AppCompatActivity {
    private SimpleExoPlayer player;
    private SimpleExoPlayerView VideoView;
    private SimpleExoPlayerView ThumbnailView;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        long position = player.getCurrentPosition();
        savedInstanceState.putLong("position", position);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_view);

        if (savedInstanceState != null) {
            long position = savedInstanceState.getLong("position");
            if (position != C.TIME_UNSET) player.seekTo(position);

        }

        /*  String ids = getIntent().getStringExtra("id");*/
        String description = getIntent().getStringExtra("description");
        String shortDescription = getIntent().getStringExtra("shortDescription");
        String videoUrl = getIntent().getStringExtra("videoUrl");
        String thumbnailUrl = getIntent().getStringExtra("thumbnailUrl");


        /*    TextView idView = findViewById(R.id.stepsId);*/

        TextView descriptionView = findViewById(R.id.stepsDescription);
      /*  TextView videoView = findViewById(R.id.videoVideoView);
        TextView thumbnailView = findViewById(R.id.videoThumbnailView);*/


        VideoView = findViewById(R.id.videoView);
        VideoView.setVisibility(View.GONE);

        ThumbnailView = findViewById(R.id.thumbnailView);
        ThumbnailView.setVisibility(View.GONE);


        if (thumbnailUrl != null) {
            Log.d("Thattt Thumbnail", thumbnailUrl);
            Uri uri = Uri.parse(thumbnailUrl);
            ThumbnailView.setVisibility(View.VISIBLE);
            initPlayer(uri);
        } else {
            ThumbnailView.setVisibility(View.GONE);
        }
        if (videoUrl != null) {
            Log.d("Thattt Video", videoUrl);
            Uri uri = Uri.parse(videoUrl);
            VideoView.setVisibility(View.VISIBLE);
            initPlayer(uri);
        } else {
            /* videoView.setVisibility(View.GONE);*/
            VideoView.setVisibility(View.GONE);

        }
        /*  idView.setText(ids);*/
        descriptionView.setText(description);
        /* videoView.setText(videoUrl);
        thumbnailView.setText(thumbnailUrl);*/
        setTitle(shortDescription);


    }

    private void initPlayer(Uri uri) {
        // 1. Create a default TrackSelector
        Handler mainHandler = new Handler();
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector =
                new DefaultTrackSelector(videoTrackSelectionFactory);
        // 2. Create the player
        player =
                ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        // Bind the player to the view.
        VideoView.setPlayer(player);
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "bakingApp"), (TransferListener<? super DataSource>) bandwidthMeter);
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
        // Prepare the player with the source.
        player.prepare(videoSource);

        // Add a listener to receive events from the player.
        //     player.addListener(eventListener);

    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
            player = null;
        }
    }

    private void releasePlayer() {
        if (player != null) {
            player.release();
            player = null;

        }

    }

}
