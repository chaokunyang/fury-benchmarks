package com.github.chaokunyang.fury.benchmark;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.chaokunyang.fury.benchmark.data.Image;
import com.github.chaokunyang.fury.benchmark.data.MediaContent;
import com.github.chaokunyang.fury.benchmark.data.Struct;
import com.github.chaokunyang.fury.benchmark.data.Media;
import org.apache.fury.Fury;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Benchmark;

import java.io.IOException;

public class JacksonBenchmark extends BenchmarkBase {
  private static final ObjectMapper mapper = new ObjectMapper(); // create once, reuse
  private static byte[] jacksonMediaContentBytes;
  private static byte[] jacksonStructBytes;
  static {
    try {
      jacksonMediaContentBytes = mapper.writeValueAsBytes(mediaContent);
      jacksonStructBytes = mapper.writeValueAsBytes(struct);
      System.out.println("jacksonMediaContentBytes size " + jacksonMediaContentBytes.length);
      System.out.println("jacksonStructBytes size " + jacksonStructBytes.length);
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
  public Object jacksonSerializeStruct() throws Exception {
    return mapper.writeValueAsBytes(struct);
  }

  @Benchmark
  public Object jacksonDeserializeStruct() throws Exception {
    return mapper.readValue(jacksonStructBytes, Struct.class);
  }

  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      String commandLine =
        "com.*JacksonBenchmark.* -f 3 -wi 5 -i 5 -t 1 -w 2s -r 2s -rf csv";
      System.out.println(commandLine);
      args = commandLine.split(" ");
    }
    Main.main(args);
  }
}
