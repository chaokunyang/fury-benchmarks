package com.github.chaokunyang.fury.benchmark;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.ImmutableCollectionsSerializers;
import com.github.chaokunyang.fury.benchmark.record.data.Image;
import com.github.chaokunyang.fury.benchmark.record.data.Media;
import com.github.chaokunyang.fury.benchmark.record.data.MediaContent;
import com.github.chaokunyang.fury.benchmark.record.data.Struct;
import io.fury.Fury;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Benchmark;

import java.io.IOException;

public class KryoBenchmark {
  private static Kryo kryo = new Kryo();
  private static MediaContent mediaContent = MediaContent.create();
  private static Struct struct = Struct.create();
  private static byte[] kryoMediaContentBytes;
  private static byte[] kryoStructBytes;
  private static Output output = new Output(1024, -1);
  private static Input input = new Input();

  static {
    // kryo.setRegistrationRequired(false);
    ImmutableCollectionsSerializers.registerSerializers(kryo);
    kryo.register(Struct.class);
    kryo.register(Image.class);
    kryo.register(Media.class);
    kryo.register(Image.Size.class);
    kryo.register(MediaContent.class);
    kryo.register(Media.Player.class);
    try {
      kryo.writeObject(output, mediaContent);
      kryoMediaContentBytes = output.toBytes();
      output.reset();
      kryo.writeObject(output, struct);
      kryoStructBytes = output.toBytes();
      System.out.println("kryoMediaContentBytes size " + kryoMediaContentBytes.length);
      System.out.println("kryoStructBytes size " + kryoStructBytes.length);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Benchmark
  public Object kryoSerializeMediaContent() throws Exception {
    output.reset();
    kryo.writeObject(output, mediaContent);
    return output.toBytes();
  }

  @Benchmark
  public Object kryoDeserializeMediaContent() throws Exception {
    input.setBuffer(kryoMediaContentBytes);
    return kryo.readObject(input, MediaContent.class);
  }

  @Benchmark
  public Object kryoSerializeStruct() throws Exception {
    output.reset();
    kryo.writeObject(output, struct);
    return output.toBytes();
  }

  @Benchmark
  public Object kryoDeserializeStruct() throws Exception {
    input.setBuffer(kryoStructBytes);
    return kryo.readObject(input, Struct.class);
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
        "com.*KryoBenchmark.* -f 3 -wi 3 -i 3 -t 1 -w 2s -r 2s -rf csv";
      System.out.println(commandLine);
      args = commandLine.split(" ");
    }
    Main.main(args);
  }
}
