package com.github.chaokunyang.fury.benchmark;

import com.github.chaokunyang.fury.benchmark.data.Image;
import com.github.chaokunyang.fury.benchmark.data.Media;
import com.github.chaokunyang.fury.benchmark.data.MediaContent;
import com.github.chaokunyang.fury.benchmark.data.Struct;
import io.fury.Fury;
import org.openjdk.jmh.annotations.Benchmark;

public class BenchmarkBase {
  public static final Fury fury = Fury.builder().build(); // create once, reuse
  public static MediaContent mediaContent = new MediaContent().populate(false);
  public static Struct struct = Struct.create();
  public static byte[] furyMediaContentBytes;
  public static byte[] furyStructBytes;

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

}
