package com.github.chaokunyang.fury.benchmark;

import com.github.chaokunyang.fury.benchmark.activejdata.Image;
import com.github.chaokunyang.fury.benchmark.activejdata.Media;
import com.github.chaokunyang.fury.benchmark.activejdata.MediaContent;
import com.github.chaokunyang.fury.benchmark.activejdata.Struct;
import io.activej.serializer.BinaryOutput;
import io.activej.serializer.BinarySerializer;
import io.activej.serializer.SerializerBuilder;
import org.apache.fury.Fury;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Benchmark;

import java.io.IOException;
import java.util.Arrays;

public class ActivejBenchmark {
  private static MediaContent mediaContent = new MediaContent().populate(false);
  private static Struct struct = Struct.create();

  static BinarySerializer<MediaContent> mediaContentSerializer = SerializerBuilder.create().build(MediaContent.class);
  static BinarySerializer<Struct> structSerializer = SerializerBuilder.create().build(Struct.class);

  // serialize a company
  static byte[] mediaContentBytes;
  static byte[] structBytes;
  private static byte[] buffer = new byte[1024];
  
  static {
    BinaryOutput output = new BinaryOutput(buffer);
    mediaContentSerializer.encode(output, mediaContent);
    mediaContentBytes = Arrays.copyOf(buffer, output.pos());
    assert mediaContentSerializer.decode(mediaContentBytes, 0).equals(mediaContent);
    output = new BinaryOutput(buffer);
    structSerializer.encode(output, struct);
    assert structSerializer.decode(structBytes, 0).equals(struct);
    System.out.println("Activej mediaContentBytes size " + mediaContentBytes.length);
    System.out.println("Activej structBytes size " + structBytes.length);
  }

  @Benchmark
  public Object activejSerializeMediaContent() throws Exception {
    BinaryOutput output = new BinaryOutput(buffer);
    mediaContentSerializer.encode(output, mediaContent);
    return Arrays.copyOf(buffer, output.pos());
  }

  @Benchmark
  public Object activejDeserializeMediaContent() throws Exception {
    return mediaContentSerializer.decode(mediaContentBytes, 0);
  }

  @Benchmark
  public Object activejSerializeStruct() throws Exception {
    BinaryOutput output = new BinaryOutput(buffer);
    structSerializer.encode(output, struct);
    return Arrays.copyOf(buffer, output.pos());
  }

  @Benchmark
  public Object activejDeserializeStruct() throws Exception {
    return structSerializer.decode(structBytes, 0);
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
        "com.*ActivejBenchmark.* -f 1 -wi 1 -i 1 -t 1 -w 2s -r 2s -rf csv";
      System.out.println(commandLine);
      args = commandLine.split(" ");
    }
    Main.main(args);
  }
}
