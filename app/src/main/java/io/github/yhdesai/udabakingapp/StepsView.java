package io.github.yhdesai.udabakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.DebugTextViewHelper;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

public class StepsView extends AppCompatActivity {
    private SimpleExoPlayer player;
    private SimpleExoPlayerView VideoView;
    private ImageView ThumbnailView;
    private Boolean Video;
    public String videoUrl;
    private Uri uri;


    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.d("onSaveInstance", savedInstanceState.toString());

        if (Video = true) {
            Log.d("The Player", player.toString());
            long position = player.getCurrentPosition();
            savedInstanceState.putLong("position", position);

            boolean isPlayWhenReady = player.getPlayWhenReady();
            savedInstanceState.putBoolean("playerState", isPlayWhenReady);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_view);
        Video = false;



        /*  String ids = getIntent().getStringExtra("id");*/
        String description = getIntent().getStringExtra("description");
        String shortDescription = getIntent().getStringExtra("shortDescription");

        videoUrl = getIntent().getStringExtra("videoUrl");
        String thumbnailUrl = getIntent().getStringExtra("thumbnailUrl");


        /*    TextView idView = findViewById(R.id.stepsId);*/

        TextView descriptionView = findViewById(R.id.stepsDescription);
      /*  TextView videoView = findViewById(R.id.videoVideoView);
        TextView thumbnailView = findViewById(R.id.videoThumbnailView);*/


        VideoView = findViewById(R.id.videoView);
        VideoView.setVisibility(View.GONE);

        ThumbnailView = findViewById(R.id.thumbnailView);
        ThumbnailView.setVisibility(View.GONE);


        if (!thumbnailUrl.equals("")) {
            if (!thumbnailUrl.contains(".mp4")) {
                Log.d("Thumbnail", "image detected");
                ThumbnailView.setVisibility(View.VISIBLE);
                Picasso.get().load(thumbnailUrl).into(ThumbnailView);

            }if (thumbnailUrl.contains(".mp4")){
                Log.d("Thumbnail", "thumbnail is not an image");

            }

        } else {
            Log.d("Thumbnail", "thumbnail empty");
            ThumbnailView.setVisibility(View.GONE);
        }


        if (!videoUrl.equals("")) {
            Log.d("Thattt Video", videoUrl);
            uri = Uri.parse(videoUrl);
            VideoView.setVisibility(View.VISIBLE);
            Video = true;
            initPlayer();
        } else {
            Video = false;
            VideoView.setVisibility(View.GONE);

        }
        /*  idView.setText(ids);*/
        descriptionView.setText(description);
        setTitle(shortDescription);

        if (savedInstanceState != null) {
            long position = savedInstanceState.getLong("position");
            initPlayer();
            if (position != C.TIME_UNSET) player.seekTo(position);


            boolean isPlayWhenReady = savedInstanceState.getBoolean("playerState");
            player.setPlayWhenReady(isPlayWhenReady);


        }
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
