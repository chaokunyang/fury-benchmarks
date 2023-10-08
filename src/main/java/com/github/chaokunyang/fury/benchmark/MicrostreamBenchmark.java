package com.github.chaokunyang.fury.benchmark;

import com.github.chaokunyang.fury.benchmark.data.Image;
import com.github.chaokunyang.fury.benchmark.data.Media;
import com.github.chaokunyang.fury.benchmark.data.MediaContent;
import com.github.chaokunyang.fury.benchmark.data.Struct;
import io.fury.Fury;
import one.microstream.persistence.binary.util.Serializer;
import one.microstream.persistence.binary.util.SerializerFoundation;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Benchmark;

import java.io.IOException;

public class MicrostreamBenchmark extends BenchmarkBase {
  static final SerializerFoundation<?> mediaContentFoundation = SerializerFoundation.New()
    .registerEntityTypes(MediaContent.class);
  static final Serializer<byte[]> mediaContentSerializer = Serializer.Bytes(mediaContentFoundation);
  static final Serializer<byte[]> structSerializer = Serializer.Bytes(SerializerFoundation.New()
    .registerEntityTypes(Struct.class));
  static final byte [] mediaContentBytes;
  static final byte [] structBytes;
  static {
    mediaContentBytes = mediaContentSerializer.serialize(mediaContent);
    structBytes = structSerializer.serialize(struct);
    System.out.println("microstream mediaContentBytes size " + mediaContentBytes.length);
    System.out.println("microstream structBytes size " + structBytes.length);
  }

  @Benchmark
  public Object microstreamSerializeMediaContent() throws Exception {
    return mediaContentSerializer.serialize(mediaContent);
  }
  @Benchmark
  public Object microstreamDeserializeMediaContent() throws Exception {
    return mediaContentSerializer.deserialize(mediaContentBytes);
  }

  @Benchmark
  public Object microstreamSerializeStruct() throws Exception {
    return structSerializer.serialize(struct);
  }
  @Benchmark
  public Object microstreamDeserializeStruct() throws Exception {
    return structSerializer.deserialize(mediaContentBytes);
  }

  public static void main(String[] args) throws IOException {
    // System.out.println(fury.deserializeJavaObject(fury.serializeJavaObject(mediaContent), MediaContent.class));
    // byte[] data = mediaContentSerializer.serialize(mediaContent);
    // MediaContent restored = mediaContentSerializer.deserialize(data);
    // System.out.println(restored);
    // System.out.println(restored.equals(mediaContent));
    if (args.length == 0) {
      String commandLine =
        "com.*MicrostreamBenchmark.* -f 1 -wi 3 -i 3 -t 1 -w 2s -r 2s -rf csv";
      System.out.println(commandLine);
      args = commandLine.split(" ");
    }
    Main.main(args);
  }
}
