package com.github.chaokunyang.fury.benchmark;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.chaokunyang.fury.benchmark.data.Image;
import com.github.chaokunyang.fury.benchmark.data.MediaContent;
import com.github.chaokunyang.fury.benchmark.data.Struct;
import com.github.chaokunyang.fury.benchmark.data.Media;
import io.fury.Fury;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Benchmark;

import java.io.IOException;

public class JacksonBenchmark {
  private static final ObjectMapper mapper = new ObjectMapper(); // create once, reuse
  private static final Fury fury = Fury.builder().build(); // create once, reuse
  private static MediaContent mediaContent = new MediaContent();
  private static Struct struct = Struct.create(2);
  private static byte[] jacksonMediaContentBytes;
  private static byte[] furyMediaContentBytes;
  private static byte[] jacksonStructBytes;
  private static byte[] furyStructBytes;

  static {
    fury.register(Struct.class);
    fury.register(Image.class);
    fury.register(Media.class);
    fury.register(Image.Size.class);
    fury.register(MediaContent.class);
    fury.register(Media.Player.class);
    try {
      jacksonMediaContentBytes = mapper.writeValueAsBytes(mediaContent);
      jacksonStructBytes = mapper.writeValueAsBytes(struct);
      furyMediaContentBytes = fury.serializeJavaObject(mediaContent);
      furyStructBytes = fury.serializeJavaObject(struct);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Benchmark
  public Object jacksonSerializeMediaContent() throws Exception {
    return mapper.writeValueAsBytes(mediaContent);
  }

  @Benchmark
  public Object jacksonDeserializeMediaContent() throws Exception {
    return mapper.readValue(jacksonMediaContentBytes, MediaContent.class);
  }

  @Benchmark
  public Object furySerializeMediaContent() throws Exception {
    return fury.serializeJavaObject(mediaContent);
  }

  @Benchmark
  public Object furyDeserializeMediaContent() throws Exception {
    return fury.deserializeJavaObject(furyMediaContentBytes, MediaContent.class);
  }

  @Benchmark
  public Object jacksonSerializeStruct() throws Exception {
    return mapper.writeValueAsBytes(struct);
  }

  @Benchmark
  public Object jacksonDeserializeStruct() throws Exception {
    return mapper.readValue(jacksonStructBytes, Struct.class);
  }

  @Benchmark
  public Object furySerializeStruct() throws Exception {
    return fury.serializeJavaObject(struct);
  }

  @Benchmark
  public Object furyDeserializeStruct() throws Exception {
    return fury.deserializeJavaObject(furyStructBytes, Struct.class);
  }

  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      String commandLine =
        "io.*JacksonBenchmark.* -f 3 -wi 5 -i 5 -t 1 -w 2s -r 2s -rf csv";
      System.out.println(commandLine);
      args = commandLine.split(" ");
    }
    Main.main(args);
  }
}
