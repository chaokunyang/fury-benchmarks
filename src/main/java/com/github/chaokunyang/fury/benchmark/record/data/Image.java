package com.github.chaokunyang.fury.benchmark.record.data;

public record Image (
  String uri,
  String title,
  int width,
  int height,
  Size size,
  Media media
) {
  public enum Size {
    SMALL,
    LARGE
  }
}
