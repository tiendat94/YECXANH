package com.dat.yecxanh.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LongDate extends Date {
    public LongDate(Date date) {
        super(date.getTime());
    }

    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS'Z'");

    public static class Serializer implements JsonDeserializer<LongDate>, JsonSerializer<LongDate> {

        @Override
        public LongDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            String str = json.getAsString();
            if (str == null) {
                return null;
            }
            try {
                Date d = df.parse(str);
                return new LongDate(d);

            } catch (ParseException e) {
                e.printStackTrace();
                throw new JsonParseException("Invalid date " + str, e);
            }
        }

        @Override
        public JsonElement serialize(LongDate src, Type typeOfSrc, JsonSerializationContext context) {
            if (src == null) {
                return null;
            }
            String str = df.format(src);
            JsonPrimitive json = new JsonPrimitive(str);
            return json;
        }
    }
}
