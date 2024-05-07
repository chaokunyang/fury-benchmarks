
package com.github.chaokunyang.fury.benchmark.record.data;

import java.util.List;

/**
 * @author chaokunyang
 */
public record MediaContent(
  Media media,
  List<Image> images
) {

  public static MediaContent create() {
    List<String> persons = List.of(
      "Bill Gates, Jr.",
      "Steven Jobs"
    );
    List<Image> images = List.of(
      new Image(
        "http://javaone.com/keynote_huge.jpg",
        "Javaone Keynote\u1234",
        32000,
        24000,
        Image.Size.LARGE,
        null),
      new Image(
        "http://javaone.com/keynote_large.jpg", null, 1024, 768, Image.Size.LARGE, null),
      new Image("http://javaone.com/keynote_small.jpg", null, 320, 240, Image.Size.SMALL, null));
    Media media = new Media(
      "http://javaone.com/keynote.ogg", null,
      641, 481, "video/theora\u1234", 18000001, 58982401, 0, false,
      persons, Media.Player.FLASH,
      "Copyright (c) 2009, Scooby Dooby Doo");
    return new MediaContent(media, images);
  }
}
