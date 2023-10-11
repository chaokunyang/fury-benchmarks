package com.github.chaokunyang.fury.benchmark.record.data;

import java.util.Random;

public record Struct(
  int f1,
  int f2,
  long f3,
  long f4,
  float f5,
  float f6,
  double f7,
  double f8,
  int f9,
  int f10,
  long f11,
  long f12,
  float f13,
  float f14,
  double f15,
  double f16
) {

  public static Struct create() {
    Struct struct = new Struct(
      10, 2, 3, 34, 400, 5, 50, 5788, 6, 67, 7, 78, 8, 88, 98, 90
    );
    return struct;
  }

}
