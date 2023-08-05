package io.chaokunyang.benchmark.data;

import java.util.Objects;
import java.util.Random;

public class Struct {
  public int f1;
  public int f2;
  public long f3;
  public long f4;
  public float f5;
  public float f6;
  public double f7;
  public double f8;
  public int f9;
  public int f10;
  public long f11;
  public long f12;
  public float f13;
  public float f14;
  public double f15;
  public double f16;

  public static Struct create(int seed) {
    Random random = new Random(seed);
    Struct struct = new Struct();
    struct.f1 = random.nextInt();
    struct.f2 = random.nextInt();
    struct.f3 = random.nextInt();
    struct.f4 = random.nextInt();
    struct.f5 = random.nextInt();
    struct.f6 = random.nextInt();
    struct.f7 = random.nextInt();
    struct.f8 = random.nextInt();
    struct.f9 = random.nextInt();
    struct.f10 = random.nextInt();
    struct.f11 = random.nextInt();
    struct.f12 = random.nextInt();
    struct.f13 = random.nextInt();
    struct.f14 = random.nextInt();
    struct.f15 = random.nextInt();
    struct.f16 = random.nextInt();
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
}
