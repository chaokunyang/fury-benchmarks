package com.github.chaokunyang.fury.benchmark;

import com.github.chaokunyang.fury.benchmark.data.MediaContent;
import com.github.chaokunyang.fury.benchmark.data.Struct;
import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Jsonb;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Benchmark;

import java.io.IOException;

public class AvajeBenchmark extends BenchmarkBase {
  static Jsonb jsonb = Jsonb.builder().build();
  static JsonType<MediaContent> mediaContentJsonType;
  static JsonType<Struct> structJsonType;
  static final byte [] mediaContentBytes;
  static final byte [] structBytes;
  static {
    mediaContentJsonType = jsonb.type(MediaContent.class);
    structJsonType = jsonb.type(Struct.class);
    mediaContentBytes = mediaContentJsonType.toJsonBytes(mediaContent);
    structBytes = structJsonType.toJsonBytes(struct);
    System.out.println("avaje mediaContentBytes size " + mediaContentBytes.length);
    System.out.println("avaje structBytes size " + structBytes.length);
  }

  @Benchmark
  public Object avajeSerializeMediaContent() throws Exception {
    return mediaContentJsonType.toJsonBytes(mediaContent);
  }
  @Benchmark
  public Object avajeDeserializeMediaContent() throws Exception {
    return mediaContentJsonType.fromJson(mediaContentBytes);
  }

  @Benchmark
  public Object avajeSerializeStruct() throws Exception {
    return structJsonType.toJsonBytes(struct);
  }
  @Benchmark
  public Object avajeDeserializeStruct() throws Exception {
    return structJsonType.fromJson(mediaContentBytes);
  }

  public static void main(String[] args) throws IOException {
    // System.out.println(fury.deserializeJavaObject(fury.serializeJavaObject(mediaContent), MediaContent.class));
    // byte[] data = mediaContentSerializer.serialize(mediaContent);
    // MediaContent restored = mediaContentSerializer.deserialize(data);
    // System.out.println(restored);
    // System.out.println(restored.equals(mediaContent));
    if (args.length == 0) {
      String commandLine =
        "com.*AvajeBenchmark.* -f 1 -wi 3 -i 3 -t 1 -w 2s -r 2s -rf csv";
      System.out.println(commandLine);
      args = commandLine.split(" ");
    }
    Main.main(args);
  }
}
