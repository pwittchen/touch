package com.github.pwittchen.touch.library;

public class TouchEvent {
  private final float x;
  private final float y;
  private final TouchType type;

  public TouchEvent(final float x, final float y, final TouchType type) {
    this.x = x;
    this.y = y;
    this.type = type;
  }

  public float x() {
    return x;
  }

  public float y() {
    return y;
  }

  public TouchType type() {
    return type;
  }

  @Override public String toString() {
    return "TouchEvent{x=" + x + ", y=" + y + ", type=" + type + '}';
  }
}
