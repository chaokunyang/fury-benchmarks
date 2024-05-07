package com.github.chaokunyang.fury.benchmark.activejdata;

import io.activej.serializer.annotations.Deserialize;
import io.activej.serializer.annotations.Serialize;
import org.apache.fury.reflect.ReflectionUtils;

import java.util.Objects;

public class Struct {
  @Serialize
  public int f1;
  @Serialize
  public int f2;
  @Serialize
  public long f3;
  @Serialize
  public long f4;
  @Serialize
  public float f5;
  @Serialize
  public float f6;
  @Serialize
  public double f7;
  @Serialize
  public double f8;
  @Serialize
  public int f9;
  @Serialize
  public int f10;
  @Serialize
  public long f11;
  @Serialize
  public long f12;
  @Serialize
  public float f13;
  @Serialize
  public float f14;
  @Serialize
  public double f15;
  @Serialize
  public double f16;

  public Struct() {

  }
  
  public Struct(@Deserialize("f1") int f1, @Deserialize("f2") int f2,
                @Deserialize("f3") long f3, @Deserialize("f4") long f4,
                @Deserialize("f5") float f5, @Deserialize("f6") float f6,
                @Deserialize("f7") double f7, @Deserialize("f8") double f8,
                @Deserialize("f9") int f9, @Deserialize("f10") int f10,
                @Deserialize("f11") long f11, @Deserialize("f12") long f12,
                @Deserialize("f13") float f13, @Deserialize("f14") float f14,
                @Deserialize("f15") double f15, @Deserialize("f16") double f16) {
    this.f1 = f1;
    this.f2 = f2;
    this.f3 = f3;
    this.f4 = f4;
    this.f5 = f5;
    this.f6 = f6;
    this.f7 = f7;
    this.f8 = f8;
    this.f9 = f9;
    this.f10 = f10;
    this.f11 = f11;
    this.f12 = f12;
    this.f13 = f13;
    this.f14 = f14;
    this.f15 = f15;
    this.f16 = f16;
  }

  public static Struct create() {
    Struct struct = new Struct();
    com.github.chaokunyang.fury.benchmark.record.data.Struct struct1
      = com.github.chaokunyang.fury.benchmark.record.data.Struct.create();
    ReflectionUtils.unsafeCopy(struct1, struct);
    return struct;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Struct struct = (Struct) o;
    return f1 == struct.f1 && f2 == struct.f2 && f3 == struct.f3 && f4 == struct.f4 && Float.compare(struct.f5, f5) == 0 && Float.compare(struct.f6, f6) == 0 && Double.compare(struct.f7, f7) == 0 && Double.compare(struct.f8, f8) == 0 && f9 == struct.f9 && f10 == struct.f10 && f11 == struct.f11 && f12 == struct.f12 && Float.compare(struct.f13, f13) == 0 && Float.compare(struct.f14, f14) == 0 && Double.compare(struct.f15, f15) == 0 && Double.compare(struct.f16, f16) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(f1, f2, f3, f4, f5, f6, f7, f8, f9, f10, f11, f12, f13, f14, f15, f16);
  }

  @Override
  public String toString() {
    return "Struct{" +
      "f1=" + f1 +
      ", f2=" + f2 +
      ", f3=" + f3 +
      ", f4=" + f4 +
      ", f5=" + f5 +
      ", f6=" + f6 +
      ", f7=" + f7 +
      ", f8=" + f8 +
      ", f9=" + f9 +
      ", f10=" + f10 +
      ", f11=" + f11 +
      ", f12=" + f12 +
      ", f13=" + f13 +
      ", f14=" + f14 +
      ", f15=" + f15 +
      ", f16=" + f16 +
      '}';
  }

  public static void main(String[] args) {
    System.out.println(create());
  }
}
