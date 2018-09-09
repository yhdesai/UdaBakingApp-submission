package io.github.yhdesai.udabakingapp.utils;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class GetHTTPResponse {
    public String getResponseFromHttpVideo(URL url) throws IOException {
















        HttpURLConnection aurlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream ainputStream = aurlConnection.getInputStream();
            Scanner ascanner = new Scanner(ainputStream);
            ascanner.useDelimiter("\\A");

            boolean hasInput = ascanner.hasNext();
            if (hasInput) {
                return ascanner.next();
            } else {
                return null;
            }
        } finally {
            aurlConnection.disconnect();
        }

    }

    public static URL parseUrl(String urlInput) {

        Uri uri = Uri.parse(urlInput)
                .buildUpon()
                .build();
        Log.d("url", uri.toString());
        URL url = null;
        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            Log.e("MovieUrlUtils", "Problems create url", e);
        }

        return url;
    }
}

