package org.fixtrading.timpani.codec;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonCodec {

  private class DateDeserializer implements JsonDeserializer<LocalDate> {
    public LocalDate deserialize(JsonElement json, Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
      return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
    }
  }

  private class DateSerializer implements JsonSerializer<LocalDate> {
    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc,
        JsonSerializationContext context) {
      return new JsonPrimitive(src.toString());
    }
  }
  
  private class DateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
    public LocalDateTime deserialize(JsonElement json, Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
      return LocalDateTime.parse(json.getAsJsonPrimitive().getAsString());
    }
  }

  private class DateTimeSerializer implements JsonSerializer<LocalDateTime> {
    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc,
        JsonSerializationContext context) {
      return new JsonPrimitive(src.toString());
    }
  }

  private class TimeDeserializer implements JsonDeserializer<LocalTime> {
    public LocalTime deserialize(JsonElement json, Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
      return LocalTime.parse(json.getAsJsonPrimitive().getAsString());
    }
  }

  private class TimeSerializer implements JsonSerializer<LocalTime> {
    @Override
    public JsonElement serialize(LocalTime src, Type typeOfSrc,
        JsonSerializationContext context) {
      return new JsonPrimitive(src.toString());
    }
  }
  
  private class ZonedDateTimeDeserializer implements JsonDeserializer<ZonedDateTime> {
    public ZonedDateTime deserialize(JsonElement json, Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
      return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString());
    }
  }

  private class ZonedDateTimeSerializer implements JsonSerializer<ZonedDateTime> {
    @Override
    public JsonElement serialize(ZonedDateTime src, Type typeOfSrc,
        JsonSerializationContext context) {
      return new JsonPrimitive(src.toString());
    }
  }
  
  private class ZonedTimeDeserializer implements JsonDeserializer<OffsetTime> {
    public OffsetTime deserialize(JsonElement json, Type typeOfT,
        JsonDeserializationContext context) throws JsonParseException {
      return OffsetTime.parse(json.getAsJsonPrimitive().getAsString());
    }
  }

  private class ZonedTimeSerializer implements JsonSerializer<OffsetTime> {
    @Override
    public JsonElement serialize(OffsetTime src, Type typeOfSrc,
        JsonSerializationContext context) {
      return new JsonPrimitive(src.toString());
    }
  }
  private final Gson jsonFormatter;

  public JsonCodec() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeDeserializer());
    builder.registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeSerializer());
    builder.registerTypeAdapter(LocalDateTime.class, new DateTimeDeserializer());
    builder.registerTypeAdapter(LocalDateTime.class, new DateTimeSerializer());
    builder.registerTypeAdapter(LocalDate.class, new DateDeserializer());
    builder.registerTypeAdapter(LocalDate.class, new DateSerializer());
    builder.registerTypeAdapter(LocalTime.class, new TimeDeserializer());
    builder.registerTypeAdapter(LocalTime.class, new TimeSerializer());
    builder.registerTypeAdapter(OffsetTime.class, new ZonedTimeDeserializer());
    builder.registerTypeAdapter(OffsetTime.class, new ZonedTimeSerializer());
    jsonFormatter = builder.create();
  }

  public <T> T decode(String message, Class<T> classOfT) {
    return jsonFormatter.fromJson(message, classOfT);
  }

  public String encode(Object obj) {
    return jsonFormatter.toJson(obj);
  }
  

}
