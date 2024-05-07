package com.github.chaokunyang.fury.benchmark;

import com.github.chaokunyang.fury.benchmark.record.data.Image;
import com.github.chaokunyang.fury.benchmark.record.data.Media;
import com.github.chaokunyang.fury.benchmark.record.data.MediaContent;
import com.github.chaokunyang.fury.benchmark.record.data.Struct;
import org.apache.fury.Fury;
import org.eclipse.serializer.Serializer;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Benchmark;

import java.io.IOException;

public class EclipseBenchmark {
  private static MediaContent mediaContent = MediaContent.create();
  private static Struct struct = Struct.create();
  
  // create a serializer which handles byte arrays
  static Serializer<byte[]> serializer = Serializer.Bytes();

  // serialize a company
  static byte[] mediaContentBytes;
  static byte[] structBytes;
  
  static {
    mediaContentBytes = serializer.serialize(mediaContent);
    structBytes = serializer.serialize(struct);
    assert serializer.deserialize(mediaContentBytes).equals(mediaContent);
    assert serializer.deserialize(structBytes).equals(struct);
    System.out.println("eclipse mediaContentBytes size " + mediaContentBytes.length);
    System.out.println("eclipse structBytes size " + structBytes.length);
  }

  @Benchmark
  public Object eclipseSerializeMediaContent() throws Exception {
    return serializer.serialize(mediaContent);
  }

  @Benchmark
  public Object eclipseDeserializeMediaContent() throws Exception {
    return serializer.deserialize(mediaContentBytes);
  }

  @Benchmark
  public Object eclipseSerializeStruct() throws Exception {
    return serializer.serialize(struct);
  }

  @Benchmark
  public Object eclipseDeserializeStruct() throws Exception {
    return serializer.deserialize(structBytes);
  }

  private static Fury fury = Fury.builder().build(); // create once, reuse
  private static byte[] furyMediaContentBytes;
  private static byte[] furyStructBytes;

  static {
    fury.register(Struct.class);
    fury.register(Image.class);
    fury.register(Media.class);
    fury.register(Image.Size.class);
    fury.register(MediaContent.class);
    fury.register(Media.Player.class);
    furyMediaContentBytes = fury.serializeJavaObject(mediaContent);
    furyStructBytes = fury.serializeJavaObject(struct);
    System.out.println("furyMediaContentBytes size " + furyMediaContentBytes.length);
    System.out.println("furyStructBytes size " + furyStructBytes.length);
  }

  @Benchmark
  public Object furySerializeStruct() throws Exception {
    return fury.serializeJavaObject(struct);
  }

  @Benchmark
  public Object furyDeserializeStruct() throws Exception {
    return fury.deserializeJavaObject(furyStructBytes, Struct.class);
  }

  @Benchmark
  public Object furySerializeMediaContent() throws Exception {
    return fury.serializeJavaObject(mediaContent);
  }

  @Benchmark
  public Object furyDeserializeMediaContent() throws Exception {
    return fury.deserializeJavaObject(furyMediaContentBytes, MediaContent.class);
  }

  public static void main(String[] args) throws IOException {
    if (args.length == 0) {
      String commandLine =
        "com.*EclipseBenchmark.* -f 3 -wi 3 -i 3 -t 1 -w 2s -r 2s -rf csv";
      System.out.println(commandLine);
      args = commandLine.split(" ");
    }
    Main.main(args);
  }
}
