package com.dat.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

public class Common {

    public static Picasso getCustomPicasso(Context context) {
        Picasso.Builder builder = new Picasso.Builder(context);
        // set 12% of available app memory for image cache
        builder.memoryCache(new LruCache(getBytesForMemCache(12, context)));
        Picasso.RequestTransformer requestTransformer = new Picasso.RequestTransformer() {
            @Override
            public Request transformRequest(Request request) {
                Log.d("image request", request.toString());
                return request;
            }
        };

        builder.requestTransformer(requestTransformer);

        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                Log.d("image load error", uri.getPath());
            }
        });

        return builder.build();
    }

    public static int getBytesForMemCache(int percent, Context context) {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);

        double availableMemory = memoryInfo.availMem;
        return (int) (percent * availableMemory / 100);
    }
}
