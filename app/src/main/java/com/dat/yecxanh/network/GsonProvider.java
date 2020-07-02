package com.dat.yecxanh.network;

import com.dat.yecxanh.model.LongDate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonProvider {
    private static Gson shared = null;

    public static Gson getInstance() {
        if (shared == null) {
            shared = new GsonBuilder()
                    .registerTypeAdapter(LongDate.class, new LongDate.Serializer())
                    .create();
        }
        return shared;
    }
}
